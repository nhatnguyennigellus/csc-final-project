package csc.fresher.finalproject.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import csc.fresher.finalproject.domain.Customer;
import csc.fresher.finalproject.domain.SavingAccount;
import csc.fresher.finalproject.domain.SavingInterestRate;
import csc.fresher.finalproject.service.CustomerService;
import csc.fresher.finalproject.service.InterestRateService;
import csc.fresher.finalproject.service.SavingAccountService;

@Controller
public class SavingAccountController {
	private SavingAccountService accountService = new SavingAccountService();
	private CustomerService customerService = new CustomerService();
	private InterestRateService rateService = new InterestRateService();

	@RequestMapping(value = "/toAddAccount")
	public String toAddCustomer(Model model, HttpServletRequest request) {

		model.addAttribute("savingAccount", new SavingAccount());
		model.addAttribute("customerId", request.getParameter("customerId"));
		return "addAccount";
	}

	@RequestMapping(value = "/addAccount", method = RequestMethod.POST)
	public String addAccount(
			@ModelAttribute("savingAccount") @Valid SavingAccount savingAccount,
			BindingResult result, Model model, HttpServletRequest request) {
		if (accountService.existedAccountNumber(savingAccount.getAccountNumber())) {
			model.addAttribute("addAccError", "This account number exists!");
			return "redirect:toAddAccount";
		}
		
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		Customer customer = customerService.getCustomerById(customerId);
		
		double interestRatePeriod = Double.parseDouble(request
				.getParameter("period"));
		SavingInterestRate interestRate = rateService
				.getInterestRateByPeriod(interestRatePeriod);

		double interest = 0;
		interest = savingAccount.getBalanceAmount()
				* interestRate.getInterestRate();
		savingAccount.setInterestRate(interestRate);
		savingAccount.setInterest(interest);
		savingAccount.setCustomer(customer);
		savingAccount.setState("new");

		Calendar cal = Calendar.getInstance();
		savingAccount.setStartDate(cal.getTime());
		cal.add(Calendar.MONTH, (int) interestRatePeriod);
		savingAccount.setDueDate(cal.getTime());

		boolean repeatable = request.getParameter("repeatable") != null;
		savingAccount.setRepeatable(repeatable);

		if (accountService.addSavingAccount(savingAccount)) {
			model.addAttribute("addAccSuccess",
					"Added new account successfully!");
		} else {
			model.addAttribute("addAccError", "Fail to add new account!");
		}
		return "addAccount";
	}
	
	@RequestMapping(value = "/viewAccount")
	public String viewAccount(Model model, HttpServletRequest request){
		List<SavingAccount> accountList = accountService.getSavingAccounts();
		
		model.addAttribute("accountList", accountList);
		
		return "viewAccount";
	}
}
