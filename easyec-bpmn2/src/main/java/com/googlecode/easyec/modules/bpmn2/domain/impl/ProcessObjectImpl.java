package com.googlecode.easyec.modules.bpmn2.domain.impl;

import com.googlecode.easyec.modules.bpmn2.domain.AttachmentObject;
import com.googlecode.easyec.modules.bpmn2.domain.CommentObject;
import com.googlecode.easyec.modules.bpmn2.domain.ProcessObject;
import com.googlecode.easyec.modules.bpmn2.domain.enums.ProcessPriority;
import com.googlecode.easyec.modules.bpmn2.domain.enums.ProcessStatus;
import com.googlecode.easyec.spirit.dao.id.annotation.Identifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.googlecode.easyec.modules.bpmn2.domain.enums.ProcessPriority.P3;

/**
 * 流程实体类。
 * <p>
 * 此类扩展了流程引擎的基本数据，并且与各个业务实体相结合。
 * 用于桥接框架与业务层的功能
 * </p>
 *
 * @author JunJie
 */
@Identifier("SEQ_PROCESS_ENTITY")
public class ProcessObjectImpl implements ProcessObject {

    private Long          uidPk;
    private String        processDefinitionId;
    private String        processDefinitionKey;
    private String        processInstanceId;
    private String        businessKey;
    private ProcessStatus processStatus;
    private String        type;
    private String        taskName;
    private boolean       rejected;
    private boolean       partialRejected;
    private String        createUser;
    private Date          createTime;
    private Date          requestTime;
    private Date          finishTime;

    private int priority = P3.getPriority();

    private List<CommentObject>    approvedComments = new ArrayList<CommentObject>();
    private List<AttachmentObject> attachments      = new ArrayList<AttachmentObject>();

    public ProcessObjectImpl() { /* no op */ }

    /**
     * 初始化一个新流程对象实例
     *
     * @param processDefinitionId 流程定义的ID
     * @param createUser          流程的申请人
     */
    public ProcessObjectImpl(String processDefinitionId, String createUser) {
        this.processDefinitionId = processDefinitionId;
        this.createUser = createUser;
    }

    public Long getUidPk() {
        return uidPk;
    }

    public void setUidPk(Long uidPk) {
        this.uidPk = uidPk;
    }

    @Override
    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    @Override
    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    @Override
    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    @Override
    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    @Override
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    @Override
    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public String getBusinessKey() {
        return businessKey;
    }

    @Override
    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    @Override
    public ProcessStatus getProcessStatus() {
        return processStatus;
    }

    @Override
    public void setProcessStatus(ProcessStatus processStatus) {
        this.processStatus = processStatus;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public void setPriority(ProcessPriority priority) {
        if (priority != null) {
            this.priority = priority.getPriority();
        }
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    @Override
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public boolean isRejected() {
        return rejected;
    }

    @Override
    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    @Override
    public boolean isPartialRejected() {
        return partialRejected;
    }

    @Override
    public void setPartialRejected(boolean partialRejected) {
        this.partialRejected = partialRejected;
    }

    @Override
    public String getCreateUser() {
        return createUser;
    }

    @Override
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getRequestTime() {
        return requestTime;
    }

    @Override
    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    @Override
    public Date getFinishTime() {
        return finishTime;
    }

    @Override
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public List<CommentObject> getApprovedComments() {
        return approvedComments;
    }

    @Override
    public void setApprovedComments(List<CommentObject> approvedComments) {
        this.approvedComments = approvedComments;
    }

    @Override
    public List<AttachmentObject> getAttachments() {
        return attachments;
    }

    @Override
    public void setAttachments(List<AttachmentObject> attachments) {
        this.attachments = attachments;
    }

    @Override
    public boolean addAttachment(AttachmentObject attachment) {
        return attachment != null && attachments.add(attachment);
    }
}