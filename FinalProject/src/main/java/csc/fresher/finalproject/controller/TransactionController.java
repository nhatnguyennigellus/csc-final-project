package csc.fresher.finalproject.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import csc.fresher.finalproject.domain.SavingAccount;
import csc.fresher.finalproject.domain.Transaction;
import csc.fresher.finalproject.domain.User;
import csc.fresher.finalproject.service.BankingService;
import csc.fresher.finalproject.service.DateUtils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import csc.fresher.finalproject.utilities.SessionName;

@Controller
public class TransactionController {
	@Autowired
	BankingService bankingService;

	@RequestMapping(value = "/accountTransaction")
	public ModelAndView performTransaction() {
		return new ModelAndView("accountTransaction");
	}

	@RequestMapping(value = "/listTransaction")
	public String listTransaction(Model model) {
		model.addAttribute("listTransaction",
				bankingService.getTransactionList());
		return "listTransaction";
	}

	@RequestMapping(value = "/performTransaction", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView getAccountTransaction(Model model,
			HttpServletRequest request) {
		String accNumber = request.getParameter("accountNumber");

		if (!bankingService.existedAccountNumber(accNumber)) {
			model.addAttribute("accTransError",
					"This account number does not exist!");
			return new ModelAndView("accountTransaction");
		}

		if (bankingService.pendingAvail(accNumber)) {
			model.addAttribute("accTransError",
					"This account still have pending transaction!"
							+ " Please come back later!");
			return new ModelAndView("accountTransaction");
		}
		SavingAccount account = bankingService
				.getSavingAccountByAccNumber(accNumber);
		if (!account.getState().equals("active")) {
			model.addAttribute("accTransError",
					"Only active account can perform transaction!");
			return new ModelAndView("accountTransaction");
		}

		model.addAttribute("account", account);

		model.addAttribute("BeforeDueTotal",
				bankingService.getBeforeDueTotal(account));

		model.addAttribute("DueDateTotal",
				bankingService.getDueDateTotal(account));

		Transaction transaction = new Transaction();
		transaction.setSavingAccount(account);
		model.addAttribute("Transaction", transaction);

		return new ModelAndView("performTransaction");
	}

	@RequestMapping(value = "/submitTransaction", method = RequestMethod.POST)
	public ModelAndView submitTransaction(
			@ModelAttribute("Transaction") @Valid Transaction transaction,
			BindingResult result, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		double currentBalance = Double.parseDouble(request
				.getParameter("balance"));
		double monthlyInterest = Double.parseDouble(request
				.getParameter("interest"));
		double dueDateTotal = Double.parseDouble(request
				.getParameter("dueDateAmount"));
		double beforeDueAmount = Double.parseDouble(request
				.getParameter("beforeDueAmount"));
		SavingAccount account = bankingService
				.getSavingAccountByAccNumber(transaction.getSavingAccount()
						.getAccountNumber());
		transaction.setSavingAccount(account);

		model.addAttribute("account", account);
		model.addAttribute("BeforeDueTotal", beforeDueAmount);
		model.addAttribute("DueDateTotal", dueDateTotal);

		if (transaction.getType().equals("Withdraw All")) {
			if (account.getBalanceAmount() == 0) {
				model.addAttribute("transError",
						"You have nothing to withdraw!");

				return new ModelAndView("performTransaction");
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
				model.addAttribute("transError",
						"You cannot withdraw more than your current balance!");

				return new ModelAndView("performTransaction");
			}

			// Check if balance after withdrawal is >= 1.000.000
			if (currentBalance - amount < 1000000) {
				model.addAttribute("transError",
						"Balance after withdrawal must be at least 1.000.000 VND!");
				model.addAttribute("account", account);
				model.addAttribute("BeforeDueTotal", beforeDueAmount);
				model.addAttribute("DueDateTotal", dueDateTotal);
				return new ModelAndView("performTransaction");
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
				model.addAttribute("transError",
						"You cannot get interest today!");
				return new ModelAndView("performTransaction");
			}
			if (bankingService.getInterestAlready(account)) {
				model.addAttribute("transError",
						"This account has withdrawn this month's interest!");
				return new ModelAndView("performTransaction");
			}
			transaction.setAmount(monthlyInterest);

		} else if (transaction.getType().equals("Deposit")) {
			// Period
			if (account.getInterestRate().getPeriod() != 0) {
				List<Date> list = bankingService.getWithdrawAll(account);
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
					model.addAttribute("transError",
							"This account cannot deposit at this time!");
					return new ModelAndView("performTransaction");
				}
			}

			if (transaction.getAmount() <= 0) {
				model.addAttribute("transError", "Amount must be above zero!");
				return new ModelAndView("performTransaction");
			}
		}

		User user = (User) request.getSession().getAttribute(SessionName.USER);
		transaction.getUsers().add(user);
		// user.getTransactions().add(transaction);

		transaction.setDate(new Date());
		transaction.setState("Pending");

		if (bankingService.performTransaction(transaction)) {
			model.addAttribute("transSuccess",
					"Added new transaction successfully!");
		} else {
			model.addAttribute("transError", "Error occur!");
		}
		model.addAttribute("account", account);
		return new ModelAndView("performTransaction");
	}

