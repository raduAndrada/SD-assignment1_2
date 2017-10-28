package ro.utcn.sd.assign.one.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Usr_Flgt")
public class UsrFlgt {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "usr_id")
	private Usr usr;

	@ManyToOne
	@JoinColumn(name = "flgt_id")
	private Flgt flgt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usr getUsr() {
		return usr;
	}

	public void setUsr(Usr usr) {
		this.usr = usr;
	}

	public Flgt getFlgt() {
		return flgt;
	}

	public void setFlgt(Flgt flgt) {
		this.flgt = flgt;
	}

}
