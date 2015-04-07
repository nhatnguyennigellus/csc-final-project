package csc.fresher.finalproject.service;

import java.util.List;

import csc.fresher.finalproject.dao.TransactionDAO;
import csc.fresher.finalproject.domain.Transaction;

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
}
