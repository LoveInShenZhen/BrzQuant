package models.tushare

import com.avaje.ebean.Model
import jodd.datetime.JDateTime
import k.aop.annotations.DBIndexed
import models.BaseModel
import javax.persistence.Column
import javax.persistence.Entity

/**
 * Created by kk on 16/9/23.
 * 交易所交易日历
 */

@Entity
class TradeCal : BaseModel() {

    @Column(columnDefinition = "CHAR(4) COMMENT '证券交易所: XSHG-上海证券交易所, XSHE-深圳证券交易所'", nullable = false)
    var exchange_name: String? = null

    @DBIndexed
    @Column(columnDefinition = "DATETIME COMMENT '日期'", nullable = false)
    var calendar_date: JDateTime? = null

    @Column(columnDefinition = "TINYINT(1) COMMENT '日期当天是否开市'")
    var is_open: Boolean = false

    @Column(columnDefinition = "DATETIME COMMENT '所在日期前一交易日'")
    var prevTradeDate:JDateTime? = null

    @Column(columnDefinition = "TINYINT(1) COMMENT '日期当天是否是当周最后交易日'")
    var is_week_end:Boolean = false

    @Column(columnDefinition = "TINYINT(1) COMMENT '日期当天是否是当月最后交易日'")
    var is_month_end:Boolean = false

    @Column(columnDefinition = "TINYINT(1) COMMENT '日期当天是否是当季度最后交易日'")
    var is_quarter_end:Boolean = false

    @Column(columnDefinition = "TINYINT(1) COMMENT '日期当天是否是当年最后交易日'")
    var is_year_end:Boolean = false

    companion object : Model.Find<Long, TradeCal>() {
    }
}
