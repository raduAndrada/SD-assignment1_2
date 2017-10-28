package ro.utcn.sd.assign.one.business;

import java.util.List;

import ro.utcn.sd.assign.one.entities.Ct;
import ro.utcn.sd.assign.one.entities.Flgt;
import ro.utcn.sd.assign.one.entities.Usr;
import ro.utcn.sd.assign.one.entities.UsrFlgt;

public interface UsrBusiness {

	public boolean isAdm(String email);

	public Usr login(String email, String pswd);

	public List<Flgt> getAllFlgts();

	public List<Flgt> getAllFlgtsForUsr(String email);

	public UsrFlgt addNewFlgtForUsr(String email, Long flgtId);

	public UsrFlgt deleteFlgtForUsr(String email, Long flgtId);

	public List<Ct> getCtsForFlgt(Long flgtId);
}
