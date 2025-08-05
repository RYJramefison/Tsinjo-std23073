package com.example.demo.entity;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Help {
  private String id;
  private Beneficiary beneficiary;
  private Integer montant;
  private String moyenPaiement;
  private LocalDateTime datePaiement;
  private String descriptionAccident;
}
