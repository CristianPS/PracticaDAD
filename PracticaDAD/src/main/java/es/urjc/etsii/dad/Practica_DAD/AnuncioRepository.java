package es.urjc.etsii.dad.Practica_DAD;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.transaction.annotation.Transactional;;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AnuncioRepository extends JpaRepository<Anuncio, Long>{
	
	List<Anuncio> findByTitle(String title);
	
	Anuncio getByTitle(String title);
	
	@Modifying @Transactional
	@Query("update Anuncio u set u.title = ?1 where u.id = ?2")
	void setTitleById(String title, long id);
	
	@Modifying @Transactional
	@Query("update Anuncio u set u.description = ?1 where u.id = ?2")
	void setDescriptionById(String description, long id);
	
}
