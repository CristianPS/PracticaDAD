package es.urjc.etsii.dad.ServicioInterno;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Comercio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	//private String username;
	//private String password;
	private String entName;
	private String city;
	private String address;
	private String email;
	private String telephone;
	
	@OneToMany(mappedBy="local")
	private List<Anuncio> anuncios; 
	
	@ManyToOne
	private Empresario owner;
	
	//Constructors
	public Comercio() 
	{
		//anuncios = new LinkedList<>();
	}
	
	public Comercio(/*long id,*/ /*String username, String password, */String entName, String city, String address, String email, String telephone, Empresario owner)
	{
		//this.id=id;
		//this.username=username;
		//this.password=password;
		this.entName=entName;
		this.city=city;
		this.address=address;
		this.email=email;
		this.telephone=telephone;
		this.owner = owner;
	}
	
	//Setters
	
	/*public void setId(long id)
	{
		this.id=id;
	}*/
	
	/*public void setUsername(String username)
	{
		this.username=username;
	}*/
	
	/*public void setPassword(String password)
	{
		this.password=password;
	}*/
	
	public void setEntName(String entName)
	{
		this.entName=entName;
	}
	
	public void setCity(String city)
	{
		this.city=city;
	}
	
	public void setAddress(String address)
	{
		this.address=address;
	}
	
	public void setEmail(String email)
	{
		this.email=email;
	}
	
	public void setTelephone(String telephone)
	{
		this.telephone=telephone;
	}
	
	public void setAnuncios(List<Anuncio> anuncios)
	{
		this.anuncios = anuncios;
	}
	
	public void setOwner(Empresario o)
	{
		this.owner = o;
	}
	
	//Getters
	
	/*public String getUsername()
	{
		return username;
	}*/
	
	/*public long getId()
	{
		return id;
	}*/
	
	/*public String getPassword()
	{
		return password;
	}*/
	
	public String getEntName()
	{
		return entName;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getTelephone()
	{
		return telephone;
	}
	
	public List<Anuncio> getAnuncios()
	{
		return this.anuncios;
	}
	
	public Empresario getOwner()
	{
		return this.owner;
	}
}
