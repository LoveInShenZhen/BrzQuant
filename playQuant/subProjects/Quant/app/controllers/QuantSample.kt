package controllers

import k.aop.annotations.Comment
import k.aop.annotations.JsonApi
import k.common.Helper
import k.controllers.JsonpController
import k.reply.ReplyBase
import play.mvc.Result
import tushare.ScriptResult

/**
 * Created by kk on 16/8/23.
 */

@Comment("Quant 临时测试组")
class QuantSample : JsonpController() {

    @Comment("Quant 临时测试代码")
    @JsonApi
    fun QuantTest(): Result {

        val jsonStr = """{
                            "csvpath": "/Users/kk/ssdwork/github/tuShareData/get_stock_basics/2016-09-27.csv",
                            "msg": "OK",
                            "ret": 0
                        }
                        """

        val result = Helper.FromJsonString(jsonStr, ScriptResult::class.java)

        Helper.DLog("csvpath: ${result.csvpath}")
        Helper.DLog("\n${Helper.ToJsonStringPretty(result)}")

        return ok(ReplyBase())
    }

}