package controllers

import k.aop.annotations.Comment
import k.aop.annotations.JsonApi
import k.common.Helper
import k.controllers.JsonpController
import k.reply.ReplyBase
import play.mvc.Result
import tushare.tasks.FetchHistoryData

/**
 * Created by kk on 16/8/23.
 */

@Comment("Quant 临时测试组")
class QuantSample : JsonpController() {

    @Comment("Quant 临时测试代码")
    @JsonApi
    fun QuantTest(): Result {

        val stocks = FetchHistoryData.FilterStocks()
        stocks.forEach { Helper.DLog("${it.code} : ${it.c_name}") }

        Helper.DLog("total: ${stocks.size}")

        return ok(ReplyBase())
    }

}