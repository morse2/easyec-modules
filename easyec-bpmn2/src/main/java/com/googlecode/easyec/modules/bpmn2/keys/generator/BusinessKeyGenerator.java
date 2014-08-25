package com.googlecode.easyec.modules.bpmn2.keys.generator;

import com.googlecode.easyec.modules.bpmn2.domain.ProcessObject;

/**
 * 业务流程KEY的生成器接口类
 *
 * @author JunJie
 */
public interface BusinessKeyGenerator {

    /**
     * 通过给定的流程实体对象，
     * 创建一个新的业务流程KEY
     *
     * @param entity 流程实体对象
     * @return 业务流程KEY
     */
    String generateKey(ProcessObject entity);
}
