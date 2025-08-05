package com.example.demo.repository;

import com.example.demo.entity.Donation;
import com.example.demo.entity.Help;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class TsinjoRepository {

  private final com.example.demo.repository.DataSource dataSource;

  public TsinjoRepository(com.example.demo.repository.DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void saveDonation(Donation donation) throws SQLException {
    String sql =
        "INSERT INTO donation (id, donor_email, donor_nom, montant, moyen_paiement, date_paiement,"
            + " status, psp_payment_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, donation.getId());
      stmt.setString(2, donation.getDonorEmail());
      stmt.setString(3, donation.getDonorNom());
      stmt.setInt(4, donation.getMontant());
      stmt.setString(5, donation.getMoyenPaiement());
      stmt.setTimestamp(6, Timestamp.valueOf(donation.getDatePaiement()));
      stmt.setString(7, donation.getStatus());
      stmt.setString(8, donation.getPspPaymentId());

      stmt.executeUpdate();
    }
  }

  public List<Donation> getAllDonations() throws SQLException {
    String sql = "SELECT * FROM donation";
    List<Donation> donations = new ArrayList<>();

    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Donation d = new Donation();
        d.setId(rs.getString("id"));
        d.setDonorEmail(rs.getString("donor_email"));
        d.setDonorNom(rs.getString("donor_nom"));
        d.setMontant(rs.getInt("montant"));
        d.setMoyenPaiement(rs.getString("moyen_paiement"));

        Timestamp ts = rs.getTimestamp("date_paiement");
        if (ts != null) {
          d.setDatePaiement(ts.toLocalDateTime());
        }

        d.setStatus(rs.getString("status"));
        d.setPspPaymentId(rs.getString("psp_payment_id"));

        donations.add(d);
      }
    }

    return donations;
  }

  public List<Help> getAllHelps() throws SQLException {
    String sql = "SELECT * FROM help";
    List<Help> helps = new ArrayList<>();

    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Help h = new Help();
        h.setId(rs.getString("id"));
        h.setBeneficiaryEmail(rs.getString("beneficiary_email"));
        h.setBeneficiaryNom(rs.getString("beneficiary_nom"));
        h.setMontant(rs.getInt("montant"));
        h.setMoyenPaiement(rs.getString("moyen_paiement"));

        Timestamp ts = rs.getTimestamp("date_paiement");
        if (ts != null) {
          h.setDatePaiement(ts.toLocalDateTime());
        }

        h.setDescriptionAccident(rs.getString("description_accident"));
        helps.add(h);
      }
    }

    return helps;
  }

  public void updateDonationStatus(String donationId, String newStatus) throws SQLException {
    String sql = "UPDATE donation SET status = ? WHERE id = ?";

    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, newStatus);
      stmt.setString(2, donationId);

      stmt.executeUpdate();
    }
  }
}
