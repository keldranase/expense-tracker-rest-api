package com.keldranase.expencetrackingapi.filters;

import com.keldranase.expencetrackingapi.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String[] authHeaders = authHeader.split("Bearer");
            if (authHeaders.length > 1 && authHeaders[1] != null) {
                String token = authHeaders[1];
                try {
                    Claims claims = Jwts.parser().setSigningKey(Constants.API_SECRET_KEY)
                            .parseClaimsJws(token).getBody();
                    // after this, userId is gonna be attached to request object
                    // so we can access userId through our code
                    request.setAttribute("userId", Integer.parseInt(claims.get("userId").toString()));
                    request.setAttribute("privilegeLevel", claims.get("privilegeLevel"));
                } catch(Exception e) {
                    response.sendError(HttpStatus.FORBIDDEN.value(), "Invalid/expired token");
                    return;
                }
            } else {
                response.sendError(HttpStatus.FORBIDDEN.value(), "Auth token must be bearer");
                return;
            }
        } else {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Please provide auth token");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
