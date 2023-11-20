package com.eteration.simplebanking;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		/*
		SpringApplication.run(DemoApplication.class, args);
		BankAccount bankAccount = new BankAccount("Jim", 12345);
		bankAccount.post(new DepositTransaction(1000));
		bankAccount.post(new WithdrawalTransaction(200));
		bankAccount.post(new PhoneBillPaymentTransaction("Vodafone", "5423345566", 96.50));

		double expectedBalance = 703.50;
		double actualBalance = account.getBalance();

		assertEquals(actualBalance, expectedBalance, 0.0001);
*/
	}

	private static void assertEquals(double actual, double expected, double delta) {
		if (Math.abs(actual - expected) > delta) {
			throw new AssertionError("Assertion failed: expected " + expected + ", but got " + actual);
		}
	}



}
