package csc.fresher.finalproject.service;

import java.io.IOException;
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
import org.springframework.web.servlet.ModelAndView;

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
import csc.fresher.finalproject.utilities.SessionName;

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
	
	@Transactional
	public boolean updateCustomer(HttpServletRequest request, Model model){
		int id = Integer.parseInt(request.getParameter("customerID"));
		String firstName = request.getParameter("customerFirstName");
		String middleName = request.getParameter("customerMiddleName");
		String lastName = request.getParameter("customerLastName");
		String address1 = request.getParameter("customerAddress1");
		String address2 = request.getParameter("customerAddress2");
		String phone1 = request.getParameter("customerPhone1");
		String phone2 = request.getParameter("customerPhone2");
		String email = request.getParameter("customerEmail");
		String idCardNumber = request.getParameter("customerIDCardNumber");
		String currentAccountNumber = request.getParameter("currentAccount");

		if (firstName == "" || lastName == ""
				|| address1 == "" || phone1 == ""
				|| email == "" || idCardNumber == ""
				|| currentAccountNumber == "") {
			model.addAttribute("updateError",
					"Please fill all fields with valid data!");
			return false;
		}

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

		SavingAccount currentAccount = this
				.getSavingAccountByAccNumber(currentAccountNumber);
		
		model.addAttribute("customer", customer);
		model.addAttribute("account", currentAccount);
		
		return true;
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

	public boolean addSavingAccount(SavingAccount account, HttpServletRequest request, HttpServletResponse response) {
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

	public double calculateInterest(SavingAccount account) {
		double interest = 0;
		SavingInterestRate interestRate = account.getInterestRate();
		if (interestRate.getPeriod() != 0) {
			int dateDiff = DateUtils.daysBetween(account.getStartDate().getTime(), account.getDueDate().getTime());
			interest = account.getBalanceAmount() * interestRate.getInterestRate() / 365 * dateDiff;
		} else {
			interest = account.getBalanceAmount() * interestRate.getInterestRate() / 360 * 30;
		}

		return interest;
	}

	public boolean updateSavingAccount(SavingAccount account) {
		return savingAccountDAO.updateSavingAccount(account);
	}

	@Transactional
	public boolean updateSavingAccount(HttpServletRequest request, Model model) {
		int interestId = 0;
		String accountNumber = "";
		String accountOwner = "";
		double balanceAmount = 0;
		double interest = 0;
		String repeatableString = "";
		String state = "";
		boolean repeatable = false;

		// Validate Saving Account
		if (request.getParameter("accountNumber") != "" && request.getParameter("accountOwner") != ""
				&& request.getParameter("balanceAmount") != "" && request.getParameter("interest") != ""
				&& request.getParameter("customerId") != "" && request.getParameter("interestId") != "") {
			accountNumber = request.getParameter("accountNumber");
			accountOwner = request.getParameter("accountOwner");
			balanceAmount = Double.parseDouble(request.getParameter("balanceAmount"));
			interest = Double.parseDouble(request.getParameter("interest"));

			repeatableString = request.getParameter("repeatable");
			if (repeatableString.equals("true")) {
				repeatable = true;
			}

			state = request.getParameter("state");

			interestId = Integer.parseInt(request.getParameter("interestId"));
		} else {
			return false;
		}
		
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
			request.getSession().setAttribute("updateError",
					"Cannot update Account!");
			
			return false;
		} else {
			request.getSession().setAttribute("updateSuccess",
					"Updated Account!");
		}
		
		model.addAttribute("account", savingAccount);
		
		return true;
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

		accNoBuilder.append(String.valueOf(cal.get(Calendar.YEAR)).substring(2));
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

	public Model getInterestRateList(Model model) {
		List<SavingInterestRate> rateList = rateDAO.getCurrentInterestRateList();

		model.addAttribute("rateList", rateList);

		return model;
	}

	public List<SavingInterestRate> getCurrentInterestRateList() {
		return rateDAO.getCurrentInterestRateList();
	}

	public SavingInterestRate getInterestRateByPeriod(Integer interestRatePeriod) {
		return rateDAO.getInterestRateByPeriod(interestRatePeriod);
	}

	public SavingInterestRate getCurrentRateByPeriod(Integer period) {
		return rateDAO.getCurrentInterestRateByPeriod(period);
	}

	public SavingInterestRate getInterestRateById(int id) {
		return rateDAO.getInterestRateById(id);
	}

	@Transactional
	public boolean updateRate(HttpServletRequest request, Model model) {
		boolean result = true;

		List<SavingInterestRate> rateList = rateDAO.getCurrentInterestRateList();

		List<SavingInterestRate> allRateList = rateDAO.getInterestRateList();

		int totalRate = rateList.size();

		int rowCount;

		if (request.getParameter("rowCount") == "") {
			rowCount = totalRate;
		} else {
			rowCount = Integer.parseInt(request.getParameter("rowCount"));
		}

		for (int i = 1; i <= rowCount; i++) {

			// Validate Interest Rates
			if (request.getParameter("interestRate" + i) == "" || request.getParameter("period" + i) == "") {
				model.addAttribute("rateList", rateList);
				model.addAttribute("notify", "<font color = 'red'>Please enter value!</font>");
			}

			double interestRate = Double.parseDouble(request.getParameter("interestRate" + i));
			Integer period = Integer.parseInt(request.getParameter("period" + i));

			if (i <= totalRate) {
				if (rateList.get(i - 1).getInterestRate() != interestRate) {

					for (SavingInterestRate rate : allRateList) {
						// If the new Rate is already existed
						if (rate.getInterestRate() == interestRate && rate.getPeriod() == period) {
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
		}

		return true;
	}

	@Transactional
	public boolean addInterestRate(SavingInterestRate newInterestRate) {
		rateDAO.updateInterestState(newInterestRate.getPeriod());
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

	public String preSubmitTransaction(Transaction transaction, HttpServletRequest request, SavingAccount account) {
		String result = "OK";
		double currentBalance = Double.parseDouble(request.getParameter("balance"));
		double monthlyInterest = Double.parseDouble(request.getParameter("interest"));
		double dueDateTotal = Double.parseDouble(request.getParameter("dueDateAmount"));
		double beforeDueAmount = Double.parseDouble(request.getParameter("beforeDueAmount"));

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
				} else if (DateUtils.isBeforeDay(new Date(), account.getDueDate())) {
					transaction.setAmount(beforeDueAmount);
					// - Withdraw after due date and account not repeatable
					// - Not necessary to check after due date for repeatable
					// account because it is auto-extended after due date
				} else if (DateUtils.isAfterDay(new Date(), account.getDueDate()) && account.isRepeatable() == false) {
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
			if (cal.get(Calendar.DAY_OF_MONTH) != todayDay || cal.get(Calendar.MONTH) + 1 == thisMonth) {
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
				if ((account.getBalanceAmount() != 0 && !DateUtils.isToday(account.getStartDate()))
						&& DateUtils.isBeforeDay(new Date(), account.getDueDate())
						&& !DateUtils.isToday(account.getDueDate())
						|| (!list.isEmpty() && DateUtils.isBeforeDay(account.getDueDate(), lastWithdrawAll))) {
					result = "This account cannot deposit at this time!";
				}
			}

			if (transaction.getAmount() <= 0) {
				result = "Amount must be above zero!";
			}
		}

		User user = (User) request.getSession().getAttribute(SessionName.USER);
		transaction.getUsers().add(user);

		transaction.setDate(new Date());
		transaction.setState("Pending");

		return result;
	}

	public boolean approveTransaction(Transaction transaction) {
		SavingAccount account = transaction.getSavingAccount();
		if (transaction.getType().equals("Withdraw All")) {
			account.setBalanceAmount(0);
			account.setInterest(0);
		} else if (transaction.getType().equals("Withdraw Balance")) {
			// Reset new balance = old balance - transaction (withdraw)
			// amount
			account.setBalanceAmount(account.getBalanceAmount() - transaction.getAmount() + account.getInterest());
			double interest = 0;

			// Reset new interest
			interest = this.calculateInterest(account);
			account.setInterest(interest);
		} else if (transaction.getType().equals("Withdraw Interest")) {

		} else if (transaction.getType().equals("Deposit")) {
			// Set new balance
			account.setBalanceAmount(account.getBalanceAmount() + transaction.getAmount());

			// Set new interest
			// If period account, reset start date and due date
			if (account.getInterestRate().getPeriod() != 0) {
				Calendar cal = Calendar.getInstance();
				account.setStartDate(cal.getTime());

				cal.add(Calendar.MONTH, (int) account.getInterestRate().getPeriod());
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
		int dateDiff = DateUtils.daysBetween(account.getStartDate().getTime(), today.getTime());
		return account.getBalanceAmount()
				+ this.getCurrentRateByPeriod(0).getInterestRate() / 365 * dateDiff * account.getBalanceAmount();
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
	public List<Transaction> searchTransaction(String state, String type, String accountNumber) {
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
