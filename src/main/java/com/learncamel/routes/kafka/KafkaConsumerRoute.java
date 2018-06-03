package com.learncamel.routes.kafka;

import com.learncamel.processors.db.InsertProcessor;
import com.learncamel.routes.exceptions.ExceptionProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.postgresql.util.PSQLException;

public class KafkaConsumerRoute extends RouteBuilder {

    public static final String KAFKA_CONSUMER = "kafkaConsumer";

    public void configure() throws Exception {
        from("kafka:localhost:9092?brokers=localhost:9092&topic=testTopic&groupId=group1&consumersCount=1&autoOffsetReset=latest").routeId(KAFKA_CONSUMER)
                .onException(PSQLException.class, Exception.class).handled(true)
                    .log("Exception While inserting messages.")
                    .process(new ExceptionProcessor())
                .end()
                .log("Received message from broker: ${body}")
                //.to("direct:readFromKafka")
                .process(new InsertProcessor())
                .to("jdbc:PGDataSource")
                .to("sql:select * from message order by create_date desc limit 1?dataSource=PGDataSource")
                .to("log:level=INFO&showBody=true")
                //.to("direct:output")
        .end();
    }
}
