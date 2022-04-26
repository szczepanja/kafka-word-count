object KafkaWordCounter extends App {

  import org.apache.kafka.streams.scala.StreamsBuilder
  import org.apache.kafka.streams.scala.ImplicitConversions._
  import org.apache.kafka.streams.scala.serialization.Serdes._

  val streamBuilder = new StreamsBuilder

  val records = streamBuilder.stream[String, String]("input1")

  val countWords = records.foreach {
    _.split("\\W+")
    .groupBy(identity)
    .view
    .mapValues(_.length)
    .foreach { case r@(word, occ) =>
      println(r)
      println(word, occ)
    }
  }

  countWords.to("output1")

  val topology = streamBuilder.build()

  import org.apache.kafka.streams.StreamsConfig
  import scala.jdk.CollectionConverters._

  import scala.concurrent.duration._

  val props = Map(
    StreamsConfig.APPLICATION_ID_CONFIG -> "kafka-streams-demo",
    StreamsConfig.BOOTSTRAP_SERVERS_CONFIG -> ":9092",
    StreamsConfig.POLL_MS_CONFIG -> 15.seconds.toMillis).asJava
  val config = new StreamsConfig(props)

  import org.apache.kafka.streams.KafkaStreams

  val streams = new KafkaStreams(topology, config)

  streams.start()
}
