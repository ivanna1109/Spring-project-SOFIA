package com.ris.izdavacka.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Korisnik;
import model.Narudzbenica;

public interface NarudzbenicaRepository extends JpaRepository<Narudzbenica, Integer>{
	
	public List<Narudzbenica> findByKorisnik(Korisnik k);

}
