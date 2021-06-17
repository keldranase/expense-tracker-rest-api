package com.keldranase.expencetrackingapi.services;

import com.keldranase.expencetrackingapi.entities.User;
import com.keldranase.expencetrackingapi.exceptions.EtAuthException;
import com.keldranase.expencetrackingapi.repositories.IUserRepository;
import org.junit.jupiter.api.*;

//@SpringBootTest
public class UserServiceTest {


    // todo: proper mocking with mockito or something
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
        Assertions.assertThrows(EtAuthException.class, () ->
                userService.registerUser("a", "b", "@.com", "1Abc"));
        Assertions.assertThrows(EtAuthException.class, () ->
                userService.registerUser("a", "b", ".@.com", "1Abc"));
        Assertions.assertThrows(EtAuthException.class, () ->
                userService.registerUser("a", "b", "inv@com@email", "1Abc"));
    }

    @Test
    public void shouldNotRegisterIfPasswordInvalid() {

        // password should contain at least 1 digit and 1 upper and 1 lowercase characters
        Assertions.assertThrows(EtAuthException.class, () ->
                userService.registerUser("a", "b", "valid@gmail.com", "123"));
        Assertions.assertThrows(EtAuthException.class, () ->
                userService.registerUser("a", "b", "valid@gmail.com", "aAbAsd"));
        Assertions.assertThrows(EtAuthException.class, () ->
                userService.registerUser("a", "b", "valid@gmail.com", "12334343"));
        Assertions.assertThrows(EtAuthException.class, () ->
                userService.registerUser("a", "b", "valid@gmail.com", "123aaa"));
    }
}
