import org.apache.kafka.clients.producer.*
import java.util.Properties
import java.util.UUID


Properties props = new Properties()
props.put("bootstrap.servers", vars.get("bootstrap_servers"))
props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
props.put("acks", "1")

KafkaProducer<String, String> producer  = new KafkaProducer<>(props)

try{
    String msgUuid = UUID.randomUUID().toString()
    int counter = vars.get("counter") as int
    boolean head = (counter % 10 != 0)

    String json = """{"msg_uuid": "${msgUuid}", "head": ${head}, "method": "POST", "uri": "/post-message"}"""

    ProducerRecorder <String, String> record = new ProducerRecorder<>(vars.get("topic"), json)
    producer.send(record)

    log.info("Sent: " + json)
} finally {
    producer.close()
}