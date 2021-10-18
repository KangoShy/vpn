package com.dachui.vpn.controller;

import com.dachui.vpn.model.po.VpnComboPO;
import com.dachui.vpn.service.VpnService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class PathController {

    @Resource
    private VpnService vpnService;

    @RequestMapping("/")
    public String lll() {
        System.err.println("进入login");
        return "login";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        System.err.println("进入login");
        return "login";
    }

    @RequestMapping("/toIndex")
    public String toIndex() {
        return "index";
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @RequestMapping("/customerService")
    public String customerService() {
        return "customerService";
    }

    @RequestMapping("/buy")
    public String buy(String id, Model model) {
        VpnComboPO po = vpnService.selectComboById(id);
        model.addAttribute("comboPO", po);
        return "buy";
    }

    @RequestMapping("/creatOrder")
    public String creatOrder(String comboName, String comboType, String orderId, String time, String price, Model model) {
        model.addAttribute("comboName", comboName);
        model.addAttribute("comboType", comboType);
        model.addAttribute("orderId", orderId);
        model.addAttribute("time", time);
        model.addAttribute("price", price);
        return "creatOrder";
    }

    @RequestMapping("/androidCourse")
    public String androidCourse() {
        return "android";
    }

    @RequestMapping("/iosCourse")
    public String iosCourse() {
        return "IOS";
    }

    @RequestMapping("/macCourse")
    public String macCourse() {
        return "mac";
    }

    @RequestMapping("/windowsCourse")
    public String windowsCourse() {
        return "windows";
    }

    @RequestMapping("/sourceError")
    public String sourceError() {
        return "sourceError";
    }

    @RequestMapping("/myOrder")
    public String myOrder() {
        return "myOrder";
    }

    @RequestMapping("/toPay")
    public String toPay() {
        return "redirectPay";
    }

}
