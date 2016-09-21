package models.tushare

import com.avaje.ebean.Model
import jodd.datetime.JDateTime
import k.aop.annotations.DBIndexed
import models.BaseModel
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity

/**
 * Created by kk on 16/9/21.
 * 历史 后复权 数据
 */

@Entity
class HistoryHfqData : BaseModel() {

    @DBIndexed
    @Column(columnDefinition = "VARCHAR(6) COMMENT '股票代码, 6位数字代码'", nullable = false)
    var code: String = ""

    @DBIndexed
    @Column(columnDefinition = "DATETIME COMMENT '交易日期'", nullable = false)
    var t_date: JDateTime? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '开盘价'")
    var open: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '最高价'")
    var high: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '收盘价'")
    var close: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '最低价'")
    var low: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '成交量'")
    var volume: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '成交金额'")
    var amount: BigDecimal? = null

    companion object : Model.Find<Long, HistoryHfqData>() {

    }
}