package com.example.demo.repository;

import com.example.demo.entity.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class TsinjoRepository {

  private final DataSource dataSource;

  public TsinjoRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void saveDonation(Donation donation) throws SQLException {
    // 1. Save Donor if not exists
    String donorSql =
        "INSERT INTO donor (email, full_name) VALUES (?, ?) ON CONFLICT (email) DO NOTHING";
    // 2. Save Payment
    String paymentSql =
        "INSERT INTO payment (id, amount, payment_method, payment_date, status) VALUES (?, ?, ?, ?,"
            + " ?)";
    // 3. Save Donation
    String donationSql = "INSERT INTO donation (id, donor_email, payment_id) VALUES (?, ?, ?)";

    try (Connection conn = dataSource.getConnection()) {
      conn.setAutoCommit(false);
      try {
        // Save Donor
        try (PreparedStatement stmt = conn.prepareStatement(donorSql)) {
          stmt.setString(1, donation.getDonor().getEmail());
          stmt.setString(2, donation.getDonor().getFullName());
          stmt.executeUpdate();
        }

        // Save Payment
        try (PreparedStatement stmt = conn.prepareStatement(paymentSql)) {
          stmt.setString(1, donation.getPayment().getId());
          stmt.setDouble(2, donation.getPayment().getAmount());
          stmt.setString(3, donation.getPayment().getPaymentMethod());
          stmt.setTimestamp(4, Timestamp.from(donation.getPayment().getDate()));
          stmt.setString(5, donation.getPayment().getStatus());
          stmt.executeUpdate();
        }

        // Save Donation
        try (PreparedStatement stmt = conn.prepareStatement(donationSql)) {
          stmt.setString(1, donation.getId());
          stmt.setString(2, donation.getDonor().getEmail());
          stmt.setString(3, donation.getPayment().getId());
          stmt.executeUpdate();
        }

        conn.commit();
      } catch (SQLException ex) {
        conn.rollback();
        throw ex;
      }
    }
  }

  public List<Donation> getAllDonations() throws SQLException {
    String sql =
        """
        SELECT d.id as donation_id, donor.email as donor_email, donor.full_name as donor_name,
               p.id as payment_id, p.amount, p.payment_method, p.payment_date, p.status
        FROM donation d
        JOIN donor ON d.donor_email = donor.email
        JOIN payment p ON d.payment_id = p.id
        """;

    List<Donation> donations = new ArrayList<>();

    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Donor donor = new Donor();
        donor.setEmail(rs.getString("donor_email"));
        donor.setFullName(rs.getString("donor_name"));

        Payment payment = new Payment();
        payment.setId(rs.getString("payment_id"));
        payment.setAmount(rs.getDouble("amount"));
        payment.setPaymentMethod(rs.getString("payment_method"));
        payment.setDate(rs.getTimestamp("payment_date").toInstant());
        payment.setStatus(rs.getString("status"));

        Donation donation = new Donation();
        donation.setId(rs.getString("donation_id"));
        donation.setDonor(donor);
        donation.setPayment(payment);

        donations.add(donation);
      }
    }

    return donations;
  }

  public List<Help> getAllHelps() throws SQLException {
    String sql =
        """
        SELECT h.id as help_id, b.email as beneficiary_email, b.full_name as beneficiary_name,
               p.id as payment_id, p.amount, p.payment_method, p.payment_date, p.status,
               h.description_accident
        FROM help h
        JOIN beneficiary b ON h.beneficiary_email = b.email
        JOIN payment p ON h.payment_id = p.id
        """;

    List<Help> helps = new ArrayList<>();

    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setEmail(rs.getString("beneficiary_email"));
        beneficiary.setFullName(rs.getString("beneficiary_name"));

        Help help = new Help();
        help.setId(rs.getString("help_id"));
        help.setBeneficiary(beneficiary);
        help.setMontant(rs.getInt("amount"));
        help.setMoyenPaiement(rs.getString("payment_method"));

        Timestamp ts = rs.getTimestamp("payment_date");
        if (ts != null) {
          help.setDatePaiement(ts.toLocalDateTime());
        }

        help.setDescriptionAccident(rs.getString("description_accident"));
        helps.add(help);
      }
    }

    return helps;
  }

  public void updateDonationStatus(String paymentId, String newStatus) throws SQLException {
    String sql = "UPDATE payment SET status = ? WHERE id = ?";

    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, newStatus);
      stmt.setString(2, paymentId);

      stmt.executeUpdate();
    }
  }
}
