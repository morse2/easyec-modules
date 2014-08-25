package com.googlecode.easyec.modules.bpmn2.service;

/**
 * 错误的流程状态异常类。
 * <p>
 * 该类用于描述流程的状态出现异常的情况。
 * 例如，当删除一个非草稿状态的流程或者
 * 流程被重复启动，则会抛出此异常。
 * </p>
 *
 * @author JunJie
 */
public class WrongProcessStatusException extends ProcessPersistentException {

    private static final long serialVersionUID = -2405957531820364567L;

    public WrongProcessStatusException() { /* no op */ }

    public WrongProcessStatusException(String message) {
        super(message);
    }

    public WrongProcessStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongProcessStatusException(Throwable cause) {
        super(cause);
    }
}
