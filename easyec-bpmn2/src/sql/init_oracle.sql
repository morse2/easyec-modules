insert into bpm_proc_role (ROLE_CODE, ROLE_NAME, ROLE_TYPE, DESCRIPTION, ENABLED) values ('FLOW_APPLICANT', 'Flow apply role', 'applicant', null, 1);
insert into bpm_proc_role (ROLE_CODE, ROLE_NAME, ROLE_TYPE, DESCRIPTION, ENABLED) values ('FLOW_ADMIN', 'Flow administrator', 'admin', null, 1);
insert into bpm_group (UIDPK, GROUP_NAME, DEFAULT_, ENABLED) values (1, 'Default Group', 1, 1);
insert into sequence_generator (ID_NAME, ID_VALUE) values ('SEQ_BPM_GROUP', '1');
