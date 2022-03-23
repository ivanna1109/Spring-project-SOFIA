package com.ris.izdavacka.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ris.izdavacka.repositories.ClanskaKartaRepository;
import com.ris.izdavacka.repositories.KategorijaRepository;
import com.ris.izdavacka.repositories.KlubCitalacaRepository;
import com.ris.izdavacka.repositories.KnjigaRepository;
import com.ris.izdavacka.repositories.KnjizevnoVeceRepository;
import com.ris.izdavacka.repositories.KomentarRepository;
import com.ris.izdavacka.repositories.KorisnikRepository;
import com.ris.izdavacka.repositories.NarudzbenicaRepository;
import com.ris.izdavacka.repositories.OcenaRepository;

import model.Clanskakarta;
import model.Kategorija;
import model.Klubcitalaca;
import model.Knjiga;
import model.Knjizevnovece;
import model.Komentar;
import model.Korisnik;
import model.Narudzbenica;
import model.Ocena;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping(value="/korisnik")
public class KorisnikController {
	
	@Autowired
	KorisnikRepository korisnikRep;
	
	@Autowired
	KategorijaRepository kategorijaRep;
	
	@Autowired
	KnjigaRepository knjigaRep;
	
	@Autowired
	KlubCitalacaRepository klubRep;
	
	@Autowired
	KnjizevnoVeceRepository veceRep;
	
	@Autowired
	NarudzbenicaRepository narudzbinaRep;
	
	@Autowired
	OcenaRepository ocenaRep;
	
	@Autowired
	KomentarRepository komRep;
	
	@Autowired
	ClanskaKartaRepository clanskaRep;
	
	//da se za izabranu kategoriju prikaze tabela sa knjigama
	@RequestMapping(value="/sveKnjige", method=RequestMethod.GET)
	public String sveKnjige(HttpServletRequest request) {
	Integer idKategorije = Integer.parseInt(request.getParameter("idKat"));

	Kategorija kat = kategorijaRep.findById(idKategorije).get();
	List<Knjiga> knjigeZaKategoriju = knjigaRep.findByKategorija(idKategorije);
	
	request.getSession().removeAttribute("knjigeZaKat");
	request.getSession().removeAttribute("kategorijaKnjige");
	if ( knjigeZaKategoriju != null && knjigeZaKategoriju.size() > 0) {
		request.getSession().setAttribute("knjigeZaKat", knjigeZaKategoriju);
	}
	request.getSession().setAttribute("kategorijaKnjige", kat);

	
	return "prikazKnjiga"; //stranica na kojoj ce se prikazati za izabranu kategoriju tabela knjiga sa pojedinim podacima
	}
	
	//kada klikne na knjigu da moze da procita vise informacija (tu se nalazi i slika)
	@RequestMapping(value="/vratiKnjigu", method=RequestMethod.GET)
	public String prikaziKnjigu(HttpServletRequest request) {
		Integer idKnjige = Integer.parseInt(request.getParameter("idKnjige"));
		
		Knjiga knjiga = knjigaRep.findById(idKnjige).get();
		List<Ocena> ocene = knjiga.getOcenas();
		double prosek = 0.0;
		for (Ocena o: ocene)
			prosek +=o.getVrednost();
		if (prosek != 0.0)
			prosek /= ocene.size();
		prosek = Math.round(prosek * 100.0) / 100.0;
		
		request.getSession().removeAttribute("knjiga");
		request.getSession().removeAttribute("ocenaKnjige");
		request.getSession().removeAttribute("poruka");
		request.getSession().removeAttribute("porukaOcena");
		request.getSession().removeAttribute("porukaKom");
		if ( knjiga != null) {
			request.getSession().setAttribute("knjiga", knjiga);
			request.getSession().setAttribute("ocenaKnjige", prosek);
		}
		return "prikazJedneKnjige";
	}
	
