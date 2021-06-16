package com.keldranase.expencetrackingapi.services;

import com.keldranase.expencetrackingapi.entities.User;
import com.keldranase.expencetrackingapi.exceptions.EtAuthException;
import com.keldranase.expencetrackingapi.repositories.IUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.annotation.Testable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    IUserRepository userRepository = new IUserRepository() {
        @Override
        public Integer create(String firstName, String lastName, String email, String password) throws EtAuthException {
            return null;
        }

        @Override
        public User findByEmailAndPassword(String email, String password) throws EtAuthException {
            return null;
        }

        @Override
        public Integer getCountByEmail(String email) {
            return null;
        }

        @Override
        public boolean isPresent(String email) {
            return false;
        }

        @Override
        public User findById(Integer userID) {
            return null;
        }

        @Override
        public User updateUser(Integer userId, String firstName, String lastName, String email, String password, User.PrivilegeLevel privilegeLevel) {
            return null;
        }
    };

    IUserService userService = new SimpleUserService(userRepository);

    @Test
    public void shouldRegisterIfEmailAndPasswordValid() {

        Assertions.assertDoesNotThrow(() ->
                userService.registerUser("a", "b", "valid@gmail.com", "1Abc"));
    }

    @Test
    public void shouldNotRegisterIfEmailInvalid() {

        Assertions.assertThrows(EtAuthException.class, () ->
                userService.registerUser("a", "b", "invalid", "1Abc"));
    }

    @Test
    public void shouldNotRegisterIfPasswordInvalid() {

        Assertions.assertThrows(EtAuthException.class, () ->
                userService.registerUser("a", "b", "valid@gmail.com", "123"));
    }

    //@Test
    public void registerUserTest() {

        // test if fails with invalid email
        // test if fails with invalid password
        // test if passes with valid email and password
    }
}
