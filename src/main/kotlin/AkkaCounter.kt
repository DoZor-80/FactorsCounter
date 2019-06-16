import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.japi.pf.ReceiveBuilder
import java.io.File
import java.math.BigInteger

data class CountFactors(val countFactors: BigInteger)

class MainActor : AbstractActor() {
    private var numberOfWorkers = 10
    private var finished = 0

    private var workers = Array<ActorRef>(numberOfWorkers) {
        context.actorOf(Props.create(SlaveActor::class.java))
    }

    private var startTime = 0L
    var count = BigInteger.ZERO

    override fun createReceive() = ReceiveBuilder()
        .match(File::class.java) {file ->
            val numbers = file.readLines().map { BigInteger(it) }
            numberOfWorkers = numbers.size
            workers = Array(numberOfWorkers) { context.actorOf(Props.create(SlaveActor::class.java)) }
            println("Workers have started to do their job: ${self.path()}")

            startTime = System.currentTimeMillis()
            for (i in 0 until numberOfWorkers) {
                workers[i].tell(numbers[i], self)
            }
        }
        .match(CountFactors::class.java) {
            count += it.countFactors
            finished++
            if (finished == numberOfWorkers) {
                val endTime = System.currentTimeMillis()
                val elapsed = (endTime - startTime)

                val file = File("result.txt")
                file.appendText("Akka\nNumber of factors: $count\nElapsed: $elapsed\n\n")
                context.system.terminate()
            }
        }
        .build()!!
}

class SlaveActor : AbstractActor() {
    override fun createReceive() = ReceiveBuilder()
        .match(BigInteger::class.java) {
            val countFactors = FactorFinder().findFactor(it)
            sender.tell(CountFactors(countFactors), self)
        }
        .matchAny {
            println("It's not a BigInteger or File")
        }
        .build()!!
}

class AkkaCounter {
    fun findAllFactors() {
        val file = File("test.txt")

        val actorSystem = ActorSystem.create("example-system")
        val actorRef = actorSystem.actorOf(Props.create(MainActor::class.java))
        actorRef.tell(file, actorRef)
    }
}