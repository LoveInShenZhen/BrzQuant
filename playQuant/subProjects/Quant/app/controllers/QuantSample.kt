package controllers

import k.aop.annotations.Comment
import k.aop.annotations.JsonApi
import k.common.Helper
import k.controllers.JsonpController
import k.reply.ReplyBase
import play.mvc.Result
import tushare.TuShareScript

/**
 * Created by kk on 16/8/23.
 */

@Comment("Quant 临时测试组")
class QuantSample : JsonpController() {

    @Comment("Quant 临时测试代码")
    @JsonApi
    fun QuantTest(): Result {

        val script = TuShareScript(name = "getStockBasics.py")
        val result = script.Run().Result

        Helper.DLog("\n${Helper.ToJsonStringPretty(result)}")


        return ok(ReplyBase())
    }

}