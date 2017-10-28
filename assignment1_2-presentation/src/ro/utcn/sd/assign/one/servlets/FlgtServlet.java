package ro.utcn.sd.assign.one.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ro.utcn.sd.assign.one.business.UsrBusiness;
import ro.utcn.sd.assign.one.business.impl.UsrBusinessImpl;
import ro.utcn.sd.assign.one.entities.Ct;
import ro.utcn.sd.assign.one.entities.Flgt;

@WebServlet(name = "FlgtServlet", urlPatterns = { "flights" }, loadOnStartup = 1)
public class FlgtServlet extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	UsrBusiness usrBusiness = new UsrBusinessImpl();

	private static final String FLGT_LIST_VIEW = "flgt-list.jsp";
	private static final String FLGT_LIST = "list";
	private static final String FLGT_ID = "flgtId";

	private static final String CT_LIST = "list";
	private static final String CT_LIST_VIEW = "ct-list.jsp";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processGet(request, response);
	}

	private void processGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String requestFlgtId = request.getParameter(FLGT_ID);
		final Long flgtId = requestFlgtId != null ? Long.valueOf(requestFlgtId) : null;

		if (flgtId == null) {
			final List<Flgt> flgtList = usrBusiness.getAllFlgts();
			request.setAttribute(FLGT_LIST, flgtList);
			request.getRequestDispatcher(FLGT_LIST_VIEW).forward(request, response);
		} else {
			final List<Ct> ctsForFlgt = usrBusiness.getCtsForFlgt(flgtId);
			request.setAttribute(CT_LIST, ctsForFlgt);
			request.getRequestDispatcher(CT_LIST_VIEW).forward(request, response);
		}
	}
}