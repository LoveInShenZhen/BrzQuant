package models.tushare

/**
 * Created by kk on 16/9/24.
 */
enum class StockIndex(val desc: String, val indexCode: String) {
    SH("上证指数", "000001"),
    SZ("深圳成指", "399001"),
    HS300("沪深300指数", "000300"),
    SZ50("上证50指数", "000016"),
    ZXB("中小板指数", "399005"),
    CYB("创业板指数", "399006"),
    SZZH("深证综合指数", "399106")
}