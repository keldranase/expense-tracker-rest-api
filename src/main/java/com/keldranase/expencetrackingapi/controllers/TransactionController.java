package com.keldranase.expencetrackingapi.controllers;

import com.keldranase.expencetrackingapi.entities.Transaction;
import com.keldranase.expencetrackingapi.services.ITransactionService;
import com.keldranase.expencetrackingapi.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides endpoints for interactions with Transactions data
 */
@RestController
@RequestMapping("/api/categories/{categoryId}/transactions")
public class TransactionController {

    ITransactionService transactionService;

    @Autowired
    public TransactionController(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Add transaction for given category of given user
     * @param request JWT user token
     * @param categoryId id of category
     * @param map transaction data
     */
    @PostMapping("")
    public ResponseEntity<Transaction> addTransaction(HttpServletRequest request,
                                                      @PathVariable("categoryId") Integer categoryId,
                                                      @RequestBody Map<String, Object> map) {

        int userId = JWTUtils.getUserIdFromRequest(request);
        Double amount = Double.valueOf(map.get("amount").toString());
        String note = (String) map.get("note");
        Long date = (Long) map.get("transactionDate");

        Transaction transaction = transactionService.addTransaction(userId, categoryId, amount, note, date);

        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    /**
     * Change existing transaction (update existing with new)
     * @param request JWT for user
     * @param categoryId id of category of transaction
     * @param transactionId id of transaction, that has to be changed
     * @param transaction data for transaction-replacement
     */
    @PutMapping("/{transactionId}")
    public ResponseEntity<Map<String, Boolean>> updateTransaction(HttpServletRequest request,
                                                                  @PathVariable("categoryId") Integer categoryId,
                                                                  @PathVariable("transactionId") Integer transactionId,
                                                                  @RequestBody Transaction transaction) {

        int userId = JWTUtils.getUserIdFromRequest(request);
        transactionService.updateTransaction(userId, categoryId, transactionId, transaction);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * Get all transactions for particular category of given user
     * @param request JWT token of user
     * @param categoryId id of transaction category
     */
    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions(HttpServletRequest request,
                                                                @PathVariable("categoryId") Integer categoryId) {

        int userId = JWTUtils.getUserIdFromRequest(request);
        List<Transaction> transactions = transactionService.fetchAllTransactions(userId, categoryId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/mean")
    public ResponseEntity<Double> getMeanTransactionValue(HttpServletRequest request,
                                                                @PathVariable("categoryId") Integer categoryId) {

        int userId = JWTUtils.getUserIdFromRequest(request);
        Double meanTransactionValue = transactionService.getMean(userId, categoryId);

        return new ResponseEntity<>(meanTransactionValue, HttpStatus.OK);
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getTotalValueOfAllTransactions(HttpServletRequest request,
                                                          @PathVariable("categoryId") Integer categoryId) {

        int userId = JWTUtils.getUserIdFromRequest(request);
        Double totalValue = transactionService.getTotal(userId, categoryId);

        return new ResponseEntity<>(totalValue, HttpStatus.OK);
    }

    /**
     * Delete a single transaction for a given user
     * @param request JWT user token
     * @param categoryId id of category
     * @param transactionId id of transaction to be deleted
     */
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Map<String, Boolean>> deleteTransaction(HttpServletRequest request,
                                                                  @PathVariable("categoryId") Integer categoryId,
                                                                  @PathVariable("transactionId") Integer transactionId) {

        int userId = JWTUtils.getUserIdFromRequest(request);
        transactionService.removeTransaction(userId, categoryId, transactionId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
