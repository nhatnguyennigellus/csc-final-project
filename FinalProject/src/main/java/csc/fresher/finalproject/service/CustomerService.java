package csc.fresher.finalproject.service;

import java.util.List;

import csc.fresher.finalproject.dao.CustomerDAO;
import csc.fresher.finalproject.domain.Customer;

public class CustomerService {
	private CustomerDAO customerDAO;	
	
	public CustomerService() {
		this.customerDAO = new CustomerDAO();
	}
	
	public List<Customer> getCustomerList() {
		return this.customerDAO.getCustomers();
	}
	public boolean addCustomer(Customer customer) {
		return this.customerDAO.addCustomer(customer);
	}
	
	public Customer getCustomerById(int id) {
		return this.customerDAO.getCustomerById(id);
	}
}