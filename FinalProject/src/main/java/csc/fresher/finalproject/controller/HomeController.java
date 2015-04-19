package csc.fresher.finalproject.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import csc.fresher.finalproject.domain.User;
import csc.fresher.finalproject.service.CustomerService;
import csc.fresher.finalproject.service.SavingAccountService;
import csc.fresher.finalproject.service.TransactionService;
import csc.fresher.finalproject.service.UserService;
import csc.fresher.finalproject.utilities.SessionName;

/**
 * This is a controller for home page when user first accesses the application
 * 
 * @author nvu3, Hiep Nguyen, Nhat Nguyen
 *
 */

@Controller
public class HomeController {
	@Autowired
	private UserService userService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private SavingAccountService accountService;
	@Autowired
	private TransactionService transService;

	@RequestMapping(value = "/404")
	public String error404() {
		return "404";
	}

	/**
	 * Redirects to Login Page
	 * 
	 * @param model
	 * @return Login Page
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView redirectLogin(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession();

		model.addObject("targetUrl", "/home");
		if (session.getAttribute(SessionName.USER) != null) {
			model.setViewName("home");

			try {
				response.sendRedirect("home");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return model;
		}

		if (error != null) {
			model.addObject("error", "Invalid username and password!");
			
			String targetUrl = getRememberMeTargetUrlFromSession(request);
			System.out.println(targetUrl);
			if (StringUtils.hasText(targetUrl)) {
				model.addObject("targetUrl", targetUrl);
			}
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
			
		}
		model.setViewName("login");

		return model;
	}

	/**
	 * Redirects to Home Page
	 * 
	 * @param model
	 * @return Home Page
	 */
	@RequestMapping(value = "/home")
	public String redirectHome(Model model, HttpServletRequest request) {
		request.getSession().removeAttribute(SessionName.LOGIN_ATTEMPT);
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String username = auth.getName();
		User user = userService.getUserByUsername(username);
		HttpSession session = request.getSession();
		session.setAttribute(SessionName.USER, user);

		setRememberMeTargetUrlToSession(request);
		model.addAttribute("CustomerNo", customerService.getCustomerList()
				.size());
		model.addAttribute("AccountNo", accountService.getSavingAccounts()
				.size());
		model.addAttribute("TransactionNo", transService.getTransactionList()
				.size());

		return ("home");
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accessDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			model.addObject("username", userDetail.getUsername());
		}

		model.setViewName("403");
		return model;

	}

	/**
	 * save targetURL in session
	 */
	private void setRememberMeTargetUrlToSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute("targetUrl", "/home");
		}
	}

	/**
	 * get targetURL from session
	 */
	private String getRememberMeTargetUrlFromSession(HttpServletRequest request) {
		String targetUrl = "";
		HttpSession session = request.getSession(false);
		if (session != null) {
			targetUrl = session.getAttribute("targetUrl") == null ? ""
					: session.getAttribute("targetUrl").toString();
		}
		return targetUrl;
	}

	/**
	 * Check if user is login by remember me cookie, refer
	 * org.springframework.security
	 * .authentication.AuthenticationTrustResolverImpl
	 */
	private boolean isRememberMeAuthenticated() {

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (authentication == null) {
			return false;
		}

		return RememberMeAuthenticationToken.class
				.isAssignableFrom(authentication.getClass());
	}
}
