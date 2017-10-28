package ro.utcn.sd.assign.one.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ro.utcn.sd.assign.one.business.AdmBusiness;
import ro.utcn.sd.assign.one.business.exceptions.BusinessException;
import ro.utcn.sd.assign.one.business.impl.AdmBusinessImpl;
import ro.utcn.sd.assign.one.entities.Usr;

/**
 * Servlet for the adm usr editing all other usr's
 *
 * @author Andrada
 *
 */
@WebServlet(name = "UsrServlet", urlPatterns = { "users" }, loadOnStartup = 1)
public class UsrServlet extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	AdmBusiness admBusiness = new AdmBusinessImpl();

	private static final String ERROR_MESSAGE = "errorMsg";
	private static final String SUCCESS_MESSAGE = "successMsg";
	private static final String USR_LIST = "list";
	private static final String USR_EMAIL = "usrEmail";
	private static final String USR_LIST_VIEW = "usr-list.jsp";
	private static final String USR_DETAILS_VIEW = "usr-detail.jsp";
	private static final String USR = "usr";

	private static final String ADDR = "addr";
	private static final String F_NM = "fNm";
	private static final String L_NM = "lNm";
	private static final String TEL = "tel";
	private static final String SER_ID = "serId";
	private static final String EMAIL = "email";
	private static final String ADM = "adm";

	private static final String ADDR_WRONG = "invalidAddr";
	private static final String F_NM_WRONG = "invalidFNm";
	private static final String L_NM_WRONG = "invalidLNm";
	private static final String TEL_WRONG = "invalidTel";
	private static final String SER_ID_WRONG = "invalidSerId";
	private static final String EMAIL_WRONG = "invalidEmail";

	private static final String USR_DELETE_EMAIL = "deleteUsrEmail";

	private static final String MODE = "mode";

	private static final String UPDATE = "update";
	private static final String CREATE = "create";

	private static final String USR_VALID = "usrValid";

	/**
	 * The get method signature for this servlet
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processGet(request, response);
	}

	/**
	 * Post method for the servlet
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		processPost(request, response);

	}

	/**
	 * multiple get options: - get the page for create usr, update and delete and
	 * get the usr list
	 */
	private void processGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// this is a param with the usr's email
		final String usrEmail = request.getParameter(USR_EMAIL);

		// the mode the adm wants to open the usr's page
		final String mode = request.getParameter(MODE);
		if (mode != null && mode.equals(CREATE)) {
			request.setAttribute(MODE, CREATE);
			request.getRequestDispatcher(USR_DETAILS_VIEW).forward(request, response);
		} else if (mode == null && usrEmail != null) {
			final Usr usr = admBusiness.getUsrByEmail(usrEmail);
			request.setAttribute(MODE, UPDATE);
			request.setAttribute(USR, usr);
			request.getRequestDispatcher(USR_DETAILS_VIEW).forward(request, response);
		}
		// if both those params are missing open the usrList view
		else {
			final List<Usr> usrList = admBusiness.getAllUsrs();
			request.setAttribute(MODE, CREATE);
			request.setAttribute(USR_LIST, usrList);
			request.getRequestDispatcher(USR_LIST_VIEW).forward(request, response);
		}
	}

	/**
	 * Multiple post options: add, update or delete usr
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// build the usr from the form in case of update or create
		Usr usr = buildUsrFromRequest(request);

		// get unique identifier email in case of update
		final String usrEmail = request.getParameter(USR_EMAIL);

		// get unique identifir in case of delete
		final String usrDeleteEmail = request.getParameter(USR_DELETE_EMAIL);

		// if we got the usrEmail param then we need to update the usr and the view
		if (usrEmail != null && !usrEmail.isEmpty() && usr != null) {
			try {
				request = validateUsr(usr, request);
				final boolean isValid = (boolean) request.getAttribute(USR_VALID);
				if (!isValid) {
					throw new BusinessException("Invalid data for user");
				}
				admBusiness.updateUsr(usrEmail, usr);
				request.setAttribute(MODE, UPDATE);
				usr.setEmail(usrEmail);
				request.setAttribute(USR, usr);
				request.setAttribute(SUCCESS_MESSAGE,
						String.format("User with email: %s successfully updated", usrEmail));
				request.getRequestDispatcher(USR_DETAILS_VIEW).forward(request, response);
			} catch (final Exception e) {
				request.setAttribute(ERROR_MESSAGE, e.getMessage());
				usr = admBusiness.getUsrByEmail(usrEmail);
				usr.setEmail(usrEmail);
				request.setAttribute(USR, usr);
				request.setAttribute(MODE, UPDATE);
				request.getRequestDispatcher(USR_DETAILS_VIEW).forward(request, response);
			}

		}
		// if the usrDelete comes back we need to delete the selected usr and update the
		// view
		else if (usrDeleteEmail != null) {
			try {
				admBusiness.deleteUsr(usrDeleteEmail);
				request.setAttribute(SUCCESS_MESSAGE, "User with email: " + usrDeleteEmail + "has been deleted");
				final List<Usr> usrList = admBusiness.getAllUsrs();
				request.setAttribute(MODE, CREATE);
				request.setAttribute(USR_LIST, usrList);
				request.getRequestDispatcher(USR_LIST_VIEW).forward(request, response);
			} catch (final Exception e) {
				request.setAttribute(ERROR_MESSAGE, e.getMessage());
				usr = admBusiness.getUsrByEmail(usrEmail);
				request.setAttribute(USR, usr);
				request.getRequestDispatcher(USR_DETAILS_VIEW).forward(request, response);
			}
		}
		// if we didn't get usrEmail but usrEmail is not null we have to add a new usr
		else if (usr.getEmail() != null) {
			try {
				request = validateUsr(usr, request);
				final boolean isValid = (boolean) request.getAttribute(USR_VALID);
				if (!isValid) {
					throw new BusinessException("Invalid data for user");
				}
				usr.setPswd(usr.getfNm());
				final Usr addedUsr = admBusiness.addNewUsr(usr);
				request.setAttribute(SUCCESS_MESSAGE,
						String.format("User with email: %s successfully added", addedUsr.getEmail()));
				final List<Usr> usrList = admBusiness.getAllUsrs();
				request.setAttribute(MODE, CREATE);
				request.setAttribute(USR_LIST, usrList);
				request.getRequestDispatcher(USR_LIST_VIEW).forward(request, response);
			} catch (final Exception e) {
				request.setAttribute(ERROR_MESSAGE, e.getMessage());
				request.setAttribute(MODE, CREATE);
				request.getRequestDispatcher(USR_DETAILS_VIEW).forward(request, response);
			}
		}

	}

	private Usr buildUsrFromRequest(HttpServletRequest request) {
		final Usr usr = new Usr();
		usr.setAddr(request.getParameter(ADDR));
		usr.setfNm(request.getParameter(F_NM));
		usr.setlNm(request.getParameter(L_NM));
		usr.setEmail(request.getParameter(EMAIL));
		usr.setTel(request.getParameter(TEL));
		usr.setSerId(request.getParameter(SER_ID));
		usr.setAdm(request.getParameter(ADM) != null && request.getParameter(ADM).equals("on"));
		return usr;
	}

	private HttpServletRequest validateUsr(Usr usr, HttpServletRequest request) {
		boolean usrValid = true;
		if (usr.getfNm().length() <= 3) {
			request.setAttribute(F_NM_WRONG, "First name must contain at least 3 letters");
			usrValid = false;
		}
		if (usr.getlNm().length() <= 3) {
			request.setAttribute(L_NM_WRONG, "Last name must contain at least 3 letters");
			usrValid = false;
		}
		if (usr.getTel().length() != 10) {
			request.setAttribute(TEL_WRONG, "Phone numbers must contain 10 characters");
			usrValid = false;
		}
		if (usr.getSerId().length() != 8) {
			request.setAttribute(SER_ID_WRONG, "Id series have 8 characters");
			usrValid = false;
		}
		if (!usr.getEmail().contains("@") && !usr.getEmail().contains(".")) {
			request.setAttribute(EMAIL_WRONG, "The email address is not valid");
			usrValid = false;
		}
		if (usr.getAddr().length() < 3) {
			request.setAttribute(ADDR_WRONG, "Address field must have at least 3 characters");
			usrValid = false;
		}
		request.setAttribute(USR_VALID, usrValid);
		return request;
	}

}