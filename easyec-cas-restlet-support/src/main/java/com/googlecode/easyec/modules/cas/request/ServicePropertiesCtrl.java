package com.googlecode.easyec.modules.cas.request;

import com.googlecode.easyec.modules.cas.ServiceProperties;

/**
 * 服务属性控制类
 *
 * @author JunJie
 */
public interface ServicePropertiesCtrl {

    /**
     * 设置服务属性对象实例
     *
     * @param serviceProperties <code>ServiceProperties</code>对象
     */
    void setServiceProperties(ServiceProperties serviceProperties);
}
