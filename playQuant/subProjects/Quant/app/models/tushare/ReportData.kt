package models.tushare

import com.avaje.ebean.Model
import jodd.datetime.JDateTime
import k.aop.annotations.DBIndexed
import models.BaseModel
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity

/**
 * Created by kk on 16/9/22.
 * 业绩报告（主表）
 * 按年度、季度获取业绩报表数据
 */

@Entity
class ReportData : BaseModel() {

    @DBIndexed
    @Column(columnDefinition = "VARCHAR(6) COMMENT '股票代码, 6位数字代码，或者指数代码 [sh=上证指数 sz=深圳成指 hs300=沪深300指数 sz50=上证50 zxb=中小板 cyb=创业板]'", nullable = false)
    var code: String = ""       // enum class: StockIndex

    @Column(columnDefinition = "VARCHAR(16) COMMENT '股票名称'", nullable = false)
    var c_name: String = ""

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '每股收益'")
    var eps: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '每股收益同比(%)'")
    var eps_yoy: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '每股净资产'")
    var bvps: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '净资产收益率(%)'")
    var roe: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '每股现金流量(元)'")
    var epcf: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '净利润(万元)'")
    var net_profits: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '净利润同比(%)'")
    var profits_yoy: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '分配方案'")
    var distrib: BigDecimal? = null

    @Column(columnDefinition = "DATETIME COMMENT '发布日期'")
    var report_date: JDateTime? = null

    companion object : Model.Find<Long, ReportData>() {

    }

}