package ro.utcn.sd.assign.one.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

@Entity
@Table(name = "FLGT")
public class Flgt {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "flgt_nb")
	private String flgtNb;

	@Column(name = "apln_tp")
	private String aplnTp;

	@Column(name = "dprt_ct")
	private String dprtCt;

	@Column(name = "arr_tm")
	private String arrTm;

	@Column(name = "nb_st")
	private Integer nbSt;

	@OneToMany(mappedBy = "flgt", fetch = FetchType.LAZY)
	private Set<UsrFlgt> usrFlgts;

	@OneToMany(mappedBy = "flgt", fetch = FetchType.LAZY)
	private Set<Ct> cts;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAplnTp() {
		return aplnTp;
	}

	public void setAplnTp(String aplnTp) {
		this.aplnTp = aplnTp;
	}

	public String getDprtCt() {
		return dprtCt;
	}

	public void setDprtCt(String dprtCt) {
		this.dprtCt = dprtCt;
	}

	public String getArrTm() {
		return arrTm;
	}

	public void setArrTm(String arrTm) {
		this.arrTm = arrTm;
	}

	public Integer getNbSt() {
		return nbSt;
	}

	public void setNbSt(Integer nbSt) {
		this.nbSt = nbSt;
	}

	public String getFlgtNb() {
		return flgtNb;
	}

	public void setFlgtNb(String flgtNb) {
		this.flgtNb = flgtNb;
	}

	public Set<UsrFlgt> getUsrFlgts() {
		return usrFlgts;
	}

	public void setUsrFlgts(Set<UsrFlgt> usrFlgts) {
		this.usrFlgts = usrFlgts;
	}

	public Set<Ct> getCts() {
		return cts;
	}

	public void setCts(Set<Ct> cts) {
		this.cts = cts;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper("").add("flgtNb", flgtNb).add("id", id).add("nbSt", nbSt)
				.add("cts", cts.toString()).add("arpnTp", aplnTp).add("arrTm", arrTm).add("arrTm", arrTm).toString();
	}

}
