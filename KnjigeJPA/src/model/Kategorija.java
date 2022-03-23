package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


/**
 * The persistent class for the kategorija database table.
 * 
 */
@Entity
@NamedQuery(name="Kategorija.findAll", query="SELECT k FROM Kategorija k")
public class Kategorija implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idKategorija;

	private String nazivKategorije;

	private String opis;

	//bi-directional many-to-many association to Klubcitalaca
	@ManyToMany(mappedBy="kategorijas")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Klubcitalaca> klubcitalacas;

	//bi-directional many-to-many association to Knjiga
	@ManyToMany(mappedBy="kategorijas")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Knjiga> knjigas;

	public Kategorija() {
		this.klubcitalacas = new ArrayList<Klubcitalaca>();
		this.knjigas = new ArrayList<Knjiga>();
	}

	public int getIdKategorija() {
		return this.idKategorija;
	}

	public void setIdKategorija(int idKategorija) {
		this.idKategorija = idKategorija;
	}

	public String getNazivKategorije() {
		return this.nazivKategorije;
	}

	public void setNazivKategorije(String nazivKategorije) {
		this.nazivKategorije = nazivKategorije;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public List<Klubcitalaca> getKlubcitalacas() {
		return this.klubcitalacas;
	}

	public void setKlubcitalacas(List<Klubcitalaca> klubcitalacas) {
		this.klubcitalacas = klubcitalacas;
	}

	public List<Knjiga> getKnjigas() {
		return this.knjigas;
	}

	public void setKnjigas(List<Knjiga> knjigas) {
		this.knjigas = knjigas;
	}

}