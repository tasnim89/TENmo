package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.awt.*;

public class TransferService {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public TransferService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public User[] listUsers() {
        User[] users = null;
        try {
            ResponseEntity<User[]> response = restTemplate.exchange(baseUrl + "listAll", HttpMethod.GET, makeAuthEntity(), User[].class);
            users = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }

    public Transfer sendTransfer(Transfer transfer) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);

        Transfer startTransfer = null;
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "transfers", HttpMethod.POST, entity, Transfer.class);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                startTransfer = response.getBody();
            }
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return startTransfer;
    }

    public Transfer[] transferHistory() {
        Transfer[] transferList = null;
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "transferHistory", HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transferList = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferList;
    }

    public Transfer getTransferDetails(Long transferId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        HttpEntity<Transfer> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "transfer/" + transferId,  HttpMethod.GET,makeAuthEntity(), Transfer.class);
            return response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return null;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }


}
