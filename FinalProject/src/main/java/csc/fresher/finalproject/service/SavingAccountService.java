package csc.fresher.finalproject.service;

import java.util.List;

import csc.fresher.finalproject.dao.SavingAccountDAO;
import csc.fresher.finalproject.domain.SavingAccount;

public class SavingAccountService {
	private SavingAccountDAO savingAccountDAO;

	public SavingAccountService() {
		this.savingAccountDAO = new SavingAccountDAO();
	}

	public boolean existedAccountNumber(String accountNo) {
		return savingAccountDAO.getAccountByAccNumber(accountNo) != null;
	}

	public List<SavingAccount> getSavingAccounts() {
		return savingAccountDAO.getAccountList();
	}

	public List<SavingAccount> getSavingAccountByState(String state) {
		return savingAccountDAO.getAccountByState(state);
	}

	public List<SavingAccount> getSavingAccountByCustomer(String customerId) {
		return savingAccountDAO.getAccountByCustomer(customerId);
	}

	public boolean addSavingAccount(SavingAccount account) {
		return savingAccountDAO.addSavingAccount(account);
	}

	public boolean updateSavingAccount(SavingAccount account) {
		return savingAccountDAO.updateSavingAccount(account);
	}

	public SavingAccount findAccount(String accountNumber) {
		return savingAccountDAO.findAccount(accountNumber);
	}

	public SavingAccount getSavingAccountByNumber(String currentAccountNumber) {
		return savingAccountDAO.getAccountByAccNumber(currentAccountNumber);
	}
}
