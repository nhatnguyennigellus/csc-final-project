package csc.fresher.finalproject.service;

import csc.fresher.finalproject.dao.CustomerDAO;
import csc.fresher.finalproject.domain.Customer;

public class CustomerService {
	private CustomerDAO customerDAO;	
	
	public CustomerService() {
		this.customerDAO = new CustomerDAO();
	}
	
	public boolean addCustomer(Customer customer) {
		return this.customerDAO.addCustomer(customer);
	}
	
	public Customer getCustomerById(int id) {
		return this.customerDAO.getCustomerById(id);
	}
}
