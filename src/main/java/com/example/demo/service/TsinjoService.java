package com.example.demo.service;

import com.example.demo.client.VolaClient;
import com.example.demo.entity.Donation;
import com.example.demo.entity.Help;
import com.example.demo.entity.Payment;
import com.example.demo.repository.TsinjoRepository;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TsinjoService {
  private final TsinjoRepository repository;
  private final VolaClient volaClient;

  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

  public List<Donation> getDonations() throws SQLException {
    return repository.getAllDonations();
  }

  public List<Help> getHelps() throws SQLException {
    return repository.getAllHelps();
  }

  public void createDonation(Donation donation) throws SQLException {
    donation.setId(UUID.randomUUID().toString());

    // Initialiser Payment si null
    if (donation.getPayment() == null) {
      donation.setPayment(new Payment());
    }

    donation.getPayment().setId(UUID.randomUUID().toString());
    donation.getPayment().setDate(Instant.now());
    donation.getPayment().setStatus("VERIFYING");

    repository.saveDonation(donation);

    // Appeler Vola
    volaClient.submitPayment(donation);

    // Polling async toutes les 5 sec
    scheduler.scheduleAtFixedRate(
        () -> {
          String currentStatus = volaClient.getPaymentStatus(donation);
          if (!"VERIFYING".equals(currentStatus)) {
            donation.getPayment().setStatus(currentStatus);
            try {
              repository.updateDonationStatus(donation.getPayment().getId(), currentStatus);
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }
        },
        0,
        5,
        TimeUnit.SECONDS);
  }
}
