package com.dachui.vpn.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dachui.vpn.common.Result;
import com.dachui.vpn.common.UserInfoUtil;
import com.dachui.vpn.model.BaseEntity;
import com.dachui.vpn.model.po.UserCenterPO;
import com.dachui.vpn.model.vo.RegVO;
import com.dachui.vpn.repository.UserCenterMapper;
import com.dachui.vpn.util.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@SuppressWarnings("all")
public class UserCenterService {

    @Resource
    private UserCenterMapper userCenterMapper;

    // 注册
    public Result<?> regAccount(RegVO vo) throws Exception {
        // 参数检查
        CHECK_PARAM:
        {
            boolean check = ManageServiceHandle.checkStringParam(
                    vo.getUserName(),
                    vo.getPassword(),
                    vo.getPassword2());
            if (check) {
                return Result.fail("请输入账号或密码！");
            }
        }
        // 账号是否存在
        ACCOUNT_EXISTS:
        {
            List<UserCenterPO> ucenterMemberList = userCenterMapper.selectList(
                    Wrappers.<UserCenterPO>lambdaQuery().eq(BaseEntity::isDeleted, Boolean.FALSE)
                            .eq(UserCenterPO::getUsername, vo.getUserName())
            );
            if (!CollectionUtils.isEmpty(ucenterMemberList)) {
                return Result.fail("账号已经存在！");
            }
            if (!vo.getPassword().equals(vo.getPassword2())) {
                return Result.fail("两次输入密码不一致!");
            }
        }
        UserCenterPO user = new UserCenterPO();
        // 注册
        ACCOUNT_REG:
        {
            String encryptPass = AESUtil.Encrypt(vo.getPassword());
            user.setUsername(vo.getUserName());
            user.setPassword(encryptPass);
            user.setUserRealName(ManageServiceHandle.makeUserName());
            user.setCreateTime(new Date());
            user.setEnabled(Boolean.TRUE);
            user.setCreator(UserInfoUtil.getUserId());
        }
        return userCenterMapper.insert(user) == 1 ? Result.success("注册成功！") : Result.fail();
    }

}
