package models.tushare

import com.avaje.ebean.Model
import k.aop.annotations.DBIndexed
import models.BaseModel
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity

/**
 * Created by kk on 16/9/24.
 * 按年度、季度获取现金流量数据
 */

@Entity
class CashflowData : BaseModel() {

    @DBIndexed
    @Column(columnDefinition = "VARCHAR(6) COMMENT '股票代码, 6位数字代码'", nullable = false)
    var code: String = ""

    @DBIndexed
    @Column(columnDefinition = "VARCHAR(16) COMMENT '股票名称'", nullable = false)
    var c_name: String = ""

    @DBIndexed
    @Column(columnDefinition = "INTEGER COMMENT '年度,4位数字'")
    var report_year: Int = -1

    @DBIndexed
    @Column(columnDefinition = "INTEGER COMMENT '季度: 1~4'")
    var report_season: Int = -1

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '经营现金净流量对销售收入比率'")
    var cf_sales: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '资产的经营现金流量回报率'")
    var rateofreturn: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '经营现金净流量与净利润的比率'")
    var cf_nm: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '经营现金净流量对负债比率'")
    var cf_liabilities: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '现金流量比率'")
    var cashflowratio: BigDecimal? = null

    companion object : Model.Find<Long, CashflowData>() {

    }

}