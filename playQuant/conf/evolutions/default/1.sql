# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table history_day (
  id                            bigint auto_increment not null,
  code                          VARCHAR(6) COMMENT '股票代码, 6位数字代码，或者指数代码 [sh=上证指数 sz=深圳成指 hs300=沪深300指数 sz50=上证50 zxb=中小板 cyb=创业板]' not null,
  t_date                        DATETIME COMMENT '交易日期' not null,
  open                          decimal(20,4) COMMENT '开盘价',
  high                          decimal(20,4) COMMENT '最高价',
  close                         decimal(20,4) COMMENT '收盘价',
  low                           decimal(20,4) COMMENT '最低价',
  volume                        decimal(20,4) COMMENT '成交量',
  price_change                  decimal(20,4) COMMENT '价格变动',
  ma5                           decimal(20,4) COMMENT '5日均价',
  ma10                          decimal(20,4) COMMENT '10日均价',
  ma20                          decimal(20,4) COMMENT '20日均价',
  v_ma5                         decimal(20,4) COMMENT '5日均量',
  v_ma10                        decimal(20,4) COMMENT '10日均量',
  v_ma20                        decimal(20,4) COMMENT '20日均量',
  turnover                      decimal(20,4) COMMENT '换手率 [注：指数无此项]',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  constraint pk_history_day primary key (id)
);

create table plan_task (
  id                            bigint auto_increment not null,
  require_seq                   tinyint(1) DEFAULT '1' COMMENT '是否要求顺序执行',
  seq_type                      varchar(64) DEFAULT 'global_seq' COMMENT '顺序执行的类别' not null,
  plan_run_time                 DATETIME COMMENT '任务计划执行时间' not null,
  task_status                   INTEGER DEFAULT 0 COMMENT '任务状态: 0:WaitingInDB, 7:WaitingInQueue, 8:Error' not null,
  class_name                    varchar(1024) COMMENT 'Runnable task class name' not null,
  json_data                     TEXT COMMENT 'Runnable task class json data' not null,
  tag                           TEXT COMMENT '标签,用于保存任务相关的额外数据',
  remarks                       TEXT COMMENT '发生异常情况的时候, 用于记录额外信息',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  constraint pk_plan_task primary key (id)
);


# --- !Downs

drop table if exists history_day;

drop table if exists plan_task;

