package com.dachui.vpn.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dachui.vpn.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

// 用户消息表
@Getter
@Setter
@TableName("user_message")
public class UserMessagePO extends BaseEntity {

    @TableId(value = "message_id", type = IdType.AUTO)
    private Long messageId;

    @TableField("user_id")
    private Long userId;

    @TableField("content")
    private String content;

    @TableField("already_read")
    private boolean alreadyRead;

    @TableField("message_type")
    private String messageType;
}
