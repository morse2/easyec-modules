package com.googlecode.easyec.modules.bpmn2.service;

import com.googlecode.easyec.modules.bpmn2.domain.AttachmentObject;
import com.googlecode.easyec.modules.bpmn2.domain.ProcessObject;

import java.util.Map;

/**
 * 流程操作的业务类
 *
 * @author JunJie
 */
public interface ProcessService {

    void deleteDraft(Long processEntityId) throws ProcessPersistentException;

    void createDraft(ProcessObject entity) throws ProcessPersistentException;

    void generateBusinessKey(ProcessObject entity);

    void discard(ProcessObject po, String reason, String type) throws ProcessPersistentException;

    void discard(ProcessObject po, String reason) throws ProcessPersistentException;

    void startProcess(ProcessObject entity) throws ProcessPersistentException;

    void startProcess(ProcessObject entity, Map<String, Object> params) throws ProcessPersistentException;

    void addAttachment(ProcessObject entity, AttachmentObject attachment)
    throws WrongProcessValueException, ProcessPersistentException;

    void setPartialReject(Long processEntityId, boolean partial) throws ProcessPersistentException;

    boolean isApproved(Long processEntityId) throws ProcessNotFoundException;

    boolean isRejected(Long processInstanceId) throws ProcessNotFoundException;

    boolean isPartialRejected(Long processEntityId) throws ProcessNotFoundException;
}
