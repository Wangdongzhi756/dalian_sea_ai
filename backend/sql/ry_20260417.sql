-- ----------------------------
-- 1、部门表
-- ----------------------------
drop table if exists sys_dept;
create table sys_dept (
  dept_id           bigint(20)      not null auto_increment    comment '部门id',
  parent_id         bigint(20)      default 0                  comment '父部门id',
  ancestors         varchar(50)     default ''                 comment '祖级列表',
  dept_name         varchar(30)     default ''                 comment '部门名称',
  order_num         int(4)          default 0                  comment '显示顺序',
  leader            varchar(20)     default null               comment '负责人',
  phone             varchar(11)     default null               comment '联系电话',
  email             varchar(50)     default null               comment '邮箱',
  status            char(1)         default '0'                comment '部门状态（0正常 1停用）',
  del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (dept_id)
) engine=innodb auto_increment=200 comment = '部门表';

-- ----------------------------
-- 初始化-部门表数据
-- ----------------------------
insert into sys_dept values(100,  0,   '0',          '大连海科技',   0, '大连海', '15888888888', 'user@daliansea.ai', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(101,  100, '0,100',      '深圳总公司', 1, '大连海', '15888888888', 'user@daliansea.ai', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(102,  100, '0,100',      '长沙分公司', 2, '大连海', '15888888888', 'user@daliansea.ai', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(103,  101, '0,100,101',  '研发部门',   1, '大连海', '15888888888', 'user@daliansea.ai', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(104,  101, '0,100,101',  '市场部门',   2, '大连海', '15888888888', 'user@daliansea.ai', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(105,  101, '0,100,101',  '测试部门',   3, '大连海', '15888888888', 'user@daliansea.ai', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(106,  101, '0,100,101',  '财务部门',   4, '大连海', '15888888888', 'user@daliansea.ai', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(107,  101, '0,100,101',  '运维部门',   5, '大连海', '15888888888', 'user@daliansea.ai', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(108,  102, '0,100,102',  '市场部门',   1, '大连海', '15888888888', 'user@daliansea.ai', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(109,  102, '0,100,102',  '财务部门',   2, '大连海', '15888888888', 'user@daliansea.ai', '0', '0', 'admin', sysdate(), '', null);


-- ----------------------------
-- 2、用户信息表
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  user_id           bigint(20)      not null auto_increment    comment '用户ID',
  dept_id           bigint(20)      default null               comment '部门ID',
  user_name         varchar(30)     not null                   comment '用户账号',
  nick_name         varchar(30)     not null                   comment '用户昵称',
  user_type         varchar(2)      default '00'               comment '用户类型（00系统用户）',
  email             varchar(50)     default ''                 comment '用户邮箱',
  phonenumber       varchar(11)     default ''                 comment '手机号码',
  sex               char(1)         default '0'                comment '用户性别（0男 1女 2未知）',
  avatar            varchar(100)    default ''                 comment '头像地址',
  password          varchar(100)    default ''                 comment '密码',
  status            char(1)         default '0'                comment '账号状态（0正常 1停用）',
  del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  login_ip          varchar(128)    default ''                 comment '最后登录IP',
  login_date        datetime                                   comment '最后登录时间',
  pwd_update_date   datetime                                   comment '密码最后更新时间',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (user_id)
) engine=innodb auto_increment=100 comment = '用户信息表';

-- ----------------------------
-- 初始化-用户信息表数据
-- ----------------------------
insert into sys_user values(1,  103, 'admin', '大连海', '00', 'admin@daliansea.ai', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), sysdate(), 'admin', sysdate(), '', null, '管理员');
insert into sys_user values(2,  105, 'demo',    '大连海', '00', 'user@daliansea.ai',  '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), sysdate(), 'admin', sysdate(), '', null, '测试员');


-- ----------------------------
-- 3、岗位信息表
-- ----------------------------
drop table if exists sys_post;
create table sys_post
(
  post_id       bigint(20)      not null auto_increment    comment '岗位ID',
  post_code     varchar(64)     not null                   comment '岗位编码',
  post_name     varchar(50)     not null                   comment '岗位名称',
  post_sort     int(4)          not null                   comment '显示顺序',
  status        char(1)         not null                   comment '状态（0正常 1停用）',
  create_by     varchar(64)     default ''                 comment '创建者',
  create_time   datetime                                   comment '创建时间',
  update_by     varchar(64)     default ''			       comment '更新者',
  update_time   datetime                                   comment '更新时间',
  remark        varchar(500)    default null               comment '备注',
  primary key (post_id)
) engine=innodb comment = '岗位信息表';

-- ----------------------------
-- 初始化-岗位信息表数据
-- ----------------------------
insert into sys_post values(1, 'ceo',  '董事长',    1, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(2, 'se',   '项目经理',  2, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(3, 'hr',   '人力资源',  3, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(4, 'user', '普通员工',  4, '0', 'admin', sysdate(), '', null, '');

-- ----------------------------
-- 3-1、租户主体表
-- ----------------------------
drop table if exists sea_tenant;
create table sea_tenant (
  tenant_id       bigint(20)      not null auto_increment    comment '租户ID',
  tenant_code     varchar(64)     not null                   comment '租户编码',
  tenant_name     varchar(100)    not null                   comment '租户名称',
  contact_name    varchar(50)     default ''                 comment '联系人',
  contact_phone   varchar(20)     default ''                 comment '联系电话',
  contact_email   varchar(100)    default ''                 comment '联系邮箱',
  plan_type       varchar(32)     default 'standard'         comment '套餐类型',
  max_vessels     int(11)         default 0                  comment '船舶额度',
  expire_time     date            default null               comment '到期日期',
  status          char(1)         default '0'                comment '状态（0正常 1停用）',
  create_by       varchar(64)     default ''                 comment '创建者',
  create_time     datetime                                   comment '创建时间',
  update_by       varchar(64)     default ''                 comment '更新者',
  update_time     datetime                                   comment '更新时间',
  remark          varchar(500)    default null               comment '备注',
  primary key (tenant_id),
  unique key uk_sea_tenant_code (tenant_code)
) engine=innodb auto_increment=100 comment = '租户主体表';

insert into sea_tenant values(1, 'DLSEA', '大连海平台租户', '大连海', '15888888888', 'admin@daliansea.ai', 'standard', 20, '2027-12-31', '0', 'admin', sysdate(), '', null, '系统初始化租户');

-- ----------------------------
-- 3-2、船舶档案表
-- ----------------------------
drop table if exists sea_vessel;
create table sea_vessel (
  vessel_id       bigint(20)      not null auto_increment    comment '船舶ID',
  tenant_id       bigint(20)      not null                   comment '租户ID',
  vessel_code     varchar(64)     not null                   comment '船舶编码',
  vessel_name     varchar(100)    not null                   comment '船舶名称',
  vessel_type     varchar(32)     default 'sightseeing'      comment '船舶类型',
  capacity        int(11)         default 0                  comment '载客人数',
  home_port       varchar(100)    default ''                 comment '母港',
  captain_name    varchar(50)     default ''                 comment '船长',
  captain_phone   varchar(20)     default ''                 comment '船长电话',
  service_area    varchar(200)    default ''                 comment '服务区域',
  status          char(1)         default '0'                comment '状态（0正常 1停用）',
  create_by       varchar(64)     default ''                 comment '创建者',
  create_time     datetime                                   comment '创建时间',
  update_by       varchar(64)     default ''                 comment '更新者',
  update_time     datetime                                   comment '更新时间',
  remark          varchar(500)    default null               comment '备注',
  primary key (vessel_id),
  unique key uk_sea_vessel_code (vessel_code),
  key idx_sea_vessel_tenant (tenant_id)
) engine=innodb auto_increment=100 comment = '船舶档案表';

insert into sea_vessel values(1, 1, 'DLSEA-001', '大连海示范船', 'sightseeing', 12, '大连港', '示范船长', '15888888888', '大连近海观光', '0', 'admin', sysdate(), '', null, '初始化示范船舶');

-- ----------------------------
-- 3-3、船员档案表
-- ----------------------------
drop table if exists sea_crew;
create table sea_crew (
  crew_id         bigint(20)      not null auto_increment    comment '船员ID',
  tenant_id       bigint(20)      not null                   comment '租户ID',
  vessel_id       bigint(20)      default null               comment '船舶ID',
  crew_code       varchar(64)     not null                   comment '船员编码',
  crew_name       varchar(50)     not null                   comment '船员姓名',
  gender          char(1)         default '2'                comment '性别（0男 1女 2未知）',
  birth_date      date            default null               comment '出生日期',
  id_card         varchar(30)     default ''                 comment '证件号码',
  phone           varchar(20)     default ''                 comment '联系电话',
  position_name   varchar(50)     default ''                 comment '岗位职务',
  onboard_status  varchar(32)     default 'standby'          comment '在船状态',
  health_status   varchar(32)     default 'normal'           comment '健康状态',
  status          char(1)         default '0'                comment '状态（0正常 1停用）',
  create_by       varchar(64)     default ''                 comment '创建者',
  create_time     datetime                                   comment '创建时间',
  update_by       varchar(64)     default ''                 comment '更新者',
  update_time     datetime                                   comment '更新时间',
  remark          varchar(500)    default null               comment '备注',
  primary key (crew_id),
  unique key uk_sea_crew_code (crew_code),
  key idx_sea_crew_tenant (tenant_id),
  key idx_sea_crew_vessel (vessel_id)
) engine=innodb auto_increment=100 comment = '船员档案表';

insert into sea_crew values(1, 1, 1, 'DLSEA-CREW-001', '示范船员', '0', '1990-01-01', '210200199001010011', '15800000001', '船长', 'onboard', 'normal', '0', 'admin', sysdate(), '', null, '初始化示范船员');

-- ----------------------------
-- 3-4、船员证书档案表
-- ----------------------------
drop table if exists sea_crew_certificate;
create table sea_crew_certificate (
  certificate_id      bigint(20)      not null auto_increment    comment '证书ID',
  tenant_id           bigint(20)      not null                   comment '租户ID',
  crew_id             bigint(20)      not null                   comment '船员ID',
  certificate_type    varchar(32)     not null                   comment '证书类型',
  certificate_no      varchar(64)     not null                   comment '证书编号',
  issue_date          date            default null               comment '签发日期',
  expire_date         date            default null               comment '到期日期',
  issuing_authority   varchar(100)    default ''                 comment '签发机构',
  certificate_status  varchar(32)     default 'valid'            comment '证书状态',
  create_by           varchar(64)     default ''                 comment '创建者',
  create_time         datetime                                   comment '创建时间',
  update_by           varchar(64)     default ''                 comment '更新者',
  update_time         datetime                                   comment '更新时间',
  remark              varchar(500)    default null               comment '备注',
  primary key (certificate_id),
  unique key uk_sea_crew_certificate_no (certificate_no),
  key idx_sea_crew_certificate_tenant (tenant_id),
  key idx_sea_crew_certificate_crew (crew_id)
) engine=innodb auto_increment=100 comment = '船员证书档案表';

insert into sea_crew_certificate values(1, 1, 1, 'captain', 'DLSEA-CERT-001', '2026-01-01', '2028-12-31', '大连海事示范机构', 'valid', 'admin', sysdate(), '', null, '初始化船员证书');

-- ----------------------------
-- 3-5、船舶证照附件表
-- ----------------------------
drop table if exists sea_vessel_license;
create table sea_vessel_license (
  license_id         bigint(20)      not null auto_increment    comment '证照ID',
  tenant_id          bigint(20)      not null                   comment '租户ID',
  vessel_id          bigint(20)      not null                   comment '船舶ID',
  license_type       varchar(32)     not null                   comment '证照类型',
  license_no         varchar(64)     not null                   comment '证照编号',
  issue_date         date            default null               comment '签发日期',
  expire_date        date            default null               comment '到期日期',
  issuing_authority  varchar(100)    default ''                 comment '签发机构',
  attachment_name    varchar(200)    default ''                 comment '附件名称',
  attachment_url     varchar(500)    default ''                 comment '附件地址',
  license_status     varchar(32)     default 'valid'            comment '证照状态',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time        datetime                                   comment '创建时间',
  update_by          varchar(64)     default ''                 comment '更新者',
  update_time        datetime                                   comment '更新时间',
  remark             varchar(500)    default null               comment '备注',
  primary key (license_id),
  unique key uk_sea_vessel_license_no (license_no),
  key idx_sea_vessel_license_tenant (tenant_id),
  key idx_sea_vessel_license_vessel (vessel_id)
) engine=innodb auto_increment=100 comment = '船舶证照附件表';

insert into sea_vessel_license values(1, 1, 1, 'operation', 'DLSEA-LICENSE-001', '2026-01-01', '2028-12-31', '大连海事示范机构', '示范营运证.pdf', '', 'valid', 'admin', sysdate(), '', null, '初始化船舶证照');

-- ----------------------------
-- 3-6、业务申请表
-- ----------------------------
drop table if exists sea_business_application;
create table sea_business_application (
  application_id    bigint(20)      not null auto_increment    comment '申请ID',
  tenant_id         bigint(20)      not null                   comment '租户ID',
  vessel_id         bigint(20)      default null               comment '船舶ID',
  crew_id           bigint(20)      default null               comment '船员ID',
  application_no    varchar(64)     not null                   comment '申请单号',
  application_type  varchar(32)     not null                   comment '申请类型',
  title             varchar(100)    not null                   comment '申请标题',
  applicant_name    varchar(50)     not null                   comment '申请人',
  applicant_phone   varchar(20)     default ''                 comment '联系电话',
  priority          varchar(32)     default 'normal'           comment '紧急程度',
  content           varchar(1000)   default ''                 comment '申请内容',
  current_step      varchar(50)     default '待审核'           comment '当前节点',
  progress_percent  int(11)         default 0                  comment '进度百分比',
  status            varchar(32)     default 'submitted'        comment '申请状态',
  submitted_time    datetime                                   comment '提交时间',
  audit_by          varchar(64)     default ''                 comment '审核人',
  audit_time        datetime                                   comment '审核时间',
  audit_opinion     varchar(500)    default ''                 comment '审核意见',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (application_id),
  unique key uk_sea_business_application_no (application_no),
  key idx_sea_business_application_tenant (tenant_id),
  key idx_sea_business_application_vessel (vessel_id),
  key idx_sea_business_application_crew (crew_id),
  key idx_sea_business_application_status (status)
) engine=innodb auto_increment=100 comment = '业务申请表';

insert into sea_business_application values(1, 1, 1, 1, 'APP202607080001', 'vessel_service', '示范船舶业务申请', '示范船员', '15800000001', 'normal', '初始化示范业务申请', '待审核', 35, 'submitted', sysdate(), '', null, '', 'admin', sysdate(), '', null, '初始化业务申请');

-- ----------------------------
-- 3-7、业务申请进度表
-- ----------------------------
drop table if exists sea_business_progress;
create table sea_business_progress (
  progress_id     bigint(20)      not null auto_increment    comment '进度ID',
  application_id  bigint(20)      not null                   comment '申请ID',
  application_no  varchar(64)     not null                   comment '申请单号',
  step_name       varchar(50)     not null                   comment '节点名称',
  step_status     varchar(32)     not null                   comment '节点状态',
  handler_name    varchar(64)     default ''                 comment '处理人',
  handle_time     datetime                                   comment '处理时间',
  handle_opinion  varchar(500)    default ''                 comment '处理意见',
  create_by       varchar(64)     default ''                 comment '创建者',
  create_time     datetime                                   comment '创建时间',
  primary key (progress_id),
  key idx_sea_business_progress_application (application_id)
) engine=innodb auto_increment=100 comment = '业务申请进度表';

insert into sea_business_progress values(1, 1, 'APP202607080001', '已提交', 'submitted', 'admin', sysdate(), '申请已提交，等待平台审核', 'admin', sysdate());

-- ----------------------------
-- 3-8、业务通知表
-- ----------------------------
drop table if exists sea_business_notice;
create table sea_business_notice (
  notice_id       bigint(20)      not null auto_increment    comment '通知ID',
  application_id  bigint(20)      not null                   comment '申请ID',
  application_no  varchar(64)     not null                   comment '申请单号',
  notice_title    varchar(100)    not null                   comment '通知标题',
  notice_content  varchar(1000)   default ''                 comment '通知内容',
  notice_type     varchar(32)     default 'application'      comment '通知类型',
  read_flag       char(1)         default '0'                comment '是否已读（0未读 1已读）',
  receiver_name   varchar(64)     default ''                 comment '接收人',
  create_by       varchar(64)     default ''                 comment '创建者',
  create_time     datetime                                   comment '创建时间',
  primary key (notice_id),
  key idx_sea_business_notice_application (application_id),
  key idx_sea_business_notice_read (read_flag)
) engine=innodb auto_increment=100 comment = '业务通知表';

insert into sea_business_notice values(1, 1, 'APP202607080001', '业务申请已提交', '申请单 APP202607080001 已提交，当前等待审核。', 'application', '0', '示范船员', 'admin', sysdate());

-- ----------------------------
-- 3-9、AI知识库表
-- ----------------------------
drop table if exists sea_ai_knowledge;
create table sea_ai_knowledge (
  knowledge_id    bigint(20)      not null auto_increment    comment '知识ID',
  tenant_id       bigint(20)      not null                   comment '租户ID',
  title           varchar(100)    not null                   comment '知识标题',
  category        varchar(32)     not null                   comment '知识分类',
  source_type     varchar(32)     default 'manual'           comment '来源类型',
  content         text            not null                   comment '知识内容',
  tags            varchar(200)    default ''                 comment '标签',
  status          char(1)         default '0'                comment '状态（0启用 1停用）',
  create_by       varchar(64)     default ''                 comment '创建者',
  create_time     datetime                                   comment '创建时间',
  update_by       varchar(64)     default ''                 comment '更新者',
  update_time     datetime                                   comment '更新时间',
  remark          varchar(500)    default null               comment '备注',
  primary key (knowledge_id),
  key idx_sea_ai_knowledge_tenant (tenant_id),
  key idx_sea_ai_knowledge_category (category),
  key idx_sea_ai_knowledge_status (status)
) engine=innodb auto_increment=100 comment = 'AI知识库表';

insert into sea_ai_knowledge values(1, 1, '船舶营运证办理材料', 'operation', 'manual', '船舶营运证办理通常需要提交船舶基础档案、船舶检验证书、保险证明、船员适任证书、企业或船东主体资料，并确保船舶状态为正常。', '营运证,证照,船舶', '0', 'admin', sysdate(), '', null, '初始化海事知识');
insert into sea_ai_knowledge values(2, 1, '出海安全检查要点', 'safety', 'manual', '出海前需要检查天气海况、船舶证照、救生消防设备、通信设备、船员健康状态、航线计划和乘客承载人数，发现异常应先完成整改。', '安全,出海,检查', '0', 'admin', sysdate(), '', null, '初始化海事知识');

-- ----------------------------
-- 3-10、AI调用日志表
-- ----------------------------
drop table if exists sea_ai_call_log;
create table sea_ai_call_log (
  log_id           bigint(20)      not null auto_increment    comment '日志ID',
  tenant_id        bigint(20)      not null                   comment '租户ID',
  scene            varchar(32)     default 'knowledge_qa'     comment 'AI场景',
  question         varchar(1000)   default ''                 comment '用户问题',
  answer           text                                       comment 'AI回答',
  provider_name    varchar(64)     default 'local'            comment '供应商',
  model_name       varchar(100)    default 'knowledge-rule-v1' comment '模型名称',
  request_tokens   int(11)         default 0                  comment '请求Token',
  response_tokens  int(11)         default 0                  comment '响应Token',
  total_tokens     int(11)         default 0                  comment '总Token',
  latency_ms       bigint(20)      default 0                  comment '耗时毫秒',
  success_flag     char(1)         default '0'                comment '是否成功（0成功 1失败）',
  error_message    varchar(500)    default ''                 comment '错误信息',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  primary key (log_id),
  key idx_sea_ai_call_log_tenant (tenant_id),
  key idx_sea_ai_call_log_scene (scene),
  key idx_sea_ai_call_log_success (success_flag)
) engine=innodb auto_increment=100 comment = 'AI调用日志表';


-- ----------------------------
-- 4、角色信息表
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  role_id              bigint(20)      not null auto_increment    comment '角色ID',
  role_name            varchar(30)     not null                   comment '角色名称',
  role_key             varchar(100)    not null                   comment '角色权限字符串',
  role_sort            int(4)          not null                   comment '显示顺序',
  data_scope           char(1)         default '1'                comment '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  menu_check_strictly  tinyint(1)      default 1                  comment '菜单树选择项是否关联显示',
  dept_check_strictly  tinyint(1)      default 1                  comment '部门树选择项是否关联显示',
  status               char(1)         not null                   comment '角色状态（0正常 1停用）',
  del_flag             char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by            varchar(64)     default ''                 comment '创建者',
  create_time          datetime                                   comment '创建时间',
  update_by            varchar(64)     default ''                 comment '更新者',
  update_time          datetime                                   comment '更新时间',
  remark               varchar(500)    default null               comment '备注',
  primary key (role_id)
) engine=innodb auto_increment=100 comment = '角色信息表';

-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------
insert into sys_role values('1', '超级管理员',  'admin',  1, 1, 1, 1, '0', '0', 'admin', sysdate(), '', null, '超级管理员');
insert into sys_role values('2', '普通角色',    'common', 2, 2, 1, 1, '0', '0', 'admin', sysdate(), '', null, '普通角色');


-- ----------------------------
-- 5、菜单权限表
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu (
  menu_id           bigint(20)      not null auto_increment    comment '菜单ID',
  menu_name         varchar(50)     not null                   comment '菜单名称',
  parent_id         bigint(20)      default 0                  comment '父菜单ID',
  order_num         int(4)          default 0                  comment '显示顺序',
  path              varchar(200)    default ''                 comment '路由地址',
  component         varchar(255)    default null               comment '组件路径',
  query             varchar(255)    default null               comment '路由参数',
  route_name        varchar(50)     default ''                 comment '路由名称',
  is_frame          int(1)          default 1                  comment '是否为外链（0是 1否）',
  is_cache          int(1)          default 0                  comment '是否缓存（0缓存 1不缓存）',
  menu_type         char(1)         default ''                 comment '菜单类型（M目录 C菜单 F按钮）',
  visible           char(1)         default 0                  comment '菜单状态（0显示 1隐藏）',
  status            char(1)         default 0                  comment '菜单状态（0正常 1停用）',
  perms             varchar(100)    default null               comment '权限标识',
  icon              varchar(100)    default '#'                comment '菜单图标',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default ''                 comment '备注',
  primary key (menu_id)
) engine=innodb auto_increment=2000 comment = '菜单权限表';

-- ----------------------------
-- 初始化-菜单信息表数据
-- ----------------------------
-- 一级菜单
insert into sys_menu values('1', '系统管理', '0', '1', 'system',           null, '', '', 1, 0, 'M', '0', '0', '', 'system',   'admin', sysdate(), '', null, '系统管理目录');
insert into sys_menu values('2', '系统监控', '0', '2', 'monitor',          null, '', '', 1, 0, 'M', '0', '0', '', 'monitor',  'admin', sysdate(), '', null, '系统监控目录');
insert into sys_menu values('3', '业务管理', '0', '3', 'business',         null, '', '', 1, 0, 'M', '0', '0', '', 'component','admin', sysdate(), '', null, '业务管理目录');
insert into sys_menu values('4', 'AI管理',   '0', '4', 'ai',               null, '', '', 1, 0, 'M', '0', '0', '', 'education','admin', sysdate(), '', null, 'AI管理目录');
-- 二级菜单
insert into sys_menu values('100',  '用户管理', '1',   '1', 'user',       'system/user/index',        '', '', 1, 0, 'C', '0', '0', 'system:user:list',        'user',          'admin', sysdate(), '', null, '用户管理菜单');
insert into sys_menu values('101',  '角色管理', '1',   '2', 'role',       'system/role/index',        '', '', 1, 0, 'C', '0', '0', 'system:role:list',        'peoples',       'admin', sysdate(), '', null, '角色管理菜单');
insert into sys_menu values('102',  '菜单管理', '1',   '3', 'menu',       'system/menu/index',        '', '', 1, 0, 'C', '0', '0', 'system:menu:list',        'tree-table',    'admin', sysdate(), '', null, '菜单管理菜单');
insert into sys_menu values('103',  '部门管理', '1',   '4', 'dept',       'system/dept/index',        '', '', 1, 0, 'C', '0', '0', 'system:dept:list',        'tree',          'admin', sysdate(), '', null, '部门管理菜单');
insert into sys_menu values('104',  '岗位管理', '1',   '5', 'post',       'system/post/index',        '', '', 1, 0, 'C', '0', '0', 'system:post:list',        'post',          'admin', sysdate(), '', null, '岗位管理菜单');
insert into sys_menu values('105',  '字典管理', '1',   '6', 'dict',       'system/dict/index',        '', '', 1, 0, 'C', '0', '0', 'system:dict:list',        'dict',          'admin', sysdate(), '', null, '字典管理菜单');
insert into sys_menu values('106',  '参数设置', '1',   '7', 'config',     'system/config/index',      '', '', 1, 0, 'C', '0', '0', 'system:config:list',      'edit',          'admin', sysdate(), '', null, '参数设置菜单');
insert into sys_menu values('107',  '通知公告', '1',   '8', 'notice',     'system/notice/index',      '', '', 1, 0, 'C', '0', '0', 'system:notice:list',      'message',       'admin', sysdate(), '', null, '通知公告菜单');
insert into sys_menu values('108',  '日志管理', '1',   '9', 'log',        '',                         '', '', 1, 0, 'M', '0', '0', '',                        'log',           'admin', sysdate(), '', null, '日志管理菜单');
insert into sys_menu values('109',  '在线用户', '2',   '1', 'online',     'monitor/online/index',     '', '', 1, 0, 'C', '0', '0', 'monitor:online:list',     'online',        'admin', sysdate(), '', null, '在线用户菜单');
insert into sys_menu values('112',  '服务监控', '2',   '4', 'server',     'monitor/server/index',     '', '', 1, 0, 'C', '0', '0', 'monitor:server:list',     'server',        'admin', sysdate(), '', null, '服务监控菜单');
insert into sys_menu values('113',  '缓存监控', '2',   '5', 'cache',      'monitor/cache/index',      '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list',      'redis',         'admin', sysdate(), '', null, '缓存监控菜单');
insert into sys_menu values('114',  '缓存列表', '2',   '6', 'cacheList',  'monitor/cache/list',       '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list',      'redis-list',    'admin', sysdate(), '', null, '缓存列表菜单');
insert into sys_menu values('300',  '租户管理', '3',   '1', 'tenant',     'business/tenant/index',    '', '', 1, 0, 'C', '0', '0', 'business:tenant:list',   'peoples',       'admin', sysdate(), '', null, '租户管理菜单');
insert into sys_menu values('301',  '船舶管理', '3',   '2', 'vessel',     'business/vessel/index',    '', '', 1, 0, 'C', '0', '0', 'business:vessel:list',   'component',     'admin', sysdate(), '', null, '船舶管理菜单');
insert into sys_menu values('302',  '船员管理', '3',   '3', 'crew',       'business/crew/index',      '', '', 1, 0, 'C', '0', '0', 'business:crew:list',     'user',          'admin', sysdate(), '', null, '船员管理菜单');
insert into sys_menu values('303',  '船员证书', '3',   '4', 'crewCertificate', 'business/crewCertificate/index', '', '', 1, 0, 'C', '0', '0', 'business:crewCertificate:list', 'documentation', 'admin', sysdate(), '', null, '船员证书菜单');
insert into sys_menu values('304',  '船舶证照', '3',   '5', 'vesselLicense',   'business/vesselLicense/index',   '', '', 1, 0, 'C', '0', '0', 'business:vesselLicense:list',   'form',          'admin', sysdate(), '', null, '船舶证照菜单');
insert into sys_menu values('305',  '业务申请', '3',   '6', 'application', 'business/application/index', '', '', 1, 0, 'C', '0', '0', 'business:application:list', 'form',          'admin', sysdate(), '', null, '业务申请菜单');
insert into sys_menu values('400',  'AI助手',   '4',   '1', 'assistant', 'ai/assistant/index', '', '', 1, 0, 'C', '0', '0', 'ai:assistant:ask', 'message', 'admin', sysdate(), '', null, 'AI助手菜单');
insert into sys_menu values('401',  '知识库',   '4',   '2', 'knowledge', 'ai/knowledge/index', '', '', 1, 0, 'C', '0', '0', 'ai:knowledge:list', 'documentation', 'admin', sysdate(), '', null, 'AI知识库菜单');
insert into sys_menu values('402',  '调用日志', '4',   '3', 'callLog',   'ai/callLog/index',   '', '', 1, 0, 'C', '0', '0', 'ai:callLog:list',   'log', 'admin', sysdate(), '', null, 'AI调用日志菜单');
-- 三级菜单
insert into sys_menu values('500',  '操作日志', '108', '1', 'operlog',    'monitor/operlog/index',    '', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list',    'form',          'admin', sysdate(), '', null, '操作日志菜单');
insert into sys_menu values('501',  '登录日志', '108', '2', 'logininfor', 'monitor/logininfor/index', '', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor',    'admin', sysdate(), '', null, '登录日志菜单');
-- 用户管理按钮
insert into sys_menu values('1000', '用户查询', '100', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1001', '用户新增', '100', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1002', '用户修改', '100', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1003', '用户删除', '100', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1004', '用户导出', '100', '5',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:export',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1005', '用户导入', '100', '6',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:import',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1006', '重置密码', '100', '7',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd',       '#', 'admin', sysdate(), '', null, '');
-- 角色管理按钮
insert into sys_menu values('1007', '角色查询', '101', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1008', '角色新增', '101', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1009', '角色修改', '101', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1010', '角色删除', '101', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1011', '角色导出', '101', '5',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:export',         '#', 'admin', sysdate(), '', null, '');
-- 菜单管理按钮
insert into sys_menu values('1012', '菜单查询', '102', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1013', '菜单新增', '102', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1014', '菜单修改', '102', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1015', '菜单删除', '102', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove',         '#', 'admin', sysdate(), '', null, '');
-- 部门管理按钮
insert into sys_menu values('1016', '部门查询', '103', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1017', '部门新增', '103', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1018', '部门修改', '103', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1019', '部门删除', '103', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove',         '#', 'admin', sysdate(), '', null, '');
-- 岗位管理按钮
insert into sys_menu values('1020', '岗位查询', '104', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1021', '岗位新增', '104', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1022', '岗位修改', '104', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1023', '岗位删除', '104', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1024', '岗位导出', '104', '5',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:export',         '#', 'admin', sysdate(), '', null, '');
-- 字典管理按钮
insert into sys_menu values('1025', '字典查询', '105', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1026', '字典新增', '105', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1027', '字典修改', '105', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1028', '字典删除', '105', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1029', '字典导出', '105', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:export',         '#', 'admin', sysdate(), '', null, '');
-- 参数设置按钮
insert into sys_menu values('1030', '参数查询', '106', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:query',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1031', '参数新增', '106', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:add',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1032', '参数修改', '106', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:edit',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1033', '参数删除', '106', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:remove',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1034', '参数导出', '106', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:export',       '#', 'admin', sysdate(), '', null, '');
-- 通知公告按钮
insert into sys_menu values('1035', '公告查询', '107', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:query',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1036', '公告新增', '107', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:add',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1037', '公告修改', '107', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1038', '公告删除', '107', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove',       '#', 'admin', sysdate(), '', null, '');
-- 操作日志按钮
insert into sys_menu values('1039', '操作查询', '500', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query',      '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1040', '操作删除', '500', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove',     '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1041', '日志导出', '500', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export',     '#', 'admin', sysdate(), '', null, '');
-- 登录日志按钮
insert into sys_menu values('1042', '登录查询', '501', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1043', '登录删除', '501', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1044', '日志导出', '501', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1045', '账户解锁', '501', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock',  '#', 'admin', sysdate(), '', null, '');
-- 在线用户按钮
insert into sys_menu values('1046', '在线查询', '109', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1047', '批量强退', '109', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1048', '单条强退', '109', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', sysdate(), '', null, '');

-- 租户管理按钮
insert into sys_menu values('1100', '租户查询', '300', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:tenant:query',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1101', '租户新增', '300', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:tenant:add',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1102', '租户修改', '300', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:tenant:edit',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1103', '租户删除', '300', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:tenant:remove',      '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1104', '租户导出', '300', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:tenant:export',      '#', 'admin', sysdate(), '', null, '');

-- 船舶管理按钮
insert into sys_menu values('1110', '船舶查询', '301', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:vessel:query',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1111', '船舶新增', '301', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:vessel:add',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1112', '船舶修改', '301', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:vessel:edit',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1113', '船舶删除', '301', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:vessel:remove',      '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1114', '船舶导出', '301', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:vessel:export',      '#', 'admin', sysdate(), '', null, '');

-- 船员管理按钮
insert into sys_menu values('1120', '船员查询', '302', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:crew:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1121', '船员新增', '302', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:crew:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1122', '船员修改', '302', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:crew:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1123', '船员删除', '302', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:crew:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1124', '船员导出', '302', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:crew:export',         '#', 'admin', sysdate(), '', null, '');

-- 船员证书按钮
insert into sys_menu values('1130', '证书查询', '303', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:crewCertificate:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1131', '证书新增', '303', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:crewCertificate:add',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1132', '证书修改', '303', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:crewCertificate:edit',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1133', '证书删除', '303', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:crewCertificate:remove','#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1134', '证书导出', '303', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:crewCertificate:export','#', 'admin', sysdate(), '', null, '');

-- 船舶证照按钮
insert into sys_menu values('1140', '证照查询', '304', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:vesselLicense:query',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1141', '证照新增', '304', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:vesselLicense:add',    '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1142', '证照修改', '304', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:vesselLicense:edit',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1143', '证照删除', '304', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:vesselLicense:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1144', '证照导出', '304', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:vesselLicense:export', '#', 'admin', sysdate(), '', null, '');

-- 业务申请按钮
insert into sys_menu values('1150', '申请查询', '305', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:application:query',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1151', '申请新增', '305', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:application:add',    '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1152', '申请修改', '305', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:application:edit',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1153', '申请删除', '305', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:application:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1154', '申请导出', '305', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:application:export', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1155', '申请审核', '305', '6', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:application:audit',  '#', 'admin', sysdate(), '', null, '');

-- AI管理按钮
insert into sys_menu values('1160', '助手问答', '400', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'ai:assistant:ask', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1161', '知识查询', '401', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'ai:knowledge:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1162', '知识新增', '401', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'ai:knowledge:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1163', '知识修改', '401', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'ai:knowledge:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1164', '知识删除', '401', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'ai:knowledge:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1165', '知识导出', '401', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'ai:knowledge:export', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1170', '日志查询', '402', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'ai:callLog:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1171', '日志删除', '402', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'ai:callLog:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1172', '日志导出', '402', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'ai:callLog:export', '#', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 6、用户和角色关联表  用户N-1角色
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  user_id   bigint(20) not null comment '用户ID',
  role_id   bigint(20) not null comment '角色ID',
  primary key(user_id, role_id)
) engine=innodb comment = '用户和角色关联表';

-- ----------------------------
-- 初始化-用户和角色关联表数据
-- ----------------------------
insert into sys_user_role values ('1', '1');
insert into sys_user_role values ('2', '2');


-- ----------------------------
-- 7、角色和菜单关联表  角色1-N菜单
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id   bigint(20) not null comment '角色ID',
  menu_id   bigint(20) not null comment '菜单ID',
  primary key(role_id, menu_id)
) engine=innodb comment = '角色和菜单关联表';

-- ----------------------------
-- 初始化-角色和菜单关联表数据
-- ----------------------------
insert into sys_role_menu values ('2', '1');
insert into sys_role_menu values ('2', '2');
insert into sys_role_menu values ('2', '100');
insert into sys_role_menu values ('2', '101');
insert into sys_role_menu values ('2', '102');
insert into sys_role_menu values ('2', '103');
insert into sys_role_menu values ('2', '104');
insert into sys_role_menu values ('2', '105');
insert into sys_role_menu values ('2', '106');
insert into sys_role_menu values ('2', '107');
insert into sys_role_menu values ('2', '108');
insert into sys_role_menu values ('2', '109');
insert into sys_role_menu values ('2', '112');
insert into sys_role_menu values ('2', '113');
insert into sys_role_menu values ('2', '114');
insert into sys_role_menu values ('2', '3');
insert into sys_role_menu values ('2', '4');
insert into sys_role_menu values ('2', '300');
insert into sys_role_menu values ('2', '301');
insert into sys_role_menu values ('2', '302');
insert into sys_role_menu values ('2', '303');
insert into sys_role_menu values ('2', '304');
insert into sys_role_menu values ('2', '305');
insert into sys_role_menu values ('2', '400');
insert into sys_role_menu values ('2', '401');
insert into sys_role_menu values ('2', '402');
insert into sys_role_menu values ('2', '500');
insert into sys_role_menu values ('2', '501');
insert into sys_role_menu values ('2', '1000');
insert into sys_role_menu values ('2', '1001');
insert into sys_role_menu values ('2', '1002');
insert into sys_role_menu values ('2', '1003');
insert into sys_role_menu values ('2', '1004');
insert into sys_role_menu values ('2', '1005');
insert into sys_role_menu values ('2', '1006');
insert into sys_role_menu values ('2', '1007');
insert into sys_role_menu values ('2', '1008');
insert into sys_role_menu values ('2', '1009');
insert into sys_role_menu values ('2', '1010');
insert into sys_role_menu values ('2', '1011');
insert into sys_role_menu values ('2', '1012');
insert into sys_role_menu values ('2', '1013');
insert into sys_role_menu values ('2', '1014');
insert into sys_role_menu values ('2', '1015');
insert into sys_role_menu values ('2', '1016');
insert into sys_role_menu values ('2', '1017');
insert into sys_role_menu values ('2', '1018');
insert into sys_role_menu values ('2', '1019');
insert into sys_role_menu values ('2', '1020');
insert into sys_role_menu values ('2', '1021');
insert into sys_role_menu values ('2', '1022');
insert into sys_role_menu values ('2', '1023');
insert into sys_role_menu values ('2', '1024');
insert into sys_role_menu values ('2', '1025');
insert into sys_role_menu values ('2', '1026');
insert into sys_role_menu values ('2', '1027');
insert into sys_role_menu values ('2', '1028');
insert into sys_role_menu values ('2', '1029');
insert into sys_role_menu values ('2', '1030');
insert into sys_role_menu values ('2', '1031');
insert into sys_role_menu values ('2', '1032');
insert into sys_role_menu values ('2', '1033');
insert into sys_role_menu values ('2', '1034');
insert into sys_role_menu values ('2', '1035');
insert into sys_role_menu values ('2', '1036');
insert into sys_role_menu values ('2', '1037');
insert into sys_role_menu values ('2', '1038');
insert into sys_role_menu values ('2', '1039');
insert into sys_role_menu values ('2', '1040');
insert into sys_role_menu values ('2', '1041');
insert into sys_role_menu values ('2', '1042');
insert into sys_role_menu values ('2', '1043');
insert into sys_role_menu values ('2', '1044');
insert into sys_role_menu values ('2', '1045');
insert into sys_role_menu values ('2', '1046');
insert into sys_role_menu values ('2', '1047');
insert into sys_role_menu values ('2', '1048');
insert into sys_role_menu values ('2', '1100');
insert into sys_role_menu values ('2', '1101');
insert into sys_role_menu values ('2', '1102');
insert into sys_role_menu values ('2', '1103');
insert into sys_role_menu values ('2', '1104');
insert into sys_role_menu values ('2', '1110');
insert into sys_role_menu values ('2', '1111');
insert into sys_role_menu values ('2', '1112');
insert into sys_role_menu values ('2', '1113');
insert into sys_role_menu values ('2', '1114');
insert into sys_role_menu values ('2', '1120');
insert into sys_role_menu values ('2', '1121');
insert into sys_role_menu values ('2', '1122');
insert into sys_role_menu values ('2', '1123');
insert into sys_role_menu values ('2', '1124');
insert into sys_role_menu values ('2', '1130');
insert into sys_role_menu values ('2', '1131');
insert into sys_role_menu values ('2', '1132');
insert into sys_role_menu values ('2', '1133');
insert into sys_role_menu values ('2', '1134');
insert into sys_role_menu values ('2', '1140');
insert into sys_role_menu values ('2', '1141');
insert into sys_role_menu values ('2', '1142');
insert into sys_role_menu values ('2', '1143');
insert into sys_role_menu values ('2', '1144');
insert into sys_role_menu values ('2', '1150');
insert into sys_role_menu values ('2', '1151');
insert into sys_role_menu values ('2', '1152');
insert into sys_role_menu values ('2', '1153');
insert into sys_role_menu values ('2', '1154');
insert into sys_role_menu values ('2', '1155');
insert into sys_role_menu values ('2', '1160');
insert into sys_role_menu values ('2', '1161');
insert into sys_role_menu values ('2', '1162');
insert into sys_role_menu values ('2', '1163');
insert into sys_role_menu values ('2', '1164');
insert into sys_role_menu values ('2', '1165');
insert into sys_role_menu values ('2', '1170');
insert into sys_role_menu values ('2', '1171');
insert into sys_role_menu values ('2', '1172');

-- ----------------------------
-- 8、角色和部门关联表  角色1-N部门
-- ----------------------------
drop table if exists sys_role_dept;
create table sys_role_dept (
  role_id   bigint(20) not null comment '角色ID',
  dept_id   bigint(20) not null comment '部门ID',
  primary key(role_id, dept_id)
) engine=innodb comment = '角色和部门关联表';

-- ----------------------------
-- 初始化-角色和部门关联表数据
-- ----------------------------
insert into sys_role_dept values ('2', '100');
insert into sys_role_dept values ('2', '101');
insert into sys_role_dept values ('2', '105');


-- ----------------------------
-- 9、用户与岗位关联表  用户1-N岗位
-- ----------------------------
drop table if exists sys_user_post;
create table sys_user_post
(
  user_id   bigint(20) not null comment '用户ID',
  post_id   bigint(20) not null comment '岗位ID',
  primary key (user_id, post_id)
) engine=innodb comment = '用户与岗位关联表';

-- ----------------------------
-- 初始化-用户与岗位关联表数据
-- ----------------------------
insert into sys_user_post values ('1', '1');
insert into sys_user_post values ('2', '2');


-- ----------------------------
-- 10、操作日志记录
-- ----------------------------
drop table if exists sys_oper_log;
create table sys_oper_log (
  oper_id           bigint(20)      not null auto_increment    comment '日志主键',
  title             varchar(50)     default ''                 comment '模块标题',
  business_type     int(2)          default 0                  comment '业务类型（0其它 1新增 2修改 3删除）',
  method            varchar(200)    default ''                 comment '方法名称',
  request_method    varchar(10)     default ''                 comment '请求方式',
  operator_type     int(1)          default 0                  comment '操作类别（0其它 1后台用户 2手机端用户）',
  oper_name         varchar(50)     default ''                 comment '操作人员',
  dept_name         varchar(50)     default ''                 comment '部门名称',
  oper_url          varchar(255)    default ''                 comment '请求URL',
  oper_ip           varchar(128)    default ''                 comment '主机地址',
  oper_location     varchar(255)    default ''                 comment '操作地点',
  oper_param        varchar(2000)   default ''                 comment '请求参数',
  json_result       varchar(2000)   default ''                 comment '返回参数',
  status            int(1)          default 0                  comment '操作状态（0正常 1异常）',
  error_msg         varchar(2000)   default ''                 comment '错误消息',
  oper_time         datetime                                   comment '操作时间',
  cost_time         bigint(20)      default 0                  comment '消耗时间',
  primary key (oper_id),
  key idx_sys_oper_log_bt (business_type),
  key idx_sys_oper_log_s  (status),
  key idx_sys_oper_log_ot (oper_time)
) engine=innodb auto_increment=100 comment = '操作日志记录';


-- ----------------------------
-- 11、字典类型表
-- ----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type
(
  dict_id          bigint(20)      not null auto_increment    comment '字典主键',
  dict_name        varchar(100)    default ''                 comment '字典名称',
  dict_type        varchar(100)    default ''                 comment '字典类型',
  status           char(1)         default '0'                comment '状态（0正常 1停用）',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  remark           varchar(500)    default null               comment '备注',
  primary key (dict_id),
  unique (dict_type)
) engine=innodb auto_increment=100 comment = '字典类型表';

insert into sys_dict_type values(1,  '用户性别', 'sys_user_sex',        '0', 'admin', sysdate(), '', null, '用户性别列表');
insert into sys_dict_type values(2,  '菜单状态', 'sys_show_hide',       '0', 'admin', sysdate(), '', null, '菜单状态列表');
insert into sys_dict_type values(3,  '系统开关', 'sys_normal_disable',  '0', 'admin', sysdate(), '', null, '系统开关列表');
insert into sys_dict_type values(6,  '系统是否', 'sys_yes_no',          '0', 'admin', sysdate(), '', null, '系统是否列表');
insert into sys_dict_type values(7,  '通知类型', 'sys_notice_type',     '0', 'admin', sysdate(), '', null, '通知类型列表');
insert into sys_dict_type values(8,  '通知状态', 'sys_notice_status',   '0', 'admin', sysdate(), '', null, '通知状态列表');
insert into sys_dict_type values(9,  '操作类型', 'sys_oper_type',       '0', 'admin', sysdate(), '', null, '操作类型列表');
insert into sys_dict_type values(10, '系统状态', 'sys_common_status',   '0', 'admin', sysdate(), '', null, '登录状态列表');


-- ----------------------------
-- 12、字典数据表
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data
(
  dict_code        bigint(20)      not null auto_increment    comment '字典编码',
  dict_sort        int(4)          default 0                  comment '字典排序',
  dict_label       varchar(100)    default ''                 comment '字典标签',
  dict_value       varchar(100)    default ''                 comment '字典键值',
  dict_type        varchar(100)    default ''                 comment '字典类型',
  css_class        varchar(100)    default null               comment '样式属性（其他样式扩展）',
  list_class       varchar(100)    default null               comment '表格回显样式',
  is_default       char(1)         default 'N'                comment '是否默认（Y是 N否）',
  status           char(1)         default '0'                comment '状态（0正常 1停用）',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  remark           varchar(500)    default null               comment '备注',
  primary key (dict_code)
) engine=innodb auto_increment=100 comment = '字典数据表';

insert into sys_dict_data values(1,  1,  '男',       '0',       'sys_user_sex',        '',   '',        'Y', '0', 'admin', sysdate(), '', null, '性别男');
insert into sys_dict_data values(2,  2,  '女',       '1',       'sys_user_sex',        '',   '',        'N', '0', 'admin', sysdate(), '', null, '性别女');
insert into sys_dict_data values(3,  3,  '未知',     '2',       'sys_user_sex',        '',   '',        'N', '0', 'admin', sysdate(), '', null, '性别未知');
insert into sys_dict_data values(4,  1,  '显示',     '0',       'sys_show_hide',       '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '显示菜单');
insert into sys_dict_data values(5,  2,  '隐藏',     '1',       'sys_show_hide',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '隐藏菜单');
insert into sys_dict_data values(6,  1,  '正常',     '0',       'sys_normal_disable',  '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values(7,  2,  '停用',     '1',       'sys_normal_disable',  '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '停用状态');
insert into sys_dict_data values(12, 1,  '是',       'Y',       'sys_yes_no',          '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '系统默认是');
insert into sys_dict_data values(13, 2,  '否',       'N',       'sys_yes_no',          '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '系统默认否');
insert into sys_dict_data values(14, 1,  '通知',     '1',       'sys_notice_type',     '',   'warning', 'Y', '0', 'admin', sysdate(), '', null, '通知');
insert into sys_dict_data values(15, 2,  '公告',     '2',       'sys_notice_type',     '',   'success', 'N', '0', 'admin', sysdate(), '', null, '公告');
insert into sys_dict_data values(16, 1,  '正常',     '0',       'sys_notice_status',   '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values(17, 2,  '关闭',     '1',       'sys_notice_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '关闭状态');
insert into sys_dict_data values(18, 99, '其他',     '0',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, '其他操作');
insert into sys_dict_data values(19, 1,  '新增',     '1',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, '新增操作');
insert into sys_dict_data values(20, 2,  '修改',     '2',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, '修改操作');
insert into sys_dict_data values(21, 3,  '删除',     '3',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '删除操作');
insert into sys_dict_data values(22, 4,  '授权',     '4',       'sys_oper_type',       '',   'primary', 'N', '0', 'admin', sysdate(), '', null, '授权操作');
insert into sys_dict_data values(23, 5,  '导出',     '5',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '导出操作');
insert into sys_dict_data values(24, 6,  '导入',     '6',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '导入操作');
insert into sys_dict_data values(25, 7,  '强退',     '7',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '强退操作');
insert into sys_dict_data values(26, 8,  '生成代码', '8',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '生成操作');
insert into sys_dict_data values(27, 9,  '清空数据', '9',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '清空操作');
insert into sys_dict_data values(28, 1,  '成功',     '0',       'sys_common_status',   '',   'primary', 'N', '0', 'admin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values(29, 2,  '失败',     '1',       'sys_common_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '停用状态');


-- ----------------------------
-- 13、参数配置表
-- ----------------------------
drop table if exists sys_config;
create table sys_config (
  config_id         int(5)          not null auto_increment    comment '参数主键',
  config_name       varchar(100)    default ''                 comment '参数名称',
  config_key        varchar(100)    default ''                 comment '参数键名',
  config_value      varchar(500)    default ''                 comment '参数键值',
  config_type       char(1)         default 'N'                comment '系统内置（Y是 N否）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (config_id)
) engine=innodb auto_increment=100 comment = '参数配置表';

insert into sys_config values(1, '主框架页-默认皮肤样式名称',     'sys.index.skinName',               'skin-blue',     'Y', 'admin', sysdate(), '', null, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow' );
insert into sys_config values(2, '用户管理-账号初始密码',         'sys.user.initPassword',            '123456',        'Y', 'admin', sysdate(), '', null, '初始化密码 123456' );
insert into sys_config values(3, '主框架页-侧边栏主题',           'sys.index.sideTheme',              'theme-dark',    'Y', 'admin', sysdate(), '', null, '深色主题theme-dark，浅色主题theme-light' );
insert into sys_config values(4, '账号自助-验证码开关',           'sys.account.captchaEnabled',       'true',          'Y', 'admin', sysdate(), '', null, '是否开启验证码功能（true开启，false关闭）');
insert into sys_config values(5, '账号自助-是否开启用户注册功能', 'sys.account.registerUser',         'false',         'Y', 'admin', sysdate(), '', null, '是否开启注册用户功能（true开启，false关闭）');
insert into sys_config values(6, '用户登录-黑名单列表',           'sys.login.blackIPList',            '',              'Y', 'admin', sysdate(), '', null, '设置登录IP黑名单限制，多个匹配项以;分隔，支持匹配（*通配、网段）');
insert into sys_config values(7, '用户管理-初始密码修改策略',     'sys.account.initPasswordModify',   '1',             'Y', 'admin', sysdate(), '', null, '0：初始密码修改策略关闭，没有任何提示，1：提醒用户，如果未修改初始密码，则在登录时就会提醒修改密码对话框');
insert into sys_config values(8, '用户管理-账号密码更新周期',     'sys.account.passwordValidateDays', '0',             'Y', 'admin', sysdate(), '', null, '密码更新周期（填写数字，数据初始化值为0不限制，若修改必须为大于0小于365的正整数），如果超过这个周期登录系统时，则在登录时就会提醒修改密码对话框');
insert into sys_config values(9, '用户管理-密码字符范围',         'sys.account.chrtype',              '0',             'Y', 'admin', sysdate(), '', null, '默认任意字符范围，0任意（密码可以输入任意字符），1数字（密码只能为0-9数字），2英文字母（密码只能为a-z和A-Z字母），3字母和数字（密码必须包含字母，数字）,4字母数字和特殊字符（目前支持的特殊字符包括：~!@#$%^&*()-=_+）');


-- ----------------------------
-- 14、系统访问记录
-- ----------------------------
drop table if exists sys_logininfor;
create table sys_logininfor (
  info_id        bigint(20)     not null auto_increment   comment '访问ID',
  user_name      varchar(50)    default ''                comment '用户账号',
  ipaddr         varchar(128)   default ''                comment '登录IP地址',
  login_location varchar(255)   default ''                comment '登录地点',
  browser        varchar(50)    default ''                comment '浏览器类型',
  os             varchar(50)    default ''                comment '操作系统',
  status         char(1)        default '0'               comment '登录状态（0成功 1失败）',
  msg            varchar(255)   default ''                comment '提示消息',
  login_time     datetime                                 comment '访问时间',
  primary key (info_id),
  key idx_sys_logininfor_s  (status),
  key idx_sys_logininfor_lt (login_time)
) engine=innodb auto_increment=100 comment = '系统访问记录';


-- ----------------------------
-- 17、通知公告表
-- ----------------------------
drop table if exists sys_notice;
create table sys_notice (
  notice_id         int(4)          not null auto_increment    comment '公告ID',
  notice_title      varchar(50)     not null                   comment '公告标题',
  notice_type       char(1)         not null                   comment '公告类型（1通知 2公告）',
  notice_content    longblob        default null               comment '公告内容',
  status            char(1)         default '0'                comment '公告状态（0正常 1关闭）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(255)    default null               comment '备注',
  primary key (notice_id)
) engine=innodb auto_increment=10 comment = '通知公告表';

-- ----------------------------
-- 初始化-公告信息表数据
-- ----------------------------
insert into sys_notice values('1', '欢迎使用大连海 AI SaaS', '2', '新版本内容', '0', 'admin', sysdate(), '', null, '管理员');
insert into sys_notice values('2', '大连海 AI SaaS 初始化通知', '1', '维护内容',   '0', 'admin', sysdate(), '', null, '管理员');
insert into sys_notice values('3', '大连海 AI SaaS 项目说明', '1', '<p>大连海 AI SaaS 已完成基础权限、组织和日志能力初始化。</p>', '0', 'admin', sysdate(), '', null, '管理员');


-- ----------------------------
-- 18、公告已读记录表
-- ----------------------------
drop table if exists sys_notice_read;
create table sys_notice_read (
  read_id          bigint(20)       not null auto_increment    comment '已读主键',
  notice_id        int(4)           not null                   comment '公告id',
  user_id          bigint(20)       not null                   comment '用户id',
  read_time        datetime         not null                   comment '阅读时间',
  primary key (read_id),
  unique key uk_user_notice (user_id, notice_id)   comment '同一用户同一公告只记录一次'
) engine=innodb auto_increment=1 comment='公告已读记录表';


-- ----------------------------
