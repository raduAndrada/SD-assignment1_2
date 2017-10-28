package ro.utcn.sd.assign.one.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ro.utcn.sd.assign.one.business.AdmBusiness;
import ro.utcn.sd.assign.one.business.UsrBusiness;
import ro.utcn.sd.assign.one.business.impl.AdmBusinessImpl;
import ro.utcn.sd.assign.one.business.impl.UsrBusinessImpl;
import ro.utcn.sd.assign.one.entities.Flgt;
import ro.utcn.sd.assign.one.entities.Usr;

@WebServlet(name = "LoginServlet", urlPatterns = { "login" }, loadOnStartup = 1)
public class LoginServlet extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	UsrBusiness usrBusiness = new UsrBusinessImpl();
	AdmBusiness admBusiness = new AdmBusinessImpl();

	private static final String LOGIN_FAIL_ERROR_MESSAGE = "Invalid login";
	private static final String LOGIN_VIEW = "login.jsp";
	private static final String USR_LOGIN_VIEW = "flgt-list.jsp";
	private static final String ADM_LOGIN_VIEW = "usr-list.jsp";
	private static final String USR_EMAIL = "email";
	private static final String USR_PSWD = "password";

	private static final String MODE = "mode";

	private static final String CREATE = "create";
	private static final String FLGT_LIST = "list";
	private static final String USR_LIST = "list";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher(LOGIN_VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String email = request.getParameter(USR_EMAIL);

		final String pswd = request.getParameter(USR_PSWD);
		try {
			final Usr usr = usrBusiness.login(email, pswd);

			System.out.println("USERRRRRRRRRRRRRR" + usr.toString());
			if (!usr.isAdm()) {
				final Cookie usrName = new Cookie("user", email);
				usrName.setMaxAge(30 * 60);
				response.addCookie(usrName);
				final List<Flgt> flgtList = admBusiness.getAllFlgts();
				request.setAttribute(FLGT_LIST, flgtList);
				request.setAttribute(MODE, CREATE);
				request.getRequestDispatcher(USR_LOGIN_VIEW).forward(request, response);
			} else {
				final Cookie admName = new Cookie("admin", email);
				admName.setMaxAge(30 * 60);
				response.addCookie(admName);
				final List<Usr> usrList = admBusiness.getAllUsrs();
				request.setAttribute(MODE, CREATE);
				request.setAttribute(USR_LIST, usrList);
				request.getRequestDispatcher(ADM_LOGIN_VIEW).forward(request, response);
			}
		} catch (final Exception e) {
			request.setAttribute("errorMsg", LOGIN_FAIL_ERROR_MESSAGE);
			request.getRequestDispatcher(LOGIN_VIEW).forward(request, response);

		}

	}
}