package com.googlecode.easyec.modules.bpmn2.mail;

import com.googlecode.easyec.modules.bpmn2.domain.TaskObject;

/**
 * 发送邮件的委托类。
 * 业务类实现流程邮件发送需要实现此接口。
 *
 * @author JunJie
 */
public interface SendMailDelegate {

    /**
     * 为给定的任务发送邮件给指定的用户。
     * <p>
     * 参数1：下一个待审批节点的任务对象，
     * 也就是将要做的任务。<br>
     * 参数2：当前正在审批的任务对象，
     * 也就是即将完成的任务。当新流程
     * 开始的时候，该值为空。<br>
     * 参数3：审批时，用户填写的备注信息。
     * 当新流程开始时候，该值为空。
     * </p>
     *
     * @param newTask 下一个新任务对象
     * @param oldTask 当前旧的任务对象
     * @param comment 用户填写的备注
     * @param fileKey 模板文件
     * @throws MailingException
     */
    void sendMail(TaskObject newTask, TaskObject oldTask, String comment, String fileKey) throws MailingException;
}
