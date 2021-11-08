package com.dachui.vpn.controller;

import com.dachui.vpn.common.Result;
import com.dachui.vpn.model.vo.RegVO;
import com.dachui.vpn.service.UserCenterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Zhouruibin
 * @Date: Created in 10:02 2021/10/14
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserCenterController {

    @Resource
    private UserCenterService userCenterService;

    @PostMapping("/regs")
    public Result<?> regs(@RequestBody RegVO regVO) throws Exception {
        return userCenterService.regAccount(regVO);
    }
}
