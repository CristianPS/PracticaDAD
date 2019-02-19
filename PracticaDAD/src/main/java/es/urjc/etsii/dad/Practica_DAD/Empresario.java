package es.urjc.etsii.dad.Practica_DAD;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Empresario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String username;
	private String password;
	private String name;
	private String city;
	private String surname;
	private String address;
	private String email;
	private String telephone;
	private String date;
	private String gender;
	
	@OneToMany(mappedBy="owner")
	private List<Comercio> comercios;
	
	public Empresario() {}
	
	public Empresario(String username, String name, String surname, String password, String city, String address, String email, String telephone, String date, String gender)
	{
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.city = city;
		this.address = address;
		this.email = email;
		this.telephone = telephone;
		this.date = date;
		this.gender = gender;
		comercios = new LinkedList<>();
	}
	
	//Getters
	
	public String getUsername()
	{
		return this.username;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getSurname()
	{
		return this.surname;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public String getCity()
	{
		return this.city;
	}
	
	public String getAddress()
	{
		return this.address;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public String getTelephone()
	{
		return this.telephone;
	}
	
	public String getDate()
	{
		return this.date;
	}
	
	public String getGender()
	{
		return this.gender;
	}
	
	public List<Comercio> getComercios()
	{
		return this.comercios;
	}
	
	//Setters
	
	public void setUsername(String u)
	{
		this.username = u;
	}
	
	public void setName(String n)
	{
		this.name = n;
	}
	
	public void setSurname(String s)
	{
		this.surname = s;
	}
	
	public void setPassword(String p)
	{
		this.password = p;
	}
	
	public void setCity(String c)
	{
		this.city = c;
	}
	
	public void setAddress(String a)
	{
		this.address = a;
	}
	
	public void setEmail(String e)
	{
		this.email = e;
	}
	
	public void setTelephone(String t)
	{
		this.telephone = t;
	}
	
	public void setDate(String d)
	{
		this.date = d;
	}
	
	public void setGender(String g)
	{
		this.gender = g;
	}
	
	public void setComercios(List<Comercio> c)
	{
		this.comercios = c;
	}
}
