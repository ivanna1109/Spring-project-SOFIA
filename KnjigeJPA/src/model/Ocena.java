package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ocena database table.
 * 
 */
@Entity
@NamedQuery(name="Ocena.findAll", query="SELECT o FROM Ocena o")
public class Ocena implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idOcena;

	private int vrednost;

	//bi-directional many-to-one association to Knjiga
	@ManyToOne
	private Knjiga knjiga;

	//bi-directional many-to-one association to Korisnik
	@ManyToOne
	private Korisnik korisnik;

	public Ocena() {
	}

	public int getIdOcena() {
		return this.idOcena;
	}

	public void setIdOcena(int idOcena) {
		this.idOcena = idOcena;
	}

	public int getVrednost() {
		return this.vrednost;
	}

	public void setVrednost(int vrednost) {
		this.vrednost = vrednost;
	}

	public Knjiga getKnjiga() {
		return this.knjiga;
	}

	public void setKnjiga(Knjiga knjiga) {
		this.knjiga = knjiga;
	}

	public Korisnik getKorisnik() {
		return this.korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

}