package csc.fresher.finalproject.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
import csc.fresher.finalproject.service.BankingService;

/**
 * This is a controller for home page when user first accesses the application
 * It also contains methods for Login page and some other pages
 * 
 * @author Nhat Nguyen
 *
 */

@Controller
public class HomeController {
	@Autowired
	BankingService bankingService;

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
		if (session.getAttribute("USER") != null) {
			model.setViewName("home");
			try {
				response.sendRedirect("home");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return model;
		}
		
		if (isBlocked(request)) {
			model.addObject("error",
					"You are temporarily blocked! Please try again later!");
			return model;
		}

		if (error != null) {
			model.addObject("error", "Invalid username and password!");

			String targetUrl = getRememberMeTargetUrlFromSession(request);
			if (StringUtils.hasText(targetUrl)) {
				model.addObject("targetUrl", targetUrl);
			}

			if (session.getAttribute("LOGIN_ATTEMPT") == null) {
				session.setAttribute("LOGIN_ATTEMPT", 1);
			} else {
				int attempt = Integer.parseInt(session.getAttribute(
						"LOGIN_ATTEMPT").toString());
				attempt += 1;
				session.setAttribute("LOGIN_ATTEMPT", attempt);

				if (attempt == 3) {
					model.addObject("error", "Too many invalid login attempts!");
					Cookie cookie = new Cookie("login_attempts", "3");
					cookie.setMaxAge(15);
					response.addCookie(cookie);
				}
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
		HttpSession session = request.getSession();
		
		if (isBlocked(request)) {
			model.addAttribute("error",
					"You are temporarily blocked! Please try again later");
			return "redirect:login";
		}

		request.getSession().removeAttribute("LOGIN_ATTEMPT");
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String username = auth.getName();
		User user = bankingService.getUserByUsername(username);

		session.setAttribute("USER", user);

		setRememberMeTargetUrlToSession(request);
		model.addAttribute("CustomerNo", bankingService.getCustomerList()
				.size());
		model.addAttribute("AccountNo", bankingService.getSavingAccounts()
				.size());
		model.addAttribute("TransactionNo", bankingService.getTransactionList()
				.size());

		return "home";
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

	public boolean isBlocked(HttpServletRequest request) {
		boolean blocked = false;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("login_attempts")) {
					blocked = true;
					break;
				}
			}
		}
		
		return blocked;
	}
}
