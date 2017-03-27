package com.googlecode.easyec.modules.cas.request;

import com.googlecode.easyec.modules.cas.ServiceProperties;
import com.googlecode.easyec.spirit.web.httpcomponent.HttpRequestHandler;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * CAS远程服务请求处理基类
 *
 * @author JunJie
 */
public abstract class CasServiceRequestHandler<T> implements HttpRequestHandler<T>, ServicePropertiesCtrl {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected ServiceProperties serviceProperties;

    public void setServiceProperties(ServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }

    @Override
    public T process(HttpClient httpClient) throws Exception {
        return doInResponse(httpClient.execute(createHttpRequest(httpClient.getParams())));
    }

    /**
     * 得到响应体的内容
     *
     * @param response <code>HttpResponse</code>对象
     */
    protected String getResponseContent(HttpResponse response) {
        try {
            return EntityUtils.toString(
                response.getEntity(),
                Charset.forName("utf-8")
            );
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 创建<code>HttpRequest</code>对象实例
     *
     * @param params
     * @return HttpRequestBase对象
     */
    abstract protected HttpRequestBase createHttpRequest(HttpParams params) throws Exception;

    /**
     * 执行Http响应的方法
     *
     * @param response <code>HttpResponse</code>对象
     * @return 返回的值
     */
    abstract protected T doInResponse(HttpResponse response);
}
