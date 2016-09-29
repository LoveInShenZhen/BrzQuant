package tushare.tasks

import jodd.datetime.JDateTime
import k.common.BizLogicException
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
    var retry_count = 3     // 当网络异常后重试次数,默认为3
    var pause = 0           // 重试时停顿秒数,默认为0

    override fun run() {

        val dataFile = File(csvPath())
        // 先判断该次请求的数据文件是否已经存在, 如果已经存在, 则直接更新到数据库
        if (dataFile.exists()) {
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
            val result = fetchData()
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
                throw BizLogicException("Failed to getHistoryData.\n${result.msg}")
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
        fun AddTask(code:String, start:JDateTime, end:JDateTime, ktype:String, retryCount:Int=3, pause:Int=0) {
            val task = FetchHistoryData();
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
    }
}