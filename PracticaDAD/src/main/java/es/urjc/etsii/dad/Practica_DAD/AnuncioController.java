package es.urjc.etsii.dad.Practica_DAD;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnuncioController {

	@Autowired
	private AnuncioRepository anuncioRepository;
	@Autowired
	private ComentarioRepository comentarioRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private String anuncioActual;
	
	@RequestMapping("/")
	public String index(Model model)
	{
		List<Anuncio> anuncios = anuncioRepository.findAll();
		
		
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
		
		return "index";
	}
	
	@RequestMapping("/guardarAnuncio")
	public String guardarAnuncio(Model model, @RequestParam String title, @RequestParam String description, @RequestParam String date)
	{
		Anuncio a = anuncioRepository.getByTitle(anuncioActual);
		
		//anuncioRepository.setTitleById(title, a.getId());
		//anuncioRepository.setDescriptionById(description, a.getId());
		//anuncioRepository.setDateById(date, a.getId());
		
		a.setTitle(title);
		a.setDescription(description);
		a.setDate(date);
		anuncioRepository.save(a);
		
		//return mostrarAnuncioPropio(model, title, description, date);
		return mostrarAnuncioPropio(model, title);
	}
	
	@RequestMapping("/mostrarAnuncios")
	public String mostrarAnuncios(Model model, @RequestParam String username)
	{
		List<Anuncio> anuncios = anuncioRepository.findAll();
		model.addAttribute("anuncios", anuncios);
		model.addAttribute("username", username);
		return "ofertas";
	}
	
	@RequestMapping("/mostrarAnuncio")
	public String mostrarAnuncio(Model model, @RequestParam String title)
	{
		Anuncio a = anuncioRepository.getByTitle(title);
		
		model.addAttribute("username", UsuarioController.getUsuarioActual());

		model.addAttribute("ent", a.getLocal().getEntName());
		model.addAttribute("description", a.getDescription());
		model.addAttribute("title",title);
		model.addAttribute("city", a.getLocal().getCity());
		model.addAttribute("address", a.getLocal().getAddress());
		model.addAttribute("email", a.getLocal().getEmail());
		model.addAttribute("telephone", a.getLocal().getTelephone());
		model.addAttribute("date", a.getDate());
		model.addAttribute("imageString", a.getImageString());
		
		if(a.getNumValoraciones() != 0)
			model.addAttribute("valoracion", a.getValoracion()/a.getNumValoraciones());
		else
			model.addAttribute("valoracion", 0);
		
		List<Comentario> comentarios = a.getComments();
		
		model.addAttribute("comentarios", comentarios);

		return "ofertaParticular"; 
	}
	
	@RequestMapping("/mostrarAnuncioPropio")
	public String mostrarAnuncioPropio(Model model, @RequestParam String title)
	{
		Anuncio a = anuncioRepository.getByTitle(title);
		
		model.addAttribute("username", EmpresarioController.getEmpresarioActual());

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
	public String nuevaOferta(Model model, @RequestParam String entName)
	{
		model.addAttribute("entName", entName);
		model.addAttribute("username", EmpresarioController.getEmpresarioActual());
		return "nuevaOferta";
	}
	
	@RequestMapping("/valorar")
	public String valorar(Model model, @RequestParam String title, @RequestParam int valoracion)
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
		return mostrarAnuncio(model, title);
	}
	
	@RequestMapping("/añadirComentario")
	public String añadirComentario(Model model, @RequestParam String title, @RequestParam String addComment)
	{
		Usuario u = usuarioRepository.getByUsername(UsuarioController.getUsuarioActual());
		Anuncio a = anuncioRepository.getByTitle(title);
		Comentario c = new Comentario(u, addComment, a);
		comentarioRepository.save(c);
		
		return mostrarAnuncio(model, title);
	}
	@RequestMapping("/eliminarComentario")
	public String eliminarComentario(Model model, @RequestParam String title, @RequestParam String comment )
	{
		Anuncio a = anuncioRepository.getByTitle(title);
		Comentario c = comentarioRepository.getByComment(comment);
		
		if(UsuarioController.getUsuarioActual().equals(c.getUser().getUsername()))
		{
			a.getComments().remove(c);
			comentarioRepository.delete(c);
		}
		
		return mostrarAnuncio(model, title);
	}
}
