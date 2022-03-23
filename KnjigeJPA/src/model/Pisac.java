package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


/**
 * The persistent class for the pisac database table.
 * 
 */
@Entity
@NamedQuery(name="Pisac.findAll", query="SELECT p FROM Pisac p")
public class Pisac implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPisac;

	@Temporal(TemporalType.DATE)
	private Date datumRodjenja;

	private String ime;

	private String prezime;

	//bi-directional many-to-one association to Knjiga
	@OneToMany(mappedBy="pisac")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Knjiga> knjigas;

	public Pisac() {
		this.knjigas = new ArrayList<Knjiga>();
	}

	public int getIdPisac() {
		return this.idPisac;
	}

	public void setIdPisac(int idPisac) {
		this.idPisac = idPisac;
	}

	public Date getDatumRodjenja() {
		return this.datumRodjenja;
	}

	public void setDatumRodjenja(Date datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public String getIme() {
		return this.ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return this.prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public List<Knjiga> getKnjigas() {
		return this.knjigas;
	}

	public void setKnjigas(List<Knjiga> knjigas) {
		this.knjigas = knjigas;
	}

	public Knjiga addKnjiga(Knjiga knjiga) {
		getKnjigas().add(knjiga);
		knjiga.setPisac(this);

		return knjiga;
	}

	public Knjiga removeKnjiga(Knjiga knjiga) {
		getKnjigas().remove(knjiga);
		knjiga.setPisac(null);

		return knjiga;
	}
	
	@Override
	public String toString() {
		return ime+" "+prezime;
	}

}