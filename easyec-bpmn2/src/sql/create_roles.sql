/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2014/11/12 22:02:41                          */
/*==============================================================*/


ALTER TABLE BPM_PROC_ROLE_RELATION
   DROP CONSTRAINT FK_BPM_PROC_REL_PROC__BPM_PROC;

ALTER TABLE BPM_USER_ROLE_RELATION
   DROP CONSTRAINT FK_BPM_USER_REL_USER__BPM_USER;

DROP TABLE BPM_PROC_ROLE CASCADE CONSTRAINTS;

DROP INDEX "BPM_PROC_ROLE_REL_GROUP_ID";

DROP INDEX "REL_PROC_ROLE_RELATION_FK";

DROP TABLE BPM_PROC_ROLE_RELATION CASCADE CONSTRAINTS;

DROP INDEX BPM_RE_PROCDEF_EXTRA_ABG;

DROP TABLE BPM_RE_PROCDEF_EXTRA CASCADE CONSTRAINTS;

DROP TABLE BPM_USER_ROLE CASCADE CONSTRAINTS;

DROP INDEX "BPM_USER_ROLE_REL_GROUP_ID";

DROP INDEX "REL_USER_ROLE_RELATION_FK";

DROP TABLE BPM_USER_ROLE_RELATION CASCADE CONSTRAINTS;

/*==============================================================*/
/* Table: BPM_PROC_ROLE                                         */
/*==============================================================*/
CREATE TABLE BPM_PROC_ROLE
(
   ROLE_CODE            VARCHAR2(64)         NOT NULL,
   ROLE_NAME            VARCHAR2(255)        NOT NULL,
   ROLE_TYPE            VARCHAR2(64)         NOT NULL,
   DESCRIPTION          VARCHAR2(512),
   ENABLED              SMALLINT             DEFAULT 1 NOT NULL,
   CONSTRAINT PK_BPM_PROC_ROLE PRIMARY KEY (ROLE_CODE)
);

/*==============================================================*/
/* Table: BPM_PROC_ROLE_RELATION                                */
/*==============================================================*/
CREATE TABLE BPM_PROC_ROLE_RELATION
(
   PROC_ROLE_CODE       VARCHAR2(64)         NOT NULL,
   GROUP_ID             VARCHAR2(64)         NOT NULL,
   PROC_DEF_KEY         VARCHAR2(512)        NOT NULL,
   CONSTRAINT PK_BPM_PROC_ROLE_RELATION PRIMARY KEY (PROC_ROLE_CODE, GROUP_ID, PROC_DEF_KEY)
);

COMMENT ON COLUMN BPM_PROC_ROLE_RELATION.PROC_DEF_KEY IS
'流程定义的KEY';

/*==============================================================*/
/* Index: "REL_PROC_ROLE_RELATION_FK"                           */
/*==============================================================*/
CREATE INDEX "REL_PROC_ROLE_RELATION_FK" ON BPM_PROC_ROLE_RELATION (
   PROC_ROLE_CODE ASC
);

/*==============================================================*/
/* Index: "BPM_PROC_ROLE_REL_GROUP_ID"                          */
/*==============================================================*/
CREATE INDEX "BPM_PROC_ROLE_REL_GROUP_ID" ON BPM_PROC_ROLE_RELATION (
   GROUP_ID ASC
);

/*==============================================================*/
/* Table: BPM_RE_PROCDEF_EXTRA                                  */
/*==============================================================*/
CREATE TABLE BPM_RE_PROCDEF_EXTRA
(
   PROC_DEF_KEY_        VARCHAR2(128)        NOT NULL,
   APPLY_BY_GROUP       SMALLINT             DEFAULT 1 NOT NULL,
   CONSTRAINT PK_BPM_RE_PROCDEF_EXTRA PRIMARY KEY (PROC_DEF_KEY_)
);

/*==============================================================*/
/* Index: BPM_RE_PROCDEF_EXTRA_ABG                              */
/*==============================================================*/
CREATE INDEX BPM_RE_PROCDEF_EXTRA_ABG ON BPM_RE_PROCDEF_EXTRA (
   APPLY_BY_GROUP ASC
);

/*==============================================================*/
/* Table: BPM_USER_ROLE                                         */
/*==============================================================*/
CREATE TABLE BPM_USER_ROLE
(
   USER_ROLE_CODE       VARCHAR2(128)        NOT NULL,
   ROLE_NAME            VARCHAR2(255)        NOT NULL,
   ROLE_TYPE            VARCHAR2(64)         NOT NULL,
   DESCRIPTION          VARCHAR2(512),
   ENABLED              SMALLINT             DEFAULT 1 NOT NULL,
   CONSTRAINT PK_BPM_USER_ROLE PRIMARY KEY (USER_ROLE_CODE)
);

/*==============================================================*/
/* Table: BPM_USER_ROLE_RELATION                                */
/*==============================================================*/
CREATE TABLE BPM_USER_ROLE_RELATION
(
   USER_ROLE_CODE       VARCHAR2(128)        NOT NULL,
   GROUP_ID             VARCHAR2(64)         NOT NULL,
   CONSTRAINT PK_BPM_USER_ROLE_RELATION PRIMARY KEY (USER_ROLE_CODE, GROUP_ID)
);

/*==============================================================*/
/* Index: "REL_USER_ROLE_RELATION_FK"                           */
/*==============================================================*/
CREATE INDEX "REL_USER_ROLE_RELATION_FK" ON BPM_USER_ROLE_RELATION (
   USER_ROLE_CODE ASC
);

/*==============================================================*/
/* Index: "BPM_USER_ROLE_REL_GROUP_ID"                          */
/*==============================================================*/
CREATE INDEX "BPM_USER_ROLE_REL_GROUP_ID" ON BPM_USER_ROLE_RELATION (
   GROUP_ID ASC
);

ALTER TABLE BPM_PROC_ROLE_RELATION
   ADD CONSTRAINT FK_BPM_PROC_REL_PROC__BPM_PROC FOREIGN KEY (PROC_ROLE_CODE)
      REFERENCES BPM_PROC_ROLE (ROLE_CODE);

ALTER TABLE BPM_USER_ROLE_RELATION
   ADD CONSTRAINT FK_BPM_USER_REL_USER__BPM_USER FOREIGN KEY (USER_ROLE_CODE)
      REFERENCES BPM_USER_ROLE (USER_ROLE_CODE);

