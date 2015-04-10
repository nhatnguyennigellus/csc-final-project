package csc.fresher.finalproject.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import csc.fresher.finalproject.domain.Transaction;
import csc.fresher.finalproject.domain.User;
import csc.fresher.finalproject.mycookies.SessionName;
import csc.fresher.finalproject.service.TransactionService;

@Controller
public class TransactionController {
	private TransactionService transactionService = new TransactionService();

	/**
	 * Redirect to searchTransaction views if logged in
	 * 
	 * @param request
	 * @return
	 * @author vinh-tp
	 */
	@RequestMapping(value = "/searchTransaction", method = RequestMethod.GET)
	public String viewSearchTransaction(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(SessionName.USER);
		String nextPage;
		if (user != null && user.getUsername() != "") { // a little
														// consideration here
			nextPage = "searchTransaction";
			System.out.println("Going to searchTransaction .....");
		} else {
			nextPage = "login";
		}
		return nextPage;
	}

	/**
	 * Check for inputs and call for services
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @author vinh-tp
	 */
	public String doSearchTransaction(HttpServletRequest request, Model model) {
		// TODO check authentication
		String state = request.getParameter("transactionState");
		String type = request.getParameter("transactionType");
		String submitAction = request.getParameter("submitAction");
		String accountNumber = request.getParameter("accountNumber");
		String transactionId = request.getParameter("transactionId");
		Hashtable<String, String> messages = new Hashtable<String, String>();
		List<Transaction> transactions = null;
		if (submitAction.equalsIgnoreCase("Search by details")) {
			transactions = transactionService.searchTransaction(state, type,
					accountNumber);

		} else if (submitAction.equalsIgnoreCase("Search by ID")) {
			Transaction transaction;
			try {
				transaction = transactionService
						.getTransactionById(transactionId);
				transactions = new ArrayList<Transaction>();
				if (transaction != null) {
					transactions.add(transaction);
				}
			} catch (NumberFormatException e) {
				messages.put("numberFormat", "NumberFormatError");
			}
		}
		model.addAttribute("transactionList", transactions);
		model.addAttribute("messageList", messages);
		return "searchTransaction";
	}
}
