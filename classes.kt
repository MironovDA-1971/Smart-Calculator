package calculator

object Input {

    private val mapList: MutableMap<String,String> = mutableMapOf()
    private val aZRegex = "[a-zA-Z]+".toRegex()
    private val digRegex = "-?\\d+".toRegex()


    fun checkInput() {
        while (true) {
            when (val string = readLine()?.trim()) {
                "/exit" -> break
                "/help" -> println("The program calculates the sum of numbers")
                "" -> continue
                else -> if (string != null) badCommandOrExpression(string)
            }
        }
    }
    private fun trimPlus(string: String) = string
        .replace(Regex("\\s{2,}"), " ")
        .replace(Regex("--"), "+")
        .replace(Regex("[+]{2,}"), "+")
        .replace(Regex("\\+- "), " - ")
        .replace(" + -", " - ")

    private fun addSpace(string: String) = string
        .replace(Regex("\\d+"), "\$0 ")
        .replace(Regex("[-+*/)(]"), "\$0 ")
        .replace(Regex("[a-zA-Z]+"), "\$0 ")
        .replace("  ", " ")

    private fun badCommandOrExpression(string: String) {
        if ("^/.*".toRegex().matches(string)) errorMessage(5)
        else checkExpression(trimPlus(string))
    }

    private fun checkExpression(string: String) {
        var stop = false
        if (".*[)(].*".toRegex().matches(string)) stop = countBracket(string)
        if (!stop) {
            if(".*=.*".toRegex().matches(string)) varEqualsVal(string)
            else if ("^-?\\d+$".toRegex().matches(string)) println(string)
            else if (".*[-+*/].*".toRegex().matches(string)) getValueOfVar(string)
            else if (string !in mapList.keys) errorMessage(3)
            else println(mapList[string])
        }
    }

    private fun countBracket(string: String): Boolean {
        var stop = false
        if (string.split("(").lastIndex != string.split(")").lastIndex) {
            stop = true
            errorMessage(4)
        }
        return stop
    }

    private fun varEqualsVal(string: String) {
        val (b,c) = string.split("=")
        if(!aZRegex.matches(b.trim())) errorMessage(1)
        else if(!aZRegex.matches(c.trim()) && !digRegex.matches(c.trim())) errorMessage(2)
        else if (mapList.containsKey(c.trim())) mapList += b.trim() to mapList[c.trim()].toString()
        else if (digRegex.matches(c.trim())) mapList += b.trim() to c.trim()
        else  errorMessage(3)
    }

    private fun getValueOfVar(string: String) {
        var setFlag = listOf<Boolean>()
        var newStr = ""
        string.split(" ").toSet().map {
            if (aZRegex.matches(it)) setFlag = listOf(it in mapList.keys)
        }
        if (false !in setFlag) {
            string.split(" ").map {
                newStr += if (aZRegex.matches(it))  "${mapList[it]} " else "$it "
            }
            sumOfVar(newStr)
        } else errorMessage(3)
    }

    private fun sumOfVar(string: String) {
        try {
        var spacedString = trimPlus(string)
            spacedString = addSpace(spacedString)
            Postfix().parseExpression(spacedString)
        } catch (e: Exception) {
           // println(e)
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