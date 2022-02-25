package com.dachui.vpn.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dachui.vpn.common.RabbitConstants;
import com.dachui.vpn.common.Result;
import com.dachui.vpn.config.AroundException;
import com.dachui.vpn.config.MessageConstants;
import com.dachui.vpn.config.SpringProperties;
import com.dachui.vpn.enums.RedisConstantsKeyEnum;
import com.dachui.vpn.enums.OrderStatusEnum;
import com.dachui.vpn.enums.ReturnCodeStatusEnum;
import com.dachui.vpn.model.BaseEntity;
import com.dachui.vpn.model.po.*;
import com.dachui.vpn.model.result.ServiceResponseStatus;
import com.dachui.vpn.model.vo.ComboResultVO;
import com.dachui.vpn.model.vo.PayRequestVO;
import com.dachui.vpn.model.vo.PlaceOrderRequestVO;
import com.dachui.vpn.repository.*;
import com.dachui.vpn.util.RedisUtil;
import com.dachui.vpn.util.StringUtil;
import io.netty.util.HashedWheelTimer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@SuppressWarnings("all")
public class VpnService {

    @Resource
    private UserKnowMapper userKnowMapper;
    @Resource
    private VpnComboMapper vpnComboMapper;
    @Resource
    private OrderRecordsMapper orderRecordsMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UserMessageMapper userMessageMapper;
    @Resource
    private GrantOrderRecordMapper grantOrderRecordMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private SpringProperties springProperties;

    public Object getUserKnow() {
        Object o = redisUtil.get(RedisConstantsKeyEnum.USER_KNOW_KEY.getKey());
        List<String> collList = new ArrayList<>();
        if (o == null) {
            List<UserKnowPO> resultList = Optional.ofNullable(
                    userKnowMapper.selectList(
                            Wrappers.<UserKnowPO>lambdaQuery().orderByAsc(UserKnowPO::getId))).orElse(Collections.EMPTY_LIST);
            List<String> collect = resultList.stream().map(UserKnowPO::getText).collect(Collectors.toList());
            Integer i = 0;
            for (String t : collect) {
                i++;
                collList.add(i + "、".concat(t));
            }
            redisUtil.set(RedisConstantsKeyEnum.USER_KNOW_KEY.getKey(), collList, RedisConstantsKeyEnum.USER_KNOW_KEY.getCacheTime());
            return collList;
        }
        collList = ((List<String>) o);
        log.info(RedisConstantsKeyEnum.USER_KNOW_KEY.getDesc().concat("{}"), "---> 从缓存获取");
        return collList;
    }

    public Object getComboList() {
        List<ComboResultVO> collectList = new ArrayList<>();
        Object o = redisUtil.get(RedisConstantsKeyEnum.COM_BO_KEY.getKey());
        if (o == null) {
            List<VpnComboPO> list = Optional.ofNullable(
                    vpnComboMapper.selectList(
                            Wrappers.<VpnComboPO>lambdaQuery().eq(VpnComboPO::isDeleted, Boolean.FALSE).orderByDesc(VpnComboPO::getUpdateTime))).orElse(Collections.EMPTY_LIST);
            for (VpnComboPO po : list) {
                BigDecimal comboPrice = po.getComboPrice();
                ComboResultVO vo = new ComboResultVO();
                vo.setComboName(po.getComboName());
                vo.setComboPrice("CNY ".concat(comboPrice.toPlainString()).concat(".00"));
                vo.setId(po.getId());
                collectList.add(vo);
            }
            redisUtil.set(RedisConstantsKeyEnum.COM_BO_KEY.getKey(), collectList, RedisConstantsKeyEnum.COM_BO_KEY.getCacheTime());
            return collectList;
        }
        collectList = ((List<ComboResultVO>) o);
        log.info(RedisConstantsKeyEnum.COM_BO_KEY.getDesc().concat("{}"), "---> 从缓存获取");
        return collectList;
    }

    public VpnComboPO selectComboById(String id) {
        VpnComboPO po = vpnComboMapper.selectOne(
                Wrappers.<VpnComboPO>lambdaQuery().eq(VpnComboPO::getId, id).eq(VpnComboPO::isDeleted, Boolean.FALSE));
        return po;
    }

