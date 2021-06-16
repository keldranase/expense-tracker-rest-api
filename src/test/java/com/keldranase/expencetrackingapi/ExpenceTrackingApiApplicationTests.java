package com.keldranase.expencetrackingapi;

import com.keldranase.expencetrackingapi.entities.User;
import com.keldranase.expencetrackingapi.exceptions.EtAuthException;
import com.keldranase.expencetrackingapi.repositories.IUserRepository;
import com.keldranase.expencetrackingapi.services.IUserService;
import com.keldranase.expencetrackingapi.services.SimpleUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExpenceTrackingApiApplicationTests {

	IUserRepository mockUserRepo = new IUserRepository() {
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

	IUserService userService = new SimpleUserService(mockUserRepo);

	@Test
	public void registerUserTest() {

		Assertions.assertThrows(EtAuthException.class, () -> userService.registerUser("Alex", "Klepov", "invalid", "1aab"));
		Assertions.assertThrows(EtAuthException.class, () -> userService.registerUser("Alex", "Klepov", "hello@email.com", "1aab"));
		Assertions.assertDoesNotThrow(() -> userService.registerUser("Alex", "Klepov", "hello@email.com", "1Aab"));
	}



}
