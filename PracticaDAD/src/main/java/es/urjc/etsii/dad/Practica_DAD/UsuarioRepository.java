package es.urjc.etsii.dad.Practica_DAD;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	List<Usuario> findByUsername(String username);
	
	Usuario getByUsername(String username);
}
