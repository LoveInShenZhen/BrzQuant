package tushare.tasks

import jodd.datetime.JDateTime
import k.common.BizLogicException
import models.task.PlanTask
import models.tushare.StockBasics
import tushare.QuantConfig
import tushare.ScriptResult
import tushare.TuShareScript

/**
 * Created by kk on 16/10/9.
 */
class FetchFQHistoryData : Runnable{

    var code = ""               // 股票代码,即6位数字代码,或者指数代码
    var index = false           // 是否是大盘指数
    var start = ""              // 开始日期,格式YYYY-MM-DD
    var end = ""                // 结束日期,格式YYYY-MM-DD
    var retry_count = 10        // 当网络异常后重试次数,默认为5
    var pause = 0.1             // 重试时停顿秒数,默认为 0.5

    var autype = "None"         // 复权类型,qfq-前复权 hfq-后复权 None-不复权
    set(value) {
        if (value !in setOf("qfq", "hfq", "None")) {
            throw BizLogicException("不合法的 autype: $value")
        }
        field = value
    }

    override fun run() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun csvPath(): String {
        val script = TuShareScript(name = "getAllHisDayData.py")
        script.AddArgument("--code").AddArgument(code)
                .AddArgument("--index").AddArgument(if (index) "True" else "False")
                .AddArgument("--autype").AddArgument(autype)
                .AddArgument("--start").AddArgument(start)
                .AddArgument("--end").AddArgument(end)
                .AddArgument("--retry_count").AddArgument(retry_count.toString())
                .AddArgument("--pause").AddArgument(pause.toString())
                .AddArgument("--dir").AddArgument(QuantConfig.dataDir)
                .AddArgument("--fake")

        return script.Run().Result.csvpath
    }

    private fun fetchData(): ScriptResult {
        val script = TuShareScript(name = "getAllHisDayData.py")
        script.AddArgument("--code").AddArgument(code)
                .AddArgument("--index").AddArgument(if (index) "True" else "False")
                .AddArgument("--autype").AddArgument(autype)
                .AddArgument("--start").AddArgument(start)
                .AddArgument("--end").AddArgument(end)
                .AddArgument("--retry_count").AddArgument(retry_count.toString())
                .AddArgument("--pause").AddArgument(pause.toString())
                .AddArgument("--dir").AddArgument(QuantConfig.dataDir)

        return script.Run().Result
    }

    companion object {
        fun FilterStocks(): List<StockBasics> {
            // 排除掉未上市的
            // 排除掉 ST 股
            return StockBasics.where()
                    .eq("expired", false)
                    .isNotNull("time_to_market")
                    .findList()
                    .filter { !it.c_name.toUpperCase().contains("st", ignoreCase = true) }
        }

        fun AddTaskFor(stock: StockBasics, autype: String) {
            val yearStart = stock.time_to_market!!
            val today = JDateTime()
            while (yearStart.year <= today.year) {
                val task = FetchFQHistoryData()
                task.code = stock.code
                task.index = false

                val startDay = if (yearStart.isBeforeDate(stock.time_to_market)) stock.time_to_market!! else yearStart
                val endDay = startDay.clone().setMonth(12).setDay(31)
                task.start = startDay.toString("YYYY-MM-DD")
                task.end = endDay.toString("YYYY-MM-DD")
                task.autype = autype

                PlanTask.addTask(task, true, "FetchData")
                yearStart.year = startDay.year + 1
            }
        }

        fun AddTaskForIndex(code: String) {
            val yearStart = JDateTime("2005-01-01")
            val today = JDateTime()
            while (yearStart.year <= today.year) {
                val task = FetchFQHistoryData()
                task.code = code
                task.index = true

                val startDay = yearStart
                val endDay = startDay.clone().setMonth(12).setDay(31)
                task.start = startDay.toString("YYYY-MM-DD")
                task.end = endDay.toString("YYYY-MM-DD")
                task.autype = "None"

                PlanTask.addTask(task, true, "FetchData")
                yearStart.year = startDay.year + 1
            }
        }

    }
}