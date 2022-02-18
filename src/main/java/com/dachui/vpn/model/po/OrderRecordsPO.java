package com.dachui.vpn.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dachui.vpn.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("order_records")
public class OrderRecordsPO extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("user_id")
    private Long userId;
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField("pay_time")
    private Date payTime;
    @TableField("price")
    private Long price;
    @TableField("failure_time")
    private Date failureTime;
}
