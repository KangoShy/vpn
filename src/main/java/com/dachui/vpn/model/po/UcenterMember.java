package com.dachui.vpn.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

/**
 * @author: DACHUI
 * @Date: 2021/3/7 11:06
 * @Description:
 */
@Getter
@Setter
@TableName(value = "user_center")
public class UcenterMember {

    @Id
    @TableId(value = "user_id",type = IdType.AUTO)
    private String userId;
    @TableField(value = "user_name")
    private String userName;
    @TableField(value = "user_account")
    private String userAccount;
    @TableField(value = "user_password")
    private String userPassWord;
    @TableField(value = "user_birthday")
    private String userBirthDay;
    @TableField(value = "user_age")
    private String userAge;
    @TableField(value = "user_type")
    private String userType;
    @TableField(value = "deleted")
    private Boolean deleted;

}
