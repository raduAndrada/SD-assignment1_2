package ro.utcn.sd.assign.one.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

@Entity
@Table(name = "Ct")
public class Ct {
	public Ct(String nm) {
		this.nm = nm;
	}

	public Ct() {
	}

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "lat")
	private String lat;

	@Column(name = "lngt")
	private String lngt;

	@Column(name = "nm")
	private String nm;

	@ManyToOne
	@JoinColumn(name = "flgt_id")
	private Flgt flgt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLngt() {
		return lngt;
	}

	public void setLngt(String lngt) {
		this.lngt = lngt;
	}

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public Flgt getFlgt() {
		return flgt;
	}

	public void setFlgt(Flgt flgt) {
		this.flgt = flgt;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper("").add("nm", nm).add("id", id).add("lat", lat).add("lngt", lngt)
				.add("flgt", flgt.toString()).toString();
	}
}
