package com.googlecode.easyec.modules.bpmn2.utils;

import com.googlecode.easyec.modules.bpmn2.domain.ProcessMailConfig;
import com.googlecode.easyec.modules.bpmn2.domain.TaskObject;
import com.googlecode.easyec.modules.bpmn2.mail.MailingException;
import com.googlecode.easyec.modules.bpmn2.mail.SendMailDelegate;
import com.googlecode.easyec.modules.bpmn2.query.ProcessMailConfigQuery;
import org.apache.commons.lang.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;

import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * 流程邮件配置工具类
 *
 * @author JunJie
 */
public class MailConfigUtils {

    private static final Logger logger = LoggerFactory.getLogger(MailConfigUtils.class);

    /**
     * 发送邮件的方法。
     *
     * @param newTask  新任务对象
     * @param oldTask  旧任务对象
     * @param comment  当前备注
     * @param fireType 触发类型
     */
    public static void sendMail(TaskObject newTask, TaskObject oldTask, String comment, String fireType) {
        Long processEntityId = newTask.getProcessObject().getUidPk();
        logger.debug("Process entity id: [{}].", processEntityId);

        // 获取邮件的配置信息
        List<ProcessMailConfig> mailConfigs
                = new ProcessMailConfigQuery()
                .processEntityId(processEntityId)
                .fireType(fireType)
                .list();

        // 如果没有邮件配置信息，则不发送邮件
        if (isEmpty(mailConfigs)) {
            logger.warn(
                    "No Process mail configuration was found. Fire type: [" + fireType +
                            "], entity id: [" + processEntityId + "]. So ignore operation else."
            );

            return;
        }

        try {
            // 默认获取第一条配置信息
            ProcessMailConfig config = mailConfigs.get(0);
            // 加载预定义的类信息
            Class cls = ClassUtils.getClass(config.getMailClass());
            logger.debug("Class name: [{}].", cls.getName());

            if (!SendMailDelegate.class.isAssignableFrom(cls)) {
                logger.warn(
                        "The class isn't assignable from [" +
                                SendMailDelegate.class.getName() + "], so ignore operation else."
                );

                return;
            }

            SendMailDelegate delegate;

            try {
                // 实例化配置的类
                delegate = (SendMailDelegate) BeanUtils.instantiateClass(cls);
                delegate.sendMail(newTask, oldTask, comment, config.getFileKey());
            } finally {
                delegate = null;
            }
        } catch (ClassNotFoundException e) {
            logger.error("Class is not found that configs in DB. Error msg: [{}].", e.getMessage());
        } catch (BeanInstantiationException e) {
            logger.error("Bean cannot be instantiated. Error msg: [{}].", e.getMessage());
        } catch (MailingException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
