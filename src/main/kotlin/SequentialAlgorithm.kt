import java.io.File
import java.math.BigInteger

class SequentialAlgorithm{
    fun findAllFactors() : BigInteger{
        val file = File("test.txt")
        var count = BigInteger.ZERO

        var i = 0
        file.forEachLine {
            i++
            println(i)
            count += FactorFinder().findFactor(BigInteger(it))
        }
        return count
    }
}
