package com.dachui.vpn.controller;

import com.dachui.vpn.model.po.OrderRecordsPO;
import com.dachui.vpn.model.po.VpnComboPO;
import com.dachui.vpn.model.result.Result;
import com.dachui.vpn.model.vo.PlaceOrderRequestVO;
import com.dachui.vpn.service.VpnService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/path")
public class PathController {

    @Resource
    private VpnService vpnService;

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

    /* 下单 */
    @RequestMapping("/placeTheOrder")
    public String placeTheOrder(@RequestBody PlaceOrderRequestVO requestVO, Model model) {
        OrderRecordsPO orderRecordsPO = vpnService.placeTheOrder(requestVO);
        model.addAttribute("orderRecords", orderRecordsPO);
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
}