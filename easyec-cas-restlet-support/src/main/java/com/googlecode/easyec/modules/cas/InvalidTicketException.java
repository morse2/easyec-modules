package com.googlecode.easyec.modules.cas;

/**
 * 无效票据异常类
 *
 * @author JunJie
 */
public class InvalidTicketException extends RuntimeException {

    private static final long serialVersionUID = -8835074476812962739L;

    public InvalidTicketException(String message) {
        super(message);
    }

    public InvalidTicketException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTicketException(Throwable cause) {
        super(cause);
    }
}
