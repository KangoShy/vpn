package com.dachui.vpn.LoginPackage.login;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dachui.vpn.LoginPackage.domain.TokenDomain;
import com.dachui.vpn.common.Result;
import com.dachui.vpn.enums.ReturnCodeStatusEnum;
import com.dachui.vpn.model.po.UcenterMember;
import com.dachui.vpn.repository.TokenMapper;
import com.dachui.vpn.repository.UcenterMemberMapper;
import com.dachui.vpn.util.AESUtil;
import com.dachui.vpn.util.JsonUtil;
import com.dachui.vpn.util.MD5Utils;
import com.dachui.vpn.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: DACHUI
 * @Date: 2021/3/7 11:37
 * @Description:
 */
@Service
public class LoginService {

    private static Logger log = LoggerFactory.getLogger(LoginService.class);

    @Resource
    private UcenterMemberMapper ucenterMemberMapper;

    @Resource
    private TokenMapper tokenMapper;

    @Resource
    private HttpServletResponse response;

    /**
     * login
     *
     * @param ucenterMember
     * @return
     */
    public Result<Map<String, Object>> getZrbLyLogin(UcenterMember ucenterMember, HttpServletRequest request) throws IOException {
        try {
            LambdaQueryWrapper<UcenterMember> wrappers = Wrappers.lambdaQuery();
            wrappers.eq(UcenterMember::getUserAccount, ucenterMember.getUserAccount());
            UcenterMember userPO = ucenterMemberMapper.selectOne(wrappers);
            if (userPO == null) return Result.instance(ReturnCodeStatusEnum.ACCOUNT_ERROR);
            if (!userPO.getUserAccount().equalsIgnoreCase(ucenterMember.getUserAccount())) {
                return Result.instance(ReturnCodeStatusEnum.ACCOUT_OR_PASS_FAIL);
            }
            String encrypt = AESUtil.Decrypt(userPO.getUserPassWord());
            if (!ucenterMember.getUserPassWord().equals(encrypt)) {
                return Result.instance(ReturnCodeStatusEnum.ACCOUT_OR_PASS_FAIL);
            }
            String jwtToken = TokenUtils.getToken(userPO.getUserAccount(), userPO.getUserPassWord());
            LambdaQueryWrapper<TokenDomain> tokenWrappers = Wrappers.lambdaQuery();
            tokenWrappers.eq(TokenDomain::getUserId, userPO.getUserId());
            TokenDomain tokenDomain = tokenMapper.selectOne(tokenWrappers);
            if (tokenDomain != null) {
                if (!tokenDomain.getToken().equals(jwtToken)) {
                    tokenDomain.setToken(jwtToken);
                    tokenMapper.updateById(tokenDomain);
                    log.info("登录成功，token已更新...");
                }
            } else {
                TokenDomain tokenDomainNull = new TokenDomain();
                tokenDomainNull.setToken(jwtToken);
                tokenDomainNull.setUserId(Long.parseLong(userPO.getUserId()));
                tokenDomainNull.setBuildTime(Long.parseLong(String.valueOf(new Date().getTime())));
                tokenMapper.insert(tokenDomainNull);
            }
            Map<String, Object> infMap = new HashMap<>();
            infMap.put("loginBean", JsonUtil.toMapFromJsonStr(JSONObject.toJSONString(userPO)));
            infMap.put("token", jwtToken);
            return Result.instance(ReturnCodeStatusEnum.LOGIN_SUCCESS, infMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.instance(ReturnCodeStatusEnum.SYSTEM_ERROR);
    }
}
