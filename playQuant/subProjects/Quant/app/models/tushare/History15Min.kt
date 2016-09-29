package models.tushare

import com.avaje.ebean.Model
import jodd.datetime.JDateTime
import k.aop.annotations.DBIndexed
import k.ebean.DB
import models.BaseModel
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import tushare.getBigDecimal
import tushare.getJDateTime
import java.io.File
import java.math.BigDecimal
import java.nio.charset.Charset
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

/**
 * Created by kk on 16/9/21.
 * 15分钟K线历史行情
 */

@Entity
@Table(name = "history15min")
class History15Min : BaseModel() {

    @DBIndexed
    @Column(columnDefinition = "VARCHAR(6) COMMENT '股票代码, 6位数字代码，或者指数代码 [sh=上证指数 sz=深圳成指 hs300=沪深300指数 sz50=上证50 zxb=中小板 cyb=创业板]'", nullable = false)
    var code: String = ""       // enum class: StockIndex

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

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '价格变动'")
    var price_change: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '涨跌幅'")
    var p_chage: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '5日均价'")
    var ma5: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '10日均价'")
    var ma10: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '20日均价'")
    var ma20: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '5日均量'")
    var v_ma5: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '10日均量'")
    var v_ma10: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '20日均量'")
    var v_ma20: BigDecimal? = null

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '换手率 [注：指数无此项]'")
    var turnover: BigDecimal? = null

    fun FillBy(stockCode: String, csvRecord: CSVRecord): History15Min {
        code = stockCode
        t_date = csvRecord.getJDateTime("date")
        open = csvRecord.getBigDecimal("open")
        high = csvRecord.getBigDecimal("high")
        close = csvRecord.getBigDecimal("close")
        low = csvRecord.getBigDecimal("low")
        volume = csvRecord.getBigDecimal("volume")
        price_change = csvRecord.getBigDecimal("price_change")
        p_chage = csvRecord.getBigDecimal("p_change")
        ma5 = csvRecord.getBigDecimal("ma5")
        ma10 = csvRecord.getBigDecimal("ma10")
        ma20 = csvRecord.getBigDecimal("ma20")
        v_ma5 = csvRecord.getBigDecimal("v_ma5")
        v_ma10 = csvRecord.getBigDecimal("v_ma10")
        v_ma20 = csvRecord.getBigDecimal("v_ma20")

        if (csvRecord.isMapped("turnover")) {
            turnover = csvRecord.getBigDecimal("turnover")
        }
        return this
    }

    companion object : Model.Find<Long, History15Min>() {
        fun CsvToDb(code:String, csvFile: File) {
            val records  = CSVParser.parse(csvFile, Charset.forName("UTF-8"), CSVFormat.DEFAULT.withFirstRecordAsHeader())
                    .asIterable()
                    .map {
                        val hisData = FindOrCreate(code, it.getJDateTime("date")!!)
                        hisData.FillBy(code, it)
                        return@map hisData
                    }

            DB.Default().saveAll(records)
        }

        fun FindOrCreate(code: String, date: JDateTime): History15Min {
            return History15Min.where()
                    .eq("code", code)
                    .eq("t_date", date)
                    .findUnique() ?: History15Min()
        }
    }
}