package com.dachui.vpn.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dachui.vpn.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("vpn_common")
public class VpnCommonPO extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("vpn_config")
    private String vpn_config;
}
