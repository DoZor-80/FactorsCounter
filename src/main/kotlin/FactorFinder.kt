import java.math.BigInteger

class FactorFinder {
    fun findFactor (number : BigInteger) : BigInteger {

        var myNumber = number
        var i = BigInteger.TWO

        var count = BigInteger.ZERO

        while (i <= myNumber) {
            if (myNumber % i == BigInteger.ZERO) {
                myNumber /= i
                count++
                continue
            }
            if (i == BigInteger.TWO){
                i++
            }
            else {
                i += BigInteger.TWO
            }
        }
        return count
    }
}
