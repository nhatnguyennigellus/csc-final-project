package csc.fresher.finalproject.mycookies;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This class handles Java servlet filter
 * 
 * @author Hiep Nguyen
 *
 */
@WebFilter("/*")
public class FilterAuthor implements Filter {
	private ServletContext context;

	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
		this.context.log("AuthenticationFilter initialized");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);
		String uri = req.getRequestURI();
		
		if (session == null
				&& !(uri.endsWith("index.jsp") || uri.endsWith("register.html") || uri
						.endsWith("submitRegister.html"))) {
			res.sendRedirect("index.jsp");
		} else {
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}

	}

	public void destroy() {
		// close any resources here
	}
}
