package com.keldranase.expencetrackingapi.repositories;

import com.keldranase.expencetrackingapi.entities.User;
import com.keldranase.expencetrackingapi.exceptions.EtAuthException;
import jdk.jshell.spi.ExecutionControl;

public interface IUserRepository {

    Integer create(String firstName, String lastName, String email, String password) throws EtAuthException;

    User findByEmailAndPassword(String email, String password) throws EtAuthException;

    Integer getCountByEmail(String email);

    boolean isPresent(String email);

    User findById(Integer userID);

    User updateUser(Integer userId, String firstName, String lastName, String email, String password, User.PrivilegeLevel privilegeLevel) throws ExecutionControl.NotImplementedException;
}
