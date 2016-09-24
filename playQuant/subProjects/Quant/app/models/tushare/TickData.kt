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
 * 分笔数据
 */

@Entity
class TickData : BaseModel() {

    @DBIndexed
    @Column(columnDefinition = "VARCHAR(6) COMMENT '股票代码, 6位数字代码，或者指数代码 [sh=上证指数 sz=深圳成指 hs300=沪深300指数 sz50=上证50 zxb=中小板 cyb=创业板]'", nullable = false)
    var code: String = ""

    @DBIndexed
    @Column(columnDefinition = "DATETIME COMMENT '分笔交易日期时间'", nullable = false)
    var tick_time: JDateTime? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '成交价格'")
    var price: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '价格变动'")
    var price_change: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '成交手'")
    var volume: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '成交金额(元)'")
    var amount: BigDecimal? = null

    @Column(columnDefinition = "VARCHAR(4) COMMENT '买卖类型[买盘,卖盘,中性盘]'")
    var t_type: String? = null

    companion object : Model.Find<Long, TickData>() {

    }
}