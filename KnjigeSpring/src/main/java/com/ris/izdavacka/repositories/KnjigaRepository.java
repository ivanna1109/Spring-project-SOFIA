package com.ris.izdavacka.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Knjiga;

public interface KnjigaRepository extends JpaRepository<Knjiga, Integer>{

	@Query("select k from Knjiga k inner join k.kategorijas kat where kat.idKategorija = :id")
	public List<Knjiga> findByKategorija(@Param("id")Integer kat);
	
	@Query("select k from Knjiga k inner join k.korisniks kor where kor.idKorisnik like :id")
	public List<Knjiga> findByKorisnik(@Param("id")Integer kor);
	
	public Knjiga findByNaziv(String naziv);
	
}
