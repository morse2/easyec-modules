package com.googlecode.easyec.modules.bpmn2.domain.ctrl;

/**
 * 流程附件对象的控制类
 * <p>
 * 子类实现此接口，可以为运行中的流程添加附件信息
 * </p>
 *
 * @author JunJie
 */
public interface AttachmentObjectCtrl {

    void setId(String id);

    void setName(String name);

    void setUserId(String userId);

    void setDescription(String description);

    void setTaskId(String taskId);

    void setUrl(String url);

    void setContent(byte[] content);
}
