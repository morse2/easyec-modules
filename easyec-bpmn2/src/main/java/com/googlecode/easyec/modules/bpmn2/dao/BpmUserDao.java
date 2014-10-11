package com.googlecode.easyec.modules.bpmn2.dao;

import com.googlecode.easyec.modules.bpmn2.domain.User;

public interface BpmUserDao {

    int deleteByPrimaryKey(String userId);

    int insert(User record);

    User selectByPrimaryKey(String userId);

    int updateByPrimaryKey(User record);

    /**
     * 查询给定用户ID在流程系统中的数量
     *
     * @param userId 用户ID
     * @return 此用户ID的数量
     */
    int selectCount(String userId);
}