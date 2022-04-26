import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Printed

import java.util.Properties

object KafkaWordCounter extends App {

  val props = new Properties()

  import org.apache.kafka.common.serialization.Serdes
  import org.apache.kafka.streams.StreamsConfig

  props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String.getClass)
  props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String.getClass)
  props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe")
  props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, ":9092")

  val builder = new StreamsBuilder

  val source = builder.stream[String, String]("input-topic")

  source.to("output-topic")
  source.print(Printed.toSysOut[String, String].withLabel("DEMO"))

  val topology = builder.build()
  println(topology.describe())
}
