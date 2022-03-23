package com.ris.izdavacka.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ris.izdavacka.repositories.ClanskaKartaRepository;
import com.ris.izdavacka.repositories.KategorijaRepository;
import com.ris.izdavacka.repositories.KnjigaRepository;
import com.ris.izdavacka.repositories.KnjizevnoVeceRepository;
import com.ris.izdavacka.repositories.KorisnikRepository;
import com.ris.izdavacka.repositories.NarudzbenicaRepository;
import com.ris.izdavacka.repositories.PisacRepository;

import model.Clanskakarta;
import model.Kategorija;
import model.Knjiga;
import model.Knjizevnovece;
import model.Korisnik;
import model.Narudzbenica;
import model.Ocena;
import model.Pisac;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping(value="/radnik")
public class RadnikController {

	@Autowired
	KategorijaRepository kategorijaRep;
	
	@Autowired
	KnjigaRepository knjigaRep;
	
	@Autowired
	KnjizevnoVeceRepository veceRep;
	
	@Autowired
	KorisnikRepository korisnikRep;
	
	@Autowired
	PisacRepository pisacRep;
	
	@Autowired
	NarudzbenicaRepository narudzbinaRep;
	
	@Autowired
	ClanskaKartaRepository clanskaRep;
	
//======================================opcija kategorije=================================================	
	
	@RequestMapping(value="/dodajKategoriju", method=RequestMethod.POST)
	public String dodajKategoriju(HttpServletRequest request) {
		String naziv = request.getParameter("naziv");
		String opis = request.getParameter("opis");
		
		Kategorija kat = new Kategorija();
		
		request.getSession().removeAttribute("porukaDodaj");
		kat.setNazivKategorije(naziv);
		kat.setOpis(opis);
		kategorijaRep.save(kat);
		
		List<Kategorija> kategorije = kategorijaRep.findAll();
		
		request.getSession().setAttribute("kategorije", kategorije);
		request.getSession().setAttribute("porukaDodaj", "Uspešno je dodata nova kategorija!");
		
		return "dodajKategoriju";
	}
	
//=================================================opcija knjizevne veceri===================================
	
	@RequestMapping(value="/sveKnjige", method=RequestMethod.GET)
	public String sveKnjige(HttpServletRequest request) {
		List<Knjiga> sveKnjige = knjigaRep.findAll();
		
		request.getSession().removeAttribute("sveKnjige");
		if ( sveKnjige.size() > 0) 
			request.getSession().setAttribute("sveKnjige", sveKnjige);
		return "zakazivanjeVeceri";
		
	}

@InitBinder
public void dateBinder( WebDataBinder binder) {
	    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	    simpleDateFormat.setLenient(false);
	    binder.registerCustomEditor( Date.class, new CustomDateEditor( simpleDateFormat,false));	   
	}
	
	@RequestMapping(value="/zakaziVece", method=RequestMethod.POST)
	public String zakazi(HttpServletRequest request) {
		Integer idKnjige = Integer.parseInt(request.getParameter("izabranaKnjiga"));		
		Knjiga knjiga = knjigaRep.findById(idKnjige).get();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date datum;
		try {
			datum = sdf.parse(request.getParameter("datum"));
		} catch (ParseException e) {
			datum = null;
		}
		String mesto = request.getParameter("mesto");
		Integer maxPosetilaca = Integer.parseInt(request.getParameter("maxPosetilaca"));
		
		request.getSession().removeAttribute("porukaVece");
		Knjizevnovece kv = new Knjizevnovece();		
		kv.setDatum(datum);
		kv.setKnjiga(knjiga);
		kv.setMaxPosetilaca(maxPosetilaca);
		kv.setMesto(mesto);
		
		veceRep.save(kv);
		
		knjiga.getKnjizevnoveces().add(kv);
		knjigaRep.save(knjiga);
		
		request.getSession().setAttribute("porukaVece", "Uspešno je zakazano književno veče!");
		return "zakazivanjeVeceri";
	}
	
	@RequestMapping(value="/sveVeceri", method=RequestMethod.GET)
	public String sveVeceri(HttpServletRequest request) {
		List<Knjizevnovece> veceri = veceRep.findAll();
		
		request.getSession().removeAttribute("veceri");
		request.getSession().removeAttribute("porukaVece");
		if ( veceri.size()> 0) {
			request.getSession().setAttribute("veceri", veceri);
		} else {
			request.getSession().setAttribute("porukaVece", "Nema predstojećih večeri!");
		}
		return "veceriRadnik";
	}
	
	@RequestMapping(value="/otkaziVece", method=RequestMethod.POST)
	public String otkazi(HttpServletRequest request) {
		Integer id = Integer.parseInt(request.getParameter("idVeceri"));
		Knjizevnovece vece = veceRep.findById(id).get();
	
		Knjiga knjiga = vece.getKnjiga();
		knjiga.getKnjizevnoveces().remove(vece);
		knjigaRep.save(knjiga);
		
		List<Korisnik> posetioci = vece.getKorisniks();
		if ( posetioci.size() > 0) {
			for(Korisnik k: posetioci) {
				k.getKnjizevnoveces().remove(vece);
				korisnikRep.save(k);
			}
		}
		veceRep.delete(vece);
		return "index";
	}

//========================================opcija knjige=======================================
	
