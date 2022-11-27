package calculator

object Input {

    fun checkInput() {
        while (true) {
            when (val numString = readLine()!!) {
                "/exit" -> break
                "/help" -> println("The program calculates the sum of numbers")
                "" -> continue
                else -> println(numString.split(" ").sumOf { it.toInt() })
            }
        }
    }
}
