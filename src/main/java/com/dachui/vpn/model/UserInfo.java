package com.dachui.vpn.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: Zhouruibin
 * @Date: Created in 10:15 2021/10/14
 * @Description:
 */
@Getter
@Setter
public class UserInfo {

    private Long userId;

    private String userName;

    private String userAccount;

    private Date userBirthday;

    private String userType;

    private String userAge;

    private String userHead;

}
