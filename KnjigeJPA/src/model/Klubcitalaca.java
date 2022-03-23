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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the klubcitalaca database table.
 * 
 */
@Entity
@NamedQuery(name="Klubcitalaca.findAll", query="SELECT k FROM Klubcitalaca k")
public class Klubcitalaca implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idKlubCitalaca;

	@Temporal(TemporalType.DATE)
	private Date datumOsnivanja;

	private String nazivKluba;

	private String opis;

	//bi-directional many-to-one association to Clanskakarta
	@OneToMany(fetch=FetchType.EAGER, mappedBy="klubcitalaca")
	private List<Clanskakarta> clanskakartas;

	//bi-directional many-to-many association to Kategorija
	@ManyToMany
	@JoinTable(
		name="kategorija_has_klubcitalaca"
		, joinColumns={
			@JoinColumn(name="KlubCitalaca_idKlubCitalaca")
			}
		, inverseJoinColumns={
			@JoinColumn(name="Kategorija_idKategorija")
			}
		)
	private List<Kategorija> kategorijas;

	public Klubcitalaca() {
		this.clanskakartas = new ArrayList<Clanskakarta>();
		this.kategorijas= new ArrayList<Kategorija>();
	}

	public int getIdKlubCitalaca() {
		return this.idKlubCitalaca;
	}

	public void setIdKlubCitalaca(int idKlubCitalaca) {
		this.idKlubCitalaca = idKlubCitalaca;
	}

	public Date getDatumOsnivanja() {
		return this.datumOsnivanja;
	}

	public void setDatumOsnivanja(Date datumOsnivanja) {
		this.datumOsnivanja = datumOsnivanja;
	}

	public String getNazivKluba() {
		return this.nazivKluba;
	}

	public void setNazivKluba(String nazivKluba) {
		this.nazivKluba = nazivKluba;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public List<Clanskakarta> getClanskakartas() {
		return this.clanskakartas;
	}

	public void setClanskakartas(List<Clanskakarta> clanskakartas) {
		this.clanskakartas = clanskakartas;
	}

	public Clanskakarta addClanskakarta(Clanskakarta clanskakarta) {
		getClanskakartas().add(clanskakarta);
		clanskakarta.setKlubcitalaca(this);

		return clanskakarta;
	}

	public Clanskakarta removeClanskakarta(Clanskakarta clanskakarta) {
		getClanskakartas().remove(clanskakarta);
		clanskakarta.setKlubcitalaca(null);

		return clanskakarta;
	}

	public List<Kategorija> getKategorijas() {
		return this.kategorijas;
	}

	public void setKategorijas(List<Kategorija> kategorijas) {
		this.kategorijas = kategorijas;
	}

}