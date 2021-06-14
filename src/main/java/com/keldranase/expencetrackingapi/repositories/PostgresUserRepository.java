package com.keldranase.expencetrackingapi.repositories;

import com.keldranase.expencetrackingapi.entities.User;
import com.keldranase.expencetrackingapi.exceptions.EtAuthException;
import com.keldranase.expencetrackingapi.exceptions.EtBadRequestException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * PostgreSQL data access level for User
 */
@Repository
public class PostgresUserRepository implements IUserRepository {

    private static final String SQL_CREATE = "INSERT INTO ET_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES(NEXTVAL('ET_USERS_SEQ'), ?, ?, ?, ?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM ET_USERS WHERE EMAIL = ?";
    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD FROM ET_USERS WHERE USER_ID = ?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD FROM ET_USERS WHERE EMAIL = ?";
    private static final String SQL_UPDATE_USER = "UPDATE ET_USERS SET FIRST_NAME = ?, LAST_NAME = ? WHERE USER_ID = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String firstName, String lastName, String email, String password) throws EtAuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, email);
                statement.setString(4, hashedPassword);
                return  statement;
                }, keyHolder);

            return (Integer) keyHolder.getKeys().get("USER_ID");
        } catch (Exception e) {
            throw new EtAuthException("Invalid details. Failed to create account " + e.getMessage());
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws EtAuthException {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
            if (!BCrypt.checkpw(password, user.getPassword())) {
                throw new EtAuthException("Invalid email/password");
            }
            return user;
        } catch (Exception e) {
            throw new EtAuthException("Invalid email/password " + e.getMessage());
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    @Override
    public boolean isPresent(String email) {
        return getCountByEmail(email) > 0;
    }

    @Override
    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId}, userRowMapper);
    }

    // RowMapper is a functional interface, that defines a rule on mapping rows
    // In this example we are getting a user object from table record
    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        return new User(rs.getInt("USER_ID"),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("EMAIL"),
                rs.getString("PASSWORD"));
    });

    @Override
    public void updateUser(Integer userId, User updateUser) {
        try {
            jdbcTemplate.update(SQL_UPDATE_USER, updateUser.getFirstName(), updateUser.getLastName(), userId);
        } catch (DataAccessException e) {
            throw new EtBadRequestException("Cant update user" + e.getMessage());
        }
    }

}
