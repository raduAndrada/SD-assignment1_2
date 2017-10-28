package ro.utcn.sd.assign.one.business.impl;

import java.util.List;
import java.util.UUID;

import ro.utcn.sd.assign.one.business.AdmBusiness;
import ro.utcn.sd.assign.one.business.exceptions.BusinessException;
import ro.utcn.sd.assign.one.dao.CtDAO;
import ro.utcn.sd.assign.one.dao.FlgtDAO;
import ro.utcn.sd.assign.one.dao.UsrDAO;
import ro.utcn.sd.assign.one.entities.Flgt;
import ro.utcn.sd.assign.one.entities.Usr;

public class AdmBusinessImpl implements AdmBusiness {
	private final UsrDAO usrDAO = new UsrDAO();
	private final FlgtDAO flgtDAO = new FlgtDAO();
	private final CtDAO ctDAO = new CtDAO();

	@Override
	public List<Usr> getAllUsrs() {
		return usrDAO.findUsrs();
	}

	@Override
	public Usr getUsrByEmail(String email) {
		return usrDAO.findUsr(email);
	}

	@Override
	public List<Flgt> getAllFlgts() {
		return flgtDAO.findFlgts();
	}

	@Override
	public Flgt getFlgt(Long flgtId) {
		return flgtDAO.findFlgt(flgtId);
	}

	@Override
	public Flgt deleteFlgt(Long flgtId) {
		if (!checkIfFlgtExists(flgtId)) {
			throw new BusinessException("The flight doesn't exist");
		}
		ctDAO.deleteCtByFlgtId(flgtId);
		return flgtDAO.deleteFlgt(flgtId);
	}

	@Override
	public Flgt addNewFlgt(Flgt newFlgt) {
		newFlgt.setFlgtNb(UUID.randomUUID().toString());
		final Flgt added = flgtDAO.addFlgt(newFlgt);
		if (added.getCts() != null) {
			added.getCts().forEach(e -> {
				e.setFlgt(added);
				ctDAO.addCt(e);
			});
		}
		return added;
	}

	@Override
	public Flgt updateFlgt(Long flgtId, Flgt updateFlgt) {
		if (!checkIfFlgtExists(flgtId)) {
			throw new BusinessException("The flight doesn't exist");
		}
		final Flgt flgt = flgtDAO.findFlgt(flgtId);
		flgt.setAplnTp(updateFlgt.getAplnTp());
		flgt.setArrTm(updateFlgt.getArrTm());
		flgt.setDprtCt(updateFlgt.getDprtCt());
		flgt.setCts(updateFlgt.getCts());

		return flgtDAO.updateFlgt(flgt);
	}

	@Override
	public Usr addNewUsr(Usr newUsr) {
		if (!checkEmail(newUsr.getEmail())) {
			throw new BusinessException("Email already taken");
		}

		return usrDAO.addUsr(newUsr);
	}

	@Override
	public Usr deleteUsr(String email) {
		return usrDAO.deleteUsr(email);
	}

	@Override
	public Usr updateUsr(String email, Usr updateUsr) {
		if (checkEmail(email)) {
			throw new BusinessException("Email doesn't exist");
		}
		final Usr usr = usrDAO.findUsr(email);
		usr.setAddr(updateUsr.getAddr());
		usr.setfNm(updateUsr.getfNm());
		usr.setlNm(updateUsr.getlNm());
		usr.setUsrFlgts(updateUsr.getUsrFlgts());
		usr.setTel(updateUsr.getTel());

		return usrDAO.updateUsr(usr);
	}

	private boolean checkIfFlgtExists(Long flgtId) {
		return flgtDAO.findFlgt(flgtId) != null ? true : false;
	}

	private boolean checkEmail(String email) {
		return usrDAO.findUsr(email) == null ? true : false;
	}

}
