package com.dachui.vpn.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@TableName("vpn_combo")
public class VpnComboPO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("combo_name")
    private String comboName;

    @TableField("combo_describe")
    private String comboDescribe;


    @TableField("combo_unit")
    private String comboUnit;

    @TableField("combo_price")
    private BigDecimal comboPrice;

    @TableField("combo_price_quarter")
    private BigDecimal comboPriceQuarter;

    @TableField("combo_price_half_year")
    private BigDecimal comboPriceHalfYear;

    @TableField("combo_price_year")
    private BigDecimal comboPriceYear;

    @TableField("vpn_ip")
    private String vpnIp;

    @TableField("vpn_password")
    private String vpnPassword;

    @TableField("vpn_config")
    private String vpnConfig;

    @TableField("deleted")
    private boolean deleted;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;
}
