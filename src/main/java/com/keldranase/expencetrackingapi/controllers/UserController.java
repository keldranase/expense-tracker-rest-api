package com.keldranase.expencetrackingapi.controllers;

import com.keldranase.expencetrackingapi.entities.User;
import com.keldranase.expencetrackingapi.filters.AuthFilter;
import com.keldranase.expencetrackingapi.services.IUserService;
import com.keldranase.expencetrackingapi.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides endpoints for interactions with users data
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Register user
     * @param userData firstName, lastName, email and password of user in form of Json
     * @return JWT user token, if successful
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userData) {

        String firstName = (String) userData.get("firstName");
        String lastName = (String) userData.get("lastName");
        String email = (String) userData.get("email");
        String password = (String) userData.get("password");

        User user = userService.registerUser(firstName, lastName, email, password);
        String token = JWTUtils.getUserJWTToken(user);
        Map<String, String> mapToken = new HashMap<>();
        mapToken.put("token", token);

        return new ResponseEntity<>(mapToken, HttpStatus.CREATED);
    }

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
        String token = JWTUtils.getUserJWTToken(user);
        Map<String, String> mapToken = new HashMap<>();
        mapToken.put("token", token);

        return new ResponseEntity<>(mapToken, HttpStatus.OK);
    }

    public void Test() {
       AuthFilter at;

    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> updateUser(HttpServletRequest request, @RequestBody Map<String, Object> newUser) {

        int userId = (Integer) request.getAttribute("userId");
        String firstName = (String) newUser.get("firstName");
        String lastName = (String) newUser.get("lastName");
        String email = (String) newUser.get("email");
        String password = (String) newUser.get("password");
        User.PrivilegeLevel privilegeLevel = User.PrivilegeLevel.USER_FREE;

        User updatedUser = userService.updateUser(userId, firstName, lastName, email, password, privilegeLevel);
        String token = JWTUtils.getUserJWTToken(updatedUser);
        Map<String, String> mapToken = new HashMap<>();
        mapToken.put("token", token);

        return new ResponseEntity<>(mapToken, HttpStatus.CREATED);
    }
}
