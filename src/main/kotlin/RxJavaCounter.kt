import java.io.File
import java.math.BigInteger
import io.reactivex.schedulers.Schedulers
import io.reactivex.Flowable

class RxJavaCounter {
    fun findAllFactors() {
        val numbers = File("test.txt").readLines().map { BigInteger(it) }
        var count = BigInteger.ZERO

        val startTime = System.currentTimeMillis()

        Flowable.range(0, numbers.size)
            .parallel()
            .runOn(Schedulers.computation())
            .map { i ->
                FactorFinder().findFactor(numbers[i])
            }
            .sequential()
            .blockingSubscribe {
                count += it
            }

        val endTime = System.currentTimeMillis()

        val elapsed = endTime - startTime
        val file = File("result.txt")
        file.appendText("RxJava\nNumber of factors: $count\nElapsed: $elapsed")
    }
}