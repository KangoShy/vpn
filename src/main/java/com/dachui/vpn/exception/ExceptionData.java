package com.dachui.vpn.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* *
 * 说明：
 * 版本：1.0
 * 创建/修改日期：2022/2/21
 * 作者：大锤
 */
public class ExceptionData {
    private static final long serialVersionUID = -7268048762713388694L;
    private final Map<String, Object> information = new HashMap();
    private boolean logged;
    private String applicationCode;
    private String moduleCode;
    private String errorCode;
    private final Date creationDate = new Date();
    private final String threadName = Thread.currentThread().getName();

    public ExceptionData() {
    }

    public Object getInformation(String key) {
        return this.information.get(key);
    }

    public ExceptionData setInformation(String key, Object value) {
        this.information.put(key, value);
        return this;
    }

    public ExceptionData setLogged(boolean logged) {
        this.logged = logged;
        return this;
    }

    public ExceptionData setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
        return this;
    }

    public ExceptionData setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
        return this;
    }

    public ExceptionData setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public Map<String, Object> getInformation() {
        return this.information;
    }

    public boolean isLogged() {
        return this.logged;
    }

    public String getApplicationCode() {
        return this.applicationCode;
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public String getThreadName() {
        return this.threadName;
    }
}
