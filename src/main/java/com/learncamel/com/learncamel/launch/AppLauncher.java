package com.learncamel.com.learncamel.launch;

import com.learncamel.routes.kafka.KafkaConsumerRoute;
import org.apache.camel.main.Main;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

public class AppLauncher {
    public static void main(String[] args) throws Exception {
        Main main = new Main();

        String url = "jdbc:postgresql://localhost:5432/testDB";

        main.bind("PGDataSource",setupDataSource(url));  //map based registry
        //main.bind();

        main.addRouteBuilder(new KafkaConsumerRoute());

        System.out.println("Starting Camel JMS to DB Route.");

        main.run();


    }


    private static DataSource setupDataSource(String connectURI) {
        BasicDataSource ds = new BasicDataSource();
        ds.setUsername("postgres");
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setPassword("postgres");
        ds.setUrl(connectURI);
        return ds;
    }
}
