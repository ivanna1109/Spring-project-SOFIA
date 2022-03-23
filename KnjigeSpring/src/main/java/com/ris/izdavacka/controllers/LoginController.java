package com.ris.izdavacka.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ris.izdavacka.repositories.KategorijaRepository;
import com.ris.izdavacka.repositories.KorisnikRepository;
import com.ris.izdavacka.repositories.UlogaRepository;

import model.Kategorija;
import model.Korisnik;


@Controller
@RequestMapping(value="/auth")
public class LoginController {
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	KorisnikRepository kr;
	
	@Autowired
	UlogaRepository ur;
	
	@Autowired
	KategorijaRepository katRep;

	@RequestMapping(value="/guest", method=RequestMethod.GET)
	public String login(Principal principal) {
		String username = principal.getName();
		Korisnik kor = kr.findByKorisnickoIme(username).get();
		request.getSession().setAttribute("korisnik", kor);
		//dobavljamo sve kategorije i sve korisnike i ubacujemo u sesiju
		List<Kategorija> kategorije = katRep.findAll();
		List<Korisnik> korisnici = kr.findByUloga(1);
		if ( kategorije != null && kategorije.size() > 0) {
			request.getSession().setAttribute("kategorije", kategorije);
			request.getSession().setAttribute("sviKorisnici", korisnici);
		}			
		return "index";
	}
	
	@RequestMapping(value="/registracija", method=RequestMethod.POST)
	public String registracija() {
		String korisnicko = request.getParameter("username");
		String sifra = request.getParameter("password");
		String ime = request.getParameter("name");
		String prezime = request.getParameter("surname");
		Integer godine = Integer.parseInt(request.getParameter("ages"));
		String adresa = request.getParameter("address");
		
		request.getSession().getAttribute("porukaReg");
		Korisnik pom = kr.findByKorisnickoIme(korisnicko).get();
		if (pom != null) {
			request.getSession().setAttribute("porukaReg", "Korisničko ime je zauzeto!");
			return "registracija";
		}
		Korisnik k = new Korisnik();
		BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
		sifra = passEncoder.encode(sifra);
		
		k.setKorisnickoIme(korisnicko);
		k.setSifra(sifra);
		k.setIme(ime);
		k.setPrezime(prezime);
		k.setBrojGodina(godine);
		k.setAdresa(adresa);
		
		k.setUloga(ur.findById(3).get());
		
		kr.save(k);
		
		return "login";
		
		
	}
	
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "login";
    }
    
}
