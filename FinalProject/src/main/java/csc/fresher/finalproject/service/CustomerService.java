package csc.fresher.finalproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import csc.fresher.finalproject.dao.CustomerDAO;
import csc.fresher.finalproject.domain.Customer;
@Service("customerService")
public class CustomerService {
	@Autowired
	private CustomerDAO customerDAO;	
	
	public CustomerService() {
//		this.customerDAO = new CustomerDAO();
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
