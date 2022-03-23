package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


/**
 * The persistent class for the korisnik database table.
 * 
 */
@Entity
@NamedQuery(name="Korisnik.findAll", query="SELECT k FROM Korisnik k")
public class Korisnik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idKorisnik;

	private String adresa;

	private int brojGodina;

	private String ime;

	@Column(name="korisnicko_ime")
	private String korisnickoIme;

	private String prezime;

	private String sifra;

	//bi-directional many-to-one association to Clanskakarta
	@OneToMany(mappedBy="korisnik")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Clanskakarta> clanskakartas;

	//bi-directional many-to-one association to Komentar
	@OneToMany(mappedBy="korisnik")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Komentar> komentars;

	//bi-directional many-to-one association to Uloga
	@ManyToOne
	private Uloga uloga;

	//bi-directional many-to-many association to Knjizevnovece
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(
		name="korisnik_has_knjizevnovece"
		, joinColumns={
			@JoinColumn(name="Korisnik_idKorisnik")
			}
		, inverseJoinColumns={
			@JoinColumn(name="KnjizevnoVece_idKnjizevnoVece")
			}
		)
	private List<Knjizevnovece> knjizevnoveces;

	//bi-directional many-to-one association to Narudzbenica
	@OneToMany(mappedBy="korisnik")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Narudzbenica> narudzbenicas;

	//bi-directional many-to-one association to Ocena
	@OneToMany(mappedBy="korisnik")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Ocena> ocenas;

	//bi-directional many-to-many association to Knjiga
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(
		name="omiljeneknjige"
		, joinColumns={
			@JoinColumn(name="Korisnik_idKorisnik")
			}
		, inverseJoinColumns={
			@JoinColumn(name="Knjiga_idKnjiga")
			}
		)
	private List<Knjiga> knjigas;

	public Korisnik() {
		this.clanskakartas = new ArrayList<Clanskakarta>();
		this.komentars = new ArrayList<Komentar>();
		this.knjizevnoveces = new ArrayList<Knjizevnovece>();
		this.narudzbenicas = new ArrayList<Narudzbenica>();
		this.ocenas = new ArrayList<Ocena>();
		this.knjigas = new ArrayList<Knjiga>();
	}

	public int getIdKorisnik() {
		return this.idKorisnik;
	}

	public void setIdKorisnik(int idKorisnik) {
		this.idKorisnik = idKorisnik;
	}

	public String getAdresa() {
		return this.adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public int getBrojGodina() {
		return this.brojGodina;
	}

	public void setBrojGodina(int brojGodina) {
		this.brojGodina = brojGodina;
	}

	public String getIme() {
		return this.ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getKorisnickoIme() {
		return this.korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getPrezime() {
		return this.prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getSifra() {
		return this.sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public List<Clanskakarta> getClanskakartas() {
		return this.clanskakartas;
	}

	public void setClanskakartas(List<Clanskakarta> clanskakartas) {
		this.clanskakartas = clanskakartas;
	}

	public Clanskakarta addClanskakarta(Clanskakarta clanskakarta) {
		getClanskakartas().add(clanskakarta);
		clanskakarta.setKorisnik(this);

		return clanskakarta;
	}

	public Clanskakarta removeClanskakarta(Clanskakarta clanskakarta) {
		getClanskakartas().remove(clanskakarta);
		clanskakarta.setKorisnik(null);

		return clanskakarta;
	}

	public List<Komentar> getKomentars() {
		return this.komentars;
	}

	public void setKomentars(List<Komentar> komentars) {
		this.komentars = komentars;
	}

	public Komentar addKomentar(Komentar komentar) {
		getKomentars().add(komentar);
		komentar.setKorisnik(this);

		return komentar;
	}

	public Komentar removeKomentar(Komentar komentar) {
		getKomentars().remove(komentar);
		komentar.setKorisnik(null);

		return komentar;
	}

	public Uloga getUloga() {
		return this.uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

	public List<Knjizevnovece> getKnjizevnoveces() {
		return this.knjizevnoveces;
	}

	public void setKnjizevnoveces(List<Knjizevnovece> knjizevnoveces) {
		this.knjizevnoveces = knjizevnoveces;
	}

	public List<Narudzbenica> getNarudzbenicas() {
		return this.narudzbenicas;
	}

	public void setNarudzbenicas(List<Narudzbenica> narudzbenicas) {
		this.narudzbenicas = narudzbenicas;
	}

	public Narudzbenica addNarudzbenica(Narudzbenica narudzbenica) {
		getNarudzbenicas().add(narudzbenica);
		narudzbenica.setKorisnik(this);

		return narudzbenica;
	}

	public Narudzbenica removeNarudzbenica(Narudzbenica narudzbenica) {
		getNarudzbenicas().remove(narudzbenica);
		narudzbenica.setKorisnik(null);

		return narudzbenica;
	}

	public List<Ocena> getOcenas() {
		return this.ocenas;
	}

	public void setOcenas(List<Ocena> ocenas) {
		this.ocenas = ocenas;
	}

	public Ocena addOcena(Ocena ocena) {
		getOcenas().add(ocena);
		ocena.setKorisnik(this);

		return ocena;
	}

	public Ocena removeOcena(Ocena ocena) {
		getOcenas().remove(ocena);
		ocena.setKorisnik(null);

		return ocena;
	}

	public List<Knjiga> getKnjigas() {
		return this.knjigas;
	}

	public void setKnjigas(List<Knjiga> knjigas) {
		this.knjigas = knjigas;
	}

}