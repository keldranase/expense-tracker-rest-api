package com.keldranase.expencetrackingapi.utils;

import com.keldranase.expencetrackingapi.Constants;
import com.keldranase.expencetrackingapi.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JWTUtils {

    // generate JWT token, from user fields, that's gonna expire after TOKEN_VALIDITY time (2h default)
    public static String getUserJWTToken(User user) {

        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY_TIME))
                .claim("userId", user.getUserId())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("privilegeLevel", user.getPrivilegeLevel())
                .compact();
        var a = Jwts.header();

        return token;
    }

    public static int getUserIdFromToken(HttpServletRequest request) {
        return (Integer) request.getAttribute("userId");
    }
}
