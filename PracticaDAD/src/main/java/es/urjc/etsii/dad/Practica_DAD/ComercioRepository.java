package es.urjc.etsii.dad.Practica_DAD;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;;

public interface ComercioRepository extends JpaRepository<Comercio, Long> {

	Comercio getByUsername(String username);
	
	@Modifying @Transactional
	@Query("update Comercio u set u.entName = ?1 where u.username = ?2")
	void setEntNameByUsername(String entName, String username);
	
	@Modifying @Transactional
	@Query("update Comercio u set u.telephone = ?1 where u.username = ?2")
	void setTelephoneByUsername(String telephone, String username);
	
	@Modifying @Transactional
	@Query("update Comercio u set u.address = ?1 where u.username = ?2")
	void setAddressByUsername(String address, String username);
	
	@Modifying @Transactional
	@Query("update Comercio u set u.email = ?1 where u.username = ?2")
	void setEmailByUsername(String email, String username);
	
	@Modifying @Transactional
	@Query("update Comercio u set u.city = ?1 where u.username = ?2")
	void setCityByUsername(String city, String username);

	@Modifying @Transactional
	@Query("update Comercio u set u.password = ?1 where u.username = ?2")
	void setPasswordByUsername(String password, String username);
}
