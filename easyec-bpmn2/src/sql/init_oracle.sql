-- 初始化流程角色的SQL
insert into bpm_proc_role (ROLE_CODE, ROLE_NAME, ROLE_TYPE, DESCRIPTION, ENABLED) values ('FLOW_APPLICANT', 'Flow apply role', 'applicant', null, 1);
insert into bpm_proc_role (ROLE_CODE, ROLE_NAME, ROLE_TYPE, DESCRIPTION, ENABLED) values ('FLOW_ADMIN', 'Flow administrator', 'admin', null, 1);

-- 初始化功能角色的SQL
insert into bpm_user_role (USER_ROLE_CODE, ROLE_NAME, ROLE_TYPE, DESCRIPTION, ENABLED) values ('SYS_ADMIN', 'System administrator', 'sys_admin', null, 1);