    /* *
     * 功能：新增订单记录，同步消息队列
     * 作者：大锤
     * 创建/修改日期：2022/2/18
     */
    @Transactional(rollbackFor = Exception.class)
    public OrderRecordsPO placeTheOrder(PlaceOrderRequestVO requestVO) {
        long start = System.currentTimeMillis();
        // 检查套餐是否合法
        VpnComboPO vpnComboPO = checkOrderExists(requestVO.getComboId());
        String orderNo = AlipayHandler.getOrderIdByTime();
        String orderKey = RedisConstantsKeyEnum.ORDER_CACHE_KEY.getKey().concat(orderNo);
        // 订单记录
        OrderRecordsPO orderRecordsPO = joinOrderRecord2DB(vpnComboPO, orderNo, requestVO);
        // 执行插入
        orderRecordsMapper.insert(orderRecordsPO);
        // 注意：缓存时间必须和超时时间保持严格一致
        Date failureTime = orderRecordsPO.getFailureTime();
        Date theNowTime = new Date();
        long cacheSeconds = (failureTime.getTime() - theNowTime.getTime()) / 1000;
        // 将订单缓存在redis中。规定时间不付款则订单失效
        joinRedisCache(orderKey, orderNo, cacheSeconds);
        // 同步至消息队列
        //joinRabbitQueue(orderNo);
        log.info(" >>>下单成功,总耗时 = {}ms, 订单信息 = {} ", (System.currentTimeMillis() - start),
                JSONObject.toJSONString(orderRecordsPO));
        return orderRecordsPO;
    }

    private void joinRedisCache(String orderKey, String orderNo, long cacheSeconds) {
        redisUtil.set(
                orderKey, // 订单key
                orderNo,  // 订单号
                cacheSeconds); // 超时时间
    }

