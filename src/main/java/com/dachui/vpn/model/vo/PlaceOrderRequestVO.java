package com.dachui.vpn.model.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/** 下单实体类 */
public class PlaceOrderRequestVO {

    private Long comboId;

    private String comboName;

    private Long price; // 应付金额

}
