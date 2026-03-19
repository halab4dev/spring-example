package com.github.halab4dev;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DomainDataController {

    @GetMapping("/domain-service/data")
    public DataPayload getData(@RequestParam("id") String id,
                               @RequestParam("sizeBytes") int sizeBytes) {
        if (sizeBytes < 0) {
            sizeBytes = 0;
        }
        String payload = DataPayloadGenerator.generatePayload(sizeBytes);
        return new DataPayload(id, payload);
    }
}

