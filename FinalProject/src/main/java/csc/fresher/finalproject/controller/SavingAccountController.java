package csc.fresher.finalproject.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import csc.fresher.finalproject.domain.Customer;
import csc.fresher.finalproject.domain.SavingAccount;
import csc.fresher.finalproject.domain.SavingInterestRate;
import csc.fresher.finalproject.domain.Transaction;
import csc.fresher.finalproject.service.BankingService;
import csc.fresher.finalproject.service.DateUtils;

@Controller
public class SavingAccountController {

	@Autowired
	BankingService bankingService;

	@RequestMapping(value = "/toAddAccount")
	public String toAddCustomer(Model model, HttpServletRequest request) {
		SavingAccount account = new SavingAccount();
		account.setAccountNumber(bankingService.generateAccountNumber());

		model.addAttribute("rateList", bankingService.getInterestRateList());
		model.addAttribute("savingAccount", account);
		model.addAttribute("customerId", request.getParameter("customerId"));
		return "addAccount";
	}

	@RequestMapping(value = "/addAccount", method = RequestMethod.POST)
	public String addAccount(
			@ModelAttribute("savingAccount") @Valid SavingAccount savingAccount,
			BindingResult result, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (bankingService.addSavingAccount(savingAccount, request, response)) {
			model.addAttribute("addAccSuccess",
					"Added new account successfully!");
		} else {
			model.addAttribute("addAccError", "Fail to add new account!");
		}
		return "addAccount";
	}

	/**
	 * Search for account existence
	 * 
	 * @author vinh-tp
	 * @since 2015-08-04
	 */
	@RequestMapping(value = "/searchAccount", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String searchAccount(HttpServletRequest request, Model model) {
		request.getSession().removeAttribute("updateSuccess");
		request.getSession().removeAttribute("updateError");
		
		List<SavingAccount> accounts = bankingService
				.searchSavingAccounts(request);
		model.addAttribute("accountList", accounts);
		
		return "searchAccount";
	}

	@RequestMapping(value = "/viewAccount")
	public String viewAccount(Model model, HttpServletRequest request) {
		List<SavingAccount> accountList = bankingService.getSavingAccounts();

		model.addAttribute("accountList", accountList);

		return "viewAccount";
	}

	@RequestMapping(value = "/modifyAccount")
	public String modifyAccount(Model model, HttpServletRequest request) {
		String accNumber = request.getParameter("accNumber");
		SavingAccount savingAccount = bankingService
				.getSavingAccountByAccNumber(accNumber);
		Customer customer = bankingService.findCustomerOfAccount(savingAccount);

		model.addAttribute("account", savingAccount);
		model.addAttribute("customer", customer);

		return "modifyAccount";
	}

	@RequestMapping(value = "/updateAccount", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String updateAccount(Model model, HttpServletRequest request) {

		int interestId = 0;
		String accountNumber = "";
		String accountOwner = "";
		double balanceAmount = 0;
		double interest = 0;
		String repeatableString = "";
		String state = "";
		boolean repeatable = false;

		// Validate Saving Account
		if (request.getParameter("accountNumber") != ""
				&& request.getParameter("accountOwner") != ""
				&& request.getParameter("balanceAmount") != ""
				&& request.getParameter("interest") != ""
				&& request.getParameter("customerId") != ""
				&& request.getParameter("interestId") != "") {
			accountNumber = request.getParameter("accountNumber");
			accountOwner = request.getParameter("accountOwner");
			balanceAmount = Double.parseDouble(request
					.getParameter("balanceAmount"));
			interest = Double.parseDouble(request.getParameter("interest"));

			repeatableString = request.getParameter("repeatable");
			if (repeatableString == "True") {
				repeatable = true;
			}

			state = request.getParameter("state");

			interestId = Integer.parseInt(request.getParameter("interestId"));
		} else {
			request.getSession().setAttribute("updateError",
					"Please fill all fields with valid data!");
			return "redirect:modifyAccount?accNumber=" + accountNumber;
		}

		SavingInterestRate savingInterestRate = bankingService
				.getInterestRateById(interestId);

		SavingAccount savingAccount = bankingService
				.getSavingAccountByAccNumber(accountNumber);
		savingAccount.setAccountOwner(accountOwner);
		savingAccount.setBalanceAmount(balanceAmount);
		savingAccount.setInterest(interest);
		savingAccount.setState(state);
		savingAccount.setInterestRate(savingInterestRate);
		savingAccount.setRepeatable(repeatable);

		boolean result = bankingService.updateSavingAccount(savingAccount);
		if (!result) {
			request.getSession().setAttribute("updateError",
					"Cannot update Account!");
		} else {
			request.getSession().setAttribute("updateSuccess",
					"Updated Account!");
		}

		model.addAttribute("account", savingAccount);

		return "redirect:modifyAccount?accNumber=" + accountNumber;
	}

	@RequestMapping(value = "/approveAccount")
	public String approve(HttpServletRequest request) {
		bankingService.approve(request);

		return "redirect:searchAccount";
	}
}
