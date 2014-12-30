package com.googlecode.easyec.modules.cas.request;

import com.googlecode.easyec.modules.cas.AuthenticationException;
import com.googlecode.easyec.modules.cas.ServiceProperties;
import com.googlecode.easyec.spirit.web.httpcomponent.HttpRequestHandler;
import org.apache.http.client.HttpClient;
import org.springframework.util.Assert;

/**
 * 自定义服务请求处理器类
 *
 * @author JunJie
 */
public abstract class CustomServiceRequestHandler<T> implements HttpRequestHandler<T>, ServicePropertiesCtrl {

    protected ServiceProperties serviceProperties;

    private String tgt;

    public CustomServiceRequestHandler(String tgt) {
        Assert.notNull(tgt, "Ticket grant ticket cannot be null.");
        this.tgt = tgt;
    }

    @Override
    public T process(HttpClient httpClient) throws Exception {
        String st = _invokeRequest(httpClient, new GrantServiceTicketRequestHandler(tgt));
        Boolean b = _invokeRequest(httpClient, new ValidateServiceRequestHandler(st));
        if (!b) throw new AuthenticationException("Invalid ticket.");

        return doInCustom(httpClient, st);
    }

    @Override
    public void setServiceProperties(ServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }

    /**
     * 执行自定义HTTP请求的方法
     *
     * @param httpClient <code>HttpClient</code>对象
     * @param ticket     服务票据信息
     */
    abstract public T doInCustom(HttpClient httpClient, String ticket);

    private <B> B _invokeRequest(HttpClient httpClient, HttpRequestHandler<B> handler) throws Exception {
        if (handler instanceof ServicePropertiesCtrl) {
            ((ServicePropertiesCtrl) handler).setServiceProperties(serviceProperties);
        }

        return handler.process(httpClient);
    }
}
