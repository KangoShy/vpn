package com.dachui.vpn.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dachui.vpn.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

// 派发记录表
@Getter
@Setter
@TableName("grant_order_record")
public class GrantOrderRecordPO extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long grantId;

    @TableField("vpn_common_id")
    private Long vpnCommonId;

    @TableField("success")
    private boolean success;



}
