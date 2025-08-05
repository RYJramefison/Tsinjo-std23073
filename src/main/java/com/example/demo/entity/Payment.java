package com.example.demo.entity;

import java.time.Instant;
import lombok.Data;

@Data
public class Payment {
  private String id;
  private Double amount;
  private String paymentMethod;
  private Instant date;
  private String status; // VERIFYING, SUCCEEDED, FAILED
}
