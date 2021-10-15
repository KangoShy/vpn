package com.dachui.vpn.LoginPackage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: DACHUI
 * @Date: 2021/3/7 11:22
 * @Description:
 */
@Getter
@Setter
@TableName(value = "user_token")
public class TokenDomain {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "build_time")
    private Long buildTime;
    @TableField(value = "token")
    private String token;
}
