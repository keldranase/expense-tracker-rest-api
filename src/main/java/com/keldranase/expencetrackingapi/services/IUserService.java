package com.keldranase.expencetrackingapi.services;

import com.keldranase.expencetrackingapi.entities.User;
import com.keldranase.expencetrackingapi.exceptions.EtAuthException;

public interface IUserService {

    User validateUser(String email, String password) throws EtAuthException;

    User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException;

    User updateUser(Integer userId, String firstName, String lastName, String email, String password, User.PrivilegeLevel privilegeLevel);
}
