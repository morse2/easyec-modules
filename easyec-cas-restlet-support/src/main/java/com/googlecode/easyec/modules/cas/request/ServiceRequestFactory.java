package com.googlecode.easyec.modules.cas.request;

import com.googlecode.easyec.modules.cas.ServiceProperties;
import com.googlecode.easyec.spirit.web.httpcomponent.DefaultSchemeRegistryFactory;
import com.googlecode.easyec.spirit.web.httpcomponent.HttpRequestFactory;
import com.googlecode.easyec.spirit.web.httpcomponent.HttpRequestHandler;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.springframework.util.Assert;

/**
 * Http请求工厂类，
 * 通过此类的实例，
 * 可以创建HttpRequest对象，
 * 以请求远程服务
 *
 * @author JunJie
 */
public final class ServiceRequestFactory {

    private static final Object lock = new Object();
    private static ServiceRequestFactory _instance;

    private ServiceProperties _serviceProperties;
    private HttpRequestFactory _factory;

    private ServiceRequestFactory() { /* no op */ }

    public static ServiceRequestFactory newInstance(ServiceProperties serviceProperties, int maxTotal, int maxPerRoute) throws Exception {
        synchronized (lock) {
            if (_instance == null) {
                _instance = new ServiceRequestFactory();

                Assert.notNull(serviceProperties, "ServiceProperties object cannot be null.");
                _instance._serviceProperties = serviceProperties;

                DefaultSchemeRegistryFactory schemeRegistryFactory = new DefaultSchemeRegistryFactory();
                schemeRegistryFactory.afterPropertiesSet();
                SchemeRegistry schemeRegistry = schemeRegistryFactory.getObject();

                PoolingClientConnectionManager clientConnectionManager = new PoolingClientConnectionManager(schemeRegistry);
                clientConnectionManager.setDefaultMaxPerRoute(maxPerRoute);
                clientConnectionManager.setMaxTotal(maxTotal);

                _instance._factory = new HttpRequestFactory();
                _instance._factory.setClientConnectionManager(clientConnectionManager);
                _instance._factory.afterPropertiesSet();
            }

            return _instance;
        }
    }

    public <T> T invokeRequest(HttpRequestHandler<T> handler) throws Exception {
        synchronized (lock) {
            if (handler instanceof ServicePropertiesCtrl) {
                ((ServicePropertiesCtrl) handler).setServiceProperties(_serviceProperties);
            }

            return _factory.getObject().request(handler);
        }
    }
}
