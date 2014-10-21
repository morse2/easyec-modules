package com.googlecode.easyec.modules.bpmn2.service.impl;

import com.googlecode.easyec.modules.bpmn2.dao.ProcessFormConfigDao;
import com.googlecode.easyec.modules.bpmn2.domain.ProcessFormConfig;
import com.googlecode.easyec.modules.bpmn2.service.ProcessFormConfigService;
import com.googlecode.easyec.spirit.mybatis.service.impl.DelegateServiceMask;
import org.springframework.stereotype.Service;

/**
 * Created by JunJie on 2014/10/21.
 */
@Service("processFormConfigService")
public class ProcessFormConfigServiceImpl extends DelegateServiceMask<ProcessFormConfigDao, ProcessFormConfig, Long>
    implements ProcessFormConfigService {
}
