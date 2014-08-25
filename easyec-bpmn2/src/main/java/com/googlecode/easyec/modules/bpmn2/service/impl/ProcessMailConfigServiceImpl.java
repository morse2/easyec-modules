package com.googlecode.easyec.modules.bpmn2.service.impl;

import com.googlecode.easyec.modules.bpmn2.dao.ProcessMailConfigDao;
import com.googlecode.easyec.modules.bpmn2.domain.ProcessMailConfig;
import com.googlecode.easyec.modules.bpmn2.service.ProcessMailConfigService;
import com.googlecode.easyec.spirit.mybatis.service.impl.DelegateServiceMask;
import org.springframework.stereotype.Service;

/**
 * Created by JunJie on 2014/8/22.
 */
@Service("processMailConfigService")
public class ProcessMailConfigServiceImpl extends DelegateServiceMask<ProcessMailConfigDao, ProcessMailConfig, Long>
    implements ProcessMailConfigService {
}
