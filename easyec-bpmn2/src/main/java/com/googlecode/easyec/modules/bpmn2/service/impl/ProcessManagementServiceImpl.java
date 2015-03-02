package com.googlecode.easyec.modules.bpmn2.service.impl;

import com.googlecode.easyec.modules.bpmn2.dao.BpmRoleDao;
import com.googlecode.easyec.modules.bpmn2.dao.ProcessAutoApprovalConfigDao;
import com.googlecode.easyec.modules.bpmn2.domain.BpmRole;
import com.googlecode.easyec.modules.bpmn2.service.ProcessManagementService;
import com.googlecode.easyec.modules.bpmn2.service.UserExistsException;
import com.googlecode.easyec.spirit.dao.DataPersistenceException;
import com.googlecode.easyec.spirit.dao.paging.Page;
import com.googlecode.easyec.spirit.dao.paging.PageComputable;
import com.googlecode.easyec.spirit.dao.paging.PageWritable;
import com.googlecode.easyec.spirit.dao.paging.factory.PageDelegate;
import com.googlecode.easyec.spirit.web.controller.formbean.impl.AbstractSearchFormBean;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.NativeProcessDefinitionQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.googlecode.easyec.modules.bpmn2.utils.ProcessConstant.ROLE_TYPE_FLOW_ADMIN;
import static org.apache.commons.collections.MapUtils.isNotEmpty;

/**
 * 流程管理的业务实现类
 *
 * @author JunJie
 */
