package es.urjc.etsii.dad.Practica_DAD;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnuncioRepository extends JpaRepository<Anuncio, Long>{
	
	List<Anuncio> findByTitle(String title);
	
	Anuncio getByTitle(String title);
}
