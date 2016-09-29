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
}