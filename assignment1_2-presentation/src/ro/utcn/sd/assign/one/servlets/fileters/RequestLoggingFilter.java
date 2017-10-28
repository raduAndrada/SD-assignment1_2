package ro.utcn.sd.assign.one.servlets.fileters;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class RequestLoggingFilter
 */
@WebFilter("/*")
public class RequestLoggingFilter implements Filter {

	private ServletContext context;

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
		this.context.log("RequestLoggingFilter initialized");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest req = (HttpServletRequest) request;
		final Enumeration<String> params = req.getParameterNames();
		while (params.hasMoreElements()) {
			final String name = params.nextElement();
			final String value = request.getParameter(name);
			this.context.log(req.getRemoteAddr() + "::Request Params::{" + name + "=" + value + "}");
		}

		final Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (final Cookie cookie : cookies) {
				this.context
						.log(req.getRemoteAddr() + "::Cookie::{" + cookie.getName() + "," + cookie.getValue() + "}");
			}
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// we can close resources here
	}

}