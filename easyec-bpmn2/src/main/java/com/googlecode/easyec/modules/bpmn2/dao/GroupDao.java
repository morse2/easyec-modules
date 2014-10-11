package com.googlecode.easyec.modules.bpmn2.dao;

import com.googlecode.easyec.modules.bpmn2.domain.Group;
import com.googlecode.easyec.modules.bpmn2.domain.GroupUserRelation;

import java.util.List;

public interface GroupDao {

    int deleteByPrimaryKey(Long uidPk);

    int insert(Group record);

    Group selectByPrimaryKey(Long uidPk);

    int updateByPrimaryKey(Group record);

    /**
     * 查找默认的流程组信息
     *
     * @return 流程组对象列表
     */
    List<Group> selectDefault();

    /**
     * 删除给定用户下的非默认的流程组的关联信息
     *
     * @param userId 用户ID
     * @return 影响的行数
     */
    int deleteNonDefaultGroupsByUserId(String userId);

    /**
     * 删除用户与流程组的关联信息
     *
     * @param userId 用户ID
     * @return 影响的行数
     */
    int deleteAllGroupsByUserId(String userId);

    /**
     * 插入流程组与用户的关系
     *
     * @param relation 流程组与用户的关系对象
     * @return 影响的行数
     */
    int insertGroupUser(GroupUserRelation relation);
}