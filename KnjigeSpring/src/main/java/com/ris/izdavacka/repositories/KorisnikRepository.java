package com.ris.izdavacka.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Korisnik;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer>{

	public Optional<Korisnik> findByKorisnickoIme(String ime);
	
	@Query("select k from Korisnik k where not k.uloga.idUloga =:id")
	public List<Korisnik> findByUloga(@Param("id")Integer korisnik);
	
	@Query("select k from Korisnik k where k.uloga.naziv=:naz")
	public List<Korisnik> findByRadnik(@Param("naz")String naziv);
}
