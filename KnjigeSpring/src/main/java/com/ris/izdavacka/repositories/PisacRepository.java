package com.ris.izdavacka.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Pisac;

public interface PisacRepository extends JpaRepository<Pisac, Integer>{

	@Query("select p from Pisac p where p.ime like :i and p.prezime like :p")
	public Pisac findByImeIPrezime(@Param("i")String ime, @Param("p")String prezime);
}
