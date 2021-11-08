package com.dachui.vpn.controller;

import com.dachui.vpn.common.Result;
import com.dachui.vpn.model.po.OrderRecordsPO;
import com.dachui.vpn.model.vo.PayRequestVO;
import com.dachui.vpn.model.vo.PlaceOrderRequestVO;
import com.dachui.vpn.service.VpnService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vpn")
public class VpnController {

    @Resource
    private VpnService vpnService;

    /* 用户须知 */
    @GetMapping("/getUserKnow")
    public Result<?> getUserKnow() {
        return Result.success(vpnService.getUserKnow());
    }

    /* 套餐列表 */
    @GetMapping("/getComboList")
    public Result<?> getComboList() {
        return Result.success(vpnService.getComboList());
    }

    /* 下单 */
    @PostMapping("/placeTheOrder")
    @ExceptionHandler(ArithmeticException.class)
    public Result<OrderRecordsPO> placeTheOrder(@RequestBody PlaceOrderRequestVO requestVO) {
        return Result.success(vpnService.placeTheOrder(requestVO));
    }

    /* 获取订单状态 */
    @GetMapping("/getOrderStatus")
    public Result<Map<String, Object>> getOrderStatus(String orderId) {
        return vpnService.getOrderStatus(orderId);
    }

    /* 关闭订单 */
    @GetMapping("/closeOrder")
    public Result<Boolean> closeOrder(String orderId) {
        return Result.success(vpnService.closeOrder(orderId));
    }

    /* 付款 */
    @PostMapping("/pay")
    public Result<Object> pay(@RequestBody PayRequestVO payRequestVO) {
        return vpnService.pay(payRequestVO);
    }

    /* 订单列表 */
    @GetMapping("/getMyOrderList")
    public Result<List<OrderRecordsPO>> getMyOrderList(String key, Integer pageSize) {
        return vpnService.getMyOrderList(key, pageSize);
    }

    /* 获取消息 */
    @GetMapping("/getMessage")
    public Result<?> getMessage() {
        return Result.success(vpnService.getMessage());
    }

    /* 消息已读 */
    @GetMapping("/read")
    public void messageRead(Long messageId) {
        vpnService.messageRead(messageId);
    }


}
