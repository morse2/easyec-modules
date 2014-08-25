package com.googlecode.easyec.modules.bpmn2.service;

import com.googlecode.easyec.modules.bpmn2.dao.ProcessMailConfigDao;
import com.googlecode.easyec.modules.bpmn2.domain.ProcessMailConfig;
import com.googlecode.easyec.spirit.mybatis.service.DelegateService;

/**
 * Created by JunJie on 2014/8/22.
 */
public interface ProcessMailConfigService extends DelegateService<ProcessMailConfigDao, ProcessMailConfig, Long> {
}
