import java.io.File
import java.math.BigInteger
import kotlin.system.measureTimeMillis

fun main () {
    println("Running...")

    // Create a file with random numbers
    // NumberGenerator().writeNumber(true)

    val file = File("result.txt")
    if (file.exists()){
        file.delete()
    }

    for (i in 1..2){
        val elapsedTime = measureTimeMillis {
            val result = useAlgorithm(i)
            var algorithmName = ""
            when (i) {
                1 -> algorithmName = "Sequential algorithm"
                2 -> algorithmName = "Synchronization primitives"
                3 -> algorithmName = "Akka"
                4 -> algorithmName = "RxJava"
            }
            file.appendText("$algorithmName\n")
            file.appendText("Number of factors: $result\n")
        }
        file.appendText("Elapsed: $elapsedTime\n\n")
    }

    AkkaCounter().findAllFactors()

    RxJavaCounter().findAllFactors()

}

fun useAlgorithm(i : Int) : BigInteger {
    return when(i) {
        1 -> SequentialAlgorithm().findAllFactors()
        2 -> SynchronizationPrimitives().findAllFactors()
        // 3 -> AkkaCounter().findAllFactors()
        // 4 -> RxJavaCounter().findAllFactors()
        else -> BigInteger.ZERO
    }
}
