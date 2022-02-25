package com.dachui.vpn.controller;

import com.dachui.vpn.model.po.OrderRecordsPO;
import com.dachui.vpn.model.po.VpnComboPO;
import com.dachui.vpn.service.VpnService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
public class PathController {

    @Resource
    private VpnService vpnService;

    @RequestMapping("toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
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
    public String creatOrder(String orderId, Model model) {
        OrderRecordsPO orderById = vpnService.getOrderById(orderId);
        model.addAttribute("orderId", orderId);
        model.addAttribute("time", orderById.getCreateTime());
        model.addAttribute("price", orderById.getPrice());
        model.addAttribute("comboName", orderById.getComboName());
        model.addAttribute("comboType", orderById.getComboType());
        return "creatOrder";
    }

    @RequestMapping("/androidCourse")
    public String androidCourse() {
        return "android";
    }

    @RequestMapping("/reg")
    public String reg() {
        return "reg";
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
    public String toPay(String orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "redirectPay";
    }

}
