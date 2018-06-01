package com.learncamel.routes.kafka;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class KafkaConsumerRouteTest extends CamelTestSupport {
    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new KafkaConsumerRoute();
    }

    @Test
    public void readMessageFromKafka(){
        String expected = "test message";
        String response = consumer.receiveBody("direct:readFromKafka", String.class);
        System.out.println("The response is : " + response);
        assertEquals(expected,response);
    }
}
