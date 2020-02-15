package com.github.halab4dev.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/*
 *
 * @author halab
 */
@Getter
@Configuration
public class JwtConfiguration {

    @Value("${security.jwt.secretKey}")
    private String secretKey;

    @Value("${security.jwt.expiredTimeInMinute}")
    private int expiredTimeInMinute;
}
