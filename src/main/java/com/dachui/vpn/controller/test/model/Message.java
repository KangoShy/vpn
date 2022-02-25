package com.dachui.vpn.controller.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/* *
 * 说明：
 * 版本：1.0
 * 创建/修改日期：2022/2/25
 * 作者：大锤
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {

    private String messageId;

    private Object messageSubj;

    private Date createTime;

}
