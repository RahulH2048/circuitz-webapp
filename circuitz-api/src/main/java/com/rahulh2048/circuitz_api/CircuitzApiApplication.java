package com.rahulh2048.circuitz_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class CircuitzApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CircuitzApiApplication.class, args);

        // read circuit
        // do dfs on the circuit
        // if vertex has been visited store the edge as a cycle edge
        // else add edge to spanning tree
    }
}
