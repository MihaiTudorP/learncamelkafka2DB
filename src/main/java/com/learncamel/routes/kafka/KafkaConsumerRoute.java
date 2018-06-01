package com.learncamel.routes.kafka;

import org.apache.camel.builder.RouteBuilder;

public class KafkaConsumerRoute extends RouteBuilder {

    public static final String KAFKA_CONSUMER = "kafkaConsumer";

    public void configure() throws Exception {
        from("kafka:localhost:9092?brokers=localhost:9092&topic=testTopic&groupId=group1&consumersCount=1&autoOffsetReset=latest").routeId(KAFKA_CONSUMER)
                .log("Received message from broker: ${body}")
                .to("direct:readFromKafka")
        .end();
    }
}
