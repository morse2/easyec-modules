package com.googlecode.easyec.modules.cas;

/**
 * 认证失败异常类
 *
 * @author JunJie
 */
public class AuthenticationException extends RuntimeException {

    private static final long serialVersionUID = -3722991789978720634L;

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}
