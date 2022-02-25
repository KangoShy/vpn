package com.dachui.vpn.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dachui.vpn.common.GlobalConstants;
import com.dachui.vpn.exception.UserCenterException;
import com.dachui.vpn.model.UserInfo;
import com.dachui.vpn.model.po.UserCenterPO;
import com.dachui.vpn.model.result.ServiceResponseStatus;
import com.dachui.vpn.repository.UserCenterMapper;
import com.dachui.vpn.util.AESUtil;
import com.dachui.vpn.util.JwtUtil;
import com.dachui.vpn.util.RedisUtil;
import com.dachui.vpn.util.VpnUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: DACHUI
 * @Date: 2021/3/7 11:37
 * @Description:
 */
@Slf4j
@Service
public class LoginService {

    @Resource
    private UserCenterMapper userCenterMapper;
    @Resource
    private RedisUtil redisUtil;

    public String Login(String username, String password) throws Exception {
        // 基本信息校验
        if (VpnUtil.hasNullParam(username, password)) {
            throw new UserCenterException("用户名或密码为空！", ServiceResponseStatus.SYSTEM_ERROR);
        }
        UserCenterPO userCenterPO =
                userCenterMapper.selectOne(
                        Wrappers.<UserCenterPO>lambdaQuery().eq(UserCenterPO::getUsername, username));
        if (userCenterPO == null || userCenterPO.isDeleted()) {
            throw new UserCenterException("用户不存在！", ServiceResponseStatus.SYSTEM_ERROR);
        }
        if (!userCenterPO.isEnabled()) {
            throw new UserCenterException("该账号已被禁用！", ServiceResponseStatus.SYSTEM_ERROR);
        }
        String decrypt = AESUtil.Decrypt(userCenterPO.getPassword());
        if (!password.equals(decrypt)) {
            throw new UserCenterException("账号或密码错误！", ServiceResponseStatus.SYSTEM_ERROR);
        }
        UserInfo tokenInfo = new UserInfo();
        BeanUtils.copyProperties(userCenterPO, tokenInfo);
        String jwtToken = JwtUtil.createJwtToken(tokenInfo);
        // 生成token
        redisUtil.set(
                GlobalConstants.UnConfig_Constants.TOKEN_REDIS_CACHE.concat(username),
                jwtToken,
                GlobalConstants.Config_Constants.TOKEN_TIMEOUT * 60);
        return jwtToken;
    }
}
