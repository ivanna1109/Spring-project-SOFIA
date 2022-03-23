package com.ris.izdavacka.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Klubcitalaca;

public interface KlubCitalacaRepository extends JpaRepository<Klubcitalaca, Integer> {
	
	@Query("select k from Klubcitalaca k inner join k.clanskakartas c where c.korisnik.idKorisnik = :id")
	public List<Klubcitalaca> findByKorisnik(@Param("id")Integer idKorisnik);

}
