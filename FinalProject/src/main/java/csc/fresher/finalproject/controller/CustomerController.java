package csc.fresher.finalproject.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import csc.fresher.finalproject.service.BankingService;

@Controller
public class CustomerController {
	@Autowired
	BankingService bankingService;

	@RequestMapping(value = "/viewCustomer")
	public String viewCustomer(Model model, HttpServletRequest request) {
		List<Customer> listCustomer = bankingService.getCustomerList();

		model.addAttribute("listCustomer", listCustomer);
		return "viewCustomer";
	}

	@RequestMapping(value = "/toAddCustomer")
	public String toAddCustomer(Model model) {
		model.addAttribute("customer", new Customer());
		return "addCustomer";
	}

	@RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
	public String addCustomer(
			@ModelAttribute("customer") @Valid Customer customer,
			BindingResult result, Model model, HttpServletRequest request) {

		if (bankingService.addCustomer(customer)) {
			model.addAttribute("addCusSuccess",
					"Added new customer successfully!");

		} else {
			model.addAttribute("addCusError", "Fail to add new customer!");
		}

		return "addCustomer";
	}

	@RequestMapping(value = "/updateCustomer", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String updateCustomer(Model model, HttpServletRequest request) {
		boolean result = bankingService.updateCustomer(request, model);
		
		if(!result){
			request.getSession().removeAttribute("updateSuccess");
			request.getSession().setAttribute("updateError",
					"Cannot update customer info!");
			
			return "redirect:modifyAccount?accNumber=" + request.getParameter("currentAccount");
		}

		request.getSession().removeAttribute("updateError");
		request.getSession().setAttribute("updateSuccess",
					"Updated customer info!");
		
		return "redirect:modifyAccount?accNumber=" + request.getParameter("currentAccount");
	}
}
