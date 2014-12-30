package com.googlecode.easyec.modules.cas.request;

import com.googlecode.easyec.modules.cas.InvalidTicketException;
import com.googlecode.easyec.spirit.web.utils.WebUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.HttpParams;
import org.springframework.util.Assert;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取服务票据的请求处理器类
 *
 * @author JunJie
 */
public class GrantServiceTicketRequestHandler extends CasServiceRequestHandler<String> {

    private String tgt;

    public GrantServiceTicketRequestHandler(String tgt) {
        Assert.notNull(tgt, "Ticket grant ticket cannot be null.");

        this.tgt = tgt;
    }

    @Override
    protected HttpRequestBase createHttpRequest(HttpParams httpParams) throws Exception {
        HttpPost post = new HttpPost(getGrantServiceTicketUrl());

        Map<String, String> params = new HashMap<String, String>();
//        params.put("resourceOperatedUpon", "1");

        params.put(
            serviceProperties.getServiceKey(),
            URLEncoder.encode(serviceProperties.getAuthServer(), "utf-8")
        );

        post.setEntity(
            new StringEntity(WebUtils.encodeQueryString(params))
        );

        return post;
    }

    @Override
    protected String doInResponse(HttpResponse response) {
        int code = response.getStatusLine().getStatusCode();
        logger.debug("Grant service ticket of status is: [{}].", code);

        if (code != 200) {
            throw new InvalidTicketException("Invalid ticket: [" + tgt + "].");
        }

        String s = getResponseContent(response);
        logger.debug("Response of granting service ticket: [{}].", s);

        return s;
    }

    protected String getGrantServiceTicketUrl() {
        String authServer = serviceProperties.getAuthServer();
        if (authServer.endsWith("/")) {
            authServer = authServer.substring(0, authServer.lastIndexOf("/"));
        }

        return new StringBuffer()
            .append(authServer)
            .append(getTicketUri())
            .append(tgt)
            .toString();
    }

    protected String getTicketUri() {
        return "/v1/tickets/";
    }
}
