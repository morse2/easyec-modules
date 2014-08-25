package com.googlecode.easyec.modules.bpmn2.service;

/**
 * 流程数据持久化异常类
 *
 * @author JunJie
 */
public class ProcessPersistentException extends Exception {

    private static final long serialVersionUID = 2520648088468502470L;

    public ProcessPersistentException() { /* no op */ }

    public ProcessPersistentException(String message) {
        super(message);
    }

    public ProcessPersistentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessPersistentException(Throwable cause) {
        super(cause);
    }
}
