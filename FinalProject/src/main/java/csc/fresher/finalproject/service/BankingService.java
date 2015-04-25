package csc.fresher.finalproject.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import csc.fresher.finalproject.dao.CustomerDAO;
import csc.fresher.finalproject.dao.InterestRateDAO;
import csc.fresher.finalproject.dao.SavingAccountDAO;
import csc.fresher.finalproject.dao.TransactionDAO;
import csc.fresher.finalproject.dao.UserDAO;
import csc.fresher.finalproject.domain.Customer;
import csc.fresher.finalproject.domain.SavingAccount;
import csc.fresher.finalproject.domain.SavingInterestRate;
import csc.fresher.finalproject.domain.Transaction;
import csc.fresher.finalproject.domain.User;

@Service("bankingService")
public class BankingService {
	@Autowired
	private CustomerDAO customerDAO;
	@Autowired
	private InterestRateDAO rateDAO;
	@Autowired
	private TransactionDAO transactionDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private SavingAccountDAO savingAccountDAO;

	public BankingService() {
		// TODO Auto-generated constructor stub
	}

	// ***************
	// *************** User services
	// ***************

	public User checkUserAuthentication(String username, String password) {
		return userDAO.checkUser(username, password);
	}

	public User getUserByUsername(String username) {
		return userDAO.getUserByUsername(username);
	}

	public List<User> getUserByRole(String role) {
		return userDAO.getUserByRole(role);
	}

	public boolean checkUserActive(String username) {
		return userDAO.checkUserActive(username);
	}

	// ***************
	// *************** Customer services
	// ***************

	public List<Customer> getCustomerList() {
		return this.customerDAO.getCustomers();
	}

	public boolean addCustomer(Customer customer) {
		return this.customerDAO.addCustomer(customer);
	}

	public Customer getCustomerById(int id) {
		return this.customerDAO.getCustomerById(id);
	}

	public Customer findCustomerOfAccount(SavingAccount savingAccount) {
		return this.customerDAO.findCustomerOfAccount(savingAccount);
	}

	public boolean updateCustomer(Customer customer) {
		return this.customerDAO.updateCustomer(customer);
	}

	// ***************
	// *************** Saving Account services
	// ***************

	public boolean existedAccountNumber(String accountNo) {
		return savingAccountDAO.getAccountByAccNumber(accountNo) != null;
	}

	public List<SavingAccount> getSavingAccounts() {
		return savingAccountDAO.getAccountList();
	}

	// public List<SavingAccount> getSavingAccountByCustomer(String customerId)
	// {
	// return savingAccountDAO.getAccountByCustomer(customerId);
	// }

	public boolean addSavingAccount(SavingAccount account,
			HttpServletRequest request, HttpServletResponse response) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		Customer customer = this.getCustomerById(customerId);

		Integer interestRatePeriod = Integer.parseInt(request
				.getParameter("period"));
		SavingInterestRate interestRate = this
				.getInterestRateByPeriod(interestRatePeriod);

		account.setInterestRate(interestRate);

		account.setCustomer(customer);
		account.setState("new");

		Calendar cal = Calendar.getInstance();
		account.setStartDate(cal.getTime());

		if (interestRatePeriod != 0) {
			cal.add(Calendar.MONTH, (int) interestRatePeriod);
			account.setDueDate(cal.getTime());
		}

		account.setInterest(0);

