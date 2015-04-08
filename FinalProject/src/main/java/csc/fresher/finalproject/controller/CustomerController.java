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
import csc.fresher.finalproject.service.CustomerService;

@Controller
public class CustomerController {
	private CustomerService customerService = new CustomerService();

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
		System.out.println(customer.getFirstName() != null ? customer
				.getFirstName() : "null First name");
		if (customerService.addCustomer(customer)) {
			model.addAttribute("addCusSuccess",
					"Added new customer successfully!");

		} else {
			model.addAttribute("addCusError", "Fail to add new customer!");
		}

		return "addCustomer";
	}
}
