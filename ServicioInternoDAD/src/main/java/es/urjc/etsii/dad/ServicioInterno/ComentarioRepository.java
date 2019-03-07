package es.urjc.etsii.dad.ServicioInterno;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long>{
	
	List<Comentario> findByAnuncio(Anuncio anuncio);
	
	Comentario getByAnuncio(Anuncio anuncio);
	
	Comentario getByComment(String comment);
	
	
}
