package com.example.demo.entity;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Help {
  private String id;
  private String beneficiaryEmail;
  private String beneficiaryNom;
  private Integer montant;
  private String moyenPaiement;
  private LocalDateTime datePaiement;
  private String descriptionAccident;
}
