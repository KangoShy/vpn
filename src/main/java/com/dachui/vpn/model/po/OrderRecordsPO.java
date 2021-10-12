package com.dachui.vpn.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dachui.vpn.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName("order_records")
public class OrderRecordsPO extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("order_id")
    private String orderId;
    @TableField("combo_id")
    private Long comboId;
    @TableField("combo_name")
    private String comboName;
    @TableField("combo_type")
    private String comboType;
    @TableField("order_price")
    private Long orderPrice;
    @TableField("order_status")
    private String orderStatus;
    @TableField("pay")
    private String pay;
    @TableField("pay_time")
    private Date payTime;
}
