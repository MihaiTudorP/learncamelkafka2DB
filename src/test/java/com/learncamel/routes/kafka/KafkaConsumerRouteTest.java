package com.learncamel.routes.kafka;

import com.learncamel.routes.exceptions.ExceptionProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class KafkaConsumerRouteTest extends CamelTestSupport {

    public static final String DIRECT_READ_FROM_KAFKA = "direct:readFromKafka";
    private Logger logger = LoggerFactory.getLogger(KafkaConsumerRouteTest.class);

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new KafkaConsumerRoute();
    }

    @Test
    public void readMessageFromKafka(){
        String expected = "test message";
        String response = consumer.receiveBody(DIRECT_READ_FROM_KAFKA, String.class);
        logger.info("The response is : [{}]", response);
        assertEquals(expected,response);
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        String url = "jdbc:postgresql://localhost:5432/testDB";
        DataSource dataSource = setupDataSource(url);

        SimpleRegistry registry = new SimpleRegistry();
        registry.put("PGDataSource",dataSource);

        CamelContext context = new DefaultCamelContext(registry);
        // plug in a seda component, as we don't really need an embedded broker
        return context;
    }

    @Test
    public void kafka2DBRouteTest(){
        String receivedMessage = consumer.receiveBody(DIRECT_READ_FROM_KAFKA, String.class);
        assertNotNull(receivedMessage);
        List responseList = (ArrayList) consumer.receiveBody("direct:output");
        logger.info("Response list size: [{}]", responseList.size());
        assertNotEquals(0,responseList.size());
        logger.info("Last entry inserted: [{}]", responseList.get(0));
    }

    private DataSource setupDataSource(String url) {
        BasicDataSource ds = new BasicDataSource();
        ds.setUsername("postgres");
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setPassword("postgres");
        ds.setUrl(url);
        return ds;
    }


}
