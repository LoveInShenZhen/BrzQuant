package models.tushare

import com.avaje.ebean.Model
import k.aop.annotations.DBIndexed
import models.BaseModel
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity

/**
 * Created by kk on 16/9/24.
 * 按年度、季度获取盈利能力数据
 */

@Entity
class ProfitData : BaseModel() {

    @DBIndexed
    @Column(columnDefinition = "VARCHAR(6) COMMENT '股票代码, 6位数字代码，或者指数代码 [sh=上证指数 sz=深圳成指 hs300=沪深300指数 sz50=上证50 zxb=中小板 cyb=创业板]'", nullable = false)
    var code: String = ""       // enum class: StockIndex

    @DBIndexed
    @Column(columnDefinition = "VARCHAR(16) COMMENT '股票名称'", nullable = false)
    var c_name: String = ""

    @DBIndexed
    @Column(columnDefinition = "INTEGER COMMENT '年度,4位数字'")
    var report_year: Int = -1

    @DBIndexed
    @Column(columnDefinition = "INTEGER COMMENT '季度: 1~4'")
    var report_season: Int = -1

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '净资产收益率(%)'")
    var roe: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '净利率(%)'")
    var net_profit_ratio: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '毛利率(%)'")
    var gross_profit_rate: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '净利润(万元)'")
    var net_profits: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '每股收益'")
    var eps: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '营业收入(百万元)'")
    var business_income: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '每股主营业务收入(元)'")
    var bips: BigDecimal? = null

    companion object : Model.Find<Long, ProfitData>() {

    }

}