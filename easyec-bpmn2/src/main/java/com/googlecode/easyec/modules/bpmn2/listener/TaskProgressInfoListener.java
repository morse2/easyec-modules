package com.googlecode.easyec.modules.bpmn2.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.googlecode.easyec.modules.bpmn2.utils.ProcessConstant.PROCESS_PERCENT;
import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * 任务完成的进度情况的监听类
 *
 * @author JunJie
 */
public class TaskProgressInfoListener implements TaskListener {

    private static final Logger logger = LoggerFactory.getLogger(TaskProgressInfoListener.class);
    private static final long serialVersionUID = 8046116465138230740L;
    private Expression percent;

    @Override
    public void notify(DelegateTask delegateTask) {
        if (percent == null) {
            logger.warn("No percent value was set.");

            return;
        }

        String val = (String) percent.getValue(delegateTask);
        if (isNotBlank(val)) {
            try {
                delegateTask.setVariable(PROCESS_PERCENT, Float.valueOf(val));
            } catch (NumberFormatException e) {
                logger.debug(e.getMessage(), e);
            }
        }
    }
}