		boolean repeatable = request.getParameter("repeatable") != null;
		account.setRepeatable(repeatable);
		return savingAccountDAO.addSavingAccount(account);
	}

	public double calculateInterest(SavingAccount account) {
		double interest = 0;
		SavingInterestRate interestRate = account.getInterestRate();
		if (interestRate.getPeriod() != 0) {
			int dateDiff = DateUtils.daysBetween(account.getStartDate()
					.getTime(), account.getDueDate().getTime());
			interest = account.getBalanceAmount()
					* interestRate.getInterestRate() / 365 * dateDiff;
		} else {
			interest = account.getBalanceAmount()
					* interestRate.getInterestRate() / 360 * 30;
		}

		return interest;
	}

	public boolean updateSavingAccount(SavingAccount account) {
		return savingAccountDAO.updateSavingAccount(account);
	}

	public SavingAccount getSavingAccountByAccNumber(String accNumber) {
		return savingAccountDAO.getAccountByAccNumber(accNumber);
	}

	public SavingAccount getSavingAccountById(int accId) {
		return savingAccountDAO.getAccountById(accId);
	}

	public String generateAccountNumber() {
		String accountNo = "";

		StringBuilder accNoBuilder = new StringBuilder();
		Calendar cal = Calendar.getInstance();
		DecimalFormat fmt2Digit = new DecimalFormat("00");

		accNoBuilder
				.append(String.valueOf(cal.get(Calendar.YEAR)).substring(2));
		accNoBuilder.append(fmt2Digit.format(cal.get(Calendar.MONTH) + 1));
		accNoBuilder.append(fmt2Digit.format(cal.get(Calendar.DAY_OF_MONTH)));

		String suffix = "";
		List<SavingAccount> listAcc = this.getSavingAccounts();
		if (this.getSavingAccountByAccNumber(accNoBuilder.toString() + "000000") == null) {
			suffix = "000000";

		} else {
			SavingAccount account = listAcc.get(listAcc.size() - 1);
			suffix = account.getAccountNumber().substring(6);
			int next = Integer.parseInt(suffix) + 1;
			DecimalFormat fmt6Digits = new DecimalFormat("000000");
			suffix = fmt6Digits.format(next);
		}

		accNoBuilder.append(suffix);
		accountNo = accNoBuilder.toString();
		return accountNo;
	}

	/**
	 * Call DAO to search Accounts
	 * 
	 * @param idCard
	 * @param accNumber
	 * @return
	 * @author vinh-tp
	 */
	public List<SavingAccount> searchSavingAccounts(HttpServletRequest request) {
		String idCardNo = request.getParameter("idCardValue");
		String accNumber = request.getParameter("accNumberValue");

		return savingAccountDAO.searchSavingAccounts(idCardNo, accNumber);
	}

	public boolean approve(HttpServletRequest request) {
		String accountNumber = request.getParameter("accountNumber");
		SavingAccount account = this.getSavingAccountByAccNumber(accountNumber);
		account.setState("active");

		return savingAccountDAO.approve(account);
	}

	// ***************
	// *************** Interest Rate services
	// ***************

	public List<SavingInterestRate> getInterestRateList() {
		return rateDAO.getInterestRateList();
	}

	public SavingInterestRate getInterestRateByPeriod(Integer interestRatePeriod) {
		return rateDAO.getInterestRateByPeriod(interestRatePeriod);
	}

	public SavingInterestRate getInterestRateById(int id) {
		return rateDAO.getInterestRateById(id);
	}

	public boolean updateRate(List<SavingInterestRate> rateList) {
		boolean result = true;

		while (result) {
			for (SavingInterestRate rate : rateList) {
				result = rateDAO.updateInterestRate(rate);

				if (!result) {
					return false;
				}
			}

			result = false;
		}

		return true;
	}

	public boolean addInterestRate(SavingInterestRate newInterestRate) {
		return rateDAO.addInterestRate(newInterestRate);
	}

	// ***************
	// *************** Transaction services
	// ***************

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
		SavingAccount account = transaction.getSavingAccount();
		if (transaction.getType().equals("Withdraw All")) {
			account.setBalanceAmount(0);
			account.setInterest(0);
		} else if (transaction.getType().equals("Withdraw Balance")) {
			// Reset new balance = old balance - transaction (withdraw)
			// amount
			account.setBalanceAmount(account.getBalanceAmount()
					- transaction.getAmount() + account.getInterest());
			double interest = 0;

			// Reset new interest
			interest = this.calculateInterest(account);/*
														 * account.getBalanceAmount
														 * ()
														 * account.getInterestRate
														 * ().getInterestRate()
														 * / 360 30;
														 */
			account.setInterest(interest);
		} else if (transaction.getType().equals("Withdraw Interest")) {

		} else if (transaction.getType().equals("Deposit")) {
			// Set new balance
			account.setBalanceAmount(account.getBalanceAmount()
					+ transaction.getAmount());

			// Set new interest
			// If period account, reset start date and due date
			if (account.getInterestRate().getPeriod() != 0) {
				Calendar cal = Calendar.getInstance();
				account.setStartDate(cal.getTime());

				cal.add(Calendar.MONTH, (int) account.getInterestRate()
						.getPeriod());
				account.setDueDate(cal.getTime());
			}
			account.setInterest(this.calculateInterest(account));
		}
		savingAccountDAO.updateSavingAccount(account);
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

	public double getBeforeDueTotal(SavingAccount account) {
		Date today = new Date();
		int dateDiff = DateUtils.daysBetween(account.getStartDate().getTime(),
				today.getTime());
		return account.getBalanceAmount()
				+ this.getInterestRateByPeriod(0).getInterestRate() / 365
				* dateDiff * account.getBalanceAmount();
	}

	public double getDueDateTotal(SavingAccount account) {
		return account.getBalanceAmount() + account.getInterest();
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
		} else {
			newState = state;
		}
		if (type.equalsIgnoreCase("all")) {
			newType = "";
		} else {
			newType = type;
		}
		
		SavingAccount account = new SavingAccount();
		account.setAccountNumber(accountNumber);
		transaction.setState(newState);
		transaction.setType(newType);
		transaction.setSavingAccount(account);
		return transactionDAO.searchTransaction(transaction);
	}

}
