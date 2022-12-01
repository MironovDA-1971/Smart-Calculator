package calculator

import java.math.BigInteger
// import kotlin.math.pow

class Postfix  {

    private var stack = mutableListOf<String>()
    private var queue = mutableListOf<String>()
    private var expressionList = mutableListOf<String>()


    fun parseExpression(string: String) {
        expressionList = string.split(" ").toMutableList()
        getPostFixEx()
    }

    private fun getPostFixEx() {
        expressionList.forEach {
            when {
                it == "(" -> push(it)
                it == ")" -> if (expressionList.contains("(")) pop()
                it == "^" -> push(it)

                Regex("-?\\d+").containsMatchIn(it) -> addQueue(it)
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
        calcPostFix()
    }

    private fun pop() {
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

    private fun addQueue(item: String) {
        queue.add(item)
    }

    private fun push(item: String) {
        stack.add(item)
    }

    private fun calcPostFix() {
        val stack = mutableListOf<BigInteger>()
        for (item in queue) {
            if (Regex("-?\\d+").matches(item)) stack.add(item.toBigInteger())
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
                /*
                "^" -> {
                    stack[stack.lastIndex - 1] = (stack[stack.lastIndex - 1].toDouble().pow(stack.last())).toInt()
                    stack.removeAt(stack.lastIndex)
                }
                 */
            }
        }

        println(stack.first())
    }
}
