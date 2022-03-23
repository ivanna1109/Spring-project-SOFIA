package com.ris.izdavacka.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Clanskakarta;
//import model.Klubcitalaca;

public interface ClanskaKartaRepository extends JpaRepository<Clanskakarta, Integer>{

	@Query("select c from Clanskakarta c where c.korisnik.idKorisnik = :id")
	public List<Clanskakarta> findByKorisnik(@Param("id")Integer idKorisnik);
}
