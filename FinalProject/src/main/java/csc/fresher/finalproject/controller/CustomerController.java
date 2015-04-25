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

		if (firstName == "" || lastName == ""
				|| address1 == "" || phone1 == ""
				|| email == "" || idCardNumber == ""
				|| currentAccountNumber == "") {
			model.addAttribute("updateError",
					"Please fill all fields with valid data!");
			return "redirect:modifyAccount?accNumber=" + currentAccountNumber;
		}

		Customer customer = bankingService.getCustomerById(id);
		customer.setFirstName(firstName);
		customer.setMiddleName(middleName);
		customer.setLastName(lastName);
		customer.setAddress1(address1);
		customer.setAddress2(address2);
		customer.setPhone1(phone1);
		customer.setPhone2(phone2);
		customer.setEmail(email);
		customer.setIdCardNumber(idCardNumber);

		SavingAccount currentAccount = bankingService
				.getSavingAccountByAccNumber(currentAccountNumber);

		boolean result = bankingService.updateCustomer(customer);

		model.addAttribute("customer", customer);
		model.addAttribute("account", currentAccount);

		if (!result) {
			request.getSession().removeAttribute("updateSuccess");
			request.getSession().setAttribute("updateError",
					"Cannot update customer info!");
		} else {
			request.getSession().removeAttribute("updateError");
			request.getSession().setAttribute("updateSuccess",
					"Updated customer info!");
		}
		return "redirect:modifyAccount?accNumber=" + currentAccountNumber;
	}
}
