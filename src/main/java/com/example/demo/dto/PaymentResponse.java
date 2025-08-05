package com.example.demo.dto;

import lombok.Data;

@Data
public class PaymentResponse {
  private String id;
  private String verificationStatus; // VERIFYING, SUCCEEDED, FAILED
}
