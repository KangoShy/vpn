package com.dachui.vpn.login.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.Id;

/**
 * @author: DACHUI
 * @Date: 2021/3/7 11:22
 * @Description:
 */
@TableName(value = "user_token")
public class TokenDomain {
    @Id
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "build_time")
    private Long buildTime;
    @TableField(value = "token")
    private String token;

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getBuildTime() {
        return buildTime;
    }

    public String getToken() {
        return token;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setBuildTime(Long buildTime) {
        this.buildTime = buildTime;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
