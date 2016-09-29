# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table big_trade (
  id                            bigint auto_increment not null,
  level_vol                     INT(8) COMMENT '手数,默认为400手',
  code                          VARCHAR(6) COMMENT '股票代码, 6位数字代码' not null,
  c_name                        varchar(16) COMMENT '股票名称' not null,
  t_time                        DATETIME COMMENT '交易日期时间' not null,
  price                         decimal(20,4) COMMENT '成交价格',
  volume                        decimal(20,4) COMMENT '成交手',
  preprice                      decimal(20,4) COMMENT '上一笔价格',
  t_type                        VARCHAR(4) COMMENT '买卖类型[买盘,卖盘,中性盘]',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  constraint pk_big_trade primary key (id)
);

create table cashflow_data (
  id                            bigint auto_increment not null,
  code                          VARCHAR(6) COMMENT '股票代码, 6位数字代码' not null,
  c_name                        varchar(16) COMMENT '股票名称' not null,
  report_year                   INTEGER COMMENT '年度,4位数字',
  report_season                 INTEGER COMMENT '季度: 1~4',
  cf_sales                      decimal(20,4) COMMENT '经营现金净流量对销售收入比率',
  rateofreturn                  decimal(20,4) COMMENT '资产的经营现金流量回报率',
  cf_nm                         decimal(20,4) COMMENT '经营现金净流量与净利润的比率',
  cf_liabilities                decimal(20,4) COMMENT '经营现金净流量对负债比率',
  cashflowratio                 decimal(20,4) COMMENT '现金流量比率',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  constraint pk_cashflow_data primary key (id)
);

create table debtpaying_data (
  id                            bigint auto_increment not null,
  code                          VARCHAR(6) COMMENT '股票代码, 6位数字代码' not null,
  c_name                        varchar(16) COMMENT '股票名称' not null,
  report_year                   INTEGER COMMENT '年度,4位数字',
  report_season                 INTEGER COMMENT '季度: 1~4',
  currentratio                  decimal(20,4) COMMENT '流动比率',
  quickratio                    decimal(20,4) COMMENT '速动比率',
  cashratio                     decimal(20,4) COMMENT '现金比率',
  icratio                       decimal(20,4) COMMENT '利息支付倍数',
  sheqratio                     decimal(20,4) COMMENT '股东权益比率',
  adratio                       decimal(20,4) COMMENT '股东权益增长率',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  constraint pk_debtpaying_data primary key (id)
);

create table growth_data (
  id                            bigint auto_increment not null,
  code                          VARCHAR(6) COMMENT '股票代码, 6位数字代码' not null,
  c_name                        varchar(16) COMMENT '股票名称' not null,
  report_year                   INTEGER COMMENT '年度,4位数字',
  report_season                 INTEGER COMMENT '季度: 1~4',
  mbrg                          DECIMAL(20,4) COMMENT '主营业务收入增长率(%)',
  nprg                          DECIMAL(20,4) COMMENT '净利润增长率(%)',
  nav                           decimal(20,4) COMMENT '净资产增长率',
  targ                          decimal(20,4) COMMENT '总资产增长率',
  epsg                          decimal(20,4) COMMENT '每股收益增长率',
  seg                           decimal(20,4) COMMENT '股东权益增长率',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  constraint pk_growth_data primary key (id)
);

create table history15min (
  id                            bigint auto_increment not null,
  code                          VARCHAR(6) COMMENT '股票代码, 6位数字代码，或者指数代码 [sh=上证指数 sz=深圳成指 hs300=沪深300指数 sz50=上证50 zxb=中小板 cyb=创业板]' not null,
  t_date                        DATETIME COMMENT '交易日期' not null,
  open                          decimal(20,4) COMMENT '开盘价',
  high                          decimal(20,4) COMMENT '最高价',
  close                         decimal(20,4) COMMENT '收盘价',
  low                           decimal(20,4) COMMENT '最低价',
  volume                        decimal(20,4) COMMENT '成交量',
  price_change                  decimal(20,4) COMMENT '价格变动',
  p_chage                       decimal(20,4) COMMENT '涨跌幅',
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
  constraint pk_history15min primary key (id)
);

