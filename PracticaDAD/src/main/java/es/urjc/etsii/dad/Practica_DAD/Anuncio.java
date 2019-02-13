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

@Entity
public class Anuncio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String title;
	private String description;
	//private List<Comentario> comments;
	
	@ManyToOne
	private Comercio local;
	//private List<Image> images;
	
	
	public Anuncio() {}
	
	public Anuncio(/*long id,*/ String title, String description, Comercio local/*, List<Image> images*/)
	{
		/*this.id=id;*/
		this.title=title;
		this.description=description;
		this.local=local;
		//this.comments=new LinkedList<>();
		//this.images=images;
	}
	
	/*public void setId(long id)
	{
		this.id=id;
	}*/
	
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
	
	/*public void setComments(List<Comentario> comments)
	{
		this.comments=comments;
	}*/
	/*
	public void setImages(List<Image> images)
	{
		this.images=images;
	}*/
	
	/*public long getId()
	{
		return id;
	}*/
	
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
	
	/*public List<Comentario> getComments()
	{
		return comments;
	}*/
	/*
	public List<Image> getImages()
	{
		return images;
	}*/

}

