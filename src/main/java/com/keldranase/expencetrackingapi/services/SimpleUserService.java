package com.keldranase.expencetrackingapi.services;

import com.keldranase.expencetrackingapi.entities.User;
import com.keldranase.expencetrackingapi.exceptions.EtAuthException;
import com.keldranase.expencetrackingapi.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

/**
 * Additional layer of abstraction for better extensibility
 * Performs intermediate checks on data
 */
@Service
@Transactional // provides transactional behaviour
public class SimpleUserService implements IUserService {

    private IUserRepository userRepository;

    @Autowired
    public SimpleUserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User validateUser(String email, String password) throws EtAuthException {

        if (email != null) {
            email = email.toLowerCase();
        }
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException {

        validateEmail(email);
        validatePassword(password);
        validateName(firstName);
        validateName(lastName);

        if (userRepository.isPresent(email)) {
            throw new EtAuthException("Email already in user");
        }

        Integer userId = userRepository.create(firstName, lastName, email, password);
        return userRepository.findById(userId);
    }

    @Override
    public User updateUser(Integer userId, String firstName, String lastName, String email, String password, User.PrivilegeLevel privilegeLevel) {

        return userRepository.updateUser(userId, firstName, lastName, email, password, privilegeLevel);
    }

    private void validateName(String name) {
        if (name.length() < 1) {
            throw new EtAuthException("Name must contain at least 1 character");
        }
    }

    private void validateEmail(String email) {

        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Pattern betterPattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

        if (email == null || !betterPattern.matcher(email).matches()) {
            throw new EtAuthException("Invalid email format");
        }
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