	//ocenjivanje
	@RequestMapping(value="/oceni", method=RequestMethod.POST)
	public String oceniKnjigu(HttpServletRequest request) {
		Korisnik k = (Korisnik) request.getSession().getAttribute("korisnik");
		Integer idKnjige = Integer.parseInt(request.getParameter("idKnjige"));
		Integer ocena = Integer.parseInt(request.getParameter("ocena")); // ja bih radio button za ocene ili slicno
		
		Knjiga knjiga = knjigaRep.findById(idKnjige).get();
		Ocena oc = new Ocena();
		
		request.getSession().removeAttribute("porukaOcena");
		List<Ocena> korisnikoveOcene = k.getOcenas();
		boolean nasao = false;
		for(Ocena o: korisnikoveOcene) {
			if ( o.getKnjiga().getIdKnjiga() == knjiga.getIdKnjiga()) {	
				nasao = true;
				break;
			}
		}
		if ( !nasao) {
			oc.setVrednost(ocena);
			oc.setKorisnik(k);
			oc.setKnjiga(knjiga);
			
			ocenaRep.save(oc);
			
			knjiga.getOcenas().add(oc); //kako da update tabelu
			knjigaRep.save(knjiga);
			k.getOcenas().add(oc); //kako da update tabelu	
			korisnikRep.save(k);
			request.getSession().setAttribute("porukaOcena", "Vaša ocena je sačuvana");
		} else 
			request.getSession().setAttribute("porukaOcena", "Ovu knjigu ste već ocenili");
		
		return "prikazJedneKnjige";
	}
	
	//komentarisanje
	@RequestMapping(value="/komentarisi", method=RequestMethod.POST)
	public String komentarisiKnjigu(HttpServletRequest request) {
		Korisnik k = (Korisnik) request.getSession().getAttribute("korisnik");
		Integer idKnjige = Integer.parseInt(request.getParameter("idKnjige"));
		String komentar = request.getParameter("komentar");
		
		request.getSession().removeAttribute("porukaKom");
		Knjiga knjiga = knjigaRep.findById(idKnjige).get();
		Komentar kom = new Komentar();
		
		kom.setTekst(komentar);
		kom.setKorisnik(k);
		kom.setKnjiga(knjiga);
		
		komRep.save(kom);
		
		knjiga.getKomentars().add(kom); 
		knjigaRep.save(knjiga);
		
		k.getKomentars().add(kom); 
		korisnikRep.save(k);
		
		request.getSession().setAttribute("porukaKom", "Vaš komentar je sačuvan");
		
		return "prikazJedneKnjige";
	}
	
	//===========================================================opcija za klubove citalaca======================================
	
	//prikaz svih klubova
	@RequestMapping(value="/sviKlubovi", method=RequestMethod.GET)
	public String getKlubovi(HttpServletRequest request) {
		Korisnik kor = (Korisnik) request.getSession().getAttribute("korisnik");
		
		List<Klubcitalaca> klubovi = klubRep.findAll();
		List<Klubcitalaca> klubKorisnika = klubRep.findByKorisnik(kor.getIdKorisnik());
		
		request.getSession().removeAttribute("klubovi");
		request.getSession().removeAttribute("kluboviKorisnika");
		request.getSession().removeAttribute("korisnikoviKlubovi");
		request.getSession().removeAttribute("porukaKlub");
		if ( klubovi != null && klubovi.size() > 0) {
			request.getSession().setAttribute("klubovi", klubovi);
			request.getSession().setAttribute("korisnikoviKlubovi", klubKorisnika);
		}
		return "prikazKlubova"; //imacemo jednu jsp stranicu za obe opcije, kolona gde ce moci da se uclani korisnik ako vec nije uclanjen
	}
	
	//napraviti da moze da se uclani u klub
//	@RequestMapping(value="/uclaniUKlub", method=RequestMethod.POST)
	@PostMapping(value="/uclaniUKlub")
	public String uclani(HttpServletRequest request) {

		Korisnik kor = (Korisnik) request.getSession().getAttribute("korisnik");
		Integer idKlub = Integer.parseInt(request.getParameter("idKlub"));

		Klubcitalaca klub = klubRep.findById(idKlub).get();		
		Clanskakarta ck = new Clanskakarta();

		ck.setKlubcitalaca(klub);
		ck.setKorisnik(kor);
		ck.setClanarina(1000);
		ck.setDatumIzdavanja(new Date());
		
		clanskaRep.save(ck);

		kor.getClanskakartas().add(ck);
		korisnikRep.save(kor);
		klub.getClanskakartas().add(ck);
		klubRep.save(klub);
		
		return "prikazKlubova";
	}
	
