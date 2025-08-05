package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@PojaGenerated
@ComponentScan({
  "com.example.demo.service",
  "com.example.demo.repository",
  "com.example.demo.client",
  "com.example.demo.dto",
  "com.example.demo.entity",
  "com.example.demo.frontend",
  "com.example.demo.endpoint.rest.controller.health.tsinjo"
})
public class PojaApplication {

  public static void main(String[] args) {
    SpringApplication.run(PojaApplication.class, args);
  }
}
