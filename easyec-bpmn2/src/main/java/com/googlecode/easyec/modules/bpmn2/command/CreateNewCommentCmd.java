package com.googlecode.easyec.modules.bpmn2.command;

import com.googlecode.easyec.modules.bpmn2.command.persistence.entity.CommentEntity;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Event;

/**
 * 创建一个新的备注的命令类
 *
 * @author JunJie
 */
public class CreateNewCommentCmd implements Command<Comment> {

    protected String taskId;
    protected String processInstanceId;
    protected String type;
    protected String message;

    public CreateNewCommentCmd(String taskId, String processInstanceId, String type, String message) {
        this.taskId = taskId;
        this.processInstanceId = processInstanceId;
        this.type = type;
        this.message = message;
    }

    @Override
    public Comment execute(CommandContext commandContext) {
        String userId = Authentication.getAuthenticatedUserId();
        CommentEntity comment = new CommentEntity();
        comment.setUserId(userId);
        comment.setType((type == null) ? CommentEntity.TYPE_COMMENT : type);
        comment.setTime(Context.getProcessEngineConfiguration().getClock().getCurrentTime());
        comment.setTaskId(taskId);
        comment.setProcessInstanceId(processInstanceId);
        comment.setAction(Event.ACTION_ADD_COMMENT);

        String eventMessage = message.replaceAll("\\s+", " ");
        if (eventMessage.length() > 163) {
            eventMessage = eventMessage.substring(0, 160) + "...";
        }

        comment.setMessage(eventMessage);
        comment.setFullMessage(message);

        commandContext.getCommentEntityManager().insert(comment);

        return comment;
    }
}
