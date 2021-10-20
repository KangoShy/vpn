package com.dachui.vpn.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

public class AlipayConfig {
    /**
     * 应用ID号（）
     */
    public static String APP_ID = "2016102600764873";//你的应用appId
    /**
     * 你的私钥
     */
    public static String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCZ1XsgIrJgZYYFInPeG+2eJ9AUfBbbCqDbbhMRRxLZcpJRi4AByxsqr+QQ90wG7j2LNFEKgRPxI5JlZBodXyTM8W8kMx4Or3WtbeyGdCyq9E+7rBl/zkvo0DCyeqQikhb0Enk68SZHmJV25jtPqxATvBky7F/KtJ3um4f9WcJdnQqod1wvzPgf4t2DUEQWecu5NiximZHsKguGJW/OCCg8i/tXepVbKzkvCKVcK3fDr9uSDAVZ5dUeldq0EPads0/qVhn59hdyNtDY8PquutIfI0TSGk9/4zVnsOTSZ3nDNd6a3X0aGmKLdvC1uAZYNeW6kp1RqbcRUepFFJVh4zSFAgMBAAECggEAYcdHk416Q7tEDE9xxmOKDVTQ9bRJ8sA2rkKw6/J5EK26QwqgOoOWqPNTy1TkhiM4Cxsdx8dznXo2uyt9Td4DziBHvIKhQERWT/3msS92lNLlOyyQqKO/rCNoUImTekXRQSHc3JIa+rcJiaGdYW4hsemFOtDzsixLljZVobtDx6vip0X+N2RWt18QNU6m62Ar1ca2WcpjTRNlEjEz0k+RTG5lndDx0nFYr3cknvJ8JdutFyCAZLLfUHZE1Nr/RF3ViR1B0Gg6V8jA/MEMj9XzKu7lqsZjLrrpimbsgDHOraQdIWkKPE6charw1USqQ8avyAj0V6d5PF2i0sN1MSjPoQKBgQDnnSOzk+Xnd8GMl+meTVo2HnXQS0eP9pAdvbywBKAykkgMkmBFfvke3LtnAyFlrzimqDKofiaa/ovbkACouH9BLiELEpyeDNvkmeyJkfPZPLw6GeHW4N667pEPgccJdYp+mod7g8/q7DqoqoViffVeub7WzEVfRDUKV5YPYfNSIwKBgQCqB+GcuiwakLE5MgIhE/UcbSkBeki7wXIXowFq26AVWgfXSuNyTb8WbrhMhNm5Cm/phuVDdR7T16Jsah/QoLwiKWJ34mul8F+jTR11YmD8hGMa4m2U2/5IMbwJMGKYuFbkqZ7xrI6nSQ2iKUC0OL2UiAZ8hk152UNsu423L2SlNwKBgQCn4i9Oq5WqAhQ5ZKPiKw+Iyut64BYvM/milTWAINo9zeqLsk42EaNhy33LXWmH20qlAqY1m7s//eJCdE8F/TXAeT9aV1NO/OMnAKuDzFeEz5FgTAJ3Tugjd2mOY/Nx19Fbk4dA8Hp1PmYGfBPTIJoK33iFGevu8fHAXVxYKhfFIQKBgB1nu4blU5dMirm/gkrCLtO5tSvCQC3oaJjZ4fbfALJwnYaINwmflHqGr2zcVOMBgRU79Q5ThP9R/Y8OU247Pg4SZishh0htZ7zQK1YPq4JASvttSVJDMZi+sSxNp9+Nkcl95DUbiJg5tBflVoFUhsTXOXfzqhZ2koiNbxbNJXTFAoGAJhygX1sxd8QIyB2F6F2ouX6bMLDNvzlT7GHoT+fjO9lMJx+X2dBG/8EFq+9xIhDItwH/4VIxDkRGhw5OicBi7rTA1NJ5gTyGLVA2lfbLVcDjqNsK3HeV/WbU87FpHfqg9ahxlKT2PpLaC4pg+AhBosH8RPL2Q4/qKVbVyT2KaYU=";
    /**
     * 编码
     */
    public static String CHARSET = "UTF-8";
    /**
     * 支付宝公钥
     */
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmdV7ICKyYGWGBSJz3hvtnifQFHwW2wqg224TEUcS2XKSUYuAAcsbKq/kEPdMBu49izRRCoET8SOSZWQaHV8kzPFvJDMeDq91rW3shnQsqvRPu6wZf85L6NAwsnqkIpIW9BJ5OvEmR5iVduY7T6sQE7wZMuxfyrSd7puH/VnCXZ0KqHdcL8z4H+Ldg1BEFnnLuTYsYpmR7CoLhiVvzggoPIv7V3qVWys5LwilXCt3w6/bkgwFWeXVHpXatBD2nbNP6lYZ+fYXcjbQ2PD6rrrSHyNE0hpPf+M1Z7Dk0md5wzXemt19Ghpii3bwtbgGWDXlupKdUam3EVHqRRSVYeM0hQIDAQAB";
    /**
     * 支付宝网关地址
     */
    //private static String GATEWAY = "https://openapi.alipay.com/gateway.do";//正式环境

    private static String GATEWAY = "https://openapi.alipaydev.com/gateway.do";//沙箱环境
    /**
     * 成功付款回调
     */
    public static String PAY_NOTIFY = "http://localhost:8080/alipay/pay";//验签
    /**
     * 支付成功回调
     */
    public static String REFUND_NOTIFY = "http://localhost:8080/alipay/pay";//姑且没用到
    /**
     * 前台通知地址
     */
    public static String RETURN_URL = "http://localhost:8080/myOrder";//支付成功后返回哪个前端页面
    /**
     * 参数类型
     */
    public static String PARAM_TYPE = "json";
    /**
     * 成功标识
     */
    public static final String SUCCESS_REQUEST = "TRADE_SUCCESS";
    /**
     * 交易关闭回调(当该笔订单全部退款完毕,则交易关闭)
     */
    public static final String TRADE_CLOSED = "TRADE_CLOSED";
    /**
     * 支付宝开发平台中的支付宝账号（企业）
     */
    public static final String SELLER_ID = "anwhou5611@sandbox.com";

    //签名算法类型(根据生成私钥的算法,RSA2或RSA)
    public static final String SIGNTYPE = "RSA2";
    /**
     * 支付宝请求客户端入口
     */
    private volatile static AlipayClient alipayClient = null;

    /**
     * 不可实例化
     */
    private AlipayConfig() {
    }

    ;

    /**
     * 双重锁单例
     *
     * @return 支付宝请求客户端实例
     */
    public static AlipayClient getInstance() {
        if (alipayClient == null) {
            synchronized (AlipayConfig.class) {
                if (alipayClient == null) {
                    alipayClient = new DefaultAlipayClient(GATEWAY, APP_ID, APP_PRIVATE_KEY, PARAM_TYPE, CHARSET, ALIPAY_PUBLIC_KEY, SIGNTYPE);
                }
            }
        }
        return alipayClient;
    }
}