	@RequestMapping(value = "/approveTransaction")
	public String approveTransaction(Model model, HttpServletRequest request) {
		int transId = Integer.parseInt(request.getParameter("transactionId"));
		Transaction trans = bankingService.getTransactionById(transId);
		trans.setState("Approved");
		User user = (User) request.getSession().getAttribute(SessionName.USER);
		trans.getUsers().add(user);
		if (bankingService.approveTransaction(trans)) {
			model.addAttribute("apprSuccess", "Transaction '" + trans.getType()
					+ "' of account "
					+ trans.getSavingAccount().getAccountNumber()
					+ " approved!");
		} else {
			model.addAttribute("apprError", "Error occurs!");
		}
		model.addAttribute("listTransaction",
				bankingService.getTransactionList());

		return "searchTransaction";
	}

	@RequestMapping(value = "/rejectTransaction")
	public String rejectTransaction(Model model, HttpServletRequest request) {
		int transId = Integer.parseInt(request.getParameter("transactionId"));
		Transaction trans = bankingService.getTransactionById(transId);
		trans.setState("Rejected");
		if (bankingService.rejectTransaction(trans)) {
			model.addAttribute("apprError", "Transaction '" + trans.getType()
					+ "' of account "
					+ trans.getSavingAccount().getAccountNumber()
					+ " rejected!");
		} else {
			model.addAttribute("apprError", "Error occurs!");
		}
		model.addAttribute("listTransaction",
				bankingService.getTransactionList());

		return "searchTransaction";
	}

	/**
	 * Redirect to searchTransaction views if logged in
	 * 
	 * @param request
	 * @return
	 * @author vinh-tp
	 */

	@RequestMapping(value = "/searchTransaction")
	public String viewSearchTransaction(Model model, HttpServletRequest request) {

		return "searchTransaction";
	}

	/**
	 * Check for inputs and call for services
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @author vinh-tp
	 */
	@RequestMapping(value = "/submitSearchTransaction", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String doSearchTransaction(HttpServletRequest request, Model model) {
		String state = request.getParameter("transactionState");
		String type = request.getParameter("transactionType");
		String submitAction = request.getParameter("submitAction");
		String accountNumber = request.getParameter("accountNumber");

		List<Transaction> transactions = null;
		if (submitAction.equalsIgnoreCase("Search by details")) {
			transactions = bankingService.searchTransaction(state, type,
					accountNumber);

		} else if (submitAction.equalsIgnoreCase("Search by ID")) {

			Transaction transaction;
			try {
				int transactionId = 0;
				if (request.getParameter("transactionId") != null) {
					transactionId = Integer.parseInt(request
							.getParameter("transactionId"));
				}
				transaction = bankingService.getTransactionById(transactionId);
				transactions = new ArrayList<Transaction>();
				if (transaction != null) {
					transactions.add(transaction);
				}
			} catch (NumberFormatException e) {
				model.addAttribute("apprError", "Please enter a transaction ID");
				return "searchTransaction";
			}
		}
		model.addAttribute("transactionList", transactions);
		return "searchTransaction";
	}
}
