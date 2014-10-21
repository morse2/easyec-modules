package com.googlecode.easyec.modules.bpmn2.service;

import com.googlecode.easyec.modules.bpmn2.dao.ProcessFormConfigDao;
import com.googlecode.easyec.modules.bpmn2.domain.ProcessFormConfig;
import com.googlecode.easyec.spirit.mybatis.service.DelegateService;

/**
 * Created by JunJie on 2014/10/21.
 */
public interface ProcessFormConfigService extends DelegateService<ProcessFormConfigDao, ProcessFormConfig, Long> {
}