    private OrderRecordsPO joinOrderRecord2DB(VpnComboPO vpnComboPO, String orderNo, PlaceOrderRequestVO requestVO) {
        OrderRecordsPO orderRecordsPO = OrderRecordsPO
                .builder()
                .comboId(vpnComboPO.getId())
                .userId(1L)
                .comboName(vpnComboPO.getComboName())
                .orderId(orderNo)
                .orderPrice(requestVO.getPrice())
                .price(requestVO.getPrice())
                .comboType(requestVO.getType())
                .orderStatus(OrderStatusEnum.PAY_NO.getCode())
                .build();
        // 设置过期时间
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.MINUTE, springProperties.getTimeout());
        orderRecordsPO.setFailureTime(instance.getTime());
        orderRecordsPO.setCreateTime(new Date());
        orderRecordsPO.setDeleted(Boolean.FALSE);
        return orderRecordsPO;
    }

    private void joinRabbitQueue(String orderNo) {
        rabbitTemplate.convertAndSend(
                RabbitConstants.ORDER_DIRECT_EX_CHANGE,
                RabbitConstants.ORDER_DIRECT_ROUTING,
                orderNo);
    }

    private VpnComboPO checkOrderExists(Long comboId) {
        if (comboId == null) throw new AroundException(ReturnCodeStatusEnum.SYSTEM_ERROR, "套餐不存在！");
        VpnComboPO vpnComboPO = selectComboById(comboId.toString());
        if (vpnComboPO == null) {
            throw new AroundException(ReturnCodeStatusEnum.SYSTEM_ERROR, "套餐不存在！");
        }
        return vpnComboPO;
    }

    private synchronized void syncOrderStatus(String orderNo) {
        log.info("--------> 开始处理订单：{}", orderNo);
        LambdaQueryWrapper<OrderRecordsPO> wrapper =
                Wrappers.<OrderRecordsPO>lambdaQuery().eq(OrderRecordsPO::getOrderId, orderNo).eq(OrderRecordsPO::isDeleted, Boolean.FALSE);
        OrderRecordsPO orderPo = orderRecordsMapper.selectOne(wrapper);
        if (orderPo != null) {
            Date date = new Date();
            LambdaUpdateWrapper<OrderRecordsPO> updateWrapper = Wrappers.lambdaUpdate();
            // 无效订单/已付款订单-关闭
            if (OrderStatusEnum.PAY_TIMEOUT.getCode().equals(orderPo.getOrderStatus())
                    || OrderStatusEnum.PAY_YES.getCode().equals(orderPo.getOrderStatus())) {
                orderRecordsMapper.update(null,
                        updateWrapper.eq(OrderRecordsPO::getId, orderPo.getId())
                                .set(OrderRecordsPO::isDeleted, Boolean.TRUE).set(OrderRecordsPO::getUpdateTime, date));
                log.info("发现一条无效/已付款订单-已关闭， id = {}\n", orderPo.getId());
                return;
            }
            // 从redis获取订单数据
            Object o = redisUtil.get(RedisConstantsKeyEnum.ORDER_CACHE_KEY.getKey().concat(orderNo));
            if (o == null || StringUtil.isEmpty(o.toString())) {
                orderRecordsMapper.update(null,
                        updateWrapper.eq(OrderRecordsPO::getOrderId, orderNo)
                                .set(OrderRecordsPO::isDeleted, Boolean.TRUE).set(OrderRecordsPO::getUpdateTime, date)
                                .set(OrderRecordsPO::getOrderStatus, OrderStatusEnum.PAY_TIMEOUT.getCode())
                );
                log.info("发现一条未支付已超时订单-已关闭， orderNo = {}\n", orderNo);
            }
        } else {
            log.info("该订单已经正常关闭，orderNo = {}\n", orderNo);
        }
    }

    public boolean closeOrder(String orderId) {
        int update = orderRecordsMapper.update(null,
                Wrappers.<OrderRecordsPO>lambdaUpdate().eq(OrderRecordsPO::getOrderId, orderId)
                        .set(OrderRecordsPO::isDeleted, Boolean.TRUE)
                        .set(OrderRecordsPO::getOrderStatus, OrderStatusEnum.PAY_OUT.getCode())
                        .set(OrderRecordsPO::getUpdateTime, new Date()));
        // 清空缓存订单数据
        if (update == 1)
            redisUtil.del(RedisConstantsKeyEnum.ORDER_CACHE_KEY.getKey().concat(orderId));
        return update == 1;
    }

    /**
     * TODO 1、检查订单状态 2、付款 3、对账 4、发货
     */

    // 检查订单状态-付款
    public Result<Object> pay(PayRequestVO payRequestVO) {
        System.err.println("orderId = " + payRequestVO.getOrderId());
        OrderRecordsPO orderRecordsPO = orderRecordsMapper.selectOne(
                Wrappers.<OrderRecordsPO>lambdaQuery().eq(OrderRecordsPO::getOrderId, payRequestVO.getOrderId()));
        if (orderRecordsPO == null || orderRecordsPO.isDeleted() || !OrderStatusEnum.PAY_NO.getCode().equals(orderRecordsPO.getOrderStatus())) {
            return Result.fail("该订单已失效，请重新下单");
        }
        // TODO 付款
        //return Result.success(AlipayHandler.TradeWapPayRequest(orderRecordsPO));
        orderRecordsMapper.update(null,
                Wrappers.<OrderRecordsPO>lambdaUpdate().eq(OrderRecordsPO::getOrderId, orderRecordsPO.getOrderId())
                        .set(OrderRecordsPO::getUpdateTime, new Date())
                        .set(OrderRecordsPO::getOrderStatus, OrderStatusEnum.PAY_YES.getCode())
                        .set(OrderRecordsPO::getPay, "支付宝")
                        .set(OrderRecordsPO::isDeleted, Boolean.TRUE));
        //new Thread(() -> this.grantOrder(payRequestVO.getOrderId())).start();
        return Result.success("付款完成");
    }

    private void grantOrder(String orderId) {
        log.info(">>> 开始派发订单！");
        long start = System.currentTimeMillis();
        OrderRecordsPO orderRecordsPO;
        // 查询订单
        FIND_ORDER:
        {
            orderRecordsPO = orderRecordsMapper.selectOne(
                    Wrappers.<OrderRecordsPO>lambdaQuery().eq(OrderRecordsPO::isDeleted, Boolean.TRUE)
                            .eq(OrderRecordsPO::getOrderId, orderId)
            );
        }
        if (orderRecordsPO == null) return;
        GrantOrderRecordPO grantOrderRecordPO = new GrantOrderRecordPO();
        // 新建vpn资源的时候 会绑定到特定套餐下 这里查询该套餐下的最新有效的vpn服务器进行派发
        //grantOrderRecordPO.setVpnCommonId(orderRecordsPO.getComboId());
        grantOrderRecordPO.setCreateTime(new Date());
        grantOrderRecordPO.setSuccess(Boolean.FALSE);
        grantOrderRecordMapper.insert(grantOrderRecordPO);
        NEW_MESSAGE:
        {
            UserMessagePO userMessagePO = new UserMessagePO();
            userMessagePO.setDeleted(Boolean.FALSE);
            userMessagePO.setAlreadyRead(Boolean.FALSE);
            userMessagePO.setContent(MessageConstants.orderMessage);
            userMessagePO.setMessageType(MessageConstants.MESSAGE);
            userMessagePO.setUserId(1L);
            userMessagePO.setCreateTime(new Date());
            userMessageMapper.insert(userMessagePO);
        }
    }

    /* *
     * 功能：获取订单状态
     * 作者：大锤
     * 创建/修改日期：2022/2/21
     */
    public Object getOrderStatus(String orderId) {
        // 订单状态 0、等待付款中 1、已付款 2、已超时 3、已付款待发货 4、待发货
        OrderRecordsPO orderRecordsPO = orderRecordsMapper.selectOne(
                Wrappers.<OrderRecordsPO>lambdaQuery()
                        .eq(StringUtil.isNotEmpty(orderId), OrderRecordsPO::getOrderId, orderId));
        if (orderRecordsPO == null) {
            return new String("抱歉，该订单发生错误，请前往首页重新下单！");
        }
        if (orderRecordsPO.isDeleted()) {
            return new String("订单已删除！");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", orderRecordsPO.getOrderStatus());
        return map;
    }

    public Result<List<OrderRecordsPO>> getMyOrderList(String key, Integer pageSize) {
        // 模拟userId
        Long userId = 1L;
        if (pageSize >= 100) {
            pageSize = 100;
        }
        LambdaQueryWrapper<OrderRecordsPO> pages = Wrappers.<OrderRecordsPO>lambdaQuery()
                .eq(OrderRecordsPO::getUserId, userId)
                .and(StringUtils.isNotEmpty(key), i -> i.like(OrderRecordsPO::getOrderId, key)
                        .or().like(OrderRecordsPO::getComboName, key)
                )
                .last(" limit " + pageSize)
                .orderByDesc(OrderRecordsPO::getCreateTime);
        List<OrderRecordsPO> orderRecordsPOS = orderRecordsMapper.selectList(pages);
        if (!orderRecordsPOS.isEmpty()) {
            for (OrderRecordsPO orderPO : orderRecordsPOS) {
                orderPO.setOrderStatus(OrderStatusEnum.getMessageByCode(orderPO.getOrderStatus()));
            }
        }
        return Result.success(orderRecordsPOS);
    }

    public OrderRecordsPO getOrderById(String orderId) {
        OrderRecordsPO orderRecordsPO = orderRecordsMapper.selectOne(
                Wrappers.<OrderRecordsPO>lambdaQuery().eq(OrderRecordsPO::getOrderId, orderId));
        return orderRecordsPO == null ? new OrderRecordsPO() : orderRecordsPO;
    }

    public UserMessagePO getMessage() {
        Long userId = 1L;
        UserMessagePO userMessagePO = null;
        List<UserMessagePO> userMessagePOS = userMessageMapper.selectList(
                Wrappers.<UserMessagePO>lambdaQuery().eq(BaseEntity::isDeleted, Boolean.FALSE)
                        .eq(UserMessagePO::isAlreadyRead, Boolean.FALSE).eq(UserMessagePO::getUserId, userId)
                        .orderByDesc(UserMessagePO::getCreateTime)
        );
        if (!CollectionUtils.isEmpty(userMessagePOS)) {
            userMessagePO = userMessagePOS.get(0);
        }
        return userMessagePO;
    }

    public void messageRead(Long messageId) {
        userMessageMapper.update(
                null, Wrappers.<UserMessagePO>lambdaUpdate()
                        .eq(UserMessagePO::getMessageId, messageId)
                        .set(UserMessagePO::getCreateTime, new Date())
                        .set(UserMessagePO::isAlreadyRead, Boolean.TRUE));
    }

    public Date getFailureTime(String orderId) {
        return getOrderById(orderId).getFailureTime();
    }
}
