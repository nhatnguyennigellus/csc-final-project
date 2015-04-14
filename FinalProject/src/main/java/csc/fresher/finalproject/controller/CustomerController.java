package csc.fresher.finalproject.controller;

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
import csc.fresher.finalproject.service.CustomerService;
import csc.fresher.finalproject.service.SavingAccountService;

@Controller
public class CustomerController {
	private CustomerService customerService = new CustomerService();
	private SavingAccountService accountService = new SavingAccountService();

	@RequestMapping(value = "/viewCustomer")
	public String viewCustomer(Model model, HttpServletRequest request) {
		List<Customer> listCustomer = customerService.getCustomerList();

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
		
		if (customerService.addCustomer(customer)) {
			model.addAttribute("addCusSuccess",
					"Added new customer successfully!");

		} else {
			model.addAttribute("addCusError", "Fail to add new customer!");
		}

		return "addCustomer";
	}
	
	@RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
	public String updateCustomer(Model model, HttpServletRequest request){
		int id = Integer.parseInt(request.getParameter("customerID"));
		String firstName = request.getParameter("customerFirstName");
		String middleName = request.getParameter("customerMiddleName");
		String lastName = request.getParameter("customerLastName");
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String phone1 = request.getParameter("phone1");
		String phone2 = request.getParameter("phone2");
		String email = request.getParameter("email");
		String idCardNumber = request.getParameter("idCard");
		String currentAccountNumber = request.getParameter("currentAccount");
		
		Customer customer = new Customer(id, firstName, middleName, lastName, address1, address2, phone1, phone2, email, idCardNumber);
		SavingAccount currentAccount = accountService.getSavingAccountByNumber(currentAccountNumber);
		
		boolean result = customerService.updateCustomer(customer);
		
		model.addAttribute("customer", customer);
		model.addAttribute("account", currentAccount);
		
		return "redirect:modifyAccount";
	}
}
