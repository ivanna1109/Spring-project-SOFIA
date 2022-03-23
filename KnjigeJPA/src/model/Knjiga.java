package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


/**
 * The persistent class for the knjiga database table.
 * 
 */
@Entity
@NamedQuery(name="Knjiga.findAll", query="SELECT k FROM Knjiga k")
public class Knjiga implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idKnjiga;

	private int brojKnjigaLager;

	private int cena;

	private int godinaIzdanja;

	private String izdavac;

	private String kratakOpis;

	private String naziv;

	@Lob
	private byte[] slika;

	//bi-directional many-to-one association to Pisac
	@ManyToOne
	private Pisac pisac;

	//bi-directional many-to-many association to Kategorija
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(
		name="knjiga_has_kategorija"
		, joinColumns={
			@JoinColumn(name="Knjiga_idKnjiga")
			}
		, inverseJoinColumns={
			@JoinColumn(name="Kategorija_idKategorija")
			}
		)
	private List<Kategorija> kategorijas;

	//bi-directional many-to-one association to Knjizevnovece
	@OneToMany(mappedBy="knjiga")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Knjizevnovece> knjizevnoveces;

	//bi-directional many-to-one association to Komentar
	@OneToMany(mappedBy="knjiga")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Komentar> komentars;

	//bi-directional many-to-one association to Narudzbenica
	@OneToMany(mappedBy="knjiga")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Narudzbenica> narudzbenicas;

	//bi-directional many-to-one association to Ocena
	@OneToMany(mappedBy="knjiga")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Ocena> ocenas;

	//bi-directional many-to-many association to Korisnik
	@ManyToMany(mappedBy="knjigas")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Korisnik> korisniks;

	public Knjiga() {
		this.kategorijas = new ArrayList<Kategorija>();
		this.knjizevnoveces = new ArrayList<Knjizevnovece>();
		this.komentars = new ArrayList<Komentar>();
		this.narudzbenicas = new ArrayList<Narudzbenica>();
		this.ocenas = new ArrayList<Ocena>();
		this.korisniks = new ArrayList<Korisnik>();
	}

	public int getIdKnjiga() {
		return this.idKnjiga;
	}

	public void setIdKnjiga(int idKnjiga) {
		this.idKnjiga = idKnjiga;
	}

	public int getBrojKnjigaLager() {
		return this.brojKnjigaLager;
	}

	public void setBrojKnjigaLager(int brojKnjigaLager) {
		this.brojKnjigaLager = brojKnjigaLager;
	}

	public int getCena() {
		return this.cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

	public int getGodinaIzdanja() {
		return this.godinaIzdanja;
	}

	public void setGodinaIzdanja(int godinaIzdanja) {
		this.godinaIzdanja = godinaIzdanja;
	}

	public String getIzdavac() {
		return this.izdavac;
	}

	public void setIzdavac(String izdavac) {
		this.izdavac = izdavac;
	}

	public String getKratakOpis() {
		return this.kratakOpis;
	}

	public void setKratakOpis(String kratakOpis) {
		this.kratakOpis = kratakOpis;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public byte[] getSlika() {
		return this.slika;
	}

	public void setSlika(byte[] slika) {
		this.slika = slika;
	}

	public Pisac getPisac() {
		return this.pisac;
	}

	public void setPisac(Pisac pisac) {
		this.pisac = pisac;
	}

	public List<Kategorija> getKategorijas() {
		return this.kategorijas;
	}

	public void setKategorijas(List<Kategorija> kategorijas) {
		this.kategorijas = kategorijas;
	}

	public List<Knjizevnovece> getKnjizevnoveces() {
		return this.knjizevnoveces;
	}

	public void setKnjizevnoveces(List<Knjizevnovece> knjizevnoveces) {
		this.knjizevnoveces = knjizevnoveces;
	}

	public Knjizevnovece addKnjizevnovece(Knjizevnovece knjizevnovece) {
		getKnjizevnoveces().add(knjizevnovece);
		knjizevnovece.setKnjiga(this);

		return knjizevnovece;
	}

	public Knjizevnovece removeKnjizevnovece(Knjizevnovece knjizevnovece) {
		getKnjizevnoveces().remove(knjizevnovece);
		knjizevnovece.setKnjiga(null);

		return knjizevnovece;
	}

	public List<Komentar> getKomentars() {
		return this.komentars;
	}

	public void setKomentars(List<Komentar> komentars) {
		this.komentars = komentars;
	}

	public Komentar addKomentar(Komentar komentar) {
		getKomentars().add(komentar);
		komentar.setKnjiga(this);

		return komentar;
	}

	public Komentar removeKomentar(Komentar komentar) {
		getKomentars().remove(komentar);
		komentar.setKnjiga(null);

		return komentar;
	}

	public List<Narudzbenica> getNarudzbenicas() {
		return this.narudzbenicas;
	}

	public void setNarudzbenicas(List<Narudzbenica> narudzbenicas) {
		this.narudzbenicas = narudzbenicas;
	}

	public Narudzbenica addNarudzbenica(Narudzbenica narudzbenica) {
		getNarudzbenicas().add(narudzbenica);
		narudzbenica.setKnjiga(this);

		return narudzbenica;
	}

	public Narudzbenica removeNarudzbenica(Narudzbenica narudzbenica) {
		getNarudzbenicas().remove(narudzbenica);
		narudzbenica.setKnjiga(null);

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
		ocena.setKnjiga(this);

		return ocena;
	}

	public Ocena removeOcena(Ocena ocena) {
		getOcenas().remove(ocena);
		ocena.setKnjiga(null);

		return ocena;
	}

	public List<Korisnik> getKorisniks() {
		return this.korisniks;
	}

	public void setKorisniks(List<Korisnik> korisniks) {
		this.korisniks = korisniks;
	}

}