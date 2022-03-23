package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the knjizevnovece database table.
 * 
 */
@Entity
@NamedQuery(name="Knjizevnovece.findAll", query="SELECT k FROM Knjizevnovece k")
public class Knjizevnovece implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idKnjizevnoVece;

	@Temporal(TemporalType.DATE)
	private Date datum;

	private int maxPosetilaca;

	private String mesto;

	//bi-directional many-to-one association to Knjiga
	@ManyToOne
	private Knjiga knjiga;

	//bi-directional many-to-many association to Korisnik
	@ManyToMany(fetch=FetchType.EAGER, mappedBy="knjizevnoveces")
	private List<Korisnik> korisniks;

	public Knjizevnovece() {
		this.korisniks = new ArrayList<Korisnik>();
	}

	public int getIdKnjizevnoVece() {
		return this.idKnjizevnoVece;
	}

	public void setIdKnjizevnoVece(int idKnjizevnoVece) {
		this.idKnjizevnoVece = idKnjizevnoVece;
	}

	public Date getDatum() {
		return this.datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public int getMaxPosetilaca() {
		return this.maxPosetilaca;
	}

	public void setMaxPosetilaca(int maxPosetilaca) {
		this.maxPosetilaca = maxPosetilaca;
	}

	public String getMesto() {
		return this.mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public Knjiga getKnjiga() {
		return this.knjiga;
	}

	public void setKnjiga(Knjiga knjiga) {
		this.knjiga = knjiga;
	}

	public List<Korisnik> getKorisniks() {
		return this.korisniks;
	}

	public void setKorisniks(List<Korisnik> korisniks) {
		this.korisniks = korisniks;
	}

}