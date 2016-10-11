package tushare.tasks

import jodd.datetime.JDateTime
import k.common.BizLogicException
import k.common.Helper
import models.task.PlanTask
import models.tushare.*
import tushare.QuantConfig
import tushare.ScriptResult
import tushare.TuShareScript
import java.io.File

/**
 * Created by kk on 16/9/27.
 */
class FetchHistoryData : Runnable {

    var code = ""           // 股票代码,即6位数字代码,或者指数代码（sh=上证指数 sz=深圳成指 hs300=沪深300指数 sz50=上证50 zxb=中小板 cyb=创业板）
    var start = ""          // 开始日期,格式YYYY-MM-DD
    var end = ""            // 结束日期,格式YYYY-MM-DD
    var ktype = ""          // 数据类型,D=日k线 W=周 M=月 5=5分钟 15=15分钟 30=30分钟 60=60分钟
        set(value) {
            if (value !in setOf("D", "W", "M", "5", "15", "30", "60")) {
                throw BizLogicException("不合法的 ktype: $value")
            }
            field = value
        }
    var retry_count = 10         // 当网络异常后重试次数,默认为5
    var pause = 0.1             // 重试时停顿秒数,默认为 0.5

    override fun run() {

        val dataFile = File(csvPath())
        // 先判断该次请求的数据文件是否已经存在, 如果已经存在, 则直接更新到数据库
        if (dataFile.exists()) {
            Helper.DLog("下载历史行情数据: code: $code  ktype: $ktype    $start to $end 请求的数据文件已经存在, 直接更新到数据库")
            when (ktype) {
                "D" -> HistoryDay.CsvToDb(code, dataFile)
                "W" -> HistoryWeek.CsvToDb(code, dataFile)
                "M" -> HistoryMonth.CsvToDb(code, dataFile)
                "5" -> History5Min.CsvToDb(code, dataFile)
                "15" -> History15Min.CsvToDb(code, dataFile)
                "30" -> History30Min.CsvToDb(code, dataFile)
                "60" -> History60Min.CsvToDb(code, dataFile)
                else -> {
                    throw BizLogicException("错误的 ktype: $ktype")
                }
            }
        } else {
            // 数据文件不存在, 则尝试去抓取数据
            Helper.DLog("下载历史行情数据: code: $code  ktype: $ktype    $start to $end ")
            val startTime = JDateTime()
            val result = fetchData()
            val endTime = JDateTime()
            val useTimeInMs = endTime.timeInMillis - startTime.timeInMillis
            Helper.DLog("下载完毕, 耗时 ${useTimeInMs/1000.0} 秒")
            if (result.ret == 0) {
                val csvFile = File(result.csvpath)
                when (ktype) {
                    "D" -> HistoryDay.CsvToDb(code, csvFile)
                    "W" -> HistoryWeek.CsvToDb(code, csvFile)
                    "M" -> HistoryMonth.CsvToDb(code, csvFile)
                    "5" -> History5Min.CsvToDb(code, csvFile)
                    "15" -> History15Min.CsvToDb(code, csvFile)
                    "30" -> History30Min.CsvToDb(code, csvFile)
                    "60" -> History60Min.CsvToDb(code, csvFile)
                    else -> {
                        throw BizLogicException("错误的 ktype: $ktype")
                    }
                }
            } else {
                if (result.ret == 99) {
                    Helper.DLog("没有数据:\n${Helper.ToJsonStringPretty(this)}")
                } else {
                    throw BizLogicException("Failed to getHistoryData.\n${result.msg}")
                }
            }
        }
    }

    private fun csvPath(): String {
        val script = TuShareScript(name = "getHistoryData.py")
        script.AddArgument("--code").AddArgument(code)
                .AddArgument("--start").AddArgument(start)
                .AddArgument("--end").AddArgument(end)
                .AddArgument("--ktype").AddArgument(ktype)
                .AddArgument("--retry_count").AddArgument(retry_count.toString())
                .AddArgument("--pause").AddArgument(pause.toString())
                .AddArgument("--dir").AddArgument(QuantConfig.dataDir)
                .AddArgument("--fake")

        return script.Run().Result.csvpath
    }

    private fun fetchData(): ScriptResult {
        val script = TuShareScript(name = "getHistoryData.py")
        script.AddArgument("--code").AddArgument(code)
                .AddArgument("--start").AddArgument(start)
                .AddArgument("--end").AddArgument(end)
                .AddArgument("--ktype").AddArgument(ktype)
                .AddArgument("--retry_count").AddArgument(retry_count.toString())
                .AddArgument("--pause").AddArgument(pause.toString())
                .AddArgument("--dir").AddArgument(QuantConfig.dataDir)

        return script.Run().Result
    }

    companion object {
        fun AddTask(code: String, start: JDateTime, end: JDateTime, ktype: String, retryCount: Int = 3, pause: Double = 0.5) {
            val task = FetchHistoryData()
            task.code = code
            task.start = start.toString("YYYY-MM-DD")
            task.end = end.toString("YYYY-MM-DD")
            task.ktype = ktype
            task.retry_count = retryCount
            task.pause = pause

            PlanTask.addTask(task = task,
                    requireSeq = true,
                    seqType = "FetchData")
        }

        fun FilterStocks(): List<StockBasics> {
            // 排除掉未上市的
            // 排除掉 ST 股
            return StockBasics.where()
                    .eq("expired", false)
                    .isNotNull("time_to_market")
                    .findList()
                    .filter { !it.c_name.toUpperCase().contains("st", ignoreCase = true) }
        }

        fun AddTaskFor(stock: StockBasics, ktype: String) {
            val yearStart = JDateTime("2013-01-01", "YYYY-MM-DD")
            val thisYear = JDateTime().year
            while (yearStart.year <= thisYear) {
                val task = FetchHistoryData()
                task.code = stock.code
                val startDay = if (yearStart.isBeforeDate(stock.time_to_market)) stock.time_to_market!! else yearStart
                val endDay = startDay.clone().setMonth(12).setDay(31)
                task.start = startDay.toString("YYYY-MM-DD")
                task.end = endDay.toString("YYYY-MM-DD")
                task.ktype = ktype

                PlanTask.addTask(task, true, "FetchData")

                yearStart.year = startDay.year + 1
            }
        }

        fun AddTaskForIndex(code: String, ktype: String) {
            val yearStart = JDateTime("2013-01-01", "YYYY-MM-DD")
            val thisYear = JDateTime().year
            while (yearStart.year <= thisYear) {
                val task = FetchHistoryData()
                task.code = code
                task.start = yearStart.toString("YYYY-MM-DD")

                val endDay = yearStart.clone().setMonth(12).setDay(31)
                task.end = endDay.toString("YYYY-MM-DD")
                task.ktype = ktype

                PlanTask.addTask(task, true, "FetchData")

                yearStart.addYear(1)
            }
        }

        fun SetupHistoryDataTask(ktype: String) {
            val indexs = setOf("sh", "sz", "hs300", "sz50", "zxb", "cyb")
            indexs.forEach { AddTaskForIndex(it, ktype) }
            FilterStocks().forEach { AddTaskFor(it, ktype) }
        }
    }
}