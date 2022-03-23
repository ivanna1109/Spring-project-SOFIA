package com.ris.izdavacka.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Knjizevnovece;

public interface KnjizevnoVeceRepository extends JpaRepository<Knjizevnovece, Integer>{

	@Query("select v from Knjizevnovece v where v.datum >= :dat")
	public List<Knjizevnovece> findByPosleDatuma(@Param("dat")Date datum);
	
	@Query("select v from Knjizevnovece v inner join v.korisniks k where k.idKorisnik like :id")
	public List<Knjizevnovece> findByKorisnik(@Param("id")Integer idKorisnik);
}