	@RequestMapping(value="/nadjiKnjigu", method=RequestMethod.GET)
	public String nadjiKnjigu(HttpServletRequest request) {
		String naziv = request.getParameter("nazivKnjige");
		
		request.getSession().removeAttribute("trazena");
		request.getSession().removeAttribute("porukaKnjiga");
		request.getSession().removeAttribute("ocenaTrazene");
		Knjiga knjiga = knjigaRep.findByNaziv(naziv);
		
		if ( knjiga != null) {
			double suma = 0.0;
			if ( knjiga.getOcenas().size() > 0) {
				for(Ocena o: knjiga.getOcenas()) {
					suma += o.getVrednost();
				}
				suma /= knjiga.getOcenas().size();
			}
			request.getSession().setAttribute("ocenaTrazene", suma);
			request.getSession().setAttribute("trazena", knjiga);
		} else {
			request.getSession().setAttribute("porukaKnjiga", "Nema knjige sa traženim nazivom!");
		}
		return "pretragaKnjiga";
	}
	
	//redirekcija sa stranicu na stranicu
	@RequestMapping(value="/redirekcija")
	public String redirekcija(HttpServletRequest request) {
		String naziv = request.getParameter("knjigaInfo");
		Knjiga trazena = knjigaRep.findByNaziv(naziv);
		request.getSession().removeAttribute("uredjivanje");
		
		request.getSession().setAttribute("uredjivanje", trazena);
		return "urediKnjigu";
	}
	
	@RequestMapping(value="/urediKnjigu", method=RequestMethod.POST)
	public String uredi(HttpServletRequest request) {
		String naziv = request.getParameter("naziv");
		String izdavac = request.getParameter("izdavac");
		Integer godina = Integer.parseInt(request.getParameter("godinaIzdanja"));
		Integer cena = Integer.parseInt(request.getParameter("cena"));
		Integer brojLager = Integer.parseInt(request.getParameter("lager"));
		String opis = request.getParameter("opis");
		Knjiga knjiga = (Knjiga) request.getSession().getAttribute("uredjivanje");
		
		knjiga.setNaziv(naziv);
		knjiga.setIzdavac(izdavac);
		knjiga.setGodinaIzdanja(godina);
		knjiga.setCena(cena);
		knjiga.setBrojKnjigaLager(brojLager);
		knjiga.setKratakOpis(opis);
		
		knjigaRep.save(knjiga);
		request.getSession().setAttribute("porukaUredjivanje", "Uspešno su promenjene informacije!");
		return "urediKnjigu";
	}
	
	@RequestMapping(value="/sveKategorijeDodaj", method=RequestMethod.GET)
	public String sveKategorije(HttpServletRequest request) {
		List<Kategorija> kategorije = kategorijaRep.findAll();
		
		request.getSession().removeAttribute("kategorijeKnjiga");
		request.getSession().removeAttribute("uspesno");
		if ( kategorije.size() > 0) 
			request.getSession().setAttribute("kategorijeKnjiga", kategorije);
		return "dodajKnjigu";
	}
	
