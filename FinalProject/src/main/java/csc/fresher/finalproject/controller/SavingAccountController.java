package csc.fresher.finalproject.controller;

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
import csc.fresher.finalproject.service.BankingService;

/**
 * This controller handles actions that are related to saving account
 * 
 * @author Nhat Nguyen
 *
 */
@Controller
public class SavingAccountController {

	@Autowired
	BankingService bankingService;

	/**
	 * Redirect to Add Account page
	 * 
	 * @author Nhat Nguyen
	 * @param model
	 * @param request
	 * @return Add Account page
	 */
	@RequestMapping(value = "/toAddAccount")
	public String toAddAccount(Model model, HttpServletRequest request) {
		SavingAccount account = new SavingAccount();
		account.setAccountNumber(bankingService.generateAccountNumber());

		model.addAttribute("rateList", bankingService.getCurrentInterestRateList());
		model.addAttribute("savingAccount", account);
		model.addAttribute("customerId", request.getParameter("customerId"));
		return "addAccount";
	}

	/**
	 * Add new account
	 * 
	 * @author Nhat Nguyen
	 * @param savingAccount
	 * @param result
	 * @param model
	 * @param request
	 * @param response
	 * @return Add Account page
	 */
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
	 * @author Vinh Truong
	 * @since 2015-08-04
	 * @return Search Account Page
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

	/**
	 * Redirect to Modify Account page
	 * 
	 * @author Tai Tran
	 * @param model
	 * @param request
	 * @return Modify Account page
	 */
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

	/**
	 * Update account
	 * 
	 * @author Tai Tran
	 * @param model
	 * @param request
	 * @return Modify Account page
	 */
	@RequestMapping(value = "/updateAccount", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String updateAccount(Model model, HttpServletRequest request) {

		boolean result = bankingService.updateSavingAccount(request, model);
		if(!result){
			request.getSession().setAttribute("updateError", "Please fill all fields with valid data!");
			return "redirect:modifyAccount?accNumber=" + request.getParameter("accountNumber");
		}

		return "redirect:modifyAccount?accNumber=" + request.getParameter("accountNumber");
	}

	/**
	 * Approve new account and change to active
	 * 
	 * @param request
	 * @return Search Account page
	 */
	@RequestMapping(value = "/approveAccount")
	public String approve(HttpServletRequest request) {
		bankingService.approve(request);

		return "redirect:searchAccount";
	}
}
