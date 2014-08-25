package com.googlecode.easyec.modules.bpmn2.dao;

import com.googlecode.easyec.modules.bpmn2.domain.ProcessObject;
import com.googlecode.easyec.spirit.mybatis.mapper.DelegateDao;

/**
 * 流程业务数据层操作类
 *
 * @author JunJie
 */
public interface ProcessObjectDao extends DelegateDao<ProcessObject, Long> {

    /**
     * 通过流程实例ID，
     * 查询流程实体对象信息。
     *
     * @param processInstanceId 流程实例ID
     * @return 流程实体对象
     */
    ProcessObject selectByProcessInstanceId(String processInstanceId);

    /**
     * 通过流程实体对象ID，
     * 查询流程在数据库中的数量
     *
     * @param uidPk 流程实体对象ID
     * @return 流程在数据库中的数量
     */
    int selectCountByPK(Long uidPk);
}