package com.dachui.vpn.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BaseEntity {

    private Date createTime;

    private Date updateTime;

    private boolean deleted;
}
