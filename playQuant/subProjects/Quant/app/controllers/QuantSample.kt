package controllers

import k.aop.annotations.Comment
import k.aop.annotations.JsonApi
import k.controllers.JsonpController
import k.reply.ReplyBase
import models.tushare.TradeCal
import play.mvc.Result
import java.io.File

/**
 * Created by kk on 16/8/23.
 */

@Comment("Quant 临时测试组")
class QuantSample : JsonpController() {

    @Comment("Quant 临时测试代码")
    @JsonApi
    fun QuantTest() : Result {
        val csvFile = File("/Users/kk/Downloads/交易所日历1991-2016.csv")
        TradeCal.CsvToDb(csvFile)
        return ok(ReplyBase())
    }

}