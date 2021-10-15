package com.dachui.vpn.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dachui.vpn.common.Result;
import com.dachui.vpn.common.UserInfoUtil;
import com.dachui.vpn.config.AroundException;
import com.dachui.vpn.enums.RedisConstantsKeyEnum;
import com.dachui.vpn.enums.OrderStatusEnum;
import com.dachui.vpn.enums.ReturnCodeStatusEnum;
import com.dachui.vpn.model.po.OrderRecordsPO;
import com.dachui.vpn.model.vo.ComboResultVO;
import com.dachui.vpn.model.po.UserKnowPO;
import com.dachui.vpn.model.po.VpnComboPO;
import com.dachui.vpn.model.vo.PayRequestVO;
import com.dachui.vpn.model.vo.PlaceOrderRequestVO;
import com.dachui.vpn.repository.OrderRecordsMapper;
import com.dachui.vpn.repository.UserKnowMapper;
import com.dachui.vpn.repository.VpnComboMapper;
import com.dachui.vpn.util.RedisUtil;
import com.dachui.vpn.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@SuppressWarnings("unchecked")
public class VpnService {

    @Resource
    private UserKnowMapper userKnowMapper;
    @Resource
    private VpnComboMapper vpnComboMapper;
    @Resource
    private OrderRecordsMapper orderRecordsMapper;
    @Resource
    private RedisUtil redisUtil;

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
                vo.setComboPrice("￥".concat(comboPrice.toPlainString()).concat(".00 CNY"));
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

    public OrderRecordsPO placeTheOrder(PlaceOrderRequestVO requestVO) {
        Long comboId = requestVO.getComboId();
        if (comboId == null) {
            comboId = -1L;
        }
        VpnComboPO vpnComboPO = selectComboById(comboId.toString());
        if (vpnComboPO == null) {
            throw new AroundException(ReturnCodeStatusEnum.SYSTEM_ERROR,"套餐不存在！");
        }
        String orderNo = UUID.randomUUID().toString().replace("-", "");
        OrderRecordsPO orderRecordsPO = new OrderRecordsPO();
        Long userId = UserInfoUtil.getUserId();
        orderRecordsPO.setUserId(userId);
        orderRecordsPO.setComboId(vpnComboPO.getId());
        orderRecordsPO.setComboName(vpnComboPO.getComboName());
        orderRecordsPO.setOrderId(orderNo);
        orderRecordsPO.setOrderPrice(requestVO.getPrice());
        orderRecordsPO.setPrice(requestVO.getPrice());
        orderRecordsPO.setDeleted(Boolean.FALSE);
        orderRecordsPO.setOrderStatus(OrderStatusEnum.PAY_NO.getCode());
        orderRecordsPO.setCreateTime(new Date());
        orderRecordsPO.setComboType(requestVO.getType());
        orderRecordsMapper.insert(orderRecordsPO);
        // 将订单缓存在redis中
        redisUtil.set(
                // "key = order::e562258e53964d7a8d4aa59afebb075f";
                RedisConstantsKeyEnum.ORDER_CACHE_KEY.getKey().concat(orderNo),
                orderRecordsPO,
                RedisConstantsKeyEnum.ORDER_CACHE_KEY.getCacheTime()); // 失效时间
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (timer) {
                    syncOrderStatus(orderNo);
                }
            }
            // 延迟处理订单
        }, (RedisConstantsKeyEnum.getDescTime()));
        return orderRecordsPO;
    }

    private void syncOrderStatus(String orderNo) {
        log.info("--------> 开始处理订单\n");
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
                                .set(OrderRecordsPO::isDeleted, Boolean.TRUE).set(OrderRecordsPO::getUpdateTime, date));
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
                        .set(OrderRecordsPO::getUpdateTime, new Date()));
        // 清空缓存订单数据
        if (update == 1)
            redisUtil.del(RedisConstantsKeyEnum.ORDER_CACHE_KEY.getKey().concat(orderId));
        return update == 1;
    }

    public Result<Object> pay(PayRequestVO PayRequestVO) {
        OrderRecordsPO orderRecordsPO = orderRecordsMapper.selectOne(
                Wrappers.<OrderRecordsPO>lambdaQuery().eq(OrderRecordsPO::getOrderId, PayRequestVO.getOrderId()));
        if (orderRecordsPO.isDeleted()) {
            return Result.fail("该订单未及时支付已超时，请重新下单");
        }
        // TODO 支付宝付款

        // TODO 对帐

        // TODO 返回成功

        return Result.success("付款完成");
    }
}
