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
 * Created by kk on 16/9/22.
 * 沪深上市公司基本情况
 */

@Entity
class StockBasics : BaseModel() {

    @DBIndexed
    @Column(columnDefinition = "VARCHAR(6) COMMENT '股票代码, 6位数字代码'", nullable = false)
    var code: String = ""

    @Column(columnDefinition = "VARCHAR(16) COMMENT '股票名称'")
    var c_name: String = ""

    @Column(columnDefinition = "VARCHAR(16) COMMENT '所属行业'")
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

    @Column(columnDefinition = "TINYINT(1) COMMENT '是否过期'")
    var expired: Boolean = false

    fun FillBy(csvRecord: CSVRecord): StockBasics {
        this.code = csvRecord.get("code")
        this.c_name = csvRecord.get("name")
        this.industry = csvRecord.get("industry")
        this.area = csvRecord.get("area")
        this.pe = csvRecord.getBigDecimal("pe")
        this.outstanding = csvRecord.getBigDecimal("outstanding")
        this.totals = csvRecord.getBigDecimal("totals")
        this.total_assets = csvRecord.getBigDecimal("totalAssets")
        this.liquid_assets = csvRecord.getBigDecimal("liquidAssets")
        this.fixed_assets = csvRecord.getBigDecimal("fixedAssets")
        this.reserved = csvRecord.getBigDecimal("reserved")
        this.reserved_pershare = csvRecord.getBigDecimal("reservedPerShare")
        this.eps = csvRecord.getBigDecimal("esp")
        this.bvps = csvRecord.getBigDecimal("bvps")
        this.pb = csvRecord.getBigDecimal("pb")
        this.time_to_market = csvRecord.getJDateTime("timeToMarket")

        return this
    }

    fun Unchage(csvRecord: CSVRecord): Boolean {
        return (this.code == csvRecord.get("code")
                && this.c_name == csvRecord.get("name")
                && this.industry == csvRecord.get("industry")
                && this.area == csvRecord.get("area")
                && this.pe == csvRecord.getBigDecimal("pe")
                && this.outstanding == csvRecord.getBigDecimal("outstanding")
                && this.totals == csvRecord.getBigDecimal("totals")
                && this.total_assets == csvRecord.getBigDecimal("totalAssets")
                && this.liquid_assets == csvRecord.getBigDecimal("liquidAssets")
                && this.fixed_assets == csvRecord.getBigDecimal("fixedAssets")
                && this.reserved == csvRecord.getBigDecimal("reserved")
                && this.reserved_pershare == csvRecord.getBigDecimal("reservedPerShare")
                && this.eps == csvRecord.getBigDecimal("esp")
                && this.bvps == csvRecord.getBigDecimal("bvps")
                && this.pb == csvRecord.getBigDecimal("pb")
                && this.time_to_market == csvRecord.getJDateTime("timeToMarket"))
    }

    fun Changed(csvRecord: CSVRecord): Boolean {
        return !Unchage(csvRecord)
    }

    companion object : Model.Find<Long, StockBasics>() {
        fun CsvToDb(csvFile: File) {
            val changedList = mutableListOf<StockBasics>()
            CSVParser.parse(csvFile, Charset.forName("UTF-8"), CSVFormat.DEFAULT.withFirstRecordAsHeader())
                    .asIterable()
                    .forEach {
                        val code = it.get("code")
                        var binfo = FindByCode(code)
                        if (binfo == null) {
                            // new data
                            binfo = StockBasics()
                            binfo.FillBy(csvRecord = it)
                            changedList.add(binfo)
                        } else {
                            // 数据库有对应的记录, 先检查该记录和最新的数据是否一致
                            // 如果不一致, 则需要将该条记录标记为"过期", 然后记录一套新的记录
                            if (binfo.Changed(csvRecord = it)) {
                                // 数据改变了
                                binfo.expired = true
                                changedList.add(binfo)

                                val newRec = StockBasics()
                                newRec.FillBy(csvRecord = it)
                                changedList.add(newRec)
                            }
                        }
                    }
            DB.Default().saveAll(changedList)
        }



        fun FindByCode(code: String): StockBasics? {
            return StockBasics.where()
                    .eq("code", code)
                    .eq("expired", false)
                    .findUnique()
        }
    }
}