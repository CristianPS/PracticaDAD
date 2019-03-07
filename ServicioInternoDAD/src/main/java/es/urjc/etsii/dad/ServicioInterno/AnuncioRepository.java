package es.urjc.etsii.dad.ServicioInterno;

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
	Anuncio getById (long id);
	
	@Modifying @Transactional
	@Query("update Anuncio u set u.title = ?1 where u.id = ?2")
	void setTitleById(String title, long id);
	
	@Modifying @Transactional
	@Query("update Anuncio u set u.description = ?1 where u.id = ?2")
	void setDescriptionById(String description, long id);
	
	@Modifying @Transactional
	@Query("update Anuncio u set u.date = ?1 where u.id = ?2")
	void setDateById(String date, long id);
	

	@Modifying @Transactional
	@Query("update Anuncio u set u.valoracion = ?1 where u.id = ?2")
	void setValoracionById(int valoracion, long id);
	
	@Modifying @Transactional
	@Query("update Anuncio u set u.numValoraciones = ?1 where u.id = ?2")
	void setNumValoracionById(int numValoracion, long id);
	
	@Modifying @Transactional
	@Query("update Anuncio u set u.valoracionMedia = ?1 where u.id = ?2")
	void setValoracionMediaById(int valoracionMedia, long id);
	
	@Modifying @Transactional
	@Query("update Anuncio u set u.image = ?1 where u.id = ?2")
	void setImageById(byte[] image, long id);
	
	@Modifying @Transactional
	@Query("update Anuncio u set u.imageString = ?1 where u.id = ?2")
	void setImageStringById(String imageString, long id);
}
