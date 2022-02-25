package com.dachui.vpn.exception;

import com.dachui.vpn.model.result.ServiceResponseStatus;

/* *
 * 说明：
 * 版本：1.0
 * 创建/修改日期：2022/2/21
 * 作者：大锤
 */
public class BaseServiceException extends BaseException {
    private static final long serialVersionUID = 4743551875405504801L;
    private final ServiceResponseStatus serviceResponseStatus;

    public BaseServiceException() {
        this.serviceResponseStatus = ServiceResponseStatus.SYSTEM_ERROR;
    }

    public BaseServiceException(ServiceResponseStatus serviceResponseStatus) {
        super(serviceResponseStatus.getMsg());
        this.serviceResponseStatus = serviceResponseStatus;
    }

    public BaseServiceException(String message, ServiceResponseStatus serviceResponseStatus) {
        super(message);
        this.serviceResponseStatus = serviceResponseStatus;
    }

    public BaseServiceException(Throwable cause, ServiceResponseStatus serviceResponseStatus) {
        super(cause);
        this.serviceResponseStatus = serviceResponseStatus;
    }

    public BaseServiceException(String message, Throwable cause, ServiceResponseStatus serviceResponseStatus) {
        super(message, cause);
        this.serviceResponseStatus = serviceResponseStatus;
    }

    public BaseServiceException(ExceptionData data, ServiceResponseStatus serviceResponseStatus) {
        super(data);
        this.serviceResponseStatus = serviceResponseStatus;
    }

    public BaseServiceException(String message, ExceptionData data, ServiceResponseStatus serviceResponseStatus) {
        super(message, data);
        this.serviceResponseStatus = serviceResponseStatus;
    }

    public BaseServiceException(Throwable cause, ExceptionData data, ServiceResponseStatus serviceResponseStatus) {
        super(cause, data);
        this.serviceResponseStatus = serviceResponseStatus;
    }

    public BaseServiceException(String message, Throwable cause, ExceptionData data, ServiceResponseStatus serviceResponseStatus) {
        super(message, cause, data);
        this.serviceResponseStatus = serviceResponseStatus;
    }

    public ServiceResponseStatus getServiceResponseStatus() {
        return this.serviceResponseStatus;
    }
}
