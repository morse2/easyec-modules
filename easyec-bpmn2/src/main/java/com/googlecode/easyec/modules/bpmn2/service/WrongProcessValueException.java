package com.googlecode.easyec.modules.bpmn2.service;

/**
 * 错误的流程数据值的异常类
 *
 * @author JunJie
 */
public class WrongProcessValueException extends RuntimeException {

    private static final long serialVersionUID = 4137527950086758183L;

    public WrongProcessValueException() { /* no op */ }

    public WrongProcessValueException(String message) {
        super(message);
    }

    public WrongProcessValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongProcessValueException(Throwable cause) {
        super(cause);
    }
}
