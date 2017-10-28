package ro.utcn.sd.assign.one.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

@Entity
@Table(name = "USR")
public class Usr {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "f_nm")
	private String fNm;

	@Column(name = "l_nm")
	private String lNm;

	@Column(name = "addr")
	private String addr;

	@Column(name = "email")
	private String email;

	@Column(name = "tel")
	private String tel;

	@Column(name = "ser_id")
	private String serId;

	@Column(name = "adm")
	private boolean adm;

	@Column(name = "pswd")
	private String pswd;

	@OneToMany(mappedBy = "usr", cascade = CascadeType.ALL)
	private Set<UsrFlgt> usrFlgts;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getfNm() {
		return fNm;
	}

	public void setfNm(String fNm) {
		this.fNm = fNm;
	}

	public String getlNm() {
		return lNm;
	}

	public void setlNm(String lNm) {
		this.lNm = lNm;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getSerId() {
		return serId;
	}

	public void setSerId(String ser_id) {
		this.serId = ser_id;
	}

	public boolean isAdm() {
		return adm;
	}

	public void setAdm(boolean adm) {
		this.adm = adm;
	}

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	public Set<UsrFlgt> getUsrFlgts() {
		return usrFlgts;
	}

	public void setUsrFlgts(Set<UsrFlgt> usrFlgts) {
		this.usrFlgts = usrFlgts;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper("").add("fNm", fNm).add("id", id).add("lNm", lNm).add("email", email)
				.add("tel", tel).add("serId", serId).add("addr", addr).add("adm", adm).toString();
	}
}
