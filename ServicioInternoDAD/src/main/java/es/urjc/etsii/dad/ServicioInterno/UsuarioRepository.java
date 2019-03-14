package es.urjc.etsii.dad.ServicioInterno;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	List<Usuario> findByUsername(String username);
	

	
	@Modifying @Transactional
	@Query("update Usuario u set u.name = ?1 where u.username = ?2")
	void setNameByUsername(String name, String username);
	
	@Modifying @Transactional
	@Query("update Usuario u set u.surname = ?1 where u.username = ?2")
	void setSurnameByUsername(String surname, String username);
	
	@Modifying @Transactional
	@Query("update Usuario u set u.gender = ?1 where u.username = ?2")
	void setGenderByUsername(String gender, String username);
	
	@Modifying @Transactional
	@Query("update Usuario u set u.email = ?1 where u.username = ?2")
	void setEmailByUsername(String email, String username);
	
	@Modifying @Transactional
	@Query("update Usuario u set u.city = ?1 where u.username = ?2")
	void setCityByUsername(String city, String username);
	/*
	@Modifying @Transactional
	@Query("update Usuario u set u.borndate = ?1 where u.username = ?2")
	void setBorndateByUsername(String borndate, String username);
	*/
	@Modifying @Transactional
	@Query("update Usuario u set u.passwordHash = ?1 where u.username = ?2")
	void setPasswordByUsername(String password, String username);
	
	
	Usuario getByUsername(String username);
	
	Usuario getByEmail(String email);
	
	 Usuario getById(long id);
}
