package csc.fresher.finalproject.service;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

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

/**
 * This class provides Service classes for banking business
 * 
 * @author Nhat Nguyen, Tai Tran, Vinh Truong
 *
 */
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

	/**
	 * Check if username and password are authenticated
	 * 
	 * @author Nhat Nguyen
	 * @param username
	 * @param password
	 * @return user if authenticated, otherwise return null
	 */
	public User checkUserAuthentication(String username, String password) {
		return userDAO.checkUser(username, password);
	}

	/**
	 * Get user by username
	 * 
	 * @author Nhat Nguyen
	 * @param username
	 * @return user
	 */
	public User getUserByUsername(String username) {
		return userDAO.getUserByUsername(username);
	}

	/**
	 * Get list of user by role
	 * 
	 * @author Nhat Nguyen
	 * @param role
	 * @return list of user
	 */
	public List<User> getUserByRole(String role) {
		return userDAO.getUserByRole(role);
	}

	/**
	 * Check if user is active
	 * 
	 * @author Nhat Nguyen
	 * @param username
	 * @return active or inactive
	 */
	public boolean checkUserActive(String username) {
		return userDAO.checkUserActive(username);
	}

	// ***************
	// *************** Customer services
	// ***************

	/**
	 * Get list of customer
	 * 
	 * @author Nhat Nguyen
	 * @return list of customer
	 */
	public List<Customer> getCustomerList() {
		return this.customerDAO.getCustomers();
	}

	/**
	 * Add new customer
	 * 
	 * @author Nhat Nguyen
	 * @param customer
	 * @return action result
	 */
	public boolean addCustomer(Customer customer) {
		return this.customerDAO.addCustomer(customer);
	}

	/**
	 * Get customer by ID
	 * 
	 * @author Nhat Nguyen
	 * @param id
	 * @return customer
	 */
	public Customer getCustomerById(int id) {
		return this.customerDAO.getCustomerById(id);
	}

	/**
	 * Find customer that owns the account
	 * 
	 * @author Nhat Nguyen
	 * @param savingAccount
	 * @return customer
	 */
	public Customer findCustomerOfAccount(SavingAccount savingAccount) {
		return this.customerDAO.findCustomerOfAccount(savingAccount);
	}

	/**
	 * Update customer information
	 * 
	 * @author Nhat Nguyen
	 * @param customer
	 * @return action result
	 */
	public boolean updateCustomer(Customer customer) {
		return this.customerDAO.updateCustomer(customer);
	}

	/**
	 * Update customer information
	 * 
	 * @author Tai Tran
	 * @param request
	 * @param model
	 * @return action result
	 */
	@Transactional
	public boolean updateCustomer(int id, String firstName, String middleName, String lastName, String address1, String address2, String phone1, String phone2, String email, String idCardNumber, String currentAccountNumber) {
		Customer customer = this.getCustomerById(id);
		customer.setFirstName(firstName);
		customer.setMiddleName(middleName);
		customer.setLastName(lastName);
		customer.setAddress1(address1);
		customer.setAddress2(address2);
		customer.setPhone1(phone1);
		customer.setPhone2(phone2);
		customer.setEmail(email);
		customer.setIdCardNumber(idCardNumber);
		
		return this.updateCustomer(customer);
	}

	// ***************
	// *************** Saving Account services
	// ***************

	/**
	 * Get list of saving account
	 * 
	 * @author Nhat Nguyen
	 * @return list of saving account
	 */
	public List<SavingAccount> getSavingAccounts() {
		return savingAccountDAO.getAccountList();
	}

	/**
	 * Add new saving account
	 * 
	 * @author Nhat Nguyen
	 * @param account
	 * @param request
	 * @param response
	 * @return action result
	 */
	public boolean addSavingAccount(SavingAccount account,
			HttpServletRequest request, HttpServletResponse response) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		Customer customer = this.getCustomerById(customerId);

		Integer rateId = Integer.parseInt(request.getParameter("period"));
		SavingInterestRate interestRate = this.getInterestRateById(rateId);

		account.setInterestRate(interestRate);

		account.setCustomer(customer);
		account.setState("new");

		Calendar cal = Calendar.getInstance();
		account.setStartDate(cal.getTime());

		if (interestRate.getPeriod() != 0) {
			cal.add(Calendar.MONTH, (int) interestRate.getPeriod());
			account.setDueDate(cal.getTime());
		}

		account.setInterest(0);

		boolean repeatable = request.getParameter("repeatable") != null;
		account.setRepeatable(repeatable);
		return savingAccountDAO.addSavingAccount(account);
	}

	/**
	 * Calculate interest for the saving account
	 * 
	 * @author Nhat Nguyen
	 * @param account
	 * @return interest
	 */
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

	/**
	 * Update saving account
	 * 
	 * @author Nhat Nguyen
	 * @param account
	 * @return action result
	 */
	public boolean updateSavingAccount(SavingAccount account) {
		return savingAccountDAO.updateSavingAccount(account);
	}

	/**
	 * Update saving account
	 * 
	 * @author Tai Tran
	 * @param request
	 * @param model
	 * @return action result
	 */
	@Transactional
	public boolean updateSavingAccount(int interestId, String accountNumber, String accountOwner, double balanceAmount, double interest, String state, boolean repeatable) {
		

		SavingInterestRate savingInterestRate = this
				.getInterestRateById(interestId);

		SavingAccount savingAccount = this
				.getSavingAccountByAccNumber(accountNumber);
		savingAccount.setAccountOwner(accountOwner);
		savingAccount.setBalanceAmount(balanceAmount);
		savingAccount.setInterest(interest);
		savingAccount.setState(state);
		savingAccount.setInterestRate(savingInterestRate);
		savingAccount.setRepeatable(repeatable);

		boolean result = this.updateSavingAccount(savingAccount);
		if (!result) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Get saving account by account number
	 * 
	 * @author Nhat Nguyen
	 * @param accNumber
	 * @return saving account
	 */
	public SavingAccount getSavingAccountByAccNumber(String accNumber) {
		return savingAccountDAO.getAccountByAccNumber(accNumber);
	}

	/**
	 * Get saving account by account ID
	 * 
	 * @author Nhat Nguyen
	 * @param accId
	 * @return saving account
	 */
	public SavingAccount getSavingAccountById(int accId) {
		return savingAccountDAO.getAccountById(accId);
	}

	/**
	 * Auto-generate account number for each account when it is created
	 * 
	 * @author Nhat Nguyen
	 * @return new account number
	 */
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
	 * Search saving account by ID card number and account number
	 * 
	 * @param idCard
	 * @param accNumber
	 * @return list of saving account
	 * @author Vinh Truong
	 */
	public List<SavingAccount> searchSavingAccounts(String idCardNo,String accNumber) {
		

		return savingAccountDAO.searchSavingAccounts(idCardNo, accNumber);
	}

	/**
	 * Approve saving account to be active
	 * 
	 * @author Nhat Nguyen
	 * @param request
	 * @return action result
	 */
	public boolean approve(HttpServletRequest request) {
		String accountNumber = request.getParameter("accountNumber");
		SavingAccount account = this.getSavingAccountByAccNumber(accountNumber);
		account.setState("active");

		return savingAccountDAO.approve(account);
	}

	// ***************
	// *************** Interest Rate services
	// ***************

	/**
	 * Get model with list of current interest rates
	 * 
	 * @author Tai Tran
	 * @param model
	 * @return model with interest rate list
	 */
	public List<SavingInterestRate> getInterestRateList() {
		return rateDAO.getCurrentInterestRateList();
	}

	/**
	 * Get list of current interest rate
	 * 
	 * @author Nhat Nguyen
	 * @return list of current interest rate
	 */
	public List<SavingInterestRate> getCurrentInterestRateList() {
		return rateDAO.getCurrentInterestRateList();
	}

	/**
	 * Get current interest rate by period
	 * 
	 * @author Nhat Nguyen
	 * @param interestRatePeriod
	 * @return interest rate
	 */
	public SavingInterestRate getCurrentRateByPeriod(Integer period) {
		return rateDAO.getCurrentInterestRateByPeriod(period);
	}

	/**
	 * Get saving interest rate by ID
	 * 
	 * @author Nhat Nguyen
	 * @param id
	 * @return interest rate
	 */
	public SavingInterestRate getInterestRateById(int id) {
		return rateDAO.getInterestRateById(id);
	}

	/**
	 * Update interest rate
	 * 
	 * @author Tai Tran
	 * @param request
	 * @param model
	 * @return action result
	 */
	@Transactional
	public boolean updateRate(int i, int totalRate, List<SavingInterestRate> rateList, double interestRate, int period) {
		List<SavingInterestRate> allRateList = this.getInterestRateList();

		if (i <= totalRate) {
			if (rateList.get(i - 1).getInterestRate() != interestRate) {

				for (SavingInterestRate rate : allRateList) {
					// If the new Rate is already existed
					if (rate.getInterestRate() == interestRate
							&& rate.getPeriod() == period) {
						rateDAO.updateInterestState(rate.getPeriod());
						rate.setState("Current");
						rateDAO.updateInterestRate(rate);
						return true;
					}
				}

				SavingInterestRate newInterestRate = new SavingInterestRate();
				newInterestRate.setPeriod(period);
				newInterestRate.setInterestRate(interestRate);
				newInterestRate.setState("Current");
				this.addInterestRate(newInterestRate);
			}
		} else {
			SavingInterestRate newInterestRate = new SavingInterestRate();
			newInterestRate.setPeriod(period);
			newInterestRate.setInterestRate(interestRate);
			newInterestRate.setState("Current");
			this.addInterestRate(newInterestRate);
		}
		
		return true;
	}

	/**
	 * Add new interest rate
	 * 
	 * @author Tai Tran
	 * @param newInterestRate
	 * @return
	 */
	@Transactional
	public boolean addInterestRate(SavingInterestRate newInterestRate) {
		rateDAO.updateInterestState(newInterestRate.getPeriod());
		return rateDAO.addInterestRate(newInterestRate);
	}

	// ***************
	// *************** Transaction services
	// ***************

	/**
	 * Get list of transaction
	 * 
	 * @author Nhat Nguyen
	 * @return list of transaction
	 */
	public List<Transaction> getTransactionList() {
		return transactionDAO.getTransactions();
	}

	/**
	 * Get transaction by ID
	 * 
	 * @author Nhat Nguyen
	 * @param id
	 * @return transaction
	 */
	public Transaction getTransactionById(int id) {
		return transactionDAO.getTransactionById(id);
	}

	/**
	 * Add new pending transaction
	 * 
	 * @author Nhat Nguyen
	 * @param transaction
	 * @return action result
	 */
	public boolean performTransaction(Transaction transaction) {
		return transactionDAO.performTransaction(transaction);
	}

	/**
	 * Check if account has withdrawn monthly interest already
	 * 
	 * @author Nhat Nguyen
	 * @param account
	 * @return has withdrawn or not yet
	 */
	public boolean getInterestAlready(SavingAccount account) {
		return transactionDAO.getInterestAlready(account);
	}

	/**
	 * Check if account still has pending transaction
	 * 
	 * @author Nhat Nguyen
	 * @param accNumber
	 * @return true or false
	 */
	public boolean pendingAvail(String accNumber) {
		return transactionDAO.pendingTransAvail(accNumber);
	}

	/**
	 * Consider the conditions before performing transaction
	 * 
	 * @author Nhat Nguyen
	 * @param transaction
	 * @param request
	 * @param account
	 * @return action result: OK or error name
	 */
	public String preSubmitTransaction(Transaction transaction,
			HttpServletRequest request, SavingAccount account) {
		String result = "OK";
		double currentBalance = Double.parseDouble(request
				.getParameter("balance"));
		double monthlyInterest = Double.parseDouble(request
				.getParameter("interest"));
		double dueDateTotal = Double.parseDouble(request
				.getParameter("dueDateAmount"));
		double beforeDueAmount = Double.parseDouble(request
				.getParameter("beforeDueAmount"));

		if (transaction.getType().equals("Withdraw All")) {
			if (account.getBalanceAmount() == 0) {
				result = "You have nothing to withdraw!";

			}
			// Period
			if (account.getInterestRate().getPeriod() != 0) {
				// Withdraw on due date
				if (DateUtils.isToday(account.getDueDate())) {
					transaction.setAmount(dueDateTotal);
					// Withdraw before due date
				} else if (DateUtils.isBeforeDay(new Date(),
						account.getDueDate())) {
					transaction.setAmount(beforeDueAmount);
					// - Withdraw after due date and account not repeatable
					// - Not necessary to check after due date for repeatable
					// account because it is auto-extended after due date
				} else if (DateUtils.isAfterDay(new Date(),
						account.getDueDate())
						&& account.isRepeatable() == false) {
					transaction.setAmount(beforeDueAmount);
				}
				// Demand
			} else {
				transaction.setAmount(dueDateTotal);
			}

		} else if (account.getInterestRate().getPeriod() == 0 // Demand
				&& transaction.getType().equals("Withdraw Balance")) {
			// Get transaction amount
			double amount = transaction.getAmount();

			// Check if transaction amount is less than current balance
			if (amount > currentBalance) {
				result = "You cannot withdraw more than your current balance!";

			}

			// Check if balance after withdrawal is >= 1.000.000
			if (currentBalance - amount < 1000000) {
				result = "Balance after withdrawal must be at least 1.000.000 VND!";
			}

			// Set amount := amount + monthly interest
			// interest will be subtracted when approving transaction
			transaction.setAmount(amount + monthlyInterest);

		} else if (transaction.getType().equals("Withdraw Interest")) {
			Calendar cal = Calendar.getInstance();
			int todayDay = cal.get(Calendar.DAY_OF_MONTH);
			int thisMonth = cal.get(Calendar.MONTH) + 1;
			cal.setTime(account.getStartDate());
			if (cal.get(Calendar.DAY_OF_MONTH) != todayDay
					|| cal.get(Calendar.MONTH) + 1 == thisMonth) {
				result = "You cannot get interest today!";
			}
			if (this.getInterestAlready(account)) {
				result = "This account has withdrawn this month's interest!";
			}
			transaction.setAmount(monthlyInterest);

		} else if (transaction.getType().equals("Deposit")) {
			// Period
			if (account.getInterestRate().getPeriod() != 0) {
				List<Date> list = this.getWithdrawAll(account);
				Date lastWithdrawAll = new Date();
				if (!list.isEmpty())
					lastWithdrawAll = list.get(list.size() - 1);
				// Deposit before due date
				if ((account.getBalanceAmount() != 0 && !DateUtils
						.isToday(account.getStartDate()))
						&& DateUtils.isBeforeDay(new Date(),
								account.getDueDate())
						&& !DateUtils.isToday(account.getDueDate())
						|| (!list.isEmpty() && DateUtils.isBeforeDay(
								account.getDueDate(), lastWithdrawAll))) {
					result = "This account cannot deposit at this time!";
				}
			}

			if (transaction.getAmount() <= 0) {
				result = "Amount must be above zero!";
			}
		}

		User user = (User) request.getSession().getAttribute("USER");
		transaction.getUsers().add(user);

		transaction.setDate(new Date());
		transaction.setState("Pending");

		return result;
	}

	/**
	 * Approve transaction and apply all changes for saving account
	 * 
	 * @author Nhat Nguyen
	 * @param transaction
	 * @return action result
	 */
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
			interest = this.calculateInterest(account);
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

	/**
	 * Reject transaction
	 * 
	 * @author Nhat Nguyen
	 * @param trans
	 * @return action result
	 */
	public boolean rejectTransaction(Transaction trans) {
		return transactionDAO.rejectTransaction(trans);
	}

	/**
	 * Get list of dates when the account has withdrawn its monthly interest
	 * 
	 * @author Nhat Nguyen
	 * @param account
	 * @return list of date
	 */
	public List<Date> getInterestWithdraw(SavingAccount account) {
		return transactionDAO.getInterestWithdraw(account);
	}

	/**
	 * Get list of dates when the account has withdrawn all its balance and
	 * interest
	 * 
	 * @author Nhat Nguyen
	 * @param account
	 * @return list of date
	 */
	public List<Date> getWithdrawAll(SavingAccount account) {
		return transactionDAO.getWithdrawAll(account);
	}

	/**
	 * Get total amount that customer can withdraw before due date
	 * 
	 * @author Nhat Nguyen
	 * @param account
	 * @return total amount of money
	 */
	public double getBeforeDueTotal(SavingAccount account) {
		Date today = new Date();
		int dateDiff = DateUtils.daysBetween(account.getStartDate().getTime(),
				today.getTime());
		return account.getBalanceAmount()
				+ this.getCurrentRateByPeriod(0).getInterestRate() / 365
				* dateDiff * account.getBalanceAmount();
	}

	/**
	 * Get total amount that customer can withdraw on due date
	 * 
	 * @author Nhat Nguyen
	 * @param account
	 * @return total amount of money
	 */
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
