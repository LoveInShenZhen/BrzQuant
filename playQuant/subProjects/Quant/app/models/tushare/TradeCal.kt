package models.tushare

import com.avaje.ebean.Model
import k.aop.annotations.DBIndexed
import k.ebean.DB
import models.BaseModel
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.File
import java.nio.charset.Charset
import javax.persistence.Column
import javax.persistence.Entity

/**
 * Created by kk on 16/9/23.
 * 交易所交易日历
 */

@Entity
class TradeCal : BaseModel() {

    @DBIndexed
    @Column(columnDefinition = "CHAR(4) COMMENT '证券交易所: XSHG-上海证券交易所, XSHE-深圳证券交易所'", nullable = false)
    var exchange_name: String? = null

    @DBIndexed
    @Column(columnDefinition = "CHAR(10) COMMENT '日期:YYYY-MM-DD'", nullable = false)
    var calendar_date: String? = null

    @Column(columnDefinition = "TINYINT(1) COMMENT '日期当天是否开市'")
    var is_open: Boolean = false

    @Column(columnDefinition = "CHAR(10) COMMENT '所在日期前一交易日'")
    var prev_Trade_date: String? = null

    @Column(columnDefinition = "TINYINT(1) COMMENT '日期当天是否是当周最后交易日'")
    var is_week_end: Boolean = false

    @Column(columnDefinition = "TINYINT(1) COMMENT '日期当天是否是当月最后交易日'")
    var is_month_end: Boolean = false

    @Column(columnDefinition = "TINYINT(1) COMMENT '日期当天是否是当季度最后交易日'")
    var is_quarter_end: Boolean = false

    @Column(columnDefinition = "TINYINT(1) COMMENT '日期当天是否是当年最后交易日'")
    var is_year_end: Boolean = false

    companion object : Model.Find<Long, TradeCal>() {

        fun CsvToDb(csvFile: File) {
            val newRecords = CSVParser.parse(csvFile, Charset.forName("UTF-8"), CSVFormat.DEFAULT.withFirstRecordAsHeader())
                    .asIterable()
                    .filter { notExists(it.get("exchangeCD"), it.get("calendarDate")) }
                    .map {
                        val cd = TradeCal()
                        cd.exchange_name = it.get("exchangeCD")
                        cd.calendar_date = it.get("calendarDate")
                        cd.is_open = it.get("isOpen") == "1"
                        cd.prev_Trade_date = it.get("prevTradeDate")
                        cd.is_week_end = it.get("isWeekEnd") == "1"
                        cd .is_month_end = it.get("isMonthEnd") == "1"
                        cd.is_quarter_end = it.get("isQuarterEnd") == "1"
                        cd.is_year_end = it.get("isYearEnd") == "1"

                        return@map cd
                    }

            DB.Default().saveAll(newRecords)
        }

        fun exists(exchangeName: String, calDate: String): Boolean {
            return TradeCal.where()
                    .eq("exchange_name", exchangeName)
                    .eq("calendar_date", calDate)
                    .findRowCount() > 0
        }

        fun notExists(exchangeName: String, calDate: String): Boolean {
            return !exists(exchangeName, calDate)
        }
    }
}

enum class Exchange(val code: String, val desc: String) {
    XSHG("XSHG", "上海证券交易所"),
    XSHE("XSHE", "深圳证券交易所")
}
