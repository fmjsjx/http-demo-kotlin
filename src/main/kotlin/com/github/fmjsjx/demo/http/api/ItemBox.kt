package com.github.fmjsjx.demo.http.api

data class ItemBox(
    var item: Int = 0,
    var num: Int = 0,
) {

    fun toPair(): Pair<Int, Int> = item to num

}
