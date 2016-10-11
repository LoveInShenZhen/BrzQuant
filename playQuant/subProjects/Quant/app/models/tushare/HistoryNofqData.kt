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

/**
 * Created by kk on 16/9/21.
 * 历史 无复权 数据
 */

@Entity
class HistoryNofqData : BaseModel() {

    @DBIndexed
    @Column(columnDefinition = "VARCHAR(6) COMMENT '股票代码, 6位数字代码'", nullable = false)
    var code: String = ""

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

    @Column(columnDefinition = "DECIMAL(20,4) COMMENT '成交金额'")
    var amount: BigDecimal? = null

    fun FillBy(stockCode: String, csvRecord: CSVRecord): HistoryNofqData {
        code = stockCode
        t_date = csvRecord.getJDateTime("date", "YYYY-MM-DD")
        open = csvRecord.getBigDecimal("open")
        high = csvRecord.getBigDecimal("high")
        close = csvRecord.getBigDecimal("close")
        low = csvRecord.getBigDecimal("low")
        volume = csvRecord.getBigDecimal("volume")
        amount = csvRecord.getBigDecimal("amount")

        return this
    }

    companion object : Model.Find<Long, HistoryNofqData>() {

        val indexMap = mapOf("上证指数" to StockIndex.SH,
                "深圳成指" to StockIndex.SZ,
                "沪深300指数" to StockIndex.HS300,
                "上证50指数" to StockIndex.SZ50,
                "中小板指数" to StockIndex.ZXB,
                "创业板指数" to StockIndex.CYB,
                "深证综合指数" to StockIndex.SZZH)

        fun CodeByFile(csvFile: File): String {
            val code = csvFile.parentFile.name
            if (indexMap.containsKey(code)) {
                return indexMap[code]!!.name
            } else {
                return code
            }
        }

        fun CsvToDb(csvFile: File) {
            val code = CodeByFile(csvFile)
            val records  = CSVParser.parse(csvFile, Charset.forName("UTF-8"), CSVFormat.DEFAULT.withFirstRecordAsHeader())
                    .asIterable()
                    .map {
                        val hisData = HistoryNofqData.FindOrCreate(code, it.getJDateTime("date", "YYYY-MM-DD")!!)
                        hisData.FillBy(code, it)
                        return@map hisData
                    }

            DB.Default().saveAll(records)
        }

        fun FindOrCreate(code: String, date: JDateTime): HistoryNofqData {
            return HistoryNofqData.where()
                    .eq("code", code)
                    .eq("t_date", date)
                    .findUnique() ?: HistoryNofqData()
        }
    }

}