package tushare

import jodd.datetime.JDateTime
import jodd.io.FastByteArrayOutputStream
import jodd.io.FileNameUtil
import k.common.Helper
import k.common.Hub
import org.apache.commons.csv.CSVRecord
import org.apache.commons.exec.CommandLine
import org.apache.commons.exec.DefaultExecutor
import org.apache.commons.exec.ExecuteWatchdog
import org.apache.commons.exec.PumpStreamHandler
import java.math.BigDecimal

/**
 * Created by kk on 16/9/26.
 */


private fun ToBigDecimal(str: String?): BigDecimal? {
    if (str == null) return null

    return BigDecimal(str)
}

private fun ToBoolean(str: String?): Boolean? {
    if (str == null) return null

    if (str.toLowerCase() in arrayOf("1", "yes", "true")) {
        return true
    } else {
        return false
    }
}

fun CSVRecord.getBigDecimal(name: String): BigDecimal? {
    return ToBigDecimal(this.get(name))
}

fun CSVRecord.getBoolean(name: String): Boolean? {
    return ToBoolean(this.get(name))
}

fun CSVRecord.getJDateTime(name: String, fmt: String = "YYYYMMDD"): JDateTime? {
    val value = this.get(name)
    // 分析数据文件得知, 等待上市的股票, 上市日期的字段会给 "0"
    if (value.isBlank() || value == "0") return null
    return JDateTime(value, fmt)
}

data class ScriptResult(var ret: Int = 0, var msg: String = "", var csvpath: String = "")

class TuShareScript(val name: String, timeOutMs: Long = 60000) {

    private val cmd: CommandLine
    private val executor: DefaultExecutor
    private var exitValue = Int.MIN_VALUE
    private val outAndErr: FastByteArrayOutputStream

    init {
        cmd = CommandLine(FileNameUtil.concat(QuantConfig.scriptDir, name))

        executor = DefaultExecutor()

        executor.watchdog = ExecuteWatchdog(timeOutMs)

        outAndErr = FastByteArrayOutputStream()
        executor.streamHandler = PumpStreamHandler(outAndErr)

        executor.setExitValue(0)

    }

    fun AddArgument(argument: String): TuShareScript {
        cmd.addArgument(argument)
        return this
    }

    fun Run(): TuShareScript {
        exitValue = executor.execute(cmd)
        return this
    }

    val ExitCode: Int
        get() = this.exitValue

    val Output: String
        get() = outAndErr.toString("UTF-8")

    val Result: ScriptResult
        get() {
            if (ExitCode == 0) {
                return Helper.FromJsonString(Output, ScriptResult::class.java)
            } else {
                return ScriptResult(ExitCode, "python script error", "")
            }
        }

    val CommandLineString: String
        get() {
            return cmd.toString()
        }
}

object QuantConfig {
    val scriptDir: String
        get() {
            return Hub.configuration().getString("tushare.scriptDir", "/Users/kk/ssdwork/github/BrzQuant/tushareQuant/src")
        }

    val dataDir: String
        get() {
            return Hub.configuration().getString("tushare.dataDir", "/Users/kk/ssdwork/github/tuShareData")
        }
}

