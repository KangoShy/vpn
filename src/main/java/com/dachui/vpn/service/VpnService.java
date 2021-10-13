package com.dachui.vpn.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dachui.vpn.enums.RedisConstantsKeyEnum;
import com.dachui.vpn.enums.OrderStatusEnum;
import com.dachui.vpn.model.po.OrderRecordsPO;
import com.dachui.vpn.model.vo.ComboResultVO;
import com.dachui.vpn.model.po.UserKnowPO;
import com.dachui.vpn.model.po.VpnComboPO;
import com.dachui.vpn.model.vo.PlaceOrderRequestVO;
import com.dachui.vpn.repository.OrderRecordsMapper;
import com.dachui.vpn.repository.UserKnowMapper;
import com.dachui.vpn.repository.VpnComboMapper;
import com.dachui.vpn.util.RedisUtil;
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
        if (vpnComboPO != null) {
            String replace = UUID.randomUUID().toString().replace("-", "");
            OrderRecordsPO orderRecordsPO = new OrderRecordsPO();
            orderRecordsPO.setComboId(vpnComboPO.getId());
            orderRecordsPO.setComboName(vpnComboPO.getComboName());
            orderRecordsPO.setOrderId(replace);
            orderRecordsPO.setOrderPrice(requestVO.getPrice());
            orderRecordsPO.setDeleted(Boolean.FALSE);
            orderRecordsPO.setOrderStatus(OrderStatusEnum.PAY_NO.getCode());
            orderRecordsPO.setCreateTime(new Date());
            orderRecordsMapper.insert(orderRecordsPO);
            return orderRecordsPO;
        }
        return null;
    }
}
