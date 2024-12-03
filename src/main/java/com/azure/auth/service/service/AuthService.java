package com.azure.auth.service.service;

import com.azure.auth.service.dto.TokenResponse;
import com.azure.auth.service.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class AuthService {

    @Autowired
    private RestClient restClient;

    @Value("${microsoft.grant_type}")
    private String grantType;

    @Value("${microsoft.client_id}")
    private String clientId;

    @Value("${microsoft.client_secret}")
    private String clientSecret;

    @Value("${microsoft.scope}")
    private String scope;

    @Value("${microsoft.url}")
    private String url;

    public TokenResponse getToken() {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", grantType);
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("scope", scope);

        ResponseEntity<AuthResponse> response = restClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .toEntity(AuthResponse.class);

        AuthResponse authResponse = response.getBody();

        if (authResponse != null) {
            TokenResponse apiResponse = new TokenResponse();
            apiResponse.setToken(authResponse.getAccess_token());
            return apiResponse;
        } else {
            throw new RuntimeException("Failed to get token: " + response.getStatusCode());
        }

    }
}
