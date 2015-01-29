package com.googlecode.easyec.modules.bpmn2.dao;

import com.googlecode.easyec.modules.bpmn2.domain.TaskDefinition;
import com.googlecode.easyec.modules.bpmn2.domain.TaskObject;
import com.googlecode.easyec.spirit.dao.paging.Page;

import java.util.List;
import java.util.Map;

/**
 * 任务实体对象数据层操作类
 *
 * @author JunJie
 */
public interface TaskObjectDao {

    /**
     * 通过任务ID，查询运行中的用户任务
     *
     * @param taskId 用户任务ID
     * @return 任务实体对象
     */
    TaskObject selectByPrimaryKey(String taskId);

    /**
     * 分页查询运行中的用户任务
     *
     * @param page 分页对象
     * @return 分页结果对象
     */
    Page find(Page page);

    /**
     * 根据条件查询运行中的用户任务
     *
     * @param params 查询条件
     * @return 任务实体对象列表
     */
    List<TaskObject> find(Map<String, Object> params);

    /**
     * 根据条件统计运行中的任务数量
     *
     * @param params 查询条件
     * @return 运行中的用户任务数量
     */
    long countTasks(Map<String, Object> params);

    /**
     * 根据流程任务的KEY，
     * 来分组查询任务定义列表
     *
     * @param params 查询条件
     * @return 任务定义对象
     */
    List<TaskDefinition> groupByTaskDefinition(Map<String, Object> params);

    /**
     * 分页查询历史用户任务信息
     *
     * @param page 分页对象
     * @return 分页结果对象
     */
    Page findHistoric(Page page);

    /**
     * 根据条件查询历史用户任务
     *
     * @param params 查询条件
     * @return 历史的用户任务列表
     */
    List<TaskObject> findHistoric(Map<String, Object> params);

    /**
     * 根据条件统计历史用户任务数量
     *
     * @param params 查询条件
     * @return 历史的用户任务数量
     */
    long countHistoricTasks(Map<String, Object> params);

    /**
     * 分页查询最近的历史用户任务信息。
     * <b>最近的任务</b>是指相同流程实例
     * 的最近一条历史任务记录
     *
     * @param page 分页对象
     * @return 分页结果对象
     */
    Page findLastHistoric(Page page);

    /**
     * 根据条件查询最近的历史用户任务。
     * <b>最近的任务</b>是指相同流程实例
     * 的最近一条历史任务记录
     *
     * @param params 查询条件
     * @return 最近的历史的用户任务列表
     */
    List<TaskObject> findLastHistoric(Map<String, Object> params);

    /**
     * 根据条件统计最近的历史用户任务数量。
     * <b>最近的任务</b>是指相同流程实例
     * 的最近一条历史任务记录
     *
     * @param params 查询条件
     * @return 最近的历史的用户任务数量
     */
    long countLastHistoricTasks(Map<String, Object> params);

    /**
     * 通过任务ID，查询历史任务信息
     *
     * @param taskId 用户任务ID
     * @return 任务实体对象
     */
    TaskObject selectHistoric(String taskId);
}
