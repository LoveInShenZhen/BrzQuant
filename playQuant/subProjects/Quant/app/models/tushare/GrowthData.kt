package models.tushare

import com.avaje.ebean.Model
import k.aop.annotations.DBIndexed
import models.BaseModel
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity

/**
 * Created by kk on 16/9/24.
 * 按年度、季度获取成长能力数据
 */

@Entity
class GrowthData : BaseModel() {

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

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '主营业务收入增长率(%)'")
    var mbrg: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '净利润增长率(%)'")
    var nprg: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '净资产增长率'")
    var nav: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '总资产增长率'")
    var targ: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '每股收益增长率'")
    var epsg: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '股东权益增长率'")
    var seg: BigDecimal? = null

    companion object : Model.Find<Long, GrowthData>() {

    }
}