create table history30min (
  id                            bigint auto_increment not null,
  code                          VARCHAR(6) COMMENT '股票代码, 6位数字代码，或者指数代码 [sh=上证指数 sz=深圳成指 hs300=沪深300指数 sz50=上证50 zxb=中小板 cyb=创业板]' not null,
  t_date                        DATETIME COMMENT '交易日期' not null,
  open                          decimal(20,4) COMMENT '开盘价',
  high                          decimal(20,4) COMMENT '最高价',
  close                         decimal(20,4) COMMENT '收盘价',
  low                           decimal(20,4) COMMENT '最低价',
  volume                        decimal(20,4) COMMENT '成交量',
  price_change                  decimal(20,4) COMMENT '价格变动',
  p_chage                       decimal(20,4) COMMENT '涨跌幅',
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
  constraint pk_history30min primary key (id)
);

create table history5min (
  id                            bigint auto_increment not null,
  code                          VARCHAR(6) COMMENT '股票代码, 6位数字代码，或者指数代码 [sh=上证指数 sz=深圳成指 hs300=沪深300指数 sz50=上证50 zxb=中小板 cyb=创业板]' not null,
  t_date                        DATETIME COMMENT '交易日期' not null,
  open                          decimal(20,4) COMMENT '开盘价',
  high                          decimal(20,4) COMMENT '最高价',
  close                         decimal(20,4) COMMENT '收盘价',
  low                           decimal(20,4) COMMENT '最低价',
  volume                        decimal(20,4) COMMENT '成交量',
  price_change                  decimal(20,4) COMMENT '价格变动',
  p_chage                       decimal(20,4) COMMENT '涨跌幅',
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
  constraint pk_history5min primary key (id)
);

create table history60min (
  id                            bigint auto_increment not null,
  code                          VARCHAR(6) COMMENT '股票代码, 6位数字代码，或者指数代码 [sh=上证指数 sz=深圳成指 hs300=沪深300指数 sz50=上证50 zxb=中小板 cyb=创业板]' not null,
  t_date                        DATETIME COMMENT '交易日期' not null,
  open                          decimal(20,4) COMMENT '开盘价',
  high                          decimal(20,4) COMMENT '最高价',
  close                         decimal(20,4) COMMENT '收盘价',
  low                           decimal(20,4) COMMENT '最低价',
  volume                        decimal(20,4) COMMENT '成交量',
  price_change                  decimal(20,4) COMMENT '价格变动',
  p_chage                       decimal(20,4) COMMENT '涨跌幅',
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
  constraint pk_history60min primary key (id)
);

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
  p_chage                       decimal(20,4) COMMENT '涨跌幅',
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

create table history_hfq_data (
  id                            bigint auto_increment not null,
  code                          VARCHAR(6) COMMENT '股票代码, 6位数字代码' not null,
  t_date                        DATETIME COMMENT '交易日期' not null,
  open                          decimal(20,4) COMMENT '开盘价',
  high                          decimal(20,4) COMMENT '最高价',
  close                         decimal(20,4) COMMENT '收盘价',
  low                           decimal(20,4) COMMENT '最低价',
  volume                        decimal(20,4) COMMENT '成交量',
  amount                        decimal(20,4) COMMENT '成交金额',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  constraint pk_history_hfq_data primary key (id)
);

create table history_month (
  id                            bigint auto_increment not null,
  code                          VARCHAR(6) COMMENT '股票代码, 6位数字代码，或者指数代码 [sh=上证指数 sz=深圳成指 hs300=沪深300指数 sz50=上证50 zxb=中小板 cyb=创业板]' not null,
  t_date                        DATETIME COMMENT '交易日期' not null,
  open                          decimal(20,4) COMMENT '开盘价',
  high                          decimal(20,4) COMMENT '最高价',
  close                         decimal(20,4) COMMENT '收盘价',
  low                           decimal(20,4) COMMENT '最低价',
  volume                        decimal(20,4) COMMENT '成交量',
  price_change                  decimal(20,4) COMMENT '价格变动',
  p_chage                       decimal(20,4) COMMENT '涨跌幅',
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
  constraint pk_history_month primary key (id)
);

create table history_nofq_data (
  id                            bigint auto_increment not null,
  code                          VARCHAR(6) COMMENT '股票代码, 6位数字代码' not null,
  t_date                        DATETIME COMMENT '交易日期' not null,
  open                          decimal(20,4) COMMENT '开盘价',
  high                          decimal(20,4) COMMENT '最高价',
  close                         decimal(20,4) COMMENT '收盘价',
  low                           decimal(20,4) COMMENT '最低价',
  volume                        decimal(20,4) COMMENT '成交量',
  amount                        decimal(20,4) COMMENT '成交金额',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  constraint pk_history_nofq_data primary key (id)
);

create table history_qfq_data (
  id                            bigint auto_increment not null,
  code                          VARCHAR(6) COMMENT '股票代码, 6位数字代码' not null,
  t_date                        DATETIME COMMENT '交易日期' not null,
  open                          decimal(20,4) COMMENT '开盘价',
  high                          decimal(20,4) COMMENT '最高价',
  close                         decimal(20,4) COMMENT '收盘价',
  low                           decimal(20,4) COMMENT '最低价',
  volume                        decimal(20,4) COMMENT '成交量',
  amount                        decimal(20,4) COMMENT '成交金额',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  constraint pk_history_qfq_data primary key (id)
);

create table history_week (
  id                            bigint auto_increment not null,
  code                          VARCHAR(6) COMMENT '股票代码, 6位数字代码，或者指数代码 [sh=上证指数 sz=深圳成指 hs300=沪深300指数 sz50=上证50 zxb=中小板 cyb=创业板]' not null,
  t_date                        DATETIME COMMENT '交易日期' not null,
  open                          decimal(20,4) COMMENT '开盘价',
  high                          decimal(20,4) COMMENT '最高价',
  close                         decimal(20,4) COMMENT '收盘价',
  low                           decimal(20,4) COMMENT '最低价',
  volume                        decimal(20,4) COMMENT '成交量',
  price_change                  decimal(20,4) COMMENT '价格变动',
  p_chage                       decimal(20,4) COMMENT '涨跌幅',
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
  constraint pk_history_week primary key (id)
);

create table operation_data (
  id                            bigint auto_increment not null,
  code                          VARCHAR(6) COMMENT '股票代码, 6位数字代码' not null,
  c_name                        varchar(16) COMMENT '股票名称' not null,
  report_year                   INTEGER COMMENT '年度,4位数字',
  report_season                 INTEGER COMMENT '季度: 1~4',
  arturnover                    DECIMAL(20,4) COMMENT '应收账款周转率(次)',
  arturndays                    DECIMAL(20,4) COMMENT '应收账款周转天数(天)',
  inventory_turnover            DECIMAL(20,4) COMMENT '存货周转率(次)',
  inventory_days                DECIMAL(20,4) COMMENT '存货周转天数(天)',
  currentasset_turnover         DECIMAL(20,4) COMMENT '流动资产周转率(次)',
  currentasset_days             DECIMAL(20,4) COMMENT '流动资产周转天数(天)',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  constraint pk_operation_data primary key (id)
);

create table plan_task (
  id                            bigint auto_increment not null,
  require_seq                   tinyint(1) COMMENT '是否要求顺序执行',
  seq_type                      varchar(64) COMMENT '顺序执行的类别' not null,
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

create table profit_data (
  id                            bigint auto_increment not null,
  code                          VARCHAR(6) COMMENT '股票代码, 6位数字代码，或者指数代码 [sh=上证指数 sz=深圳成指 hs300=沪深300指数 sz50=上证50 zxb=中小板 cyb=创业板]' not null,
  c_name                        varchar(16) COMMENT '股票名称' not null,
  report_year                   INTEGER COMMENT '年度,4位数字',
  report_season                 INTEGER COMMENT '季度: 1~4',
  roe                           DECIMAL(20,4) COMMENT '净资产收益率(%)',
  net_profit_ratio              DECIMAL(20,4) COMMENT '净利率(%)',
  gross_profit_rate             DECIMAL(20,4) COMMENT '毛利率(%)',
  net_profits                   DECIMAL(20,4) COMMENT '净利润(万元)',
  eps                           decimal(20,4) COMMENT '每股收益',
  business_income               DECIMAL(20,4) COMMENT '营业收入(百万元)',
  bips                          DECIMAL(20,4) COMMENT '每股主营业务收入(元)',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  constraint pk_profit_data primary key (id)
);

create table report_data (
  id                            bigint auto_increment not null,
  code                          VARCHAR(6) COMMENT '股票代码, 6位数字代码' not null,
  c_name                        varchar(16) COMMENT '股票名称' not null,
  eps                           decimal(20,4) COMMENT '每股收益',
  eps_yoy                       DECIMAL(20,4) COMMENT '每股收益同比(%)',
  bvps                          decimal(20,4) COMMENT '每股净资产',
  roe                           DECIMAL(20,4) COMMENT '净资产收益率(%)',
  epcf                          DECIMAL(20,4) COMMENT '每股现金流量(元)',
  net_profits                   DECIMAL(20,4) COMMENT '净利润(万元)',
  profits_yoy                   DECIMAL(20,4) COMMENT '净利润同比(%)',
  distrib                       decimal(20,4) COMMENT '分配方案',
  report_date                   DATETIME COMMENT '发布日期',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  constraint pk_report_data primary key (id)
);

create table stock_basics (
  id                            bigint auto_increment not null,
  code                          VARCHAR(6) COMMENT '股票代码, 6位数字代码' not null,
  c_name                        varchar(16) COMMENT '股票名称',
  industry                      varchar(16) COMMENT '所属行业',
  area                          varchar(8) COMMENT '地区',
  pe                            decimal(20,4) COMMENT '市盈率',
  outstanding                   decimal(20,4) COMMENT '流通股本',
  totals                        DECIMAL(20,4) COMMENT '总股本(万)',
  total_assets                  DECIMAL(20,4) COMMENT '总资产(万)',
  liquid_assets                 decimal(20,4) COMMENT '流动资产',
  fixed_assets                  decimal(20,4) COMMENT '固定资产',
  reserved                      decimal(20,4) COMMENT '公积金',
  reserved_pershare             decimal(20,4) COMMENT '每股公积金',
  eps                           decimal(20,4) COMMENT '每股收益',
  bvps                          decimal(20,4) COMMENT '每股净资',
  pb                            decimal(20,4) COMMENT '市净率',
  time_to_market                DATETIME COMMENT '上市日期',
  expired                       tinyint(1) COMMENT '是否过期',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  constraint pk_stock_basics primary key (id)
);

create table tick_data (
  id                            bigint auto_increment not null,
  code                          VARCHAR(6) COMMENT '股票代码, 6位数字代码，或者指数代码 [sh=上证指数 sz=深圳成指 hs300=沪深300指数 sz50=上证50 zxb=中小板 cyb=创业板]' not null,
  tick_time                     DATETIME COMMENT '分笔交易日期时间' not null,
  price                         decimal(20,4) COMMENT '成交价格',
  price_change                  decimal(20,4) COMMENT '价格变动',
  volume                        decimal(20,4) COMMENT '成交手',
  amount                        DECIMAL(20,4) COMMENT '成交金额(元)',
  t_type                        VARCHAR(4) COMMENT '买卖类型[买盘,卖盘,中性盘]',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  constraint pk_tick_data primary key (id)
);

create table trade_cal (
  id                            bigint auto_increment not null,
  exchange_name                 CHAR(4) COMMENT '证券交易所: XSHG-上海证券交易所, XSHE-深圳证券交易所' not null,
  calendar_date                 char(10) COMMENT '日期:YYYY-MM-DD' not null,
  is_open                       tinyint(1) COMMENT '日期当天是否开市',
  prev_trade_date               char(10) COMMENT '所在日期前一交易日',
  is_week_end                   tinyint(1) COMMENT '日期当天是否是当周最后交易日',
  is_month_end                  tinyint(1) COMMENT '日期当天是否是当月最后交易日',
  is_quarter_end                tinyint(1) COMMENT '日期当天是否是当季度最后交易日',
  is_year_end                   tinyint(1) COMMENT '日期当天是否是当年最后交易日',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  constraint pk_trade_cal primary key (id)
);


# --- !Downs

drop table if exists big_trade;

drop table if exists cashflow_data;

drop table if exists debtpaying_data;

drop table if exists growth_data;

drop table if exists history15min;

drop table if exists history30min;

drop table if exists history5min;

drop table if exists history60min;

drop table if exists history_day;

drop table if exists history_hfq_data;

drop table if exists history_month;

drop table if exists history_nofq_data;

drop table if exists history_qfq_data;

drop table if exists history_week;

drop table if exists operation_data;

drop table if exists plan_task;

drop table if exists profit_data;

drop table if exists report_data;

drop table if exists stock_basics;

drop table if exists tick_data;

drop table if exists trade_cal;

