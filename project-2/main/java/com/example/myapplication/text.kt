package com.example.myapplication
fun main() {
    println("请输入第一个数字:")
    val num1 = readLine()?.toDouble() ?: 0.0

    println("请输入第二个数字:")
    val num2 = readLine()?.toDouble() ?: 0.0

    println("请选择操作符(+, -, *, /):")
    val operator = readLine()

    val result = when (operator) {
        "+" -> num1 + num2
        "-" -> num1 - num2
        "*" -> num1 * num2
        "/" -> num1 / num2
        else -> 0.0
    }

    println("结果为: $result")
}


