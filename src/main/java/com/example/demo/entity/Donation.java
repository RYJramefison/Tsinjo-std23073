package com.example.demo.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Donation {
  private String id;
  private String donorEmail;
  private String donorNom;
  private Integer montant;
  private String moyenPaiement;
  private LocalDateTime datePaiement;
  private String status;
  private String pspPaymentId;
}
