package com.stronger.study

import kotlin.*;

fun main() {
    var qwe: String = "asdq"
    printProduct("6", qwe)
}
fun printProduct(arg1: String, arg2: String) {
    val x = parseInt(arg1)
    val y = parseInt(arg2)

    // 直接使用 `x * y` 会导致编译错误，因为它们可能为 null
    if (x != null && y != null) {
        // 在空检测后，x 与 y 会自动转换为非空值（non-nullable）
        println(x * y)
    }
    else {
        println("'$arg1' or '$arg2' is not a number")
    }
}

fun parseInt(str: String): Int?{

    return str.toIntOrNull()
}


