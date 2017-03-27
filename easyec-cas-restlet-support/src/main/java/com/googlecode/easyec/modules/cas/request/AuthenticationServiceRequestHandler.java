package com.googlecode.easyec.modules.cas.request;

import com.googlecode.easyec.modules.cas.AuthenticationException;
import com.googlecode.easyec.spirit.web.utils.WebUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.HttpParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.util.Assert;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * 认证服务请求处理器类
 *
 * @author JunJie
 */
public class AuthenticationServiceRequestHandler extends CasServiceRequestHandler<String> {

    private String username;
    private String password;

    public AuthenticationServiceRequestHandler(String username, String password) {
        Assert.notNull(username, "'username' is null.");
        Assert.notNull(password, "'password' is null.");

        this.username = username;
        this.password = password;
    }

    @Override
    protected HttpRequestBase createHttpRequest(HttpParams httpParams) throws Exception {
        HttpPost post = new HttpPost(getAuthenticationUrl());

        Map<String, String> params = new HashMap<String, String>();
        params.put(serviceProperties.getUsernameKey(), username);
        params.put(serviceProperties.getPasswordKey(), password);

        params.put(
            serviceProperties.getServiceKey(),
            URLEncoder.encode(serviceProperties.getServiceUrl(), "utf-8")
        );

        post.setEntity(
            new StringEntity(WebUtils.encodeQueryString(params))
        );

        return post;
    }

    @Override
    protected String doInResponse(HttpResponse response) {
        int code = response.getStatusLine().getStatusCode();
        logger.debug("Response code for authentication is: [{}].", code);

        String s = getResponseContent(response);
        if (code != 201) {
            throw new AuthenticationException("Authentication is failed.");
        }

        if (isBlank(s)) throw new AuthenticationException("No response content was found.");

        try {
            Document doc = Jsoup.parse(s);
            String url = doc.select("form").attr("action");
            int i = url.indexOf("TGT");

            return url.substring(i);
        } catch (Exception e) {
            throw new AuthenticationException("Cannot found TGT ticket identity.");
        }
    }

    /**
     * 得到认证的URL
     */
    protected String getAuthenticationUrl() {
        String authServer = serviceProperties.getAuthServer();
        if (authServer.endsWith("/")) {
            authServer = authServer.substring(0, authServer.lastIndexOf("/"));
        }

        return new StringBuffer()
            .append(authServer)
            .append(getTicketUri())
            .toString();
    }

    /**
     * 得到票据服务的URI
     */
    protected String getTicketUri() {
        return "/v1/tickets";
    }
}
