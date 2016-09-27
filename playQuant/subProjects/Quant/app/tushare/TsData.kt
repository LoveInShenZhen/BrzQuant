package tushare

import jodd.datetime.JDateTime
import org.apache.commons.csv.CSVRecord
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
    if (value.isBlank()) return null
    return JDateTime(value, fmt)
}