	//prikaz klubova korisnika
	@RequestMapping(value="/kluboviKorisnika", method=RequestMethod.GET)
	public String prikaziKorisnikKlubove(HttpServletRequest request) {
		Korisnik kor = (Korisnik) request.getSession().getAttribute("korisnik");
		
		List<Clanskakarta> klubovi = clanskaRep.findByKorisnik(kor.getIdKorisnik());
		
		request.getSession().removeAttribute("klubovi");
		request.getSession().removeAttribute("kluboviKorisnika");
		request.getSession().removeAttribute("korisnikoviKlubovi");
		request.getSession().removeAttribute("porukaKlub");
		if ( klubovi != null && klubovi.size() > 0) {
			request.getSession().setAttribute("kluboviKorisnika", klubovi);
		} else {
			request.getSession().setAttribute("porukaKlub","Trenutno niste učlanjeni ni u jedan klub");
		}
		return "prikazKlubova"; 
	}
	
	
	//================================================================meni knjizevne veceri===============================================
	
	//prikaz knjizevnih veceri
	@RequestMapping(value="/sveVeceri", method=RequestMethod.GET)
	public String prikazVeceri(HttpServletRequest request) {
		Korisnik kor = (Korisnik) request.getSession().getAttribute("korisnik");
		
		List<Knjizevnovece> veceri = veceRep.findByPosleDatuma(new Date());
		List<Knjizevnovece> veceriKor = veceRep.findByKorisnik(kor.getIdKorisnik());
		
		request.getSession().removeAttribute("veceri");
		request.getSession().removeAttribute("veceriKor");
		request.getSession().removeAttribute("veceriKorisnika");
		request.getSession().removeAttribute("porukaVece");
		if ( veceri != null && veceri.size() > 0) {
			request.getSession().setAttribute("veceri", veceri);
			request.getSession().setAttribute("veceriKor", veceriKor);
		}
		return "prikazVeceri";
	}
	
	//za jsp stranicu
	public static boolean contains(List<Knjizevnovece> list, Knjizevnovece o) {
		      return list.contains(o);
		   }
	
	//da prisustvuje knjizevnoj veceri treba da napravim
	@RequestMapping(value="/prisustvujVeceri", method=RequestMethod.POST)
	public String prisustvuj(HttpServletRequest request) {
		Integer knjizevnoId = Integer.parseInt(request.getParameter("idVece"));
		Korisnik kor = (Korisnik) request.getSession().getAttribute("korisnik");
		
		Knjizevnovece vece = veceRep.findById(knjizevnoId).get();
		
		int trenutnoPosetilaca = vece.getKorisniks().size();
		System.out.println(vece.getKorisniks().size());
		
		if ( trenutnoPosetilaca + 1 <= vece.getMaxPosetilaca()) {
			vece.getKorisniks().add(kor);
			kor.getKnjizevnoveces().add(vece);
			veceRep.save(vece);
			korisnikRep.save(kor);
			request.getSession().setAttribute("uspesno", " ");
		} else {
			request.getSession().setAttribute("poruka", "Sva mesta su popunjena za ovo knjizevno vece!");
		}
		
		return "index";
	}
	
	//prikazi korisnikove knjizevne veceri
	@RequestMapping(value="/sveVeceriKorisnika", method=RequestMethod.GET)
	public String getSveVeceri(HttpServletRequest request) {
		Korisnik kor = (Korisnik) request.getSession().getAttribute("korisnik");
		
		List<Knjizevnovece> veceri = veceRep.findByKorisnik(kor.getIdKorisnik());
		
		request.getSession().removeAttribute("veceri");
		request.getSession().removeAttribute("porukaVece");
		if ( veceri != null && veceri.size() > 0) {
			request.getSession().setAttribute("veceriKorisnika", veceri);
		} else {
			request.getSession().setAttribute("porukaVece", "Nemate predstojećih večeri");
		}		
		return "prikazVeceri";
	}
	
	//=============================================opcija narudzbenica=============================================================
	
