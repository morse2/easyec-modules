package com.googlecode.easyec.bpmn2.test.service;

import com.googlecode.easyec.modules.bpmn2.domain.CommentObject;
import com.googlecode.easyec.modules.bpmn2.domain.ProcessObject;
import com.googlecode.easyec.modules.bpmn2.domain.TaskObject;
import com.googlecode.easyec.spirit.dao.DataPersistenceException;

import java.util.Map;

/**
 * Created by 俊杰 on 2014/7/18.
 */
public interface ProcessDelegateService {

    void deleteDraft(ProcessObject entity, Map<String, Object> customVariables) throws DataPersistenceException;

    void prepareToNew(ProcessObject entity, Map<String, Object> customVariables) throws DataPersistenceException;

    void prepareToNew(ProcessObject entity) throws DataPersistenceException;

    void start(ProcessObject entity, Map<String, Object> params, Map<String, Object> customVariables) throws DataPersistenceException;

    void approve(TaskObject task, String comment, Map<String, Object> variables, Map<String, Object> customVariables) throws DataPersistenceException;

    void reject(TaskObject task, String comment, Map<String, Object> variables, Map<String, Object> customVariables) throws DataPersistenceException;

    void rejectPartially(TaskObject task, String comment, Map<String, Object> variables, Map<String, Object> customVariables) throws DataPersistenceException;

    void claim(TaskObject task, Map<String, Object> customVariables);

    void unclaim(TaskObject task, Map<String, Object> customVariables);

    void addComment(TaskObject task, CommentObject comment, Map<String, Object> customVariables);
}
