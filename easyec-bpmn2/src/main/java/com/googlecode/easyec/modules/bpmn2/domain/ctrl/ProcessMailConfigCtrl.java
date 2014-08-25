package com.googlecode.easyec.modules.bpmn2.domain.ctrl;

/**
 * Created by JunJie on 2014/8/20.
 */
public interface ProcessMailConfigCtrl {

    void setProcessKey(String procDefKey);

    void setFireType(String mailFireType);

    void setFileKey(String mailFileKey);

    void setMailClass(String mailClass);
}
