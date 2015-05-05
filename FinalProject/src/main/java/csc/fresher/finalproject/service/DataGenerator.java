package csc.fresher.finalproject.service;

import org.springframework.beans.factory.annotation.Autowired;

import csc.fresher.finalproject.domain.SavingAccount;

public class DataGenerator {
	@Autowired
	private BankingService bankingService;
	
	public DataGenerator() {}
	
	public int generateSavingAccounts() {
		SavingAccount account = new SavingAccount();
		for (Integer i = 0 ; i < 100; i++) {
			String accountNumber = "150505";
			account.setAccountNumber("150505" );
		}
		return 0;
	}
}
