package calculator

object Input {

    fun checkInput() {
        while (true) {
            when (val numString = readLine()?.trim()) {
                "/exit" -> break
                "/help" -> println("The program calculates the sum of numbers")
                "" -> continue
                else -> if (numString != null) plusMinus(numString)
            }
        }
    }

    private fun plusMinus(numString: String) {
        var numList = List(0){""}
        if ("/.*".toRegex().matches(numString)) println("Unknown command")
        else {
            numList = numString
                .replace("\\s{2,}".toRegex(), " ")
                .replace("--".toRegex(), "+")
                .replace("[+]{2,}".toRegex(), "+")
                .replace("\\+- ".toRegex(), "+ -")
                .replace(" - ".toRegex(), " + -")
                .split(" + ")
        }
        try {
            var result = numList.sumOf { it.trim().toInt() }
            println(result)
        } catch (e: Exception) {
            println("Invalid expression")
        }
    }
}
