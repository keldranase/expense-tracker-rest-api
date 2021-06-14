package com.keldranase.expencetrackingapi.repositories;

import com.keldranase.expencetrackingapi.entities.User;
import com.keldranase.expencetrackingapi.exceptions.EtAuthException;

public interface IUserRepository {

    Integer create(String firstName, String lastName, String email, String password) throws EtAuthException;

    User findByEmailAndPassword(String email, String password) throws EtAuthException;

    Integer getCountByEmail(String email);

    boolean isPresent(String email);

    User findById(Integer userID);

    void updateUser(Integer userId, User updateUser);
}
