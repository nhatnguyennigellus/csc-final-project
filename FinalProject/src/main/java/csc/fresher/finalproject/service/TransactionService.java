package csc.fresher.finalproject.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hibernate.ObjectNotFoundException;

import csc.fresher.finalproject.dao.SavingAccountDAO;
import csc.fresher.finalproject.dao.TransactionDAO;
import csc.fresher.finalproject.domain.SavingAccount;
import csc.fresher.finalproject.domain.Transaction;
import csc.fresher.finalproject.mycookies.TransactionConstants;

@Service("transactionService")
public class TransactionService {
	@Autowired
	private TransactionDAO transactionDAO;

	public TransactionService() {
	}

	public List<Transaction> getTransactionList() {
		return transactionDAO.getTransactions();
	}

	public Transaction getTransactionById(int id) {
		return transactionDAO.getTransactionById(id);
	}

	public boolean performTransaction(Transaction transaction) {
		return transactionDAO.performTransaction(transaction);
	}

	public boolean getInterestAlready(SavingAccount account) {
		return transactionDAO.getInterestAlready(account);
	}

	public boolean pendingAvail(String accNumber) {
		return transactionDAO.pendingTransAvail(accNumber);
	}

	public boolean approveTransaction(Transaction transaction) {
		return transactionDAO.approveTransaction(transaction);
	}

	public boolean rejectTransaction(Transaction trans) {
		return transactionDAO.rejectTransaction(trans);
	}

	public List<Date> getInterestWithdraw(SavingAccount account) {
		return transactionDAO.getInterestWithdraw(account);
	}

	public List<Date> getWithdrawAll(SavingAccount account) {
		return transactionDAO.getWithdrawAll(account);
	}

	public List<Transaction> getTransactionByState(String state) {
		return transactionDAO.getTransactionsByState(state);
	}

	/**
	 * Call DAO to search for transactions
	 * 
	 * @param state
	 * @param type
	 * @param accountNumber
	 * @return transactions that satisfy the conditions
	 * @author vinh-tp
	 */
	public List<Transaction> searchTransaction(String state, String type,
			String accountNumber) {
		Transaction transaction = new Transaction();
		String newState = "";
		String newType = "";
		
		
		if (state.equalsIgnoreCase("all")) {
			newState = "";
		}
		else {
			newState = state;
		}
		if (type.equalsIgnoreCase("all")) {
			newType = "";
		}
		else {
			newType = type;
		}
		// SavingAccount account =
		// accountDao.getAccountByAccNumber(accountNumber);
		SavingAccount account = new SavingAccount();
		account.setAccountNumber(accountNumber);
		transaction.setState(newState);
		transaction.setType(newType);
		transaction.setSavingAccount(account);
		return transactionDAO.searchTransaction(transaction);
	}
}
