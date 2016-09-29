package tushare.tasks

import k.common.BizLogicException
import k.common.Helper
import models.tushare.StockBasics
import tushare.QuantConfig
import tushare.ScriptResult
import tushare.TuShareScript
import java.io.File

/**
 * Created by kk on 16/9/29.
 */

class FetchStockBasics : Runnable {

    override fun run() {
        val dataFile = File(csvPath())
        // 先判断该次请求的数据文件是否已经存在, 如果已经存在, 则直接更新到数据库
        if (dataFile.exists()) {
            StockBasics.CsvToDb(dataFile)
        } else {
            val result = fetchData()
            if (result.ret == 0) {
                val csvFile = File(result.csvpath)
                StockBasics.CsvToDb(csvFile)
            } else {
                throw BizLogicException("Failed to getStockBasics.\n${result.msg}")
            }
        }
    }

    private fun csvPath(): String {
        val script = TuShareScript(name = "getStockBasics.py")
        script.AddArgument("--dir").AddArgument(QuantConfig.dataDir).AddArgument("--fake")

        return script.Run().Result.csvpath
    }

    private fun fetchData(): ScriptResult {
        val script = TuShareScript(name = "getStockBasics.py")
        script.AddArgument("--dir").AddArgument(QuantConfig.dataDir)
        Helper.DLog(script.CommandLineString)
        return script.Run().Result
    }
}