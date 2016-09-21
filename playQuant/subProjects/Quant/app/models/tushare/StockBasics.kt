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
 */

@Entity
class StockBasics : BaseModel() {

    @DBIndexed
    @Column(columnDefinition = "VARCHAR(6) COMMENT '股票代码, 6位数字代码'", nullable = false)
    var code: String = ""

    @Column(columnDefinition = "VARCHAR(16) COMMENT '股票名称'", nullable = false)
    var c_name: String = ""

    @Column(columnDefinition = "VARCHAR(16) COMMENT '股票名称'")
    var industry: String = ""

    @Column(columnDefinition = "VARCHAR(8) COMMENT '地区'")
    var area: String = ""

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '市盈率'")
    var pe: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '流通股本'")
    var outstanding: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '总股本(万)'")
    var totals: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '总资产(万)'")
    var total_assets: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '流动资产'")
    var liquid_assets: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '固定资产'")
    var fixed_assets: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '公积金'")
    var reserved: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '每股公积金'")
    var reserved_pershare: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '每股收益'")
    var eps: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '每股净资'")
    var bvps: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '市净率'")
    var pb: BigDecimal? = null

    @Column(columnDefinition = "DATETIME COMMENT '上市日期'")
    var time_to_market: JDateTime? = null

    companion object : Model.Find<Long, StockBasics>() {

    }
}