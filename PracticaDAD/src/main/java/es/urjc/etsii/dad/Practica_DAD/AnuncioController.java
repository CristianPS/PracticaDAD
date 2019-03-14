package es.urjc.etsii.dad.Practica_DAD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Base64;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.WebSocketContainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AnuncioController {

	@Autowired
	private AnuncioRepository anuncioRepository;
	@Autowired
	private ComentarioRepository comentarioRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private EmpresarioRepository empresarioRepository;
	
	private String anuncioActual;
	
	@RequestMapping("/CerrarSesion")
	public String CerrarSesion(Model model,  HttpServletRequest request)
	{
		 try {
				request.logout();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return index(model,request);
	}
	@RequestMapping("/")
	public String index(Model model,  HttpServletRequest request)
	{
		List<Anuncio> anuncios = anuncioRepository.findAll();
		if(request.getUserPrincipal() == null)
		{
			
		
				
		Comparator<Anuncio> a = (x, b) -> b.getValoracionMedia() - x.getValoracionMedia();
		anuncios.sort(a);
		
		int numAnuncios = anuncios.size();
		
		List<Anuncio> mejoresAnuncios = new LinkedList<>();
		
		if(numAnuncios < 4)
		{
			mejoresAnuncios = anuncios.subList(0, numAnuncios);
		}
		else
		{
			mejoresAnuncios = anuncios.subList(0, 4);	
		}
		
		model.addAttribute("mejoresAnuncios", mejoresAnuncios);
		 try {
			request.logout();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "index";
		}
		else
		{
			String name = request.getUserPrincipal().getName();
			
			
			if(usuarioRepository.getByUsername(name) != null)
			{
				model.addAttribute("username", request.getUserPrincipal().getName()/*name*/);

				String usuarioActual = name;
				//List<Anuncio> anuncios = anuncioRepository.findAll();


				Comparator<Anuncio> a = (x, b) -> b.getValoracionMedia() - x.getValoracionMedia();
				anuncios.sort(a);

				int numAnuncios = anuncios.size();

				List<Anuncio> mejoresAnuncios = new LinkedList<>();

				if(numAnuncios < 4)
				{
					mejoresAnuncios = anuncios.subList(0, numAnuncios);
				}
				else
				{
					mejoresAnuncios = anuncios.subList(0, 4);
				}

				model.addAttribute("mejoresAnuncios", mejoresAnuncios);

				return "inicioConUsuario";
			}
			else if(empresarioRepository.getByUsername(name) != null)
			{
				model.addAttribute("username", name);

				String empresarioActual = name;
				Empresario e = empresarioRepository.getByUsername(name);
				List<Comercio> comercios = e.getComercios();
				List<Anuncio> anuncios2 = new LinkedList<>();
				EmpresarioController.setEmpresarioActual(name);
				for(Comercio c : comercios)
				{
					anuncios2.addAll(c.getAnuncios());
				}

				model.addAttribute("anuncios", anuncios2);

				return "misOfertas";
			}
			else
			{
				throw new RuntimeException("No hay ningun usuario registrado con ese nombre");
			}
		}
	}
	
	@RequestMapping("/buscar")
	public String buscar(Model model, @RequestParam String searchbar, HttpServletRequest request)
	{
		String name = request.getUserPrincipal().getName();
		List<Anuncio> anunciosAux = anuncioRepository.findAll();
		List<Anuncio> anuncios = new LinkedList<>();
		
		for(Anuncio a : anunciosAux)
		{
			if(a.getTitle().contains(searchbar) || a.getDate().contains(searchbar) ||
					a.getLocal().getEntName().contains(searchbar) || a.getLocal().getCity().contains(searchbar) ||
					a.getTitle().equalsIgnoreCase(searchbar) || a.getDate().equalsIgnoreCase(searchbar) ||
					a.getLocal().getEntName().equalsIgnoreCase(searchbar) || a.getLocal().getCity().equalsIgnoreCase(searchbar))
			{
				anuncios.add(a);
			}
		}
		
		model.addAttribute("anuncios", anuncios);
		model.addAttribute("username", name);
		model.addAttribute("busqueda", "Ofertas / " + searchbar);
		return "ofertas";
	}
	
	@RequestMapping("/guardarAnuncio")
	public String guardarAnuncio(Model model, @RequestParam String title, @RequestParam String description, @RequestParam String date, @RequestParam MultipartFile selectFile, HttpServletRequest request) throws IOException
	{
		Anuncio a = anuncioRepository.getByTitle(anuncioActual);
		
		//anuncioRepository.setTitleById(title, a.getId());
		//anuncioRepository.setDescriptionById(description, a.getId());
		//anuncioRepository.setDateById(date, a.getId());
		
		byte[] encImage = Base64.getEncoder().encode(selectFile.getBytes());
		
		a.setTitle(title);
		a.setDescription(description);
		a.setDate(date);
		
		if(!selectFile.isEmpty())
		{
			a.setImage(encImage);
			a.setImageString(new String(encImage));
		}
		
		anuncioRepository.save(a);
		
		//return mostrarAnuncioPropio(model, title, description, date);
		return mostrarAnuncioPropio(model, title, request);
	}
	
	@RequestMapping("/mostrarAnuncios")
	public String mostrarAnuncios(Model model, HttpServletRequest request)
	{
		String name = request.getUserPrincipal().getName();
		List<Anuncio> anuncios = anuncioRepository.findAll();
		model.addAttribute("anuncios", anuncios);
		model.addAttribute("username", name);
		model.addAttribute("busqueda", "Todas las ofertas");
		return "ofertas";
	}
	
	@RequestMapping("/mostrarAnuncio")
	public String mostrarAnuncio(Model model, @RequestParam String title, HttpServletRequest request)
	{
		String name = request.getUserPrincipal().getName(); 
		Anuncio a = anuncioRepository.getByTitle(title);
		
		model.addAttribute("username", name);

		model.addAttribute("ent", a.getLocal().getEntName());
		model.addAttribute("description", a.getDescription());
		model.addAttribute("title",title);
		model.addAttribute("city", a.getLocal().getCity());
		model.addAttribute("address", a.getLocal().getAddress());
		model.addAttribute("email", a.getLocal().getEmail());
		model.addAttribute("telephone", a.getLocal().getTelephone());
		model.addAttribute("date", a.getDate());
		model.addAttribute("imageString", a.getImageString());
		model.addAttribute("offerId", a.getId());
		if(a.getNumValoraciones() != 0)
			model.addAttribute("valoracion", a.getValoracion()/a.getNumValoraciones());
		else
			model.addAttribute("valoracion", 0);
		
		List<Comentario> comentarios = a.getComments();
		
		model.addAttribute("comentarios", comentarios);

		return "ofertaParticular"; 
	}
	
	@RequestMapping("/mostrarAnuncioPropio")
	public String mostrarAnuncioPropio(Model model, @RequestParam String title, HttpServletRequest request)
	{
		String name = request.getUserPrincipal().getName();
		Anuncio a = anuncioRepository.getByTitle(title);
		
		model.addAttribute("username", name);

		model.addAttribute("ent", a.getLocal().getEntName());
		model.addAttribute("description", a.getDescription());
		model.addAttribute("title", a.getTitle());
		model.addAttribute("city", a.getLocal().getCity());
		model.addAttribute("address", a.getLocal().getAddress());
		model.addAttribute("email", a.getLocal().getEmail());
		model.addAttribute("telephone", a.getLocal().getTelephone());
		model.addAttribute("date", a.getDate());
		model.addAttribute("imageString", a.getImageString());
		
		if(a.getNumValoraciones() != 0)
			model.addAttribute("valoracion", a.getValoracion()/a.getNumValoraciones());
		else
			model.addAttribute("valoracion", a.getValoracion());

		List<Comentario> comentarios = a.getComments();
		
		model.addAttribute("comentarios", comentarios);
		
		anuncioActual = title;
		
		return "OfertaPropia";
	}

	@RequestMapping("/nuevaOferta")
	public String nuevaOferta(Model model, @RequestParam String entName, HttpServletRequest request)
	{
		String name = request.getUserPrincipal().getName();
		model.addAttribute("entName", entName);
		model.addAttribute("username", name);
		return "nuevaOferta";
	}
	
	@RequestMapping("/valorar")
	public String valorar(Model model, @RequestParam String title, @RequestParam int valoracion, HttpServletRequest request)
	{
		Anuncio a = anuncioRepository.getByTitle(title);
		int val = a.getValoracion();
		int numVal = a.getNumValoraciones();
		
		/*anuncioRepository.setValoracionById((valoracion+val), a.getId());
		anuncioRepository.setNumValoracionById((1+numVal), a.getId());*/
		
		a.setValoracion(valoracion+val);
		a.setNumValoraciones(numVal+1);
		
		int valoracionMedia = (valoracion+val)/(numVal+1);
		
		//anuncioRepository.setValoracionMediaById(valoracionMedia, a.getId());
		
		a.setValoracionMedia(valoracionMedia);
		anuncioRepository.save(a);
		
		//return mostrarAnuncio2(model, title, valoracionMedia);
		return mostrarAnuncio(model, title, request);		
	}
	
	@RequestMapping("/obtenerOferta")
	public String obtenerOferta(Model model, @RequestParam String title, HttpServletRequest request) throws UnknownHostException, IOException
	{	
		String name = request.getUserPrincipal().getName();
		Usuario u = usuarioRepository.getByUsername(name);
		Anuncio a = anuncioRepository.getByTitle(title);
		/*
		//SocketFactory socketFactory = SSLSocketFactory.getDefault();
		//SSLSocket socket = (SSLSocket) socketFactory.createSocket("127.0.0.1", 7777);
		Socket socket = new Socket("127.0.0.1", 7777);
		//BufferedReader leerServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		OutputStream os = socket.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
				
		
		/*oos.writeLong(u.getId());
		oos.flush();
		
		oos.writeLong(anuncioRepository.getByTitle(title).getId());
		oos.flush();
		
		oos.writeObject((String) u.getEmail());
		oos.flush();
		
		oos.writeObject((String) a.getTitle());
		oos.flush();
		
		oos.writeObject((String) a.getLocal().getEntName());
		oos.flush();
		
		oos.writeObject((String) a.getLocal().getAddress());
		oos.flush();
		
		oos.writeObject((String) a.getLocal().getCity());
		oos.flush();
		
		oos.writeObject((String) a.getDate());
		oos.flush();
		
		oos.close();
		
		
		os.close();
		socket.close();*/
		
		model.addAttribute("username", name);
		model.addAttribute("email", u.getEmail());
		model.addAttribute("title", title);
		
		return "verificacionObtenerOferta";
	}
	
	@RequestMapping("/añadirComentario")
	public String añadirComentario(Model model, @RequestParam String title, @RequestParam String addComment, HttpServletRequest request)
	{
		String name = request.getUserPrincipal().getName();
		Usuario u = usuarioRepository.getByUsername(name);
		Anuncio a = anuncioRepository.getByTitle(title);
		Comentario c = new Comentario(u, addComment, a);
		comentarioRepository.save(c);
		
		return mostrarAnuncio(model, title, request);
	}
	@RequestMapping("/eliminarComentario")
	public String eliminarComentario(Model model, @RequestParam String title, @RequestParam String comment, HttpServletRequest request)
	{
		String name = request.getUserPrincipal().getName();
		Anuncio a = anuncioRepository.getByTitle(title);
		Comentario c = comentarioRepository.getByComment(comment);
		
		if(name.equals(c.getUser().getUsername()))
		{
			a.getComments().remove(c);
			comentarioRepository.delete(c);
		}
		
		return mostrarAnuncio(model, title, request);
	}
}
