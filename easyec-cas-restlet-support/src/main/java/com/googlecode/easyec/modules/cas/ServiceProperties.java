package com.googlecode.easyec.modules.cas;

import org.springframework.util.Assert;

import java.util.Properties;

import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * 服务属性类
 *
 * @author JunJie
 */
public class ServiceProperties {

    public static final String SP_AUTH_SERVER = "sp.auth.server";
    public static final String SP_SERVICE_URL = "sp.service.url";
    public static final String SP_VALIDATE_URI = "sp.validate.uri";
    public static final String SP_KEY_USERNAME = "sp.key.username";
    public static final String SP_KEY_PASSWORD = "sp.key.password";
    public static final String SP_KEY_SERVICE = "sp.key.service";

    private String authServer;
    private String usernameKey;
    private String passwordKey;
    private String serviceKey;
    private String validateUri;
    private String serviceUrl;

    private static final Object lock = new Object();
    private static ServiceProperties _instance;

    private ServiceProperties() { /* no op */ }

    public static ServiceProperties newInstance(Properties props) {
        synchronized (lock) {
            if (_instance == null) {
                _instance = new ServiceProperties();
                _instance._loadProperties(props);
            }

            return _instance;
        }
    }

    private void _loadProperties(Properties props) {
        authServer = props.getProperty(SP_AUTH_SERVER);
        Assert.isTrue(isNotBlank(authServer), "'" + SP_AUTH_SERVER + "' must be set.");

        validateUri = props.getProperty(SP_VALIDATE_URI, "/serviceValidate");
        usernameKey = props.getProperty(SP_KEY_USERNAME, "username");
        passwordKey = props.getProperty(SP_KEY_PASSWORD, "password");
        serviceKey = props.getProperty(SP_KEY_SERVICE, "service");
        serviceUrl = props.getProperty(SP_SERVICE_URL, "");
    }

    public String getAuthServer() {
        return authServer;
    }

    public String getUsernameKey() {
        return usernameKey;
    }

    public String getPasswordKey() {
        return passwordKey;
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public String getValidateUri() {
        return validateUri;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }
}
