//package com.dachui.vpn.task;
//
//import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.dachui.vpn.config.MessageConstants;
//import com.dachui.vpn.enums.OrderStatusEnum;
//import com.dachui.vpn.model.BaseEntity;
//import com.dachui.vpn.model.po.GrantOrderRecordPO;
//import com.dachui.vpn.model.po.OrderRecordsPO;
//import com.dachui.vpn.model.po.UserMessagePO;
//import com.dachui.vpn.repository.GrantOrderRecordMapper;
//import com.dachui.vpn.repository.OrderRecordsMapper;
//import com.dachui.vpn.repository.UserMessageMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//
///**
// * @Author: Zhouruibin
// * @Date: Created in 15:40 2021/10/14
// * @Description:派发订单定时器
// */
//@Slf4j
//@Component
//@SuppressWarnings("all")
//public class GrantServerTask {
//
//    // 封装线程池
//    @Resource(name = "statisticsPool")
//    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
//
//    @Resource
//    private OrderRecordsMapper orderRecordsMapper;
//
//    @Resource
//    private GrantOrderRecordMapper grantOrderRecordMapper;
//
//    @Resource
//    private UserMessageMapper userMessageMapper;
//
//    @Scheduled(cron = "*/1 * * * * ?")
//    public void SyncOrderInfo() {
//        // 多线程执行器
//        CompletableFuture.allOf(CompletableFuture.runAsync(this::grantOrder, threadPoolTaskExecutor)).join();
//    }
//
//    // 派发已下订单的服务
//    private void grantOrder() {
//        List<OrderRecordsPO> orderRecordsPOS = orderRecordsMapper.selectList(
//                Wrappers.<OrderRecordsPO>lambdaQuery().eq(BaseEntity::isDeleted, Boolean.TRUE)
//                        .eq(OrderRecordsPO::getOrderStatus, OrderStatusEnum.PAY_YES.getCode())
//        );
//        if (CollectionUtils.isEmpty(orderRecordsPOS)) {
//            return;
//        }
//        for (OrderRecordsPO order : orderRecordsPOS) {
//            // 新增派发记录
//            GrantOrderRecordPO grantOrderRecordPO = new GrantOrderRecordPO();
//            ADD_GRANT_RECORDS: {
//                grantOrderRecordPO.setSuccess(Boolean.FALSE);
//                grantOrderRecordPO.setVpnCommonId(order.getComboId());
//                grantOrderRecordPO.setCreateTime(new Date());
//                grantOrderRecordMapper.insert(grantOrderRecordPO);
//            }
//            // 派发订单
//            UserMessagePO userMessagePO = new UserMessagePO();
//            THEN_GRANT:{
//                userMessagePO.setContent(MessageConstants.orderMessage);
//                userMessagePO.setMessageType(MessageConstants.MESSAGE);
//                userMessagePO.setRead(Boolean.FALSE);
//                userMessagePO.setUserId(1L);// TODO
//                userMessagePO.setCreateTime(new Date());
//                userMessagePO.setDeleted(Boolean.FALSE);
//                userMessageMapper.insert(userMessagePO);
//            }
//        }
//    }
//}
