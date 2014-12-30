package com.googlecode.easyec.modules.cas.request;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.params.HttpParams;
import org.springframework.util.Assert;

/**
 * 登出CAS服务的请求处理器类
 *
 * @author JunJie
 */
public class LogoutServiceRequestHandler extends CasServiceRequestHandler<Void> {

    private String tgt;

    public LogoutServiceRequestHandler(String tgt) {
        Assert.notNull(tgt, "Ticket service ticket cannot be null.");
        this.tgt = tgt;
    }

    @Override
    protected HttpRequestBase createHttpRequest(HttpParams params) throws Exception {
        return new HttpDelete(getLogoutServiceUrl());
    }

    @Override
    protected Void doInResponse(HttpResponse response) {
        int code = response.getStatusLine().getStatusCode();
        logger.debug("Logout service' response is: [{}].", code);

        return null;
    }

    protected String getLogoutServiceUrl() {
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
