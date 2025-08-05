package com.example.demo.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSource {
  private final String host = System.getenv("DATABASE_HOST");
  private final String database = System.getenv("DATABASE_NAME");
  private final String user = System.getenv("DATABASE_USER");
  private final String password = System.getenv("DATABASE_PASSWORD");
  private final int port = 5432;
  private final String jdbcUrl;

  public DataSource() {
    jdbcUrl = "jdbc:postgresql://" + host + ":" + port + "/" + database;
  }

  public Connection getConnection() {
    try {
      return DriverManager.getConnection(jdbcUrl, user, password);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
