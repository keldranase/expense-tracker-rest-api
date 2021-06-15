package com.keldranase.expencetrackingapi.services;

import com.keldranase.expencetrackingapi.entities.User;
import com.keldranase.expencetrackingapi.exceptions.EtAuthException;
import com.keldranase.expencetrackingapi.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.message.AuthException;
import java.util.regex.Pattern;

/**
 * Additional layer of abstraction for better extensibility
 * Performs intermediate checks on data
 */
@Service
@Transactional // provides transactional behaviour
public class SimpleUserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public User validateUser(String email, String password) throws EtAuthException {
        if (email != null) {
            email = email.toLowerCase();
        }
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException {

        if (!isValidEmail(email)) {
            throw new EtAuthException("Invalid email format");
        }
        if (userRepository.isPresent(email)) {
            throw new EtAuthException("Email already in user");
        }
        validatePassword(password);

        Integer userId = userRepository.create(firstName, lastName, email, password);
        return userRepository.findById(userId);
    }

    @Override
    public void updateUser(Integer userId, User userUpdate) {
        userRepository.updateUser(userId, userUpdate);
    }

    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        return pattern.matcher(email).matches();
    }

    private void validatePassword(String password) {
         if (password.length() < 4) {
             throw new EtAuthException("Password must contain more then 4 characters");
         }
         boolean containsNumber = false;
         boolean containsLowerCaseChar = false;
         boolean containsUpperCaseChar = false;
         for (char ch : password.toCharArray()) {
             if (Character.isDigit(ch)) {
                 containsNumber = true;
             }
             if (Character.isLowerCase(ch)) {
                 containsLowerCaseChar = true;
             }
             if (Character.isUpperCase(ch)) {
                 containsUpperCaseChar = true;
             }
         }
         if (!containsNumber || !containsLowerCaseChar || !containsUpperCaseChar) {
             throw new EtAuthException("Password must contain at least 1 number, 1 lowercase and 1 uppercase letter");
         }
    }
}
