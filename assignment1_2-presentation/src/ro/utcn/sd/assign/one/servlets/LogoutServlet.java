package ro.utcn.sd.assign.one.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LogoutServlet", urlPatterns = { "logout" }, loadOnStartup = 1)
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		final Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (final Cookie cookie : cookies) {
				if (cookie.getName().equals("JSESSIONID")) {
					System.out.println("JSESSIONID=" + cookie.getValue());
					break;
				}
			}
		}
		// invalidate the session if exists
		final HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		eraseCookie(request, response);
		response.sendRedirect("login.jsp");
	}

	private void eraseCookie(HttpServletRequest req, HttpServletResponse resp) {
		final Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (final Cookie cookie : cookies) {
				cookie.setValue("");
				cookie.setPath("/");
				cookie.setMaxAge(0);
				resp.addCookie(cookie);
			}
		}
	}
}