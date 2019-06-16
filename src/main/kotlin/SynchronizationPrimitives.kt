import java.io.File
import java.math.BigInteger

class SynchronizationPrimitives {
    private val numberOfThreads = 2
    private var numbersProcessed = 0
    private val file = File("test.txt")
    private var count = BigInteger.ZERO
    private val numbers = file.readLines().map { BigInteger(it) }

    fun findAllFactors() : BigInteger{

        val threads = List(numberOfThreads){
            Thread(Runnable {
                println("${Thread.currentThread().name} has run.")
                var countThread = BigInteger.ZERO
                while (numbersProcessed < numbers.size){
                    val number = getNumber()
                    countThread += FactorFinder().findFactor(number)
                }
                increaseCounter(countThread)
            })
        }

        for (thread in threads){
            thread.start()
        }

        for (thread in threads){
            thread.join()
        }

        return count
    }

    @Synchronized private fun getNumber(): BigInteger {
        numbersProcessed++
        return numbers[numbersProcessed-1]
    }

    @Synchronized private fun increaseCounter(numOfFactors : BigInteger) {
        count += numOfFactors
    }
}
