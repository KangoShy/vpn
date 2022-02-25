package com.dachui.vpn.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dachui.vpn.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/* *
 * 说明：
 * 版本：1.0
 * 创建/修改日期：2022/2/21
 * 作者：大锤
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_center")
public class UserCenterPO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;
    //登录用户名
    @TableField("user_name")
    private String username;
    //登录密码
    @TableField("user_password")
    private String password;
    /* 真实姓名 */
    @TableField("user_real_name")
    private String userRealName;
    /* 注册时间 */
    @TableField("user_join_time")
    private Date userJoinTime;
    /* 禁用启用 1-启用 0-禁用 */
    @TableField("enabled")
    private boolean enabled;

}
