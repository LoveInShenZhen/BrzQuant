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
 * 大单交易数据(默认为大于等于100手)
 */

@Entity
class BigTrade : BaseModel() {

    @DBIndexed
    @Column(columnDefinition = "INT(8) COMMENT '手数,默认为400手'")
    var level_vol: Int = 400

    @DBIndexed
    @Column(columnDefinition = "VARCHAR(6) COMMENT '股票代码, 6位数字代码'", nullable = false)
    var code: String = ""

    @Column(columnDefinition = "VARCHAR(16) COMMENT '股票名称'", nullable = false)
    var c_name: String = ""

    @DBIndexed
    @Column(columnDefinition = "DATETIME COMMENT '交易日期时间'", nullable = false)
    var t_time: JDateTime? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '成交价格'")
    var price: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '成交手'")
    var volume: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '上一笔价格'")
    var preprice: BigDecimal? = null

    @Column(columnDefinition = "VARCHAR(4) COMMENT '买卖类型[买盘,卖盘,中性盘]'")
    var t_type: String? = null

    companion object : Model.Find<Long, BigTrade>() {

    }
}