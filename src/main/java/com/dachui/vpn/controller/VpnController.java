package com.dachui.vpn.controller;

import com.dachui.vpn.common.Result;
import com.dachui.vpn.service.VpnService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

}
