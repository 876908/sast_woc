create database sast_woc;

use sast_woc;

set foreign_key_checks = 0;

/*--学院表--*/
create table academy (
                         id bigint primary key auto_increment comment '学院id',
                         name varchar(30) unique key not null comment '学院名称'
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='学院信息表';

/*--比赛表--*/
create table competition (
                             id bigint primary key comment '比赛id',
                             name varchar(50) not null comment '比赛名称',
                             description text comment '比赛描述',
                             max_team_members int comment '最多队员数',
                             min_team_members int comment '最少队员数',
                             work_code varchar(20) not null comment '活动负责⼈学⼯号',
                             start_time datetime comment '比赛开始时间',
                             end_time datetime comment '比赛结束时间',
                             review_begin_time datetime comment '评审开始时间',
                             review_end_time datetime comment '评审结束时间',
                             create_time datetime default current_timestamp comment '创建时间'
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='比赛信息表';

/*--用户表--*/
create table user (
                      id bigint primary key auto_increment comment '用户id',
                      user_code varchar(50) not null comment '账号',
                      password varchar(255) not null comment '密码',
                      name varchar(100) not null comment '用户姓名',
                      academy_id bigint default null comment '所属学院id',
                      role tinyint not null comment '角色: 0-队长, 1-评委, 2-二级管理员, 3-超级管理员',
                      com_id bigint default null comment '关联的比赛id',
                      unique key uk_user_code (user_code),
                      key idx_academy (academy_id),
                      key idx_role (role),
                      constraint fk_user_academy foreign key (academy_id) references academy(id)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='系统用户表';

/*--队伍表--*/
create table team (
                      id bigint primary key auto_increment comment '队伍id',
                      com_id bigint not null comment '所属比赛id',
                      name varchar(20) not null comment '队伍名称',
                      captain_id bigint not null comment '队长用户id',
                      captain_name varchar(10) not null comment '队长姓名',
                      status int default '0' comment '队伍状态 (0-正常, 1-已提交等)',
                      create_time datetime default current_timestamp comment '创建时间',
                      unique key uk_com_name (com_id, name) comment '同一比赛内队伍名唯一',
                      key idx_captain(captain_id),
                      key idx_com(com_id),
                      constraint fk_team_competition foreign key (com_id) references competition (id),
                      constraint fk_team_captain foreign key (captain_id) references user (id)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='队伍信息表';

/*--队员表--*/
create table member (
                        id bigint primary key comment '成员id',
                        team_id bigint not null comment '所属队伍id',
                        student_id varchar(50) not null comment '学生对应id',
                        name varchar(10) not null comment '成员姓名',
                        academy_id bigint not null comment '所属学院id',
                        phone varchar(20) default null comment '联系电话',
                        is_captain tinyint comment '是否为队长 (0-否, 1-是)',
                        unique key uk_team_student (team_id, student_id) comment '一个学生在同一队伍只能出现一次',
                        key idx_team (team_id),
                        key idx_student (student_id),
                        constraint fk_member_team foreign key (team_id) references team (id),
                        constraint fk_member_academy foreign key (academy_id) references academy (id)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='队员信息表';

/*--指导老师表--*/
create table instructor(
                           id bigint primary key auto_increment comment '指导老师id',
                           name varchar(10) not null comment '老师姓名',
                           work_code varchar(20) unique key not null comment '工号',
                           academy_id bigint not null comment '所属学院id',
                           phone varchar(20) default null comment '联系电话',
                           team_id bigint comment '所属队伍id',
                           key idx_academy (academy_id),
                           constraint fk_instructor_academy foreign key (academy_id) references academy (id),
                           constraint fk_instructor_team foreign key (team_id) references team(id)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='指导老师表';

/*--队伍-指导老师关联表--*/
create table team_instructor (
                                 id bigint primary key auto_increment comment '关联记录id',
                                 team_id bigint not null comment '队伍id',
                                 instructor_id bigint not null comment '指导老师id',
                                 unique key uk_team_instructor (team_id, instructor_id),
                                 constraint fk_ti_team foreign key (team_id) references team (id) on delete cascade,
                                 constraint fk_ti_instructor foreign key (instructor_id) references instructor (id) on delete cascade
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='队伍-指导老师关联表';

/*--评委-比赛关联表--*/
create table judge_competition (
                                   id bigint primary key auto_increment comment '关联记录id',
                                   judge_id varchar(50) not null comment '评委用户名',
                                   com_id bigint not null comment '比赛id',
                                   unique key uk_judge_com (judge_id, com_id),
                                   key idx_com (com_id),
                                   constraint fk_jc_judge foreign key (judge_id) references user (user_code) on delete cascade,
                                   constraint fk_jc_competition foreign key (com_id) references competition (id) on delete cascade
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='评委比赛关联表';

/*--作品表--*/
create table work (
                      id bigint primary key auto_increment comment '作品id',
                      name varchar(200) not null comment '作品名称',
                      team_id bigint not null comment '队伍id',
                      captain_student_id varchar(50) default null comment '队长学号',
                      status tinyint default '0' comment '作品状态',
                      submit_time datetime default current_timestamp comment '提交时间',
                      key idx_team (team_id),
                      constraint fk_work_team foreign key (team_id) references team (id)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='参赛作品表';

/*--评分表--*/
create table score (
                       id bigint primary key comment '评分记录id',
                       judge_id varchar(50) not null comment '评委用户id',
                       work_code varchar(50) not null comment '评委工号',
                       team_id bigint not null comment '队伍id',
                       com_id bigint not null comment '所比赛id',
                       score int check (score >= 0 and score <= 100) comment '评分',
                       comment text comment '评语',
                       create_time datetime default current_timestamp comment '评分时间',
                       update_time datetime default current_timestamp on update current_timestamp comment '更新时间',
                       unique key uk_judge_work (judge_id, work_code) comment '一个评委对同一作品只能评分一次',
                       key idx_team (team_id),
                       key idx_competition (com_id),
                       constraint fk_score_judge foreign key (judge_id) references user(user_code),
                       constraint fk_score_team foreign key (team_id) references team (id),
                       constraint fk_score_competition foreign key (com_id) references competition (id)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='评委评分表';













