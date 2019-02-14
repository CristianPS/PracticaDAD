package es.urjc.etsii.dad.Practica_DAD;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Comentario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne
	private Usuario user;
	private String comment;
	@ManyToOne
	private Anuncio anuncio;
	
	public Comentario() {}
	
	public Comentario(Usuario user, String comment, Anuncio anuncio)
	{
		this.user = user;
		this.comment = comment;
		this.anuncio = anuncio;
	}
	
	public Usuario getUser()
	{
		return user;
	}
	
	public String getComment()
	{
		return comment;
	}
	
	public Anuncio getAnuncio()
	{
		return anuncio;
	}
	
	public void setUser(Usuario user)
	{
		this.user=user;
	}
	
	public void setComment(String comment)
	{
		this.comment=comment;
	}
	
	public void setAnuncio(Anuncio anuncio)
	{
		this.anuncio=anuncio;
	}
}
