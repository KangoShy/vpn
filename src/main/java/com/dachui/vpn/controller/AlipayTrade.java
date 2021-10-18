package com.dachui.vpn.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import org.springframework.ui.Model;
import com.dachui.vpn.config.AlipayConfig;
import com.dachui.vpn.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 支付宝支付（手机web网页支付）
 *
 * @author qingzhi
 * @date 2020/4/2 15:12
 */
@Controller
@RequestMapping("/alipay")
public class AlipayTrade {
    private Logger logger = LoggerFactory.getLogger(AlipayTrade.class);

    /**
     * web支付下单并支付(web支付在安卓中是可以直接唤醒支付宝APP的)
     *
     * @return web支付的表单
     */
    @RequestMapping("/webPay")
    public String TradeWapPayRequest(Model model) {
        Map<String, String> sParaTemp = new HashMap<>();
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.RETURN_URL);//前台回调地址
        alipayRequest.setNotifyUrl(AlipayConfig.PAY_NOTIFY);//成功付款回调
        // 待请求参数数组
        sParaTemp.put("seller_id", AlipayConfig.SELLER_ID);//收款方账号
        sParaTemp.put("out_trade_no", getOrderIdByTime());//订单号(唯一）注意（Test）：这一单已付款，再掉起支付时会报此订单已支付。那么就得换个订单号，索性搞个生成订单号方法函数
        sParaTemp.put("total_amount", "0.01");//订单金额:0.01元，精准到分
        sParaTemp.put("subject", "商品下单");//订单标题
        // sParaTemp.put("product_code", "QUICK_WAP_PAY");//手机网页支付
        sParaTemp.put("body", "牛奶泡澡，尽显奢侈，嗷里个嗷！只需0.01");//没看到在哪显示了，搞了再说。
        alipayRequest.setBizContent(JSON.toJSONString(sParaTemp));//
        String form = "";
        try {
            form = AlipayConfig.getInstance().pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            logger.error("支付宝构造表单失败", e);
        }
        logger.debug("支付宝支付表单构造:" + form);
        model.addAttribute("form", form);
        return "redirectPay";
    }

    /**
     * 申请退款
     *
     * @param sParaTemp 退款参数
     * @return true成功, 回调中处理
     * 备注:https://doc.open.alipay.com/docs/api.htm?spm=a219a.7629065.0.0.3RjsEZ&apiId=759&docType=4
     */
    public boolean tradeRefundRequest(Map<String, ?> sParaTemp) throws AlipayApiException {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setReturnUrl(AlipayConfig.RETURN_URL);//前台回调地址
        request.setNotifyUrl(AlipayConfig.REFUND_NOTIFY);//后台回调地址
        // 待请求参数数组
        request.setBizContent(JSON.toJSONString(sParaTemp));
        AlipayTradeRefundResponse response = AlipayConfig.getInstance().execute(request);
        logger.debug("支付宝退货结果:" + response.isSuccess());
        return response.isSuccess();
    }

    /**
     * 支付宝回调验签（也可在此处理业务逻辑）
     *
     * @param request 回调请求
     * @return true成功
     * 备注:验签成功后，按照支付结果异步通知中的描述(二次验签接口,貌似称为历史接口了)
     */
    @RequestMapping("/pay")
    @ResponseBody
    public boolean verifyNotify(HttpServletRequest request) throws AlipayApiException {
        Map<String, String> paranMap = SignUtil.request2Map(request);//工具类：把request转为map
        logger.debug("支付宝回调参数:" + paranMap.toString());
        boolean isVerify = false;
        //验证
        if (AlipayConfig.SUCCESS_REQUEST.equals(paranMap.get("trade_status")) || AlipayConfig.TRADE_CLOSED.equals(paranMap.get("trade_status"))) {
            isVerify = AlipaySignature.rsaCheckV1(paranMap, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2"); //调用SDK验证签名，注意：RSA2类型，不填写会报错，验签异常错误，公钥格式不正确。
        }
        logger.debug("支付宝验签结果" + isVerify);
        return isVerify;
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
}