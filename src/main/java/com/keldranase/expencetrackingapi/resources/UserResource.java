package com.keldranase.expencetrackingapi.resources;

import com.keldranase.expencetrackingapi.Constants;
import com.keldranase.expencetrackingapi.entities.User;
import com.keldranase.expencetrackingapi.services.IUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides endpoints for interactions with users data
 */
@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    IUserService userService;

    /**
     * User login
     * @param userMap email and password in form of Json
     * @return JWT user token, if successful
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap) {

        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");

        User user = userService.validateUser(email, password);

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    /**
     * Register user
     * @param userMap firstName, lastName, email and password of user in form of Json
     * @return JWT user token, if successful
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap) {

        String firstName = (String) userMap.get("firstName");
        String lastName = (String) userMap.get("lastName");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");

        User user = userService.registerUser(firstName, lastName, email, password);

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Boolean>> updateUser(HttpServletRequest request, @RequestBody Map<String, Object> newUser) {

        int userId = (Integer) request.getAttribute("userId");
        String firstName = (String) newUser.get("firstName");
        String lastName = (String) newUser.get("lastName");
        String email = (String) newUser.get("email");
        String password = (String) newUser.get("password");

        User updateUser = new User(userId, firstName, lastName, email, password);
        userService.updateUser(userId, updateUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // generate JWT token, from userId and names, that's gonna expire after TOKEN_VALIDITY time (2h default)
    private Map<String, String> generateJWTToken(User user) {

        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("userId", user.getUserId())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .compact();

        Map<String, String> mapToken = new HashMap<>();
        mapToken.put("token", token);

        return mapToken;
    }
}