	//pribavi kategorije po kojima ce videti koju knjigu da naruci
	@RequestMapping(value="/sveKategorije", method=RequestMethod.GET)
	public String vratiKategorije(HttpServletRequest request) {
		List<Kategorija> kategorije = kategorijaRep.findAll();
	
		request.getSession().removeAttribute("listaKat");
		request.getSession().removeAttribute("knjigeZaKat");
		if ( kategorije != null && kategorije.size() > 0)
			request.getSession().setAttribute("listaKat", kategorije);
		return "narucivanjeKnjige";
	}
	
	@RequestMapping(value="/knjigeNarucivanje", method=RequestMethod.GET)
	public String knjigeNaruci(HttpServletRequest request) {
		Integer idKategorije = Integer.parseInt(request.getParameter("idKat"));

		List<Knjiga> knjigeZaKategoriju = knjigaRep.findByKategorija(idKategorije);
		
		request.getSession().removeAttribute("knjigeZaKat");
		if ( knjigeZaKategoriju != null && knjigeZaKategoriju.size() > 0) {
			request.getSession().setAttribute("knjigeZaKat", knjigeZaKategoriju);
		}
		return "narucivanjeKnjige";
	}
	//da prikaze sve narudzbenice
	@RequestMapping(value="/sveNarudzbenice", method=RequestMethod.GET)
	public String getNarudzbenice(HttpServletRequest request) {
		Korisnik kor = (Korisnik) request.getSession().getAttribute("korisnik");
		
		List<Narudzbenica> narudzbenice = narudzbinaRep.findByKorisnik(kor);
		
		request.getSession().removeAttribute("narudzbenice");
		if ( narudzbenice != null && narudzbenice.size() > 0) {
			request.getSession().setAttribute("narudzbenice", narudzbenice);
		}
		return "stranaNarudzbenice"; 
	}
	
	//imace na kraju dugme za generisanje izvestaja gde ce biti polje sa ukupnim iznosom za narucivanje
	@RequestMapping(value="/izvestajNarudzbenica", method=RequestMethod.GET)
	public void generisiIzvestaj(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Korisnik korisnik = (Korisnik) request.getSession().getAttribute("korisnik");
		
		List<Narudzbenica> narudzbenica = narudzbinaRep.findByKorisnik(korisnik);
		
		response.setContentType("text/html");
		JRBeanCollectionDataSource jrb = new JRBeanCollectionDataSource(narudzbenica);
		InputStream in = this.getClass().getResourceAsStream("/reports/narudzbenice.jrxml");
		
		JasperReport jasperR = JasperCompileManager.compileReport(in);
		
		Map<String, Object> mapa = new HashMap<String, Object>();
		
		String korisnikImePrezime = korisnik.getIme()+" "+korisnik.getPrezime();
		String adresa = korisnik.getAdresa();

		if(narudzbenica!=null && narudzbenica.size()>0) {
			mapa.put("korisnik", korisnikImePrezime);
			mapa.put("adresa", adresa);
		}
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperR, mapa, jrb);
		in.close();
		
		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=MojeNarudzbenice.pdf");
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,out);
	}
	
	//narucivanje knjige (bice combo box po cemu ce se traziti knjiga za narucivanje
	@RequestMapping(value="/naruciKnjigu", method=RequestMethod.POST)
	public String naruciKnjigu(HttpServletRequest request) {
		Korisnik korisnik = (Korisnik) request.getSession().getAttribute("korisnik");
		Integer idKnjige = Integer.parseInt(request.getParameter("idKnjiga"));
		
		Knjiga knjiga = knjigaRep.findById(idKnjige).get();
		
		Narudzbenica naruci = new Narudzbenica();
		
		if (knjiga.getBrojKnjigaLager() > 0) {
			naruci.setKnjiga(knjiga);
			naruci.setKorisnik(korisnik);
			naruci.setDatumNarucivanja(new Date());
			naruci.setCena(knjiga.getCena()+200);
			
			narudzbinaRep.save(naruci);
			
			korisnik.getNarudzbenicas().add(naruci);
			korisnikRep.save(korisnik);
			knjiga.getNarudzbenicas().add(naruci);
			knjiga.setBrojKnjigaLager(knjiga.getBrojKnjigaLager()-1);
			knjigaRep.save(knjiga);
		}
			
		//da se promeni iz naruci u naruceno u tabeli (ako moze tako)
		request.getSession().setAttribute("uspesno", " ");
		
		return "narucivanjeKnjige";
	}
	
	//====================================================opcija omiljene knjige===================================================
	
	@RequestMapping(value="/omiljeneKnjige", method=RequestMethod.GET)
	public String getOmiljeneKnjigeZaKorisnika(HttpServletRequest request) {
		Korisnik kor = (Korisnik) request.getSession().getAttribute("korisnik");
		List<Knjiga> omiljene = knjigaRep.findByKorisnik(kor.getIdKorisnik());
		
		request.getSession().removeAttribute("omiljeneKnjige");
		if ( omiljene != null && omiljene.size() > 0) {
			request.getSession().setAttribute("omiljeneKnjige", omiljene);
		}
		return "prikaziOmiljene"; //poslednja kolona ce biti da izbrise knjigu iz svojih omiljenih knjiga
	}
	
	@RequestMapping(value = "/prikaziSliku", method = RequestMethod.GET)
	public void showImageIMG(String id, HttpServletResponse response,HttpServletRequest request) throws ServletException, IOException{
		int ID = Integer.parseInt(id);
		Knjiga k = knjigaRep.findById(ID).get();
		response.setContentType("image/jpeg, image/jpg, image/png");
	    response.getOutputStream().write(k.getSlika());	
	
	    response.getOutputStream().close();
	}
	
	@PostMapping(value="/dodajOmiljenu")
	public String dodaj(HttpServletRequest request) {
		Korisnik korisnik = (Korisnik) request.getSession().getAttribute("korisnik");
		Integer idK = Integer.parseInt(request.getParameter("idKnjige"));
		
		request.getSession().removeAttribute("poruka");
		Knjiga k = knjigaRep.findById(idK).get();
		List<Knjiga> omiljene = korisnik.getKnjigas();
		
		List<Integer> tmp = omiljene.stream().map(x -> x.getIdKnjiga()).collect(Collectors.toList());
		
		if (tmp.contains(k.getIdKnjiga())) {
			request.getSession().setAttribute("poruka", "Knjiga je već u Vašim omiljenim knjigama");
		
		} else {
			korisnik.getKnjigas().add(k);
			korisnikRep.save(korisnik);
			k.getKorisniks().add(korisnik);
			knjigaRep.save(k);
			request.getSession().setAttribute("poruka", "Uspešno je dodata knjiga u omiljene");
		}
		
		return "prikazJedneKnjige";
	}
	
	//za brisanje omiljenje knjige