public class ProcessManagementServiceImpl implements ProcessManagementService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessManagementServiceImpl.class);

    @Resource
    private IdentityService identityService;
    @Resource
    private RepositoryService repositoryService;

    @Resource
    private BpmRoleDao bpmRoleDao;

    @Resource
    private ProcessAutoApprovalConfigDao processAutoApprovalConfigDao;

    @Resource
    private PageDelegate pageConfigurer;

    @Override
    public List<ProcessDefinition> findProcessDefinitionsByFlowAdmin(String userId) {
        return findProcessDefinitionsForProcess(userId, ROLE_TYPE_FLOW_ADMIN);
    }

    @Override
    public List<ProcessDefinition> findProcessDefinitionsForProcess(String userId, String roleType) {
        StringBuffer querySql = new StringBuffer();

        querySql.append(" select RES.* from ACT_RE_PROCDEF RES JOIN BPM_PROC_ROLE_RELATION prr ON RES.KEY_ = prr.PROC_DEF_KEY ");
        querySql.append(" JOIN BPM_PROC_ROLE pr ON prr.proc_role_code = pr.role_code AND pr.role_type = #{roleType} AND pr.enabled = 1 ");
        querySql.append(" JOIN ACT_ID_MEMBERSHIP M ON M .group_id_ = prr. GROUP_ID where M .user_id_ = #{userId} ");
        querySql.append(" AND RES.VERSION_ = (select max(VERSION_) from ACT_RE_PROCDEF where KEY_ = RES.KEY_) AND (RES.SUSPENSION_STATE_ = 1) ");
        querySql.append(" order by RES.TENANT_ID_ ASC, RES.KEY_ ASC ");

        return repositoryService.createNativeProcessDefinitionQuery()
            .parameter("roleType", roleType)
            .parameter("userId", userId)
            .sql(querySql.toString())
            .list();
    }

    @Override
    public List<ProcessDefinition> findProcessDefinitionsByApplicant(String userId) {
        StringBuffer querySql = new StringBuffer();
        querySql.append(" select * from (");
        querySql.append(" select RES.* from ACT_RE_PROCDEF RES join BPM_RE_PROCDEF_EXTRA REX ON RES.KEY_ = REX.PROC_DEF_KEY_ ");
        querySql.append(" AND REX.APPLY_BY_GROUP = 0 where RES.VERSION_ = (select max(VERSION_) from ACT_RE_PROCDEF where KEY_ = RES.KEY_) ");
        querySql.append(" AND (RES.SUSPENSION_STATE_ = 1)");
        querySql.append(" union ");
        querySql.append(" select RES.* from ACT_RE_PROCDEF RES JOIN BPM_RE_PROCDEF_EXTRA REX ON RES.KEY_ = REX.PROC_DEF_KEY_ ");
        querySql.append(" AND REX.APPLY_BY_GROUP = 1 JOIN BPM_PROC_ROLE_RELATION prr ON RES.KEY_ = prr.PROC_DEF_KEY ");
        querySql.append(" JOIN BPM_PROC_ROLE pr ON prr.proc_role_code = pr.role_code AND pr.role_type = 'applicant' ");
        querySql.append(" JOIN ACT_ID_MEMBERSHIP M ON M.group_id_ = prr.group_id where m.user_id_ = #{userId} AND RES.VERSION_ = ( ");
        querySql.append(" select max(VERSION_) from ACT_RE_PROCDEF where KEY_ = RES.KEY_) AND (RES.SUSPENSION_STATE_ = 1) ");
        querySql.append(" ) RES order by RES.TENANT_ID_ ASC, RES.KEY_ ASC ");

        return repositoryService.createNativeProcessDefinitionQuery()
            .parameter("userId", userId)
            .sql(querySql.toString())
            .list();
    }

    @Override
    public Page findProcessDefinitionsByApplicant(AbstractSearchFormBean bean) {
        StringBuffer countSql = new StringBuffer(" select count(distinct id_) from ");
        StringBuffer querySql = new StringBuffer(" select * from ");
        StringBuffer orderBySql = new StringBuffer(" order by RES.TENANT_ID_ ASC, RES.KEY_ ASC ");

        StringBuffer majorSql = new StringBuffer();
        majorSql.append(" (select RES.* from ACT_RE_PROCDEF RES join BPM_RE_PROCDEF_EXTRA REX ON RES.KEY_ = REX.PROC_DEF_KEY_ ");
        majorSql.append(" AND REX.APPLY_BY_GROUP = 0 where RES.VERSION_ = (select max(VERSION_) from ACT_RE_PROCDEF where KEY_ = RES.KEY_) ");
        majorSql.append(" AND (RES.SUSPENSION_STATE_ = 1)");
        majorSql.append(" union ");
        majorSql.append(" select RES.* from ACT_RE_PROCDEF RES JOIN BPM_RE_PROCDEF_EXTRA REX ON RES.KEY_ = REX.PROC_DEF_KEY_ ");
        majorSql.append(" AND REX.APPLY_BY_GROUP = 1 JOIN BPM_PROC_ROLE_RELATION prr ON RES.KEY_ = prr.PROC_DEF_KEY ");
        majorSql.append(" JOIN BPM_PROC_ROLE pr ON prr.proc_role_code = pr.role_code AND pr.role_type = 'applicant' ");
        majorSql.append(" JOIN ACT_ID_MEMBERSHIP M ON M.group_id_ = prr.group_id where m.user_id_ = #{userId} AND RES.VERSION_ = ( ");
        majorSql.append(" select max(VERSION_) from ACT_RE_PROCDEF where KEY_ = RES.KEY_) AND (RES.SUSPENSION_STATE_ = 1) ");
        majorSql.append(" ) RES ");

        NativeProcessDefinitionQuery query = repositoryService.createNativeProcessDefinitionQuery();
        Map<String, Object> terms = bean.getSearchTerms();
        if (isNotEmpty(terms)) {
            Set<String> keySet = terms.keySet();
            for (String key : keySet) {
                query.parameter(key, terms.get(key));
            }
        }

        Page page = pageConfigurer.createPage(bean);

        if (page instanceof PageWritable) {
            // 计算总记录数
            query.sql(countSql.append(majorSql).toString());
            ((PageWritable) page).setTotalRecordsCount((int) query.count());

            // 计算结果记录
            query.sql(querySql.append(majorSql).append(orderBySql).toString());
            ((PageWritable) page).setRecords(
                query.listPage(_getFirstResult(bean.getPageNumber()), pageConfigurer.getPageSize())
            );
        }

        if (page instanceof PageComputable) {
            ((PageComputable) page).compute();
        }

        return page;
    }

    @Override
    public List<BpmRole> findUserRoles(String userId) {
        return bpmRoleDao.selectByUserId(userId);
    }

    @Override
    public User addUser(String userId) throws UserExistsException, DataPersistenceException {
        logger.debug("User want to add into system. [{}].", userId);

        // 创建新用户
        User user = identityService.newUser(userId);

        try {
            identityService.saveUser(user);
        } catch (RuntimeException e) {
            logger.error(e.getMessage());

            throw new UserExistsException("User has already been existed.");
        }

        // 导入用户到公共组中
        Group group = identityService.createGroupQuery()
            .groupId("public")
            .singleResult();

        if (group != null) {
            identityService.createMembership(userId, group.getId());
        }

        return user;
    }

    @Override
    public void deleteUser(String userId) throws DataPersistenceException {
        List<Group> groups = identityService.createNativeGroupQuery()
            .sql("select g.id_ from ACT_ID_GROUP g join ACT_ID_MEMBERSHIP m on g.id_ = m.group_id_ where m.user_id_ = #{userId}")
            .parameter("userId", userId)
            .list();

        for (Group group : groups) {
            identityService.deleteMembership(userId, group.getId());
        }

        identityService.deleteUser(userId);
    }

    @Override
    public boolean isTaskApprovedAutomatically(String processDefinitionKey, String taskDefinitionKey) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("procDefKey", processDefinitionKey);
        params.put("taskDefKey", taskDefinitionKey);

        return processAutoApprovalConfigDao.countBy(params) > 0;
    }

    private int _getFirstResult(int currentPage) {
        return (currentPage - 1) * pageConfigurer.getPageSize();
    }

    private StringBuffer _getQueryProcessDefinitionMajorSql() {
        StringBuffer majorSql = new StringBuffer();
        majorSql.append(" (select RES.* from ACT_RE_PROCDEF RES join BPM_RE_PROCDEF_EXTRA REX ON RES.KEY_ = REX.PROC_DEF_KEY_ ");
        majorSql.append(" AND REX.APPLY_BY_GROUP = 0 where RES.VERSION_ = (select max(VERSION_) from ACT_RE_PROCDEF where KEY_ = RES.KEY_) ");
        majorSql.append(" AND (RES.SUSPENSION_STATE_ = 1)");
        majorSql.append(" union ");
        majorSql.append(" select RES.* from ACT_RE_PROCDEF RES JOIN BPM_RE_PROCDEF_EXTRA REX ON RES.KEY_ = REX.PROC_DEF_KEY_ ");
        majorSql.append(" AND REX.APPLY_BY_GROUP = 1 JOIN BPM_PROC_ROLE_RELATION prr ON RES.KEY_ = prr.PROC_DEF_KEY ");
        majorSql.append(" JOIN BPM_PROC_ROLE pr ON prr.proc_role_code = pr.role_code AND pr.role_type = 'applicant' ");
        majorSql.append(" JOIN ACT_ID_MEMBERSHIP M ON M.group_id_ = prr.group_id where m.user_id_ = #{userId} AND RES.VERSION_ = ( ");
        majorSql.append(" select max(VERSION_) from ACT_RE_PROCDEF where KEY_ = RES.KEY_) AND (RES.SUSPENSION_STATE_ = 1) ");
        majorSql.append(" ) RES ");

        return majorSql;
    }
}
