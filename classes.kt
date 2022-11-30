package calculator

object Input {

    private val mapList: MutableMap<String,String> = mutableMapOf()
    private val aZRegex = "[a-zA-Z]+".toRegex()
    private val digRegex = "-?\\d+".toRegex()


    fun checkInput() {
        while (true) {
            when (val numString = readLine()?.trim()) {
                "/exit" -> break
                "/help" -> println("The program calculates the sum of numbers")
                "" -> continue
                else -> if (numString != null) badCommandOrExpression(numString)
            }
        }
    }

    private fun badCommandOrExpression(numString: String) {
        if ("^/.*".toRegex().matches(numString)) errorMessage(5)
        else checkExpression(numString.trim())
    }

    private fun checkExpression(numString: String) {
        if(".*=.*".toRegex().matches(numString)) varEqualsVal(numString)
        else if ("^-?\\d+$".toRegex().matches(numString)) println(numString)
        else if (".*[-+*/].*".toRegex().matches(numString)) getValueOfVar(numString)
        else if (numString !in mapList.keys) errorMessage(3)
        else println(mapList[numString])
    }

    private fun varEqualsVal(numString: String) {
        val (b,c) = numString.split("=")
        if(!aZRegex.matches(b.trim())) errorMessage(1)
        else if(!aZRegex.matches(c.trim()) && !digRegex.matches(c.trim())) errorMessage(2)
        else if (mapList.containsKey(c.trim())) mapList += b.trim() to mapList[c.trim()].toString()
        else if (digRegex.matches(c.trim())) mapList += b.trim() to c.trim()
        else  errorMessage(3)
    }

    private fun getValueOfVar(numString: String) {
        var setFlag = listOf<Boolean>()
        var newStr = ""
        numString.split(" ").toSet().map {
            if (aZRegex.matches(it)) setFlag = listOf(it in mapList.keys)
        }
        if (false !in setFlag) {
            numString.split(" ").map {
                newStr += if (aZRegex.matches(it))  "${mapList[it]} " else "$it "
            }
            sumOfVar(newStr)
        } else errorMessage(3)
    }

    private fun sumOfVar(numString: String) {

        val string = numString
            .replace("\\s{2,}".toRegex(), " ")
            .replace("--".toRegex(), "+")
            .replace("[+]{2,}".toRegex(), "+")
            .replace("\\+- ".toRegex(), " - ")
            .replace(" + -".toRegex(), " - ")
           // .replace("\\+- ".toRegex(), "+ -")
           // .replace(" - ".toRegex(), " + -")
           // .split(" + ")
        try {
           // println(numList.sumOf { it.trim().toInt() })
            println("string = $string")
            Postfix().parseExpression(string)
        } catch (e: Exception) {
            errorMessage(4)
        }
    }

    private fun errorMessage(num: Int) {
        when(num) {
            1 -> println("Invalid identifier")
            2 -> println("Invalid assignment")
            3 -> println("Unknown variable")
            4 -> println("Invalid expression")
            5 -> println("Unknown command")
        }
    }
}