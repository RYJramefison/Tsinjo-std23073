package com.example.demo.controller;

import com.example.demo.entity.Donation;
import com.example.demo.entity.Donor;
import com.example.demo.entity.Help;
import com.example.demo.entity.Payment;
import com.example.demo.service.TsinjoService;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TsinjoController {

  private final TsinjoService tsinjoService;

  @GetMapping("/tsinjo")
  public String home(Model model) throws SQLException {
    model.addAttribute("donations", tsinjoService.getDonations());
    model.addAttribute("helps", tsinjoService.getHelps());
    return "index";
  }

  @GetMapping("/helps")
  public List<Help> getHelps() throws SQLException {
    return tsinjoService.getHelps();
  }

  @PostMapping("/donate")
  public String donate(
      @RequestParam String donorEmail,
      @RequestParam String donorNom,
      @RequestParam String moyenPaiement,
      @RequestParam String pspPaymentId,
      @RequestParam Integer montant)
      throws SQLException {

    Donation donation = new Donation();
    Donor donor = new Donor();
    donor.setEmail(donorEmail);
    donor.setFullName(donorNom);

    Payment payment = new Payment();
    payment.setAmount(montant.doubleValue());
    payment.setPaymentMethod(moyenPaiement);
    payment.setId(pspPaymentId); // Tu pourras ignorer ce champ si Vola te génère l’id

    donation.setDonor(donor);
    donation.setPayment(payment);

    tsinjoService.createDonation(donation);

    return "redirect:/tsinjo";
  }
}
