package csc.fresher.finalproject.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import csc.fresher.finalproject.domain.User;
import csc.fresher.finalproject.mycookies.SessionName;
import csc.fresher.finalproject.service.UserService;

/**
 * This is a controller for home page when user first accesses the application
 * 
 * @author nvu3, Hiep Nguyen, Nhat Nguyen
 *
 */

@Controller
public class HomeController {
	private UserService userService = new UserService();

	/**
	 * Redirects to Login Page
	 * 
	 * @param model
	 * @return Login Page
	 */
	@RequestMapping(value = "/login")
	public String redirectLogin(Model model) {
		return ("login");
	}

	/**
	 * Logout customer account and redirects to Login Page
	 * 
	 * @param request
	 * @param response
	 * @return Login View
	 */
	@RequestMapping(value = "/logout")
	public ModelAndView redirectLogout(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}
	
	
	
	/**
	 * Redirects to Home Page
	 * 
	 * @param model
	 * @return Home Page
	 */
	@RequestMapping(value = "/home")
	public String redirectHome(Model model) {
		return ("home");
	}
	
	/**
	 * Submits Login Form and redirects to Home Page if login succeeds or back
	 * to Login Page if fails
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return Login Page or Home Page
	 */
	@RequestMapping(value = "/submitLogin")
	public String submitLogin(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session.getAttribute(SessionName.USERNAME) == null) {
			String username = request.getParameter("txtUsername");
			if (!this.userService.checkUserActive(username)) {
				model.addAttribute("eNotify", "This username was deactivated!");
				return ("login");
			}
			String password = request.getParameter("txtPassword");
			String isRememberPass = request.getParameter("cbRemember");
			if (isRememberPass != null && isRememberPass.equals("yes")) {
				Cookie useNameCookie = new Cookie(SessionName.USERNAME,
						request.getParameter("txtUsername"));
				useNameCookie.setMaxAge(3600);
				response.addCookie(useNameCookie);

				Cookie passwordCookie = new Cookie(SessionName.PASSWORD,
						request.getParameter("txtPassword"));
				passwordCookie.setMaxAge(3600);
				response.addCookie(passwordCookie);
			}
			User user = this.userService.checkUserAuthentication(username, password);
			if (user != null) {
				session.setAttribute(SessionName.USER, user);
				
				return ("home");
			}
			model.addAttribute("eNotify", "Invalid Username or Password");
			return ("login");
		} else {
			return ("home");
		}

	}

	
}
