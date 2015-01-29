package com.googlecode.easyec.modules.cas.test;

import com.googlecode.easyec.modules.cas.ServiceProperties;
import com.googlecode.easyec.modules.cas.request.*;
import com.tnt.cert.utils.CipherUtils;
import org.apache.http.client.HttpClient;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static com.googlecode.easyec.modules.cas.request.ServiceRequestFactory.newInstance;

/**
 * @author JunJie
 */
public class CasTestCase {

    private ServiceRequestFactory serviceRequestFactory;
    private String tgt;

    @Before
    public void beforeRun() throws Exception {
        Properties props = new Properties();
        props.setProperty("sp.auth.server", "http://auth.cx.tnt.com:7071/cas");
        props.setProperty("sp.service.url", "http://auth.cx.tnt.com:7071/cas");

        serviceRequestFactory = newInstance(
            ServiceProperties.newInstance(props), 80, 10
        );

        tgt = CipherUtils.getInstance().decrypt(
            "NFXWcsIAREywTphODkdtTKwaegTR3esil5BYC6rdvCfnSMN6sidpd3Qi+i+tIG15bXy9DeZu1OZhVHQHbDS4OU/psSkxJP21s9pKbnloeahiAXm8+cIzWmVChjTJ70zowvDGm05h5trn0xIY0VguzeKRhxSY6yAFY9HB71MHa84="
        );
    }

    @Test
    public void login() throws Exception {
        String tgt = serviceRequestFactory.invokeRequest(
            new AuthenticationServiceRequestHandler("n195fyj", "China321")
        );

        System.out.println(tgt);
    }

    @Test
    public void grantST() throws Exception {
        String st = serviceRequestFactory.invokeRequest(
            new GrantServiceTicketRequestHandler(tgt)
        );

        System.out.println(st);
    }

    @Test
    public void logout() throws Exception {
        serviceRequestFactory.invokeRequest(
            new LogoutServiceRequestHandler(tgt)
        );
    }

    @Test
    public void requestAsCustom() throws Exception {
        serviceRequestFactory.invokeRequest(
            new CustomServiceRequestHandler(tgt) {

                @Override
                public Object doInCustom(HttpClient httpClient, String ticket) {
                    return null;
                }
            }
        );
    }

    @Test
    public void validate() throws Exception {
        Boolean b = serviceRequestFactory.invokeRequest(
            new ValidateServiceRequestHandler("ST-6-j7Sv2nDTgdidYq0cIRlV-auth.cx.tnt.com")
        );

        System.out.println(b);
    }
}
