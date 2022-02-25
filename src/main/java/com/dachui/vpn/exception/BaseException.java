package com.dachui.vpn.exception;

import java.util.Date;

/* *
 * 说明：
 * 版本：1.0
 * 创建/修改日期：2022/2/21
 * 作者：大锤
 */
public class BaseException extends Exception {
    private static final long serialVersionUID = 1573390505967873811L;
    private ExceptionData data;

    public BaseException() {
    }

    public BaseException(ExceptionData data) {
        this.data = data;
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(String message, ExceptionData data) {
        super(message);
        this.data = data;
    }

    public BaseException(Throwable cause, ExceptionData data) {
        super(cause);
        this.data = data;
    }

    public BaseException(String message, Throwable cause, ExceptionData data) {
        super(message, cause);
        this.data = data;
    }

    public Object getInformation(String key) {
        return this.data == null ? null : this.data.getInformation(key);
    }

    public void setInformation(String key, Object value) {
        if (this.data == null) {
            this.data = new ExceptionData();
        }

        this.data.setInformation(key, value);
    }

    public boolean isLogged() {
        return this.data != null && this.data.isLogged();
    }

    public void setLogged(boolean logged) {
        if (this.data == null) {
            this.data = new ExceptionData();
        }

        this.data.setLogged(logged);
    }

    public String getApplicationCode() {
        return this.data == null ? null : this.data.getApplicationCode();
    }

    public String getModuleCode() {
        return this.data == null ? null : this.data.getModuleCode();
    }

    public String getErrorCode() {
        return this.data == null ? null : this.data.getErrorCode();
    }

    public Date getCreationDate() {
        if (this.data == null) {
            this.data = new ExceptionData();
        }

        return this.data.getCreationDate();
    }

    public String getThreadName() {
        if (this.data == null) {
            this.data = new ExceptionData();
        }

        return this.data.getThreadName();
    }
}
