package csc.fresher.finalproject.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
