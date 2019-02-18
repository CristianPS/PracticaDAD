package es.urjc.etsii.dad.Practica_DAD;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Anuncio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String title;
	private String description;
	private int valoracion;
	private int numValoraciones;
	private String date;
	private int valoracionMedia;
	
	@ManyToOne
	private Comercio local;
	//private List<Image> images;
	@OneToMany(mappedBy="anuncio")
	private List<Comentario> comments;
	
	public Anuncio() {}
	
	public Anuncio(/*long id,*/ String title, String description, Comercio local, String date/*, List<Image> images*/)
	{
		this.title=title;
		this.description=description;
		this.local=local;
		this.valoracion = 0;
		this.numValoraciones = 0;
		this.comments=new LinkedList<>();
		this.date = date;
		this.valoracionMedia = 0;
		//this.images=images;
	}

	public void setTitle(String title)
	{
		this.title=title;
	}
	
	public void setDescription(String description)
	{
		this.description=description;
	}
	
	public void setLocal(Comercio local)
	{
		this.local=local;
	}
	
	public void setValoracion(int val)
	{
		this.valoracion = val;
	}
	
	public void setNumValoraciones(int val)
	{
		this.numValoraciones = val;
	}
	
	public void setComments(List<Comentario> comments)
	{
		this.comments=comments;
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}
	
	public void setValoracionMedia(int valoracionMedia)
	{
		this.valoracionMedia = valoracionMedia;
	}
	/*
	public void setImages(List<Image> images)
	{
		this.images=images;
	}*/
	
	public long getId() {
		return this.id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public Comercio getLocal()
	{
		return local;
	}
	
	public int getValoracion()
	{
		return this.valoracion;
	}
	
	public int getNumValoraciones()
	{
		return this.numValoraciones;
	}
	
	public List<Comentario> getComments()
	{
		return comments;
	}
	
	public String getDate()
	{
		return this.date;
	}
	
	public int getValoracionMedia()
	{
		return this.valoracionMedia;
	}
	
	public void updateValoracionMedia()
	{
		this.valoracionMedia = this.valoracion/this.numValoraciones;
	}
	/*
	public List<Image> getImages()
	{
		return images;
	}*/

}

