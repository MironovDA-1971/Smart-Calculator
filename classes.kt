package calculator

object Input {
    private val mapList: MutableMap<String,String> = mutableMapOf()

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
        else if("^[a-zA-Z]+.*".toRegex().matches(numString)) checkVar(numString.trim())
        else plusMinus(numString)
    }

    private fun checkVar(numString: String) {
        var uvFlag = true
        var iaFlag = true
        var iiFlag = true
        var setFlag = listOf<Boolean>()
        var newStr = ""
        if(".*=.*".toRegex().matches(numString)){
            val (b,c) = numString.split("=")
            if(!"[a-zA-Z]+".toRegex().matches(b.trim())) iiFlag = false
            else if(
                !"[a-zA-Z]+".toRegex().matches(c.trim()) &&
                !"\\d+".toRegex().matches(c.trim())
            ) iaFlag = false
            else if (mapList.containsKey(c.trim())) mapList += b.trim() to mapList[c.trim()].toString()
            else if ("\\d+".toRegex().matches(c.trim())) mapList += b.trim() to c.trim()
            else  uvFlag = false
            //println(mapList)
        }
        else if (".*[-|+].*".toRegex().matches(numString)) {
            numString.split(" ").toSet().mapNotNull {
                if ("[a-zA-Z]+".toRegex().matches(it)) setFlag = listOf(it in mapList.keys)
            }
            if (false !in setFlag) {
                numString.split(" ").map {
                        newStr += if ("[a-zA-Z]+".toRegex().matches(it))  "${mapList[it]} "
                        else "$it "
                    }
                plusMinus(newStr)
            }
        }
        else if (numString !in mapList.keys) uvFlag = false
        else println(mapList[numString])

        if (!iiFlag) println("Invalid identifier")
        if (!iaFlag) println("Invalid assignment")
        if (!uvFlag || (false in setFlag)) println("Unknown variable")
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
