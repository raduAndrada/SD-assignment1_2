package ro.utcn.sd.assign.one.servlets.fileters;

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

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

	private ServletContext context;

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
		this.context.log("AuthenticationFilter initialized");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse res = (HttpServletResponse) response;

		final String uri = req.getRequestURI();
		this.context.log("Requested Resource::" + uri);

		final HttpSession session = req.getSession(false);

		if (session == null && !(uri.endsWith("jsp") || uri.endsWith("login"))) {
			this.context.log("Unauthorized access request");
			res.sendRedirect("login.jsp");
		} else {
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}

	}

	@Override
	public void destroy() {
		// close any resources here
	}

}