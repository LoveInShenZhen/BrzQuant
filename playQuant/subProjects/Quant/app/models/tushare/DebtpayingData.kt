package models.tushare

import com.avaje.ebean.Model
import k.aop.annotations.DBIndexed
import models.BaseModel
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity

/**
 * Created by kk on 16/9/24.
 * 按年度、季度获取偿债能力数据
 */

@Entity
class DebtpayingData : BaseModel() {

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

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '流动比率'")
    var currentratio: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '速动比率'")
    var quickratio: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '现金比率'")
    var cashratio: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '利息支付倍数'")
    var icratio: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '股东权益比率'")
    var sheqratio: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '股东权益增长率'")
    var adratio: BigDecimal? = null

    companion object : Model.Find<Long, DebtpayingData>() {

    }

}