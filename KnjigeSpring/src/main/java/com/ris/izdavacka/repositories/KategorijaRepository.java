package com.ris.izdavacka.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Kategorija;

public interface KategorijaRepository extends JpaRepository<Kategorija, Integer>{

	public Kategorija findByNazivKategorije(String naziv);
}
