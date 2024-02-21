package com.employees.crud.employees_crud.utils;

import com.employees.crud.employees_crud.domain.Role;
import com.employees.crud.employees_crud.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.InvalidObjectException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

@Component
public class JwtUtil implements Serializable {


    public static final SecretKey key = Jwts.SIG.HS256.key().build();
    @Serial
    private static final long serialVersionUID = -2550185165626007488L;


    public String generateToken(User user) throws IOException {
        try {

            final Set<Role> roles = user.getRoles();
            ArrayList<String> rolesString = new ArrayList<>();
            for (Role role : roles) {
                rolesString.add(role.getName());
            }
            return Jwts.builder()
                    .subject(user.getId().toString())
                    .issuer("employees_crud")
                    .claim("roles", String.join(",", rolesString))
                    .claim("username", user.getUsername())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                    .signWith(key)
                    .compact();
        } catch (Exception e) {
            throw new IOException("Couldn't generate token!!");
        }
    }

    public String getUsernameFromToken(String token) throws InvalidObjectException {
        try {
            final Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
            return (String) claims.get("username");
        } catch (Exception e) {
            throw new InvalidObjectException("Bad token!!");
        }

    }

    public Date getExpirationDateFromToken(String token) throws InvalidObjectException {
        try {
            final Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
            return claims.getExpiration();
        } catch (Exception e) {
            throw new InvalidObjectException("Bad token!!");
        }

    }

    public ArrayList<String> getRolesFromToken(String token) throws InvalidObjectException {
        try {
            final Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
            String[] arr = ((String) claims.get("roles")).split(",");


            return new ArrayList<>(Arrays.asList(arr));
        } catch (Exception e) {
            throw new InvalidObjectException("Bad token!!");
        }
    }

    public boolean validateToken(String token, String username) throws InvalidObjectException {
        try {
            final String un = getUsernameFromToken(token);
            final Date eT = getExpirationDateFromToken(token);
            return username.equals(un) && eT.after(new Date());
        } catch (Exception e) {
            throw new InvalidObjectException("Could not verify token!!");
        }
    }
}

