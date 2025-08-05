package com.example.demo.endpoint.rest.controller.health.tsinjo;

import com.example.demo.entity.Donation;
import com.example.demo.entity.Help;
import com.example.demo.service.TsinjoService;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TsinjoController {

  private final TsinjoService service;

  @GetMapping("/donations")
  public List<Donation> getDonations() throws SQLException {
    return service.getDonations();
  }

  @GetMapping("/helps")
  public List<Help> getHelps() throws SQLException {
    return service.getHelps();
  }

  @PostMapping("/donate")
  public void createDonation(@RequestBody Donation donation) throws SQLException {
    service.createDonation(donation);
  }
}