	@RequestMapping(value="/sacuvajKnjigu", method=RequestMethod.POST)
	public String sacuvajKnjigu(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
		byte[] b = file.getBytes();
		
		Integer idKategorije = Integer.parseInt(request.getParameter("idKategorije"));
		String naziv = request.getParameter("naziv");
		String pisac = request.getParameter("pisac");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date datum;
		try {
			datum = sdf.parse(request.getParameter("datumRodjenja"));
		} catch (ParseException e) {
			datum = null;
		}
		String izdavac = request.getParameter("izdavac");
		Integer godina = Integer.parseInt(request.getParameter("godinaIzdanja"));
		Integer cena = Integer.parseInt(request.getParameter("cena"));
		Integer brojLager = Integer.parseInt(request.getParameter("lager"));
		String opis = request.getParameter("opis");
		
		Kategorija kat1 = kategorijaRep.findById(idKategorije).get();
		String[] imePrezime = pisac.split(" ");
		Pisac pom = pisacRep.findByImeIPrezime(imePrezime[0], imePrezime[1]);			
		
		
		Knjiga novaKnjiga = new Knjiga();
		//fali jos kategorija i mesto za sliku i pisca
		novaKnjiga.setNaziv(naziv);
		novaKnjiga.setIzdavac(izdavac);
		novaKnjiga.setGodinaIzdanja(godina);
		novaKnjiga.setCena(cena);
		novaKnjiga.setBrojKnjigaLager(brojLager);
		novaKnjiga.setKratakOpis(opis);
		novaKnjiga.getKategorijas().add(kat1); //videcemo sta cemo sa ostalim kategorijama
		
		novaKnjiga.setSlika(b);
		
		if (pom != null) {
			novaKnjiga.setPisac(pom);
			knjigaRep.save(novaKnjiga);
			pom.getKnjigas().add(novaKnjiga);
			pisacRep.save(pom);			
		} else {
			Pisac noviPisac = new Pisac();
			noviPisac.setIme(imePrezime[0]);
			noviPisac.setPrezime(imePrezime[1]);
			noviPisac.setDatumRodjenja(datum);
			pisacRep.save(noviPisac);
			novaKnjiga.setPisac(noviPisac);
			knjigaRep.save(novaKnjiga);
			noviPisac.getKnjigas().add(novaKnjiga);
			pisacRep.save(noviPisac);					
		}

		kat1.getKnjigas().add(novaKnjiga);
		kategorijaRep.save(kat1);
		
		request.getSession().setAttribute("uspesno", "Uspešno ste uneli novu knjigu!");
		return "dodajKnjigu";
	}
	
//=======================================izvestaji==============================
	@RequestMapping(value="/izvestajNarudzbina", method=RequestMethod.GET)
	public void izvestajNarudzbina(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Korisnik k = (Korisnik) request.getSession().getAttribute("korisnik");
		
		List<Narudzbenica> sveNarudzbine = narudzbinaRep.findAll(); 
		
		sveNarudzbine = sveNarudzbine.stream().sorted((s1,s2)-> s1.getKnjiga().getIzdavac().compareTo(s2.getKnjiga().getIzdavac())).collect(Collectors.toList());
		if (sveNarudzbine == null) {
			request.getSession().setAttribute("porukaN", "Nema narudžbina u sistemu!");
			return;
		}
		
		response.setContentType("text/html");
		JRBeanCollectionDataSource jrb = new JRBeanCollectionDataSource(sveNarudzbine);
		InputStream in = this.getClass().getResourceAsStream("/reports/radnikNarudzbine.jrxml");
		
		JasperReport jasperR = JasperCompileManager.compileReport(in);
		
		Map<String, Object> mapa = new HashMap<String, Object>();
		
		String radnik = k.getIme()+" "+k.getPrezime();
		
		if(sveNarudzbine!=null && sveNarudzbine.size()>0) {
			mapa.put("radnik", radnik);
		}
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperR, mapa, jrb);
		in.close();
		
		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=SpisakNarudzbina.pdf");
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,out);
	}
	
	@RequestMapping(value="/izvestajKlubova", method=RequestMethod.GET)
	public void izvestajKlubovi(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Korisnik k = (Korisnik) request.getSession().getAttribute("korisnik");
		List<Clanskakarta> clanskeKarte = clanskaRep.findAll();

		clanskeKarte = clanskeKarte.stream().sorted((s1, s2)-> s1.getKlubcitalaca().getNazivKluba().compareTo(s2.getKlubcitalaca().getNazivKluba())).collect(Collectors.toList());
		if (clanskeKarte == null) {
			request.getSession().setAttribute("poruka", "Nema clanskih karti.");
			return;
		}
		
		response.setContentType("text/html");
		JRBeanCollectionDataSource jrb = new JRBeanCollectionDataSource(clanskeKarte);
		InputStream in = this.getClass().getResourceAsStream("/reports/clanskeKarteReport.jrxml");
		
		JasperReport jasperR = JasperCompileManager.compileReport(in);
		
		Map<String, Object> mapa = new HashMap<String, Object>();
		
		String radnik = k.getIme()+" "+k.getPrezime();
		
		if(clanskeKarte!=null && clanskeKarte.size()>0) {
			mapa.put("radnik", radnik);
			mapa.put("datum", new Date());
		}
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperR, mapa, jrb);
		in.close();
		
		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=ClanskeKarte.pdf");
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,out);
	}
	
	@RequestMapping(value="/izvestajKnjiga", method=RequestMethod.GET)
	public void izvestajKnjiga(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Korisnik k = (Korisnik) request.getSession().getAttribute("korisnik");
		List<Knjiga> sveKnjige = knjigaRep.findAll();
		
		sveKnjige = sveKnjige.stream().sorted((s1, s2) -> s1.getIzdavac().compareTo(s2.getIzdavac())).collect(Collectors.toList());

		if (sveKnjige == null) {
			request.getSession().setAttribute("poruka", "Nema knjiga.");
			return;
		}
		
		response.setContentType("text/html");
		JRBeanCollectionDataSource jrb = new JRBeanCollectionDataSource(sveKnjige);
		InputStream in = this.getClass().getResourceAsStream("/reports/knjigeReport.jrxml");
		
		JasperReport jasperR = JasperCompileManager.compileReport(in);
		
		Map<String, Object> mapa = new HashMap<String, Object>();
		
		String radnik = k.getIme()+" "+k.getPrezime();
		
		if(sveKnjige!=null && sveKnjige.size()>0) {
			mapa.put("radnik", radnik);
			mapa.put("datum", new Date());
		}
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperR, mapa, jrb);
		in.close();
		
		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=SpisakKnjiga.pdf");
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,out);
	}
}
