package com.dachui.vpn.service;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.dachui.vpn.common.Result;
import com.dachui.vpn.config.AlipayConfig;
import com.dachui.vpn.model.po.OrderRecordsPO;
import com.dachui.vpn.model.vo.PayRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 *
 * </p>
 *
 * @author root0day
 * @since 2021/10/19
 */
@Slf4j
@Service
public class AlipayHandler {

    public static String TradeWapPayRequest(OrderRecordsPO payRequestVO) {
        Map<String, String> sParaTemp = new HashMap<>();
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.RETURN_URL);//前台回调地址
        alipayRequest.setNotifyUrl(AlipayConfig.PAY_NOTIFY);//成功付款回调
        // 待请求参数数组
        sParaTemp.put("seller_id", AlipayConfig.SELLER_ID);//收款方账号
        sParaTemp.put("out_trade_no", payRequestVO.getOrderId());//订单号(唯一）注意（Test）：这一单已付款，再掉起支付时会报此订单已支付。那么就得换个订单号，索性搞个生成订单号方法函数
        sParaTemp.put("total_amount", payRequestVO.getPrice() + ".00");
        sParaTemp.put("subject", "天下数据-VPN");//订单标题
        //sParaTemp.put("product_code", "QUICK_WAP_PAY");//手机网页支付
        sParaTemp.put("body", payRequestVO.getOrderId());//没看到在哪显示了，搞了再说。
        alipayRequest.setBizContent(JSON.toJSONString(sParaTemp));//
        String form = "";
        try {
            form = AlipayConfig.getInstance().pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            log.error("支付宝构造表单失败", e);
        }
        log.debug("支付宝支付表单构造:" + form);
        return form;
    }

    /**
     * 生成订单号，高并发情况下可加入订单ID
     *
     * @return
     */
    public static String getOrderIdByTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result += random.nextInt(10);
        }
        return newDate + result;
    }



//    public static void main(String[] args) {
//        System.err.println(getOrderIdByTime());
//    }
}
