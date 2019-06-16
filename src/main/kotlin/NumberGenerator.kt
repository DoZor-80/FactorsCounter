import java.io.File
import java.math.BigInteger

class NumberGenerator {
    private fun generateNumber() : BigInteger {
        return BigInteger(32, java.util.Random())
    }

    fun writeNumber(newFile : Boolean) {
        val file = File("test.txt")
        if (newFile) {
            file.delete()
        }

        for (i in 1..500) {
            val newNumber = generateNumber()
            file.appendText("$newNumber\n")
        }
    }
}
