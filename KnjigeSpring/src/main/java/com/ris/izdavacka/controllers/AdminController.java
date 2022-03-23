package com.ris.izdavacka.controllers;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ris.izdavacka.repositories.KorisnikRepository;
import com.ris.izdavacka.repositories.UlogaRepository;

import model.Korisnik;
import model.Uloga;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping(value="/administrator")
public class AdminController {

	@Autowired
	KorisnikRepository korisnikRep;
	
	@Autowired
	UlogaRepository ulogaRep;
	
	@RequestMapping(value="/radnici", method=RequestMethod.GET)
	public String radnici(HttpServletRequest request) {
		List<Korisnik> radnici = korisnikRep.findByRadnik("radnik");
		
		request.getSession().removeAttribute("radnici");
		if (radnici.size() > 0) {
			request.getSession().setAttribute("radnici", radnici);
		}
		return "sviRadnici";
	}
	
	@RequestMapping(value="/redirekcija", method=RequestMethod.GET)
	public String redirektuj(HttpServletRequest request) {
		Integer idRadnik = Integer.parseInt(request.getParameter("idRadnik"));
		Korisnik radnik = korisnikRep.findById(idRadnik).get();
		
		request.getSession().removeAttribute("porukaRadnik");
		request.getSession().setAttribute("radnik", radnik);
		return "izmeniRadnika";
	}
	
	@RequestMapping(value="/urediRadnika", method=RequestMethod.POST)
	public String izmeniRadnika(HttpServletRequest request) {
		Korisnik radnik = (Korisnik) request.getSession().getAttribute("radnik");
		String ime = request.getParameter("ime");
		String prezime = request.getParameter("prezime");
		Integer godine = Integer.parseInt(request.getParameter("brojGodina"));
		String adresa = request.getParameter("adresa");
		
		request.getSession().removeAttribute("porukaRadnik");
		request.getSession().removeAttribute("porukaDodaj");
		//videcu da li moze da menja sifru, ali vrv ce moci sto da ne??
		radnik.setIme(ime);
		radnik.setPrezime(prezime);
		radnik.setBrojGodina(godine);
		radnik.setAdresa(adresa);
		korisnikRep.save(radnik);
		
		request.getSession().setAttribute("porukaRadnik", "Uspešno ste promenili informacije!");
		return "izmeniRadnika";
	}
	
	@RequestMapping(value="/izbrisiRadnika", method=RequestMethod.POST)
	public String otkazi(HttpServletRequest request) {
		Integer id = Integer.parseInt(request.getParameter("idRadnik"));
		Korisnik radnik = korisnikRep.findById(id).get();
	
		korisnikRep.delete(radnik);
		return "sviRadnici";
	}
	
	@RequestMapping(value="/sacuvajRadnika", method=RequestMethod.POST)
	public String dodajRadnika(HttpServletRequest request) {
		String ime = request.getParameter("ime");
		String prezime = request.getParameter("prezime");
		Integer brojGodina = Integer.parseInt(request.getParameter("brojGodina"));
		String adresa = request.getParameter("adresa");
		String korisnickoIme = request.getParameter("korisnickoIme");
		String sifra = request.getParameter("sifra");
		
		request.getSession().removeAttribute("porukaDodaj");
		Optional<Korisnik> pom = korisnikRep.findByKorisnickoIme(korisnickoIme);
		if ( pom.isPresent()) {
			request.getSession().setAttribute("porukaDodaj", "Postoji već radnik sa ovim korisničkim imenom. Unesite ponovo!");
			return "dodajRadnika";
		}
		//kriptovanje sifre
		BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
		sifra = passEncoder.encode(sifra);
		
		Uloga ulogaRadnika = ulogaRep.findById(2).get();
		Korisnik noviKorisnik = new Korisnik();
		noviKorisnik.setKorisnickoIme(korisnickoIme);
		noviKorisnik.setSifra(sifra);
		noviKorisnik.setIme(ime);
		noviKorisnik.setPrezime(prezime);
		noviKorisnik.setAdresa(adresa);
		noviKorisnik.setBrojGodina(brojGodina);
		noviKorisnik.setUloga(ulogaRadnika);
		
		korisnikRep.save(noviKorisnik);
		
		request.getSession().setAttribute("porukaDodaj", "Uspešno ste sačuvali novog radnika!");		
		return "dodajRadnika";
	}
	
	@RequestMapping(value="/izvestajKorisnika", method=RequestMethod.GET)
	public void izvestajKorisnik(HttpServletRequest request, HttpServletResponse response) throws Exception {
			List<Korisnik> korisnici = korisnikRep.findByUloga(1);
			Korisnik k = (Korisnik) request.getSession().getAttribute("korisnik");
			if (korisnici == null) {
				request.getSession().setAttribute("poruka", "Nema knjiga.");
				return;
			}
			korisnici = korisnici.stream().sorted((s1, s2)->s1.getUloga().getNaziv().compareTo(s2.getUloga().getNaziv())).collect(Collectors.toList());
			
			response.setContentType("text/html");
			JRBeanCollectionDataSource jrb = new JRBeanCollectionDataSource(korisnici);
			InputStream in = this.getClass().getResourceAsStream("/reports/korisniciReport.jrxml");
			
			JasperReport jasperR = JasperCompileManager.compileReport(in);
			
			Map<String, Object> mapa = new HashMap<String, Object>();
			
			String administrator = k.getIme()+" "+k.getPrezime();

			if(korisnici!=null && korisnici.size()>0) {
				mapa.put("administrator", administrator);
				mapa.put("datum", (new SimpleDateFormat("dd-MM-yyyy")).format(new Date()));
			}
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperR, mapa, jrb);
			in.close();
			
			response.setContentType("application/x-download");
			response.addHeader("Content-disposition", "attachment; filename=SpisakKorisnika.pdf");
			OutputStream out = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint,out);
		}
}
