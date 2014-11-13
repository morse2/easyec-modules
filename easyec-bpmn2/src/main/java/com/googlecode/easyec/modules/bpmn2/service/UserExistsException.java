package com.googlecode.easyec.modules.bpmn2.service;

/**
 * 表示用户已存在于系统中的异常类
 *
 * @author JunJie
 */
public class UserExistsException extends Exception {

    private static final long serialVersionUID = 4370972939262317818L;

    public UserExistsException() { /* no op */ }

    public UserExistsException(String message) {
        super(message);
    }

    public UserExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserExistsException(Throwable cause) {
        super(cause);
    }
}
