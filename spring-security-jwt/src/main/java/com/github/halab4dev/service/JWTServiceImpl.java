package com.github.halab4dev.service;

import com.github.halab4dev.constant.Privilege;
import com.github.halab4dev.constant.Role;
import com.github.halab4dev.constant.Time;
import com.github.halab4dev.domain.User;
import com.github.halab4dev.exception.AccessTokenExpiredException;
import com.github.halab4dev.exception.InvalidAccessTokenException;
import com.github.halab4dev.exception.UnauthorizedException;
import com.github.halab4dev.repository.RoleRepository;
import com.github.halab4dev.security.JwtConfiguration;
import com.github.halab4dev.security.JwtData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 *
 * @author halab
 */
@Service
@AllArgsConstructor
public class JWTServiceImpl implements JWTService {

    private static final String USER_ID = "user_id";
    private static final String ROLES = "roles";

    private final JwtConfiguration jwtConfiguration;
    private final RoleRepository roleRepository;

    @Override
    public String generateJWT(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put(USER_ID, user.getId());
        map.put(ROLES, user.getRoles());

        return Jwts.builder()
                .setClaims(map)
                .signWith(SignatureAlgorithm.HS256,
                        jwtConfiguration.getSecretKey().getBytes())
                .setExpiration(generateExpiryDate())
                .setIssuedAt(new Date())
                .compact();
    }

    private Date generateExpiryDate() {
        Date date = new Date();
        return new Date(date.getTime() + jwtConfiguration.getExpiredTimeInMinute() * Time.A_MINUTE);
    }

    @Override
    public Authentication getAuthentication(String accessToken) {
        JwtData jwtData = parseToken(accessToken);
        List<String> rolesNames = jwtData.getRoles();

        List<Role> roles = roleRepository.find(rolesNames);

        List<SimpleGrantedAuthority>privileges = roles.stream()
                .map(Role::getPrivileges)
                .flatMap(Collection::stream)
                .map(privilege -> new SimpleGrantedAuthority(privilege.toString()))
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(jwtData, "", privileges);
    }

    @SuppressWarnings("unchecked")
    private JwtData parseToken(String accessToken) {
        JwtData JWTData = new JwtData();
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtConfiguration.getSecretKey().getBytes())
                    .parseClaimsJws(accessToken).getBody();
            Integer userId = claims.get(USER_ID, Integer.class);
            List<String> roles = (List<String>) claims.get(ROLES, List.class);
            JWTData.setUserId(userId);
            JWTData.setRoles(roles);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new UnauthorizedException();
        } catch (ExpiredJwtException ex) {
            throw new AccessTokenExpiredException();
        } catch (Exception ex) {
            throw new InvalidAccessTokenException();
        }
        return JWTData;
    }
}
