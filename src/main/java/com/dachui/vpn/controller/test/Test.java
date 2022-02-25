package com.dachui.vpn.controller.test;

import com.alibaba.fastjson.JSONObject;
import com.dachui.vpn.util.HttpUtil;
import com.dachui.vpn.util.JsonUtil;
import com.dachui.vpn.util.StringUtil;
import lombok.Data;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/* *
 * 功能：
 * 版本：1.0
 * 创建/修改日期：2021/11/17
 * 说明：
 * 作者：root0day
 */
public class Test {

    public static void main(String[] args) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("1");
        arrayList.add("222xxx");
        String s = arrayList.toString();
        System.err.println(s);

        new ReentrantLock();



        JSONObject json = new JSONObject();


    }



    /*public static void request(String mobile) {
        int count = 0;
        for (int i = 0; i < 100; i++) {
            String url = "http://c.huayeee.com/online/common/getVerifyCode?phone=" + mobile;
            String s = HttpUtil.doGet(url);
            if (StringUtil.isNotEmpty(s)) {
                RES res = JsonUtil.toBeanFromStr(s, RES.class);
                assert res != null;
                if ("40017".equals(res.getResult().toString())) {
                    System.err.println(res.getMessage());
                    break;
                }else{
                    count++;
                    System.err.println("=======" + res.getMessage());
                }
            }
        }
        System.err.println("共发送" + count + "条！");
    }

    @Data
    static
    class RES{
        private String data;
        private Integer result;
        private String message;
    }

  public static void main(String[] args) {
      request("15769618635");
  }*/
}
