package calculator

object Input {

    fun checkInput() {
        while (true) {
            when (val numString = readLine()?.trim()) {
                "/exit" -> break
                "/help" -> println("The program calculates the sum of numbers")
                "" -> continue
                else -> if (numString != null) testString(numString)
            }
        }
    }

    private fun testString(numString: String) {
        if ("/.*".toRegex().matches(numString)) println("Unknown command")
        else if("^[a-zA-Z]+.*".toRegex().matches(numString)) {
            checkVal(numString)
        }
        else plusMinus(numString)

    }

    private fun checkVal(numString: String) {
        if("^[a-zA-Z]+\\s*=\\s*\\d+".toRegex().matches(numString)){}
    }

    private fun plusMinus(numString: String) {
        val numList = numString
            .replace("\\s{2,}".toRegex(), " ")
            .replace("--".toRegex(), "+")
            .replace("[+]{2,}".toRegex(), "+")
            .replace("\\+- ".toRegex(), "+ -")
            .replace(" - ".toRegex(), " + -")
            .split(" + ")
        try {
            println(numList.sumOf { it.trim().toInt() })
        } catch (e: Exception) {
            println("Invalid expression")
        }
    }
}
