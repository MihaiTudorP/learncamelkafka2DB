package com.learncamel.processors.db;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsertProcessor implements org.apache.camel.Processor {

    private Logger logger = LoggerFactory.getLogger(InsertProcessor.class);

    public void process(Exchange exchange) throws Exception {
        String input = exchange.getIn().getBody(String.class);
        logger.info("Input to be persisted : [{}]", input);

        String insertQuery = "INSERT INTO message (message) values ( '" + input+"')";
        logger.info("The insert query is: [{}]", insertQuery);
        exchange.getIn().setBody(insertQuery);
    }
}
