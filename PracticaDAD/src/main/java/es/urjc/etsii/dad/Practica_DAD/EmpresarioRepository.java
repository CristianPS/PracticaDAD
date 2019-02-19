package es.urjc.etsii.dad.Practica_DAD;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresarioRepository extends JpaRepository<Empresario, Long>{

	Empresario getByUsername(String username);
	

}
