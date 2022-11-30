package calculator

import kotlin.math.pow

class Postfix  {

    var stack = mutableListOf<String>()
    var queue = mutableListOf<String>()
    var expressionList = mutableListOf<String>()

    fun parseExpression(string: String) {
        expressionList = string.split(" ").toMutableList()

        print("Инфиксное выражение: ")
        expressionList.forEach {
            print(it)
        }
        println()
        getPostFixEx()
    }

    fun getPostFixEx() {
        expressionList.forEach {
            when {
                it == "(" -> push(it)
                it == ")" -> if (expressionList.contains("(")) pop()
                it == "^" -> push(it)

                Regex("[\\d]+").containsMatchIn(it) -> addQueue(it)
                Regex("[+-]").containsMatchIn(it) ->
                    if (stack.isEmpty() || stack.last() == "(") push(it)
                    else if (stack.last().contains(Regex("[/*]"))) {
                        pop()
                        push(it)
                    } else {
                        addQueue(stack.last())
                        stack[stack.lastIndex] = it
                    }

                Regex("[*/]").containsMatchIn(it) -> {
                    if (stack.isNotEmpty() && (stack.last() == "*" || stack.last() == "/")) pop()
                    push(it)
                }
            }
        }
        if (stack.isNotEmpty()) {
            for (i in stack.lastIndex downTo 0) {
                if (stack[i] != "(") addQueue(stack[i])
            }
        }
        print("Постфиксное выражение: $queue\n")
        queue.forEach {
            print(it)
        }
        println()
        calcPostFix()
    }

    fun pop() {
        for (i in stack.lastIndex downTo 0) {
            if (stack[i] == "(") {
                stack[i] = " "
                break
            }
            addQueue(stack[i])
            stack[i] = " "
        }
        stack.removeIf { it == " " }
    }

    fun addQueue(item: String) {
        queue.add(item)
    }

    fun push(item: String) {
        stack.add(item)
    }

    fun calcPostFix() {
        val stack = mutableListOf<Int>()
        for (item in queue) {
            if (Regex("[\\d]+").matches(item)) stack.add(item.toInt())
            when (item) {
                "+" -> {
                    stack[stack.lastIndex - 1] = stack[stack.lastIndex - 1] + stack.last()
                    stack.removeAt(stack.lastIndex)
                }

                "*" -> {
                    stack[stack.lastIndex - 1] = stack[stack.lastIndex - 1] * stack.last()
                    stack.removeAt(stack.lastIndex)
                }

                "/" -> {
                    stack[stack.lastIndex - 1] = stack[stack.lastIndex - 1] / stack.last()
                    stack.removeAt(stack.lastIndex)
                }

                "-" -> {
                    stack[stack.lastIndex - 1] = stack[stack.lastIndex - 1] - stack.last()
                    stack.removeAt(stack.lastIndex)
                }

                "^" -> {
                    stack[stack.lastIndex - 1] = (stack[stack.lastIndex - 1].toDouble().pow(stack.last())).toInt()
                    stack.removeAt(stack.lastIndex)
                }
            }
        }

        println("Ответ: ${stack.first()}")
    }
}