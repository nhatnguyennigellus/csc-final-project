package csc.fresher.finalproject.controller;

import org.springframework.stereotype.Controller;

import csc.fresher.finalproject.service.CustomerService;

@Controller
public class CustomerController {
	private CustomerService customerService = new CustomerService();

}
