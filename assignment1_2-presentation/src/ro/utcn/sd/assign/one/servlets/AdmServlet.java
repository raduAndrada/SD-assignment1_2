package ro.utcn.sd.assign.one.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderStatus;
import com.google.code.geocoder.model.LatLng;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

import ro.utcn.sd.assign.one.business.AdmBusiness;
import ro.utcn.sd.assign.one.business.exceptions.BusinessException;
import ro.utcn.sd.assign.one.business.impl.AdmBusinessImpl;
import ro.utcn.sd.assign.one.entities.Ct;
import ro.utcn.sd.assign.one.entities.Flgt;

@WebServlet(name = "AdmServlet", urlPatterns = { "flights-users" }, loadOnStartup = 1)
public class AdmServlet extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	AdmBusiness admBusiness = new AdmBusinessImpl();

	private static final String ERROR_MESSAGE = "errorMsg";
	private static final String SUCCESS_MESSAGE = "successMsg";
	private static final String FLGT_LIST_VIEW = "flgt-list.jsp";
	private static final String FLGT_LIST = "list";
	private static final String FLGT = "flgt";

	private static final String FLGT_ID = "flgtId";
	private static final String FLGT_DELETE_ID = "deleteFlgtId";

	private static final String FLGT_DETAILS_VIEW = "flgt-detail.jsp";

	private static final String FLGT_NB = "flgtNb";
	private static final String APLN_TP = "aplnTp";
	private static final String DPRT_CT = "dprtCt";
	private static final String ARR_TM = "arrTm";
	private static final String NB_ST = "nbSt";

	private static final String FLGT_NB_WRONG = "invalidFlgtNb";
	private static final String APLN_TP_WRONG = "invalidAplnTp";
	private static final String DPRT_CT_WRONG = "invalidDprtCt";

	private static final String CT_NMS = "citiesNames";

	private static final String MODE = "mode";
	private static final String CREATE = "create";
	private static final String UPDATE = "update";

	private static final String REDIRECT_FLGT_LIST_URL = "http://localhost:8080/assignment1_2/flights-users";

	private static final String FLGT_VALID = "flgtValid";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processGet(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		processPost(request, response);

	}

	private void processGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String requestFlgtId = request.getParameter(FLGT_ID);
		final String requestFlgtNb = request.getParameter(FLGT_NB);
		final Long flgtId = requestFlgtId != null ? Long.valueOf(requestFlgtId) : null;
		final String mode = request.getParameter(MODE);
		if (mode != null && mode.equals(CREATE)) {
			request.setAttribute(MODE, CREATE);
			request.getRequestDispatcher(FLGT_DETAILS_VIEW).forward(request, response);
		} else if (flgtId == null) {
			final List<Flgt> flgtList = admBusiness.getAllFlgts();
			request.setAttribute(FLGT_LIST, flgtList);
			request.setAttribute(MODE, CREATE);
			request.getRequestDispatcher(FLGT_LIST_VIEW).forward(request, response);
		} else if (requestFlgtNb != null) {
			request.setAttribute(SUCCESS_MESSAGE,
					String.format("Flight with number: %s successfully added", requestFlgtNb));
			final List<Flgt> flgtList = admBusiness.getAllFlgts();
			request.setAttribute(FLGT_LIST, flgtList);
			request.setAttribute(MODE, CREATE);
			request.getRequestDispatcher(FLGT_LIST_VIEW).forward(request, response);
		} else if (flgtId != null) {
			final Flgt flgt = admBusiness.getFlgt(flgtId);
			request.setAttribute(FLGT, flgt);
			request.setAttribute(MODE, UPDATE);
			request.getRequestDispatcher(FLGT_DETAILS_VIEW).forward(request, response);
		}
	}

	private void processPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Flgt flgt = buildFlgtFromRequest(request);

		final String requestFlgtId = request.getParameter(FLGT_ID);
		final Long flgtId = requestFlgtId != null ? Long.valueOf(requestFlgtId) : null;

		final String requestFlgtDeleteId = request.getParameter(FLGT_DELETE_ID);
		final Long flgtDeleteId = requestFlgtDeleteId != null ? Long.valueOf(requestFlgtDeleteId) : null;

		if (flgt != null && flgtId != null) {
			try {
				request = validateFlgt(flgt, request);
				final boolean isValid = (boolean) request.getAttribute(FLGT_VALID);
				if (!isValid) {
					throw new BusinessException("The submitted flight doesn't meet the requirements");
				}
				admBusiness.updateFlgt(flgtId, flgt);
				request.setAttribute(MODE, UPDATE);
				request.setAttribute(FLGT, flgt);
				request.setAttribute(SUCCESS_MESSAGE,
						String.format("Flight with number: %s successfully updated", flgt.getFlgtNb()));
				request.getRequestDispatcher(FLGT_DETAILS_VIEW).forward(request, response);
			} catch (final Exception e) {
				request.setAttribute(ERROR_MESSAGE, e.getMessage());
				flgt = admBusiness.getFlgt(flgtId);
				request.setAttribute(FLGT, flgt);
				request.setAttribute(MODE, UPDATE);
				request.getRequestDispatcher(FLGT_DETAILS_VIEW).forward(request, response);
			}
		} else if (flgtDeleteId != null) {
			try {
				admBusiness.deleteFlgt(flgtDeleteId);
				request.setAttribute(SUCCESS_MESSAGE, "Flight with id: " + flgtDeleteId + "has been deleted");
				final List<Flgt> flgtList = admBusiness.getAllFlgts();
				request.setAttribute(FLGT_LIST, flgtList);
				request.getRequestDispatcher(FLGT_LIST_VIEW).forward(request, response);
			} catch (final Exception e) {
				request.setAttribute(ERROR_MESSAGE, e.getMessage());
				flgt = admBusiness.getFlgt(flgtId);
				request.setAttribute(FLGT, flgt);
				request.getRequestDispatcher(FLGT_DETAILS_VIEW).forward(request, response);
			}
		}

		else if (flgt != null) {
			try {
				request = validateFlgt(flgt, request);
				final boolean isValid = (boolean) request.getAttribute(FLGT_VALID);
				if (!isValid) {
					throw new BusinessException("The submitted flight doesn't meet the requirements");
				}
				final Flgt newFlgt = admBusiness.addNewFlgt(flgt);

				final Map<String, String> data = Maps.newHashMap();
				data.put("redirect", REDIRECT_FLGT_LIST_URL);
				data.put(FLGT_NB, newFlgt.getFlgtNb());
				final String json = new Gson().toJson(data);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			} catch (final Exception e) {
				request.setAttribute(ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(FLGT_DETAILS_VIEW).forward(request, response);
			}

		}

	}

	private Flgt buildFlgtFromRequest(HttpServletRequest request) {

		final Flgt flgt = new Flgt();
		final String ctNms = request.getParameter(CT_NMS);
		if (ctNms != null && !ctNms.isEmpty()) {
			final List<String> ctNmList = Arrays.asList(ctNms.split(","));
			final List<Ct> flgtCts = Lists.newArrayList();
			ctNmList.forEach(e -> {

				final double[] latAndLongForCt = getLatLngForAddr(e);
				final Ct ct = new Ct(e);
				ct.setLat(latAndLongForCt != null ? String.valueOf(latAndLongForCt[0]) : null);
				ct.setLngt(latAndLongForCt != null ? String.valueOf(latAndLongForCt[1]) : null);
				flgtCts.add(ct);
			});
			flgt.setCts(new HashSet<Ct>(flgtCts));
		}

		flgt.setAplnTp(request.getParameter(APLN_TP));
		flgt.setFlgtNb(request.getParameter(FLGT_NB));
		flgt.setDprtCt(request.getParameter(DPRT_CT));
		System.out.println(request.getParameter(ARR_TM));
		if (request.getParameter(ARR_TM) != null) {
			flgt.setArrTm(request.getParameter(ARR_TM));
		}
		final String nbStRequest = request.getParameter(NB_ST);
		if (nbStRequest != null) {
			Integer nbSt = null;
			try {
				nbSt = Integer.valueOf(nbStRequest);
			} catch (final NumberFormatException e) {
				nbSt = -1;
			}
			flgt.setNbSt(nbSt);
		}
		return flgt;
	}

	private static double[] getLatLngForAddr(String addr) {
		if (addr == null) {
			return null;
		}

		final Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest;
		GeocodeResponse geocoderResponse;
		geocoderRequest = new GeocoderRequestBuilder().setAddress(addr).setLanguage("en").getGeocoderRequest();
		geocoderResponse = geocoder.geocode(geocoderRequest);
		if (geocoderResponse != null) {
			if (geocoderResponse.getStatus() == GeocoderStatus.OK) {
				if (!geocoderResponse.getResults().isEmpty()) {
					final GeocoderResult geocoderResult = // Get the first result
							geocoderResponse.getResults().iterator().next();
					final double[] loc = new double[2];
					final LatLng ll = geocoderResult.getGeometry().getLocation();

					loc[0] = ll.getLat().doubleValue();
					loc[1] = ll.getLng().doubleValue();
					return loc;
				}
			}
		}
		return null;
	}

	private HttpServletRequest validateFlgt(Flgt flgt, HttpServletRequest request) {
		boolean flgtValid = true;
		if (flgt.getAplnTp().length() <= 3) {
			request.setAttribute(APLN_TP_WRONG, "Airplane type must contain at least 3 letters");
			flgtValid = false;
		}
		if (flgt.getDprtCt() == null) {
			request.setAttribute(DPRT_CT_WRONG, "Flight must have a departure city");
			flgtValid = false;
		}
		if (flgt.getNbSt() < 0) {
			request.setAttribute(FLGT_NB_WRONG, "Number of seats available in the flight must be greater than 0");
			flgtValid = false;
		}
		request.setAttribute(FLGT_VALID, flgtValid);
		return request;
	}

}