package es.urjc.etsii.dad.Practica_DAD;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface EmpresarioRepository extends JpaRepository<Empresario, Long>{

	Empresario getByUsername(String username);
	
	Empresario getByEmail(String email);
	
	@Modifying @Transactional
	@Query("update Empresario u set u.name = ?1 where u.username = ?2")
	void setNameByUsername(String name, String username);
	
	@Modifying @Transactional
	@Query("update Empresario u set u.surname = ?1 where u.username = ?2")
	void setSurnameByUsername(String surname, String username);
	
	@Modifying @Transactional
	@Query("update Empresario u set u.gender = ?1 where u.username = ?2")
	void setGenderByUsername(String gender, String username);
	
	@Modifying @Transactional
	@Query("update Empresario u set u.email = ?1 where u.username = ?2")
	void setEmailByUsername(String email, String username);
	
	@Modifying @Transactional
	@Query("update Empresario u set u.city = ?1 where u.username = ?2")
	void setCityByUsername(String city, String username);
	
	@Modifying @Transactional
	@Query("update Empresario u set u.date = ?1 where u.username = ?2")
	void setDateByUsername(String date, String username);
	
	@Modifying @Transactional
	@Query("update Empresario u set u.passwordHash = ?1 where u.username = ?2")
	void setPasswordHashByUsername(String password, String username);
	
	@Modifying @Transactional
	@Query("update Empresario u set u.telephone = ?1 where u.username = ?2")
	void setTelephoneByUsername(String telephone, String username);
	
	@Modifying @Transactional
	@Query("update Empresario u set u.address = ?1 where u.username = ?2")
	void setAddressByUsername(String address, String username);
}
