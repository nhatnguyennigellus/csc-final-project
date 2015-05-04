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

/**
 * This controller handles customer related actions
 * 
 * @author Nhat Nguyen, Tai Tran
 *
 */
@Controller
public class CustomerController {
	@Autowired
	BankingService bankingService;

	/**
	 * Redirect to Customer List page with customer list
	 * 
	 * @author Nhat Nguyen
	 * @param model
	 * @param request
	 * @return View Customer page
	 */
	@RequestMapping(value = "/viewCustomer")
	public String viewCustomer(Model model, HttpServletRequest request) {
		List<Customer> listCustomer = bankingService.getCustomerList();

		model.addAttribute("listCustomer", listCustomer);
		return "viewCustomer";
	}

	/**
	 * Redirect to Add Customer Page
	 * 
	 * @author Nhat Nguyen
	 * @param model
	 * @return Add Customer Page
	 */
	@RequestMapping(value = "/toAddCustomer")
	public String toAddCustomer(Model model) {
		model.addAttribute("customer", new Customer());
		return "addCustomer";
	}

	/**
	 * Add new customer
	 * 
	 * @author Nhat Nguyen
	 * @param customer
	 * @param result
	 * @param model
	 * @param request
	 * @return Add Customer Page
	 */
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

	/**
	 * Update customer information
	 * 
	 * @author Tai Tran
	 * @param model
	 * @param request
	 * @return Update Customer page
	 */
	@RequestMapping(value = "/updateCustomer", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String updateCustomer(Model model, HttpServletRequest request) {
		boolean result = false;
		
		int id = Integer.parseInt(request.getParameter("customerID"));
		String firstName = request.getParameter("customerFirstName");
		String middleName = request.getParameter("customerMiddleName");
		String lastName = request.getParameter("customerLastName");
		String address1 = request.getParameter("customerAddress1");
		String address2 = request.getParameter("customerAddress2");
		String phone1 = request.getParameter("customerPhone1");
		String phone2 = request.getParameter("customerPhone2");
		String email = request.getParameter("customerEmail");
		String idCardNumber = request.getParameter("customerIDCardNumber");
		String currentAccountNumber = request.getParameter("currentAccount");

		if (firstName == "" || lastName == "" || address1 == "" || phone1 == ""
				|| email == "" || idCardNumber == ""
				|| currentAccountNumber == "") {
			model.addAttribute("updateError",
					"Please fill all fields with valid data!");
			
			return "redirect:modifyAccount?accNumber="
			+ request.getParameter("currentAccount");
		}
		
		result = bankingService.updateCustomer(id, firstName, middleName, lastName, address1, address2, phone1, phone2, email, idCardNumber, currentAccountNumber);

		Customer customer = bankingService.getCustomerById(id);
		SavingAccount currentAccount = bankingService
				.getSavingAccountByAccNumber(currentAccountNumber);
		
		model.addAttribute("customer", customer);
		model.addAttribute("account", currentAccount);
		
		if (!result) {
			request.getSession().removeAttribute("updateSuccess");
			request.getSession().setAttribute("updateError",
					"Cannot update customer info!");

			return "redirect:modifyAccount?accNumber="
					+ request.getParameter("currentAccount");
		}

		request.getSession().removeAttribute("updateError");
		request.getSession().setAttribute("updateSuccess",
				"Updated customer info!");

		return "redirect:modifyAccount?accNumber="
				+ request.getParameter("currentAccount");
	}
}
