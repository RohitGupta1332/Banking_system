package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AccountDao;
import com.example.demo.model.Account;

@Service
public class AccountService {

	@Autowired
	AccountDao accountDao;
	public boolean isExists(String email) {
		return accountDao.existsByEmail(email);
	}
	public void createAccount(String email, String name, int amount, String pin) {
		Account account = new Account();
		account.setEmail(email);
		account.setFull_name(name);
		account.setBalance(amount);
		account.setSecurityPin(pin);
		account.setAccount_number(generateAccountNumber());
		accountDao.save(account);
	}
	
	public long generateAccountNumber() {
		long number = accountDao.getAccountNumber();
		return number+1;
	}
	public int debit(String email, String pin, int amount) {
		if(accountDao.existsByEmailAndSecurityPin(email, pin)) {
			int balance = accountDao.getBalanceByEmail(email);
			if(balance >= amount) {
				accountDao.updateAccountBalanceByEmail(email, balance-amount);
				return balance-amount;
			}
			return -1;
		}
		return -2;
	}
	public int credit(String email, String pin, int amount) {
		if(accountDao.existsByEmailAndSecurityPin(email, pin)) {
			int balance = accountDao.getBalanceByEmail(email);
			accountDao.updateAccountBalanceByEmail(email, balance+amount);			
			return balance+amount;
		}
		return -1;
	}
	public int transfer(String email, long account_number, String pin, int amount) {
		if(accountDao.existsByEmailAndSecurityPin(email, pin)) {
			int balance = accountDao.getBalanceByEmail(email);
			if(balance >= amount) {
				int balance2 = accountDao.getBalanceByAccountNumber(account_number);
				accountDao.updateAccountBalanceByAccountNumber(account_number, balance2+amount);
				accountDao.updateAccountBalanceByEmail(email, balance-amount);
				return balance-amount;
			}
			else {
				return -1;
			}
		}
		return -2;
	}
	public int getAmountByEmail(String email) {
		return accountDao.getBalanceByEmail(email);
	}
	public long getAccountNumberByEmail(String email) {
		Account account = accountDao.getAccountNumberByEmail(email);
		return account.getAccountNumber();
	}
	
}
