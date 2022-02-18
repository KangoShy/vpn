//package com.dachui.vpn.task;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
//import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.dachui.vpn.enums.OrderStatusEnum;
//import com.dachui.vpn.enums.RedisConstantsKeyEnum;
//import com.dachui.vpn.model.po.OrderRecordsPO;
//import com.dachui.vpn.repository.OrderRecordsMapper;
//import com.dachui.vpn.util.JsonUtil;
//import com.dachui.vpn.util.RedisUtil;
//import com.dachui.vpn.util.StringUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.stream.Collectors;
//
///**
// * @Author: Zhouruibin
// * @Date: Created in 15:40 2021/10/14
// * @Description:同步超时订单
// */
//@Slf4j
//@Component
//public class SyncOrderTask {
//
//    @Resource
//    private RedisUtil redisUtil;
//    @Resource
//    private OrderRecordsMapper orderMapper;
//    @Resource(name = "statisticsPool")
//    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
//
//    // FIXME create : 创建订单时，生成订单记录，存数据库并同步缓存，此时订单都是待付款状态
//    // FIXME around : 定时任务定时检测过期和损坏订单，并更新状态
//    // FIXME paying : 付款时判断订单是否过期、是否关闭，付款完成后，删除缓存订单数据
//
//    @Scheduled(cron = "*/1 * * * * ?")
//    public void SyncOrderInfo() {
//        // 多线程执行器
//        CompletableFuture.allOf(CompletableFuture.runAsync(this::sync, threadPoolTaskExecutor)).join();
//    }
//
//    private void sync() {
//        long start = System.currentTimeMillis();
//        int shutDown = 0;
//        int timeOuts = 0;
//        // "订单号作为key，获取待付款状态的订单号当作key去缓存查询，不存在则过期，更新订单状态"
//        LambdaQueryWrapper<OrderRecordsPO> findWrapper = Wrappers.lambdaQuery();
//        findWrapper.eq(OrderRecordsPO::isDeleted, Boolean.FALSE);
//        findWrapper.eq(OrderRecordsPO::getOrderStatus, OrderStatusEnum.PAY_NO.getCode());
//        // 套餐id为空的情况过滤
//        List<OrderRecordsPO> orderRecordsPOS =
//                orderMapper.selectList(findWrapper)
//                        .stream().filter(o -> o.getComboId() != null).collect(Collectors.toList());
//        if (CollectionUtils.isEmpty(orderRecordsPOS)) {
//            return;
//        }
//        LambdaUpdateWrapper<OrderRecordsPO> updateWrapper;
//        for (OrderRecordsPO orderPo : orderRecordsPOS) {
//            Date now = new Date();
//            updateWrapper = Wrappers.lambdaUpdate();
//            String orderNo = orderPo.getOrderId();
//            // 无效订单-关闭
//            if (StringUtil.isEmpty(orderNo)) {
//                orderMapper.update(null,
//                        updateWrapper.eq(OrderRecordsPO::getId, orderPo.getId())
//                                .set(OrderRecordsPO::isDeleted, Boolean.TRUE)
//                                .set(OrderRecordsPO::getUpdateTime, now));
//                shutDown++;
//                log.info("发现一条无效订单已关闭， id = {}", orderPo.getId());
//                continue;
//            }
//            // 从redis获取订单数据
//            Object o = redisUtil.get(RedisConstantsKeyEnum.ORDER_CACHE_KEY.getKey().concat(orderNo));
//            if (o == null || StringUtil.isEmpty(o.toString())) {
//                orderMapper.update(null, updateWrapper.eq(OrderRecordsPO::getOrderId, orderNo)
//                        .set(OrderRecordsPO::isDeleted, Boolean.TRUE)
//                        .set(OrderRecordsPO::getOrderStatus, OrderStatusEnum.PAY_TIMEOUT.getCode())
//                        .set(OrderRecordsPO::getUpdateTime, now));
//                log.info("发现一条超时订单已处理， orderNo = {}", orderNo);
//                timeOuts++;
//            }
//        }
//        if (logIf(shutDown, timeOuts)) {
//            log.info("订单同步完成，共处理无效订单 {} 条，处理超时订单 {} 条，共耗时 {} ms",
//                    shutDown,
//                    timeOuts,
//                    (System.currentTimeMillis() - start));
//        }
//    }
//
//    private boolean logIf(Integer... s) {
//        for (Integer integer : s) {
//            if (integer > 0) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
