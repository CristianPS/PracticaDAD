package es.urjc.etsii.dad.Practica_DAD;


public class Comentario {

	private Usuario user;
	private String comment;
	
	public Comentario(Usuario user, String comment)
	{
		this.user = user;
		this.comment = comment;
	}
	
	public Usuario getUser()
	{
		return user;
	}
	
	public String getComment()
	{
		return comment;
	}
	
	public void setUser(Usuario user)
	{
		this.user = user;
	}
	
	public void setComment(String comment)
	{
		this.comment = comment;
	}
}
