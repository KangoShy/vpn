package com.dachui.vpn.exception;

import com.dachui.vpn.model.result.ServiceResponseStatus;
import lombok.Getter;

/* *
 * 说明：
 * 版本：1.0
 * 创建/修改日期：2022/2/21
 * 作者：大锤
 */
@Getter
public class UserCenterException extends BaseServiceException {
    private static final long serialVersionUID = 1L;

    /** 默认构造器，错误码 SYSTEM_ERROR（999999） */
    public UserCenterException() {}

    public UserCenterException(ServiceResponseStatus serviceResponseStatus) {
        super(serviceResponseStatus);
    }

    public UserCenterException(final String message, ServiceResponseStatus serviceResponseStatus) {
        super(message, serviceResponseStatus);
    }

    public UserCenterException(final Throwable cause, ServiceResponseStatus serviceResponseStatus) {
        super(cause, serviceResponseStatus);
    }

    public UserCenterException(
            final String message, final Throwable cause, ServiceResponseStatus serviceResponseStatus) {
        super(message, cause, serviceResponseStatus);
    }

    public UserCenterException(
            final ExceptionData data, ServiceResponseStatus serviceResponseStatus) {
        super(data, serviceResponseStatus);
    }

    public UserCenterException(
            final String message, final ExceptionData data, ServiceResponseStatus serviceResponseStatus) {
        super(message, data, serviceResponseStatus);
    }

    public UserCenterException(
            final Throwable cause,
            final ExceptionData data,
            ServiceResponseStatus serviceResponseStatus) {
        super(cause, data, serviceResponseStatus);
    }

    public UserCenterException(
            final String message,
            final Throwable cause,
            final ExceptionData data,
            ServiceResponseStatus serviceResponseStatus) {
        super(message, cause, data, serviceResponseStatus);
    }
}
