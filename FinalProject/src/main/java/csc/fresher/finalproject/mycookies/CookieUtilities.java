package csc.fresher.finalproject.mycookies;

import javax.servlet.http.*;

/**
 * This class defines methods to get cookies and cookies values from this web
 * application
 * 
 * @author Hiep Nguyen
 *
 */
public class CookieUtilities {

	public static String getCookieValue(HttpServletRequest request,
			String cookieName, String defaultValue) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {
					return (cookie.getValue());
				}
			}
		}
		return (defaultValue);
	}

	public static Cookie getCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {
					return (cookie);
				}
			}
		}
		return (null);
	}
}