//	@RequestMapping(value="/izbrisiOmiljenu", method=RequestMethod.POST) //nisam sigurna da li je post u pitanju???
	@PostMapping(value="/izbrisiOmiljenu")
	public String izbrisiOmiljenuKnjigu(HttpServletRequest request) {
		Korisnik kor = (Korisnik) request.getSession().getAttribute("korisnik");
		Integer idKnjiga = Integer.parseInt(request.getParameter("idKnjige"));
		
		Knjiga knjiga = knjigaRep.findById(idKnjiga).get();
		

		knjiga.getKorisniks().remove(kor);
		knjigaRep.save(knjiga);
		kor.getKnjigas().remove(knjiga);
		korisnikRep.save(kor);
		
		//ako moze da se izbrise taj red u tabeli odmah
		request.getSession().setAttribute("uspesno", " ");
		
		return "prikaziOmiljene";
	}
	
	//=============================================================opcija nalog===============================================================
	
	//da se uredi profil po parametrima
	@RequestMapping(value="/urediProfil", method=RequestMethod.POST)
	public String urediInformacije(HttpServletRequest request) {
		
		Korisnik kor = (Korisnik) request.getSession().getAttribute("korisnik");
		String ime = request.getParameter("ime");
		String prezime = request.getParameter("prezime");
		Integer godine = Integer.parseInt(request.getParameter("brojGodina"));
		String adresa = request.getParameter("adresa");
		
		request.getSession().removeAttribute("poruka");
		//videcu da li moze da menja sifru, ali vrv ce moci sto da ne??
		kor.setIme(ime);
		kor.setPrezime(prezime);
		kor.setBrojGodina(godine);
		kor.setAdresa(adresa);
		korisnikRep.save(kor);
		
		request.getSession().setAttribute("poruka", "Uspešno ste promenili informacije!");
		
		return "urediProfil";
	}
	
	//logout je u controlleru login

}
