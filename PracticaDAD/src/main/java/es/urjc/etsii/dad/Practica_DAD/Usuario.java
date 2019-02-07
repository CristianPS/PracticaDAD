package es.urjc.etsii.dad.Practica_DAD;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String username;
	private String name;
	private String surname;
	private String bornDate;
	private String city;
	private String password;
	private String gender;
	private String email;
	
	//Constructores
	
	public Usuario() {}
	
	public Usuario(String username, String name, String surname, String borndate, String city, String password, String gender, String email)
	{
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.bornDate = borndate;
		this.city = city;
		this.password = password;
		this.gender = gender;
		this.email = email;
	}
	
	//Getters and Setters
	
	public long getId()
	{
		return this.id;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setSurname(String surname)
	{
		this.surname = surname;
	}
	
	public String getSurname()
	{
		return this.surname;
	}
	
	public void setBornDate(String borndate)
	{
		this.bornDate = borndate;
	}
	
	public String getBornDate()
	{
		return this.bornDate;
	}
	
	public void setCity(String city)
	{
		this.city = city;
	}
	
	public String getCity()
	{
		return this.city;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public void setGender(String gender)
	{
		this.gender = gender;
	}
	
	public String getGender()
	{
		return this.gender;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getEmail()
	{
		return this.email;
	}
}
