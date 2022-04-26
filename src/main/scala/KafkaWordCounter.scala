object KafkaWordCounter extends App {

  import org.apache.kafka.streams.scala.StreamsBuilder
  import org.apache.kafka.streams.scala.ImplicitConversions._
  import org.apache.kafka.streams.scala.serialization.Serdes._

  val streamBuilder = new StreamsBuilder

  type Line = String
  streamBuilder
    .stream[String, Line]("input1")
    .flatMapValues { _.split("\\W+") }
    .groupBy { (_, value) => value }
    .count
    // optional
    // Alternatively, use
    // --value-deserializer "org.apache.kafka.common.serialization.LongDeserializer"
    .mapValues(_.toString)
    .toStream
    .to("output1")

  val topology = streamBuilder.build()

  import org.apache.kafka.streams.StreamsConfig
  import scala.jdk.CollectionConverters._

  import scala.concurrent.duration._

  import org.apache.kafka.clients.consumer.ConsumerConfig
  val props = Map(
    StreamsConfig.APPLICATION_ID_CONFIG -> "brand_new_brand_new_kafka-streams-demo",
    StreamsConfig.BOOTSTRAP_SERVERS_CONFIG -> ":9092",
    StreamsConfig.COMMIT_INTERVAL_MS_CONFIG -> 5.seconds.toMillis,
    ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> "latest").asJava
  val config = new StreamsConfig(props)

  import org.apache.kafka.streams.KafkaStreams
  val streams = new KafkaStreams(topology, config)
  streams.start()
}
