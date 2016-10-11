package controllers

import k.aop.annotations.Comment
import k.aop.annotations.JsonApi
import k.controllers.JsonpController
import k.reply.ReplyBase
import models.tushare.StockBasics
import models.tushare.TradeCal
import play.mvc.Result
import tushare.tasks.FetchHistoryData
import tushare.tasks.FetchStockBasics
import java.io.File

/**
 * Created by kk on 16/9/26.
 */

@Comment("设置 Quant 基础数据")
class QuantData : JsonpController() {

    @Comment("从指定的 CSV 文件加载 交易所交易日历数据 到数据库")
    @JsonApi
    fun SetupTradeCal(@Comment("CSV 数据文件路径") csvPath: String): Result {
        val cfile = File(csvPath)
        TradeCal.CsvToDb(cfile)
        return ok(ReplyBase())
    }

    @Comment("下载股票基本信息数据并更新到数据库")
    @JsonApi
    fun DownloadStockBasics(): Result {
        val task = FetchStockBasics()
        task.run()
        return ok(ReplyBase())
    }

    @Comment("批量创建任务用于下载个股和指数的历史数据(排除掉 ST 股)")
    @JsonApi
    fun SetupTaskDownloadHistoryData(): Result {
        // D=日k线 W=周 M=月 5=5分钟 15=15分钟 30=30分钟 60=60分钟
        val ktypes = setOf("D", "W", "M", "5", "15", "30", "60")
        ktypes.forEach {
            FetchHistoryData.SetupHistoryDataTask(it)
        }
        return ok(ReplyBase())
    }

    @Comment("批量创建任务用于下载个股和指数的不复权历史数据(排除掉 ST 股)")
    @JsonApi
    fun SetupTaskDownloadNoFqHistData(): Result {

        return ok(ReplyBase())
    }
}