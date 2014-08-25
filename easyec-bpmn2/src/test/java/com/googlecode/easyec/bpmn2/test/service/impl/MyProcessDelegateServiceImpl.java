package com.googlecode.easyec.bpmn2.test.service.impl;

import com.googlecode.easyec.bpmn2.test.service.ProcessDelegateService;
import com.googlecode.easyec.modules.bpmn2.domain.CommentObject;
import com.googlecode.easyec.modules.bpmn2.domain.ProcessObject;
import com.googlecode.easyec.modules.bpmn2.domain.TaskObject;
import com.googlecode.easyec.spirit.dao.DataPersistenceException;

import java.util.Map;

/**
 * Created by 俊杰 on 2014/7/15.
 */
public class MyProcessDelegateServiceImpl implements ProcessDelegateService {

    @Override
    public void deleteDraft(ProcessObject entity, Map<String, Object> customVariables) throws DataPersistenceException {
        System.out.println(entity);
    }

    @Override
    public void prepareToNew(ProcessObject entity, Map<String, Object> customVariables) throws DataPersistenceException {
        System.out.println(entity);
    }

    @Override
    public void prepareToNew(ProcessObject entity) throws DataPersistenceException {
        System.out.println(entity);
    }

    @Override
    public void start(ProcessObject entity, Map<String, Object> params, Map<String, Object> customVariables) throws DataPersistenceException {
        System.out.println(entity);
    }

    @Override
    public void approve(TaskObject task, String comment, Map<String, Object> variables, Map<String, Object> customVariables) throws DataPersistenceException {
    }

    @Override
    public void reject(TaskObject task, String comment, Map<String, Object> variables, Map<String, Object> customVariables) throws DataPersistenceException {
        System.out.println(task);
    }

    @Override
    public void rejectPartially(TaskObject task, String comment, Map<String, Object> variables, Map<String, Object> customVariables) throws DataPersistenceException {
    }

    @Override
    public void claim(TaskObject task, Map<String, Object> customVariables) {
    }

    @Override
    public void unclaim(TaskObject task, Map<String, Object> customVariables) {
    }

    @Override
    public void addComment(TaskObject task, CommentObject comment, Map<String, Object> customVariables) {
    }
}
