package csc.fresher.finalproject.controller;

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

import java.util.ArrayList;
import java.util.List;

import csc.fresher.finalproject.utilities.SessionName;

@Controller
public class TransactionController {
	@Autowired
	BankingService bankingService;

	/**
	 * Redirect to Transaction Account page, where user can enter account number
	 * of account that is going to perform transaction
	 * 
	 * @author Nhat Nguyen
	 * @return Transaction Account page 
	 */
	@RequestMapping(value = "/accountTransaction")
	public ModelAndView performTransaction() {
		return new ModelAndView("accountTransaction");
	}

	/**
	 * Get information from database and redirect to Perform Transaction page
	 * 
	 * @author Nigellus
	 * @param model
	 * @param request
	 * @return Perform Transaction page
	 */
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

	/**
	 * Submit Transaction with information and state "Pending"
	 * 
	 * @param transaction
	 * @param result
	 * @param model
	 * @param request
	 * @param response
	 * @return Perform Transaction page
	 */
	@RequestMapping(value = "/submitTransaction", method = RequestMethod.POST)
	public ModelAndView submitTransaction(
			@ModelAttribute("Transaction") @Valid Transaction transaction,
			BindingResult result, Model model, HttpServletRequest request,
			HttpServletResponse response) {
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

		String resultMes = bankingService.preSubmitTransaction(transaction,
				request, account);
		if (!resultMes.equals("OK")) {
			model.addAttribute("transError", resultMes);
			return new ModelAndView("performTransaction");
		}

		if (bankingService.performTransaction(transaction)) {
			model.addAttribute("transSuccess",
					"Added new transaction successfully!");
		} else {
			model.addAttribute("transError", "Error occur!");
		}
		model.addAttribute("account", account);
		return new ModelAndView("performTransaction");
	}

	/**
	 * Approve transaction and apply all changes for saving account
	 * 
	 * @param model
	 * @param request
	 * @return Perform Transaction page
	 */
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

	/**
	 * Reject transaction
	 * 
	 * @param model
	 * @param request
	 * @return Perform Transaction page
	 */
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
	 * Redirect to Search Transaction page if logged in
	 * 
	 * @param request
	 * @return
	 * @author Vinh Truong
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
	 * @author Vinh Truong
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
