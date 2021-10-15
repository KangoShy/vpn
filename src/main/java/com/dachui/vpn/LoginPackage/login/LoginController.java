package com.dachui.vpn.LoginPackage.login;

import com.dachui.vpn.common.Result;
import com.dachui.vpn.enums.ReturnCodeStatusEnum;
import com.dachui.vpn.model.po.UcenterMember;
import com.dachui.vpn.util.StringUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author: DACHUI
 * @Date: 2021/3/7 11:34
 * @Description:
 */
@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public Result<Map<String, Object>> verificationLogin(UcenterMember ucenterMember, HttpServletRequest request) throws IOException {
        if (ucenterMember != null && !StringUtil.isBlank(ucenterMember.getUserAccount()) && !StringUtil.isBlank(ucenterMember.getUserPassWord())) {
            return loginService.getZrbLyLogin(ucenterMember, request);
        }
        return Result.instance(ReturnCodeStatusEnum.EMPTY_PARAM);
    }

}
