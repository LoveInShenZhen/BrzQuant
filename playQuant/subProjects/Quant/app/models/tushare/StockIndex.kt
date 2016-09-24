package models.tushare

/**
 * Created by kk on 16/9/24.
 */
enum class StockIndex(val code:String, val desc:String) {
    SH("sh", "上证指数"),
    SZ("sz", "深圳成指"),
    HS300("hs300", "沪深300指数"),
    SZ50("sz50", "上证50"),
    ZXB("zxb", "中小板"),
    CYB("cyb", "创业板")
}