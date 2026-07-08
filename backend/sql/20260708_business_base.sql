-- 大连海 AI SaaS 业务基础表与菜单增量脚本
-- 适用于已经导入过基础系统表的数据库。

create table if not exists sea_tenant (
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

create table if not exists sea_vessel (
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

create table if not exists sea_crew (
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

create table if not exists sea_crew_certificate (
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

create table if not exists sea_vessel_license (
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

create table if not exists sea_business_application (
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

create table if not exists sea_business_progress (
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

create table if not exists sea_business_notice (
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

create table if not exists sea_ai_knowledge (
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

create table if not exists sea_ai_call_log (
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

insert ignore into sea_tenant values(1, 'DLSEA', '大连海平台租户', '大连海', '15888888888', 'admin@daliansea.ai', 'standard', 20, '2027-12-31', '0', 'admin', sysdate(), '', null, '系统初始化租户');
insert ignore into sea_vessel values(1, 1, 'DLSEA-001', '大连海示范船', 'sightseeing', 12, '大连港', '示范船长', '15888888888', '大连近海观光', '0', 'admin', sysdate(), '', null, '初始化示范船舶');
insert ignore into sea_crew values(1, 1, 1, 'DLSEA-CREW-001', '示范船员', '0', '1990-01-01', '210200199001010011', '15800000001', '船长', 'onboard', 'normal', '0', 'admin', sysdate(), '', null, '初始化示范船员');
insert ignore into sea_crew_certificate values(1, 1, 1, 'captain', 'DLSEA-CERT-001', '2026-01-01', '2028-12-31', '大连海事示范机构', 'valid', 'admin', sysdate(), '', null, '初始化船员证书');
insert ignore into sea_vessel_license values(1, 1, 1, 'operation', 'DLSEA-LICENSE-001', '2026-01-01', '2028-12-31', '大连海事示范机构', '示范营运证.pdf', '', 'valid', 'admin', sysdate(), '', null, '初始化船舶证照');
insert ignore into sea_business_application values(1, 1, 1, 1, 'APP202607080001', 'vessel_service', '示范船舶业务申请', '示范船员', '15800000001', 'normal', '初始化示范业务申请', '待审核', 35, 'submitted', sysdate(), '', null, '', 'admin', sysdate(), '', null, '初始化业务申请');
insert ignore into sea_business_progress values(1, 1, 'APP202607080001', '已提交', 'submitted', 'admin', sysdate(), '申请已提交，等待平台审核', 'admin', sysdate());
insert ignore into sea_business_notice values(1, 1, 'APP202607080001', '业务申请已提交', '申请单 APP202607080001 已提交，当前等待审核。', 'application', '0', '示范船员', 'admin', sysdate());
insert ignore into sea_ai_knowledge values(1, 1, '船舶营运证办理材料', 'operation', 'manual', '船舶营运证办理通常需要提交船舶基础档案、船舶检验证书、保险证明、船员适任证书、企业或船东主体资料，并确保船舶状态为正常。', '营运证,证照,船舶', '0', 'admin', sysdate(), '', null, '初始化海事知识');
insert ignore into sea_ai_knowledge values(2, 1, '出海安全检查要点', 'safety', 'manual', '出海前需要检查天气海况、船舶证照、救生消防设备、通信设备、船员健康状态、航线计划和乘客承载人数，发现异常应先完成整改。', '安全,出海,检查', '0', 'admin', sysdate(), '', null, '初始化海事知识');

insert ignore into sys_menu values('3', '业务管理', '0', '3', 'business', null, '', '', 1, 0, 'M', '0', '0', '', 'component','admin', sysdate(), '', null, '业务管理目录');
insert ignore into sys_menu values('4', 'AI管理', '0', '4', 'ai', null, '', '', 1, 0, 'M', '0', '0', '', 'education','admin', sysdate(), '', null, 'AI管理目录');
insert ignore into sys_menu values('300', '租户管理', '3', '1', 'tenant', 'business/tenant/index', '', '', 1, 0, 'C', '0', '0', 'business:tenant:list', 'peoples', 'admin', sysdate(), '', null, '租户管理菜单');
insert ignore into sys_menu values('301', '船舶管理', '3', '2', 'vessel', 'business/vessel/index', '', '', 1, 0, 'C', '0', '0', 'business:vessel:list', 'component', 'admin', sysdate(), '', null, '船舶管理菜单');
insert ignore into sys_menu values('302', '船员管理', '3', '3', 'crew', 'business/crew/index', '', '', 1, 0, 'C', '0', '0', 'business:crew:list', 'user', 'admin', sysdate(), '', null, '船员管理菜单');
insert ignore into sys_menu values('303', '船员证书', '3', '4', 'crewCertificate', 'business/crewCertificate/index', '', '', 1, 0, 'C', '0', '0', 'business:crewCertificate:list', 'documentation', 'admin', sysdate(), '', null, '船员证书菜单');
insert ignore into sys_menu values('304', '船舶证照', '3', '5', 'vesselLicense', 'business/vesselLicense/index', '', '', 1, 0, 'C', '0', '0', 'business:vesselLicense:list', 'form', 'admin', sysdate(), '', null, '船舶证照菜单');
insert ignore into sys_menu values('305', '业务申请', '3', '6', 'application', 'business/application/index', '', '', 1, 0, 'C', '0', '0', 'business:application:list', 'form', 'admin', sysdate(), '', null, '业务申请菜单');
insert ignore into sys_menu values('400', 'AI助手', '4', '1', 'assistant', 'ai/assistant/index', '', '', 1, 0, 'C', '0', '0', 'ai:assistant:ask', 'message', 'admin', sysdate(), '', null, 'AI助手菜单');
insert ignore into sys_menu values('401', '知识库', '4', '2', 'knowledge', 'ai/knowledge/index', '', '', 1, 0, 'C', '0', '0', 'ai:knowledge:list', 'documentation', 'admin', sysdate(), '', null, 'AI知识库菜单');
insert ignore into sys_menu values('402', '调用日志', '4', '3', 'callLog', 'ai/callLog/index', '', '', 1, 0, 'C', '0', '0', 'ai:callLog:list', 'log', 'admin', sysdate(), '', null, 'AI调用日志菜单');

insert ignore into sys_menu values('1100', '租户查询', '300', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:tenant:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1101', '租户新增', '300', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:tenant:add', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1102', '租户修改', '300', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:tenant:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1103', '租户删除', '300', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:tenant:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1104', '租户导出', '300', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:tenant:export', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1110', '船舶查询', '301', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:vessel:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1111', '船舶新增', '301', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:vessel:add', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1112', '船舶修改', '301', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:vessel:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1113', '船舶删除', '301', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:vessel:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1114', '船舶导出', '301', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:vessel:export', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1120', '船员查询', '302', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:crew:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1121', '船员新增', '302', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:crew:add', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1122', '船员修改', '302', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:crew:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1123', '船员删除', '302', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:crew:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1124', '船员导出', '302', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:crew:export', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1130', '证书查询', '303', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:crewCertificate:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1131', '证书新增', '303', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:crewCertificate:add', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1132', '证书修改', '303', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:crewCertificate:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1133', '证书删除', '303', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:crewCertificate:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1134', '证书导出', '303', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:crewCertificate:export', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1140', '证照查询', '304', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:vesselLicense:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1141', '证照新增', '304', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:vesselLicense:add', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1142', '证照修改', '304', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:vesselLicense:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1143', '证照删除', '304', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:vesselLicense:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1144', '证照导出', '304', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:vesselLicense:export', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1150', '申请查询', '305', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:application:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1151', '申请新增', '305', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:application:add', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1152', '申请修改', '305', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:application:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1153', '申请删除', '305', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:application:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1154', '申请导出', '305', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:application:export', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1155', '申请审核', '305', '6', '#', '', '', '', 1, 0, 'F', '0', '0', 'business:application:audit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1160', '助手问答', '400', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'ai:assistant:ask', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1161', '知识查询', '401', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'ai:knowledge:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1162', '知识新增', '401', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'ai:knowledge:add', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1163', '知识修改', '401', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'ai:knowledge:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1164', '知识删除', '401', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'ai:knowledge:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1165', '知识导出', '401', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'ai:knowledge:export', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1170', '日志查询', '402', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'ai:callLog:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1171', '日志删除', '402', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'ai:callLog:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values('1172', '日志导出', '402', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'ai:callLog:export', '#', 'admin', sysdate(), '', null, '');

insert ignore into sys_role_menu values ('2', '3');
insert ignore into sys_role_menu values ('2', '4');
insert ignore into sys_role_menu values ('2', '300');
insert ignore into sys_role_menu values ('2', '301');
insert ignore into sys_role_menu values ('2', '302');
insert ignore into sys_role_menu values ('2', '303');
insert ignore into sys_role_menu values ('2', '304');
insert ignore into sys_role_menu values ('2', '305');
insert ignore into sys_role_menu values ('2', '400');
insert ignore into sys_role_menu values ('2', '401');
insert ignore into sys_role_menu values ('2', '402');
insert ignore into sys_role_menu values ('2', '1100');
insert ignore into sys_role_menu values ('2', '1101');
insert ignore into sys_role_menu values ('2', '1102');
insert ignore into sys_role_menu values ('2', '1103');
insert ignore into sys_role_menu values ('2', '1104');
insert ignore into sys_role_menu values ('2', '1110');
insert ignore into sys_role_menu values ('2', '1111');
insert ignore into sys_role_menu values ('2', '1112');
insert ignore into sys_role_menu values ('2', '1113');
insert ignore into sys_role_menu values ('2', '1114');
insert ignore into sys_role_menu values ('2', '1120');
insert ignore into sys_role_menu values ('2', '1121');
insert ignore into sys_role_menu values ('2', '1122');
insert ignore into sys_role_menu values ('2', '1123');
insert ignore into sys_role_menu values ('2', '1124');
insert ignore into sys_role_menu values ('2', '1130');
insert ignore into sys_role_menu values ('2', '1131');
insert ignore into sys_role_menu values ('2', '1132');
insert ignore into sys_role_menu values ('2', '1133');
insert ignore into sys_role_menu values ('2', '1134');
insert ignore into sys_role_menu values ('2', '1140');
insert ignore into sys_role_menu values ('2', '1141');
insert ignore into sys_role_menu values ('2', '1142');
insert ignore into sys_role_menu values ('2', '1143');
insert ignore into sys_role_menu values ('2', '1144');
insert ignore into sys_role_menu values ('2', '1150');
insert ignore into sys_role_menu values ('2', '1151');
insert ignore into sys_role_menu values ('2', '1152');
insert ignore into sys_role_menu values ('2', '1153');
insert ignore into sys_role_menu values ('2', '1154');
insert ignore into sys_role_menu values ('2', '1155');
insert ignore into sys_role_menu values ('2', '1160');
insert ignore into sys_role_menu values ('2', '1161');
insert ignore into sys_role_menu values ('2', '1162');
insert ignore into sys_role_menu values ('2', '1163');
insert ignore into sys_role_menu values ('2', '1164');
insert ignore into sys_role_menu values ('2', '1165');
insert ignore into sys_role_menu values ('2', '1170');
insert ignore into sys_role_menu values ('2', '1171');
insert ignore into sys_role_menu values ('2', '1172');
