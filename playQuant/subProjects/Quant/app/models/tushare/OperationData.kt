package models.tushare

import com.avaje.ebean.Model
import k.aop.annotations.DBIndexed
import models.BaseModel
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity

/**
 * Created by kk on 16/9/24.
 * 按年度、季度获取营运能力数据
 */

@Entity
class OperationData : BaseModel() {

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

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '应收账款周转率(次)'")
    var arturnover: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '应收账款周转天数(天)'")
    var arturndays: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '存货周转率(次)'")
    var inventory_turnover: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '存货周转天数(天)'")
    var inventory_days: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '流动资产周转率(次)'")
    var currentasset_turnover: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '流动资产周转天数(天)'")
    var currentasset_days: BigDecimal? = null

    companion object : Model.Find<Long, OperationData>() {

    }
}