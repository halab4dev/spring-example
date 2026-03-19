package com.github.halab4dev;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class DomainServiceRestClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public DomainServiceRestClient(@Value("${domain-service.rest-base-url}") String baseUrl) {
        this.restTemplate = new RestTemplate();
        this.baseUrl = baseUrl;
    }

    public DataPayload call(String id, int sizeBytes) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/domain-service/data")
                .queryParam("id", id)
                .queryParam("sizeBytes", sizeBytes)
                .toUriString();
        return restTemplate.getForObject(url, DataPayload.class);
    }
}

