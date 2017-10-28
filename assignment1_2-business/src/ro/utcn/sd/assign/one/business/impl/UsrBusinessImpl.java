package ro.utcn.sd.assign.one.business.impl;

import java.util.List;

import com.google.common.collect.Lists;

import ro.utcn.sd.assign.one.business.UsrBusiness;
import ro.utcn.sd.assign.one.business.exceptions.BusinessException;
import ro.utcn.sd.assign.one.dao.CtDAO;
import ro.utcn.sd.assign.one.dao.FlgtDAO;
import ro.utcn.sd.assign.one.dao.UsrDAO;
import ro.utcn.sd.assign.one.dao.UsrFlgtDAO;
import ro.utcn.sd.assign.one.entities.Ct;
import ro.utcn.sd.assign.one.entities.Flgt;
import ro.utcn.sd.assign.one.entities.Usr;
import ro.utcn.sd.assign.one.entities.UsrFlgt;

public class UsrBusinessImpl implements UsrBusiness {

	private final UsrDAO usrDAO = new UsrDAO();
	private final UsrFlgtDAO usrFlgtDAO = new UsrFlgtDAO();
	private final FlgtDAO flgtDAO = new FlgtDAO();
	private final CtDAO ctDAO = new CtDAO();

	@Override
	public boolean isAdm(String email) {
		if (!checkIfUsrExists(email)) {
			throw new BusinessException("Invalid email");
		}
		return usrDAO.findUsr(email).isAdm();
	}

	@Override
	public Usr login(String email, String pswd) {
		if (!checkIfUsrExists(email)) {
			throw new BusinessException("Invalid email");
		}
		final Usr usr = usrDAO.findUsr(email);
		if (!sameProperty(pswd, usr.getPswd())) {
			throw new BusinessException("Invalid login");
		}
		return usr;
	}

	@Override
	public List<Flgt> getAllFlgts() {
		return flgtDAO.findFlgts();
	}

	@Override
	public List<Flgt> getAllFlgtsForUsr(String email) {
		final Usr usr = usrDAO.findUsr(email);
		final List<UsrFlgt> usrFlgts = usrFlgtDAO.findUsrFlgtsByUsrId(usr.getId());
		final List<Flgt> flgts = Lists.newArrayList();
		usrFlgts.forEach(f -> flgts.add(flgtDAO.findFlgt(f.getFlgt().getId())));
		return flgts;
	}

	@Override
	public UsrFlgt addNewFlgtForUsr(String email, Long flgtId) {
		final Usr usr = usrDAO.findUsr(email);
		final Flgt flgt = flgtDAO.findFlgt(flgtId);
		final UsrFlgt usrFlgt = new UsrFlgt();
		usrFlgt.setUsr(usr);
		usrFlgt.setFlgt(flgt);
		usrFlgtDAO.addUsrFlgt(usrFlgt);
		return usrFlgt;
	}

	@Override
	public UsrFlgt deleteFlgtForUsr(String email, Long flgtId) {
		final Usr usr = usrDAO.findUsr(email);
		return usrFlgtDAO.deleteUsrFlgtByUsrIdAndFlgtId(usr.getId(), flgtId);
	}

	@Override
	public List<Ct> getCtsForFlgt(Long flgtId) {
		return ctDAO.findCtByFlgtId(flgtId);
	}

	private boolean checkIfUsrExists(String email) {
		return usrDAO.findUsr(email) != null ? true : false;
	}

	private boolean sameProperty(final String newProp, final String dbProp) {
		if (newProp == null) {
			return true;
		}
		if (newProp.equals(dbProp)) {
			return true;
		}
		return false;
	}

}
