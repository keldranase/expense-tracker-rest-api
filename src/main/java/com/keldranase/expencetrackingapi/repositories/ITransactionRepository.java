package com.keldranase.expencetrackingapi.repositories;

import com.keldranase.expencetrackingapi.entities.Transaction;
import com.keldranase.expencetrackingapi.exceptions.EtBadRequestException;
import com.keldranase.expencetrackingapi.exceptions.EtResourceNotFoundException;

import java.util.List;

public interface ITransactionRepository {
    List<Transaction> findAll(Integer userId, Integer categoryId);

    Transaction findById(Integer userId, Integer categoryId, Integer transactionid)
        throws EtResourceNotFoundException;

    Integer create(Integer userId, Integer categoryId, Double amount, String note, Long date)
        throws EtBadRequestException;

    void update(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction)
        throws EtBadRequestException;

    void remove(Integer userId, Integer categoryId, Integer transactionId)
        throws EtResourceNotFoundException;
}
