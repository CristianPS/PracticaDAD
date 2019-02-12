package es.urjc.etsii.dad.Practica_DAD;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

	@Autowired
	private UsuarioRepository userRepository;
	@Autowired
	private AnuncioRepository anuncioRepository;
	@Autowired
	private ComercioRepository comercioRepository;
	
	/*@PostConstruct
	public void init()
	{
		userRepository.save(new Usuario("CristianPS","Cristian","Posada Santos","01/08/1997","Madrid","contraseña","Hombre","c.posada@alumnos.urjc.es"));
		userRepository.save(new Usuario("SitoDiaz","Jose Ignacio","Diaz Errejon","13/07/97","Sevilla la Nueva","contraseña","Hombre","ji.diaze@alumnos.urjc.es"));
		
		comercioRepository.save(new Comercio("CarlosGil","comercio","Fabrik","Madrid","C/AlcaldeMostoles,5","c.gilsab@alumnos.urjc.es","918170864"));
		comercioRepository.save(new Comercio("JorgePRG","comercio","Anubis","Arroyomolinos","CC.Xanadu","j.prietogo@alumnos.urjc.es","918146753"));
		
		Comercio c1 = comercioRepository.getByUsername("CarlosGil");
		Comercio c2 = comercioRepository.getByUsername("JorgePRG");
		
		Anuncio a1 = new Anuncio();
		a1.setTitle("Entrada-10Euros");
		a1.setDescription("XXX");
		a1.setLocal(c1);
		
		Anuncio a2 = new Anuncio();
		a2.setTitle("CachimbaPremium-11Euros");
		a2.setDescription("XXX");
		a2.setLocal(c2);
			
		anuncioRepository.save(a1);
		anuncioRepository.save(a2);
		
		c1.getAnuncios().add(a1);
		c2.getAnuncios().add(a2);
		
	}*/

	@RequestMapping("/registroUsuario")
	public String registroUsuario(Model model, @RequestParam String username, @RequestParam String name, @RequestParam String apellidos, @RequestParam String email, @RequestParam String fecha, @RequestParam String genero, @RequestParam String city, @RequestParam String password) {

		//Ademas aqui deberiamos insertar todos los elementos obtenidos a la base de datos
		
		Usuario u = new Usuario(username, name, apellidos, fecha, city, password, genero, email);
		
		userRepository.save(u);
		
		model.addAttribute("username", username);

		return "inicioConUsuario";
	}
	
	@RequestMapping("/inicioUsuario")
	public String inicioUsuario(Model model, @RequestParam String name) {

		model.addAttribute("username", name);

		return "inicioConUsuario";
	}
	
	@RequestMapping("/mostrarPerfil")
	public String mostrarPerfil(Model model, @RequestParam String username)
	{
		Usuario u = userRepository.getByUsername(username);
		
		model.addAttribute("username", u.getUsername());
		model.addAttribute("nombre", u.getName());
		model.addAttribute("apellidos", u.getSurname());
		model.addAttribute("correo", u.getEmail());
		model.addAttribute("ciudad", u.getCity());
		model.addAttribute("password", u.getPassword());
		
		return "perfil_usuario";
	}
	
	@RequestMapping("/mostrarAnuncios")
	public String mostrarAnuncios(Model model)
	{
		List<Anuncio> anuncios = anuncioRepository.findAll();
		model.addAttribute("anuncios", anuncios);
		return "ofertas";
	}
	
	@RequestMapping("/mostrarAnuncio")
	public String mostrarAnuncio(Model model, @RequestParam String title)
	{
		Anuncio a = anuncioRepository.getByTitle(title);
		
		model.addAttribute("ent", a.getLocal().getEntName());
		model.addAttribute("description", a.getDescription());
		model.addAttribute("title",title);
		model.addAttribute("city", a.getLocal().getCity());
		model.addAttribute("address", a.getLocal().getAddress());
		model.addAttribute("email", a.getLocal().getEmail());
		model.addAttribute("telephone", a.getLocal().getTelephone());
		
		return "ofertaParticular";
	}
}