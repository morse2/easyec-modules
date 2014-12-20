package com.googlecode.easyec.modules.bpmn2.mail;

import com.googlecode.easyec.modules.bpmn2.domain.TaskObject;
import com.googlecode.easyec.spirit.web.mail.MailObject;
import com.googlecode.easyec.spirit.web.mail.execution.SendMailExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.googlecode.easyec.spirit.web.utils.SpringContextUtils.autowireBeanProperties;
import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * 发送邮件的抽象委托类
 *
 * @author JunJie
 */
public abstract class AbstractSendMailDelegate implements SendMailDelegate {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private SendMailExecutor sendMailExecutor;

    protected AbstractSendMailDelegate() {
        autowireBeanProperties(this, true);
    }

    @Override
    public void sendMail(TaskObject newTask, TaskObject oldTask, String comment, String fileKey)
    throws MailingException {

        // 创建邮件对象实例
        MailObject mo = createMailObject(newTask, oldTask, comment, fileKey);

        if (mo == null) {
            logger.warn("There is a null object after calling method 'createMailObject', so ignore sending mail.");

            return;
        }

        List<String> recipients = new ArrayList<String>();
        // 获取处理人的邮件地址
        String assignee = newTask.getAssignee();
        if (isNotBlank(assignee)) {
            String mail = getUserMail(assignee);
            if (isNotBlank(mail)) recipients.add(mail);
        }
        // 获取候选人的邮件地址
        else {
            List<String> candidates = newTask.getCandidates();
            for (String candidate : candidates) {
                String userMail = getUserMail(candidate);
                if (isNotBlank(userMail)) recipients.add(userMail);
            }
        }

        if (recipients.isEmpty()) {
            logger.warn("There has no any assignee or candidates to deal with task, task id: [{}].", newTask.getTaskId());

            return;
        }

        for (String recipient : recipients) {
            mo.addRecipient(recipient);
        }

        // 将邮件对象推送到邮件队列中
        sendMailExecutor.prepare(mo);
    }

    /**
     * 创建邮件对象。
     *
     * @param newTask 下一个新任务对象
     * @param oldTask 当前旧的任务对象
     * @param comment 当前审批备注内容
     * @param fileKey 模板文件
     * @return 邮件对象
     */
    abstract public MailObject createMailObject(TaskObject newTask, TaskObject oldTask, String comment, String fileKey);

    /**
     * 通过用户ID，获取用户用户的Email信息。
     *
     * @param userId 用户ID
     * @return Email地址
     */
    abstract public String getUserMail(String userId);
}
