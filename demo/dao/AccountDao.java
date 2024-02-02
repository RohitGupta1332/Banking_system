package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Account;

public interface AccountDao extends JpaRepository<Account, Long> {

    boolean existsByEmail(String email);

    @Query(value = "SELECT account_number FROM account ORDER BY account_number DESC LIMIT 1", nativeQuery = true)
    long getAccountNumber();

    boolean existsByEmailAndSecurityPin(String email, String securityPin);

    Account findByEmail(String email);

    @Query("SELECT a.balance FROM Account a WHERE a.email = ?1")
    int getBalanceByEmail(String email);

    // Query to update balance by email
    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.balance = ?2 WHERE a.email = ?1")
    void updateAccountBalanceByEmail(String email, int newBalance);

    @Query("SELECT a.balance FROM Account a WHERE a.accountNumber = ?1")
	int getBalanceByAccountNumber(long account_number);

	@Transactional
    @Modifying
    @Query("UPDATE Account a SET a.balance = ?2 WHERE a.accountNumber = ?1")
	void updateAccountBalanceByAccountNumber(long account_number, int i);

	Account getAccountNumberByEmail(String email);

}
