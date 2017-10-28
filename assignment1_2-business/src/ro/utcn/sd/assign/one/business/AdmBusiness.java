package ro.utcn.sd.assign.one.business;

import java.util.List;

import ro.utcn.sd.assign.one.entities.Flgt;
import ro.utcn.sd.assign.one.entities.Usr;

public interface AdmBusiness {

	public Usr getUsrByEmail(String email);

	public List<Usr> getAllUsrs();

	public Usr addNewUsr(Usr newUsr);

	public Usr deleteUsr(String email);

	public Usr updateUsr(String email, Usr updateUsr);

	public List<Flgt> getAllFlgts();

	public Flgt getFlgt(Long flgtId);

	public Flgt deleteFlgt(Long flgtId);

	public Flgt addNewFlgt(Flgt newFlgt);

	public Flgt updateFlgt(Long flgtId, Flgt updateFlgt);

}
