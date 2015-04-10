package csc.fresher.finalproject.service;

import java.util.List;

import org.hibernate.ObjectNotFoundException;

import csc.fresher.finalproject.dao.SavingAccountDAO;
import csc.fresher.finalproject.dao.TransactionDAO;
import csc.fresher.finalproject.domain.SavingAccount;
import csc.fresher.finalproject.domain.Transaction;
import csc.fresher.finalproject.mycookies.TransactionConstants;

public class TransactionService {
	private TransactionDAO transactionDAO;
	
	public TransactionService() {
		transactionDAO = new TransactionDAO();
	}
	
	public List<Transaction> getTransactionList() {
		return transactionDAO.getTransactions();
	}
	
	public Transaction getTransactionById(int id) {
		return transactionDAO.getTransactionById(id);
	}
	
	
	public List<Transaction> getTransactionByState(String state) {
		return transactionDAO.getTransactionsByState(state);
	}
	
	/**
	 * Call DAO to search for transactions
	 * @param state
	 * @param type
	 * @param accountNumber
	 * @return transactions that satisfy the conditions
	 * @author vinh-tp
	 */
	public List<Transaction> searchTransaction(String state,String type, String accountNumber)  {
		Transaction transaction = new Transaction();
		SavingAccountDAO accountDao = new SavingAccountDAO();
		if (state.equalsIgnoreCase(TransactionConstants.STATE_APPROVED) 
				|| state.equalsIgnoreCase(TransactionConstants.STATE_PENDING)
				|| state.equalsIgnoreCase(TransactionConstants.STATE_REJECTED)) {
			state = Character.toUpperCase(state.charAt(0)) + state.substring(1);
		}
		if (state.equalsIgnoreCase("all")) {
			type="";
		}
		if (type.equalsIgnoreCase(TransactionConstants.TYPE_DEPOSIT)
				|| type.equalsIgnoreCase(TransactionConstants.TYPE_WITHDRAW_BALANCE)
				|| type.equalsIgnoreCase(TransactionConstants.TYPE_WITHDRAW_INTEREST)) {
			type = Character.toUpperCase(state.charAt(0)) + state.substring(1);
		}
		if (type.equalsIgnoreCase("all")) {
			type = "";
		}
		SavingAccount account = accountDao.getAccountByAccNumber(accountNumber);
		transaction.setState(state);
		transaction.setType(type);
		transaction.setSavingAccount(account);
		return transactionDAO.searchTransaction(transaction);
	}
	
	public Transaction getTransactionById(String id) {
		int tid = Integer.parseInt(id);
		return transactionDAO.getTransactionById(tid);
	}
}
