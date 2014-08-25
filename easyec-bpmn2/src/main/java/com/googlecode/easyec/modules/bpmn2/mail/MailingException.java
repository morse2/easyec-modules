package com.googlecode.easyec.modules.bpmn2.mail;

/**
 * 可以表示邮件发送异常等错误信息的类
 *
 * @author JunJie
 */
public class MailingException extends Exception {

    private static final long serialVersionUID = -5545773329758999066L;

    public MailingException() { /* no op */ }

    public MailingException(String message) {
        super(message);
    }

    public MailingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailingException(Throwable cause) {
        super(cause);
    }
}
