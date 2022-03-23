package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the clanskakarta database table.
 * 
 */
@Entity
@NamedQuery(name="Clanskakarta.findAll", query="SELECT c FROM Clanskakarta c")
public class Clanskakarta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idClanskaKarta;

	private int clanarina;

	@Temporal(TemporalType.DATE)
	private Date datumIzdavanja;

	//bi-directional many-to-one association to Klubcitalaca
	@ManyToOne
	private Klubcitalaca klubcitalaca;

	//bi-directional many-to-one association to Korisnik
	@ManyToOne
	private Korisnik korisnik;

	public Clanskakarta() {
	}

	public int getIdClanskaKarta() {
		return this.idClanskaKarta;
	}

	public void setIdClanskaKarta(int idClanskaKarta) {
		this.idClanskaKarta = idClanskaKarta;
	}

	public int getClanarina() {
		return this.clanarina;
	}

	public void setClanarina(int clanarina) {
		this.clanarina = clanarina;
	}

	public Date getDatumIzdavanja() {
		return this.datumIzdavanja;
	}

	public void setDatumIzdavanja(Date datumIzdavanja) {
		this.datumIzdavanja = datumIzdavanja;
	}

	public Klubcitalaca getKlubcitalaca() {
		return this.klubcitalaca;
	}

	public void setKlubcitalaca(Klubcitalaca klubcitalaca) {
		this.klubcitalaca = klubcitalaca;
	}

	public Korisnik getKorisnik() {
		return this.korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

}