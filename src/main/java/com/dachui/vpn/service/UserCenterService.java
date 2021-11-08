package com.dachui.vpn.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dachui.vpn.common.Result;
import com.dachui.vpn.model.BaseEntity;
import com.dachui.vpn.model.po.UcenterMember;
import com.dachui.vpn.model.vo.RegVO;
import com.dachui.vpn.repository.UcenterMemberMapper;
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
    private UcenterMemberMapper ucenterMemberMapper;

    // 注册
    public Result<?> regAccount(RegVO vo) throws Exception {
        // 参数检查
        CHECK_PARAM:
        {
            boolean check = ManageServiceHandle.checkStringParam(
                    vo.getUserAccount(),
                    vo.getPassword(),
                    vo.getPassword2());
            if (check) {
                return Result.fail("请输入账号或密码！");
            }
        }
        // 账号是否存在
        ACCOUNT_EXISTS:
        {
            List<UcenterMember> ucenterMemberList = ucenterMemberMapper.selectList(
                    Wrappers.<UcenterMember>lambdaQuery().eq(BaseEntity::isDeleted, Boolean.FALSE)
                            .eq(UcenterMember::getUserAccount, vo.getUserAccount())
            );
            if (!CollectionUtils.isEmpty(ucenterMemberList)) {
                return Result.fail("账号已经存在！");
            }
            if (!vo.getPassword().equals(vo.getPassword2())) {
                return Result.fail("两次输入密码不一致!");
            }
        }
        UcenterMember user = new UcenterMember();
        // 注册
        ACCOUNT_REG:
        {
            String encryptPass = AESUtil.Encrypt(vo.getPassword());
            user.setUserAccount(vo.getUserAccount());
            user.setUserPassWord(encryptPass);
            user.setUserName(ManageServiceHandle.makeUserName());
            user.setCreateTime(new Date());
            user.setUserId(getNewUserId());
        }
        return ucenterMemberMapper.insert(user) == 1 ? Result.success("注册成功！") : Result.fail();
    }

    public String getNewUserId() {
        List<UcenterMember> ucenterMembers = ucenterMemberMapper.selectList(
                Wrappers.<UcenterMember>lambdaQuery().eq(BaseEntity::isDeleted, Boolean.FALSE).orderByDesc(UcenterMember::getUserId)
        );
        String resUserId = "1";
        if (!CollectionUtils.isEmpty(ucenterMembers)) {
            for (UcenterMember uc : ucenterMembers) {
                resUserId = uc.getUserId() + 1L;
                break;
            }
        }
        return resUserId;
    }
}
