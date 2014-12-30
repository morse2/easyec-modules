package com.googlecode.easyec.modules.cas.request;

import com.googlecode.easyec.spirit.web.utils.WebUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.params.HttpParams;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.util.Assert;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证服务的请求处理器类
 *
 * @author JunJie
 */
public class ValidateServiceRequestHandler extends CasServiceRequestHandler<Boolean> {

    private String st;

    public ValidateServiceRequestHandler(String st) {
        Assert.notNull(st, "Service ticket cannot be null.");
        this.st = st;
    }

    @Override
    protected HttpRequestBase createHttpRequest(HttpParams httpParams) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put(
            serviceProperties.getServiceKey(),
            URLEncoder.encode(serviceProperties.getServiceUrl(), "utf-8")
        );
        params.put("ticket", st);

        HttpGet get = new HttpGet(
            new StringBuffer()
                .append(getValidateServiceUrl())
                .append("?").append(WebUtils.encodeQueryString(params))
                .toString()
        );

        return get;
    }

    @Override
    protected Boolean doInResponse(HttpResponse response) {
        int code = response.getStatusLine().getStatusCode();
        logger.debug("Validate service result is: [{}].", code);

        String s = getResponseContent(response);
        logger.debug("Validation result: [{}].", s);

        try {
            Element root = DocumentHelper.parseText(s).getRootElement();
            return _checkStatus(root);
        } catch (DocumentException e) {
            logger.error(e.getMessage(), e);
        }

        return false;
    }

    protected String getValidateServiceUrl() {
        String authServer = serviceProperties.getAuthServer();
        if (authServer.endsWith("/")) {
            authServer = authServer.substring(0, authServer.lastIndexOf("/"));
        }

        return new StringBuffer()
            .append(authServer)
            .append(serviceProperties.getValidateUri())
            .toString();
    }

    private boolean _checkStatus(Element e) {
        return e.element("authenticationSuccess") != null;
    }
}
