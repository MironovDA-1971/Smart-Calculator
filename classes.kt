package calculator

object Input {

    fun checkInput() {

        while (true) {
            val numString = readLine()?.trim()
                ?.replace("\\s{2,}".toRegex(), " ")
                ?.replace("[+]{2,}".toRegex(), "+")

            when (numString) {
                "/exit" -> break
                "/help" -> println("The program calculates the sum of numbers")
                "" -> continue
                else -> if (numString != null) regexStr(numString)
            }
        }
    }

    private fun plusMinus (numString: String) {
        val outString = numString.split(" ").toMutableList()
        try {
            var result = outString[0].toInt()
            for (i in outString.indices) {
                if ("--" in outString[i]) {
                    if (outString[i].length % 2 == 0) outString[i] = "+" else outString[i] = "-"
                }
                if (outString[i] == "+") result += outString[i + 1].toInt()
                else if (outString[i] == "-") result -= outString[i + 1].toInt()
            }
            println(result)
        } catch (e: Exception) {
            println("Invalid expression")
        }
    }

    private fun regexStr(numString: String) {
        if ("/.*".toRegex().matches(numString)) println("Unknown command")
        else if (
            "\\s?[-+]?\\d+\\s[+-]*".toRegex().containsMatchIn(numString) ||
            "\\s?[-+]?\\d+".toRegex().matches(numString)
        ) {
            plusMinus(numString)
        }
        else println("Invalid expression")
    }

}
