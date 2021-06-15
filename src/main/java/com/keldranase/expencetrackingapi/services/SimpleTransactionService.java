package com.keldranase.expencetrackingapi.services;

import com.keldranase.expencetrackingapi.entities.Transaction;
import com.keldranase.expencetrackingapi.exceptions.EtBadRequestException;
import com.keldranase.expencetrackingapi.exceptions.EtResourceNotFoundException;
import com.keldranase.expencetrackingapi.repositories.PostgresTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Intermediate level, for better extensibility
 */
@Service
@Transactional
public class SimpleTransactionService implements ITransactionService {

    @Autowired
    PostgresTransactionRepository repository;

    @Override
    public List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId) {
        return repository.findAll(userId, categoryId);
    }

    @Override
    public Transaction fetchTransaction(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException {
        return repository.findById(userId, categoryId, transactionId);
    }

    @Override
    public Transaction addTransaction(Integer userId, Integer categoryId, Double amount, String note, Long date) throws EtBadRequestException {
        int transactionId = repository.create(userId, categoryId, amount, note, date);
        return repository.findById(userId, categoryId, transactionId);
    }

    @Override
    public void updateTransaction(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestException {
        repository.update(userId, categoryId, transactionId, transaction);
    }

    @Override
    public void removeTransaction(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException {
        repository.remove(userId, categoryId, transactionId);
    }

    @Override
    public Double getTotal(Integer userId, Integer categoryId) {

        return getTransactionsSum(fetchAllTransactions(userId, categoryId));
    }

    @Override
    public Double getMean(Integer userId, Integer categoryId) {

        List<Transaction> transactions = fetchAllTransactions(userId, categoryId);
        Double sum = getTransactionsSum(transactions);

        return sum / transactions.size();
    }

    private Double getTransactionsSum(List<Transaction> transactions) {
        Double sum = 0.0;

        for (Transaction transaction : transactions) {
            sum += transaction.getAmount();
        }

        return sum;
    }
}
