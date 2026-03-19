package com.github.halab4dev;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {

    private final DomainServiceRestClient restClient;
    private final DomainServiceGrpcClient grpcClient;

    public GatewayController(DomainServiceRestClient restClient, DomainServiceGrpcClient grpcClient) {
        this.restClient = restClient;
        this.grpcClient = grpcClient;
    }

    @GetMapping("/gateway/rest")
    public DataPayload viaRest(@RequestParam("id") String id,
                               @RequestParam("sizeBytes") int sizeBytes) {
        return restClient.call(id, sizeBytes);
    }

    @GetMapping("/gateway/grpc")
    public DataPayload viaGrpc(@RequestParam("id") String id,
                               @RequestParam("sizeBytes") int sizeBytes) {
        return grpcClient.call(id, sizeBytes);
    }
}

