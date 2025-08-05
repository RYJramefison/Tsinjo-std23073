package com.example.demo.client;

import com.example.demo.dto.PaymentResponse;
import com.example.demo.entity.Donation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class VolaClient {

  private final String apiKey = System.getenv("VOLA_API_KEY");
  private final String baseUrl = System.getenv("VOLA_BASE_URL");

  private final RestTemplate restTemplate = new RestTemplate();

  public void submitPayment(Donation donation) {
    String url =
        String.format(
            "%s/payment?apiKey=%s&payerEmail=%s&pspType=ORANGE_MONEY&pspPaymentId=%s",
            baseUrl, apiKey, donation.getDonorEmail(), donation.getPspPaymentId());
    restTemplate.postForEntity(url, null, String.class);
  }

  public String getPaymentStatus(Donation donation) {
    String url =
        String.format(
            "%s/payment?apiKey=%s&payerEmail=%s&pspType=ORANGE_MONEY&pspPaymentId=%s",
            baseUrl, apiKey, donation.getDonorEmail(), donation.getPspPaymentId());
    PaymentResponse response = restTemplate.getForObject(url, PaymentResponse.class);
    return response.getVerificationStatus();
  }
}
