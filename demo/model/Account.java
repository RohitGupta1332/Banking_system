package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Account {
	@Id
	private long accountNumber;
	private String full_name;
	private String email;
	private double balance;
	private String securityPin;
	public long getAccountNumber() {
		return accountNumber;
	}
	public void setAccount_number(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getSecurityPin() {
		return securityPin;
	}
	public void setSecurityPin(String security_pin) {
		this.securityPin = security_pin;
	}
	
}
