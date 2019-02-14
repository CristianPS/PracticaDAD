package es.urjc.etsii.dad.Practica_DAD;

import java.awt.Image;
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

	public String convertirFecha(String fecha)
	{
		String day = "";
		String month = "";
		String year = "";
		String aux = "";
		int numGuiones = 0;

		for(int i=0; i<fecha.length(); i++)
		{
			if(numGuiones == 1)
			{
				if(fecha.charAt(i) != '-')
					month = month + fecha.charAt(i);
				else
					numGuiones++;
			}
			else if(numGuiones == 2)
			{
				day = day + fecha.charAt(i);
			}
			else
			{
				if(fecha.charAt(i) != '-')
					year = year + fecha.charAt(i);
				else
					numGuiones++;
			}
		}

		aux = day + "-" + month + "-" + year;
		return aux;
	}

	@RequestMapping("/registroUsuario")
	public String registroUsuario(Model model, @RequestParam String username, @RequestParam String name, @RequestParam String apellidos, @RequestParam String email, @RequestParam String fecha, @RequestParam String genero, @RequestParam String city, @RequestParam String password) {

		//Ademas aqui deberiamos insertar todos los elementos obtenidos a la base de datos

		String aux = convertirFecha(fecha);

		Usuario u = new Usuario(username, name, apellidos, aux, city, password, genero, email);

		userRepository.save(u);

		model.addAttribute("username", username);

		return "inicioConUsuario";
	}
	@RequestMapping("/registroComercio")
	public String registroComercio(Model model, @RequestParam String username, @RequestParam String nameEmpresa, @RequestParam String dir, @RequestParam String email, @RequestParam String fecha, @RequestParam String telefono, @RequestParam String city, @RequestParam String password) {

		//Ademas aqui deberiamos insertar todos los elementos obtenidos a la base de datos
		
		
		Comercio c = new Comercio(username, password, nameEmpresa, city, dir, email, telefono);
		
		comercioRepository.save(c);
		
		model.addAttribute("username", username);

		return "misOfertas";
	}

	@RequestMapping("/inicioUsuario")
	public String inicioUsuario(Model model, @RequestParam String name) {

		model.addAttribute("username", name);

		return "inicioConUsuario";
	}

	@RequestMapping("/inicioComercio")
	public String inicioComercio(Model model, @RequestParam String name) {

		Comercio c = comercioRepository.getByUsername(name);

		List<Anuncio> anuncios = c.getAnuncios();

		model.addAttribute("anuncios", anuncios);

		model.addAttribute("username", name);

		return "misOfertas";
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
		model.addAttribute("gender", u.getGender());
		model.addAttribute("fecha", u.getBornDate());

		return "perfil_usuario";
	}

	@RequestMapping("/mostrarAnuncios")
	public String mostrarAnuncios(Model model, @RequestParam String username)
	{
		List<Anuncio> anuncios = anuncioRepository.findAll();
		model.addAttribute("anuncios", anuncios);
		model.addAttribute("username",username);
		return "ofertas";
	}

	@RequestMapping("/mostrarAnuncio")
	public String mostrarAnuncio(Model model, @RequestParam String title, @RequestParam String username)
	{
		Anuncio a = anuncioRepository.getByTitle(title);
		
		model.addAttribute("username", username);

		model.addAttribute("ent", a.getLocal().getEntName());
		model.addAttribute("description", a.getDescription());
		model.addAttribute("title",title);
		model.addAttribute("city", a.getLocal().getCity());
		model.addAttribute("address", a.getLocal().getAddress());
		model.addAttribute("email", a.getLocal().getEmail());
		model.addAttribute("telephone", a.getLocal().getTelephone());
		
		if(a.getNumValoraciones() != 0)
			model.addAttribute("valoracion", a.getValoracion()/a.getNumValoraciones());
		else
			model.addAttribute("valoracion", a.getValoracion());

		return "ofertaParticular";
	}
	
	@RequestMapping("/mostrarAnuncioPropio")
	public String mostrarAnuncioPropio(Model model, @RequestParam String title, @RequestParam String username)
	{
		Anuncio a = anuncioRepository.getByTitle(title);
		
		model.addAttribute("username", username);

		model.addAttribute("ent", a.getLocal().getEntName());
		model.addAttribute("description", a.getDescription());
		model.addAttribute("title",title);
		model.addAttribute("city", a.getLocal().getCity());
		model.addAttribute("address", a.getLocal().getAddress());
		model.addAttribute("email", a.getLocal().getEmail());
		model.addAttribute("telephone", a.getLocal().getTelephone());
		
		if(a.getNumValoraciones() != 0)
			model.addAttribute("valoracion", a.getValoracion()/a.getNumValoraciones());
		else
			model.addAttribute("valoracion", a.getValoracion());

		return "OfertaPropia";
	}

	@RequestMapping("/guardar")
	public String guardar(Model model, @RequestParam String username, @RequestParam String nombre, @RequestParam String apellidos, @RequestParam String correo, @RequestParam String ciudad, @RequestParam String fecha, @RequestParam String gender, @RequestParam String password, @RequestParam String passwordNew, @RequestParam String confirmPassword)
	{
		Usuario u = userRepository.getByUsername(username);
		userRepository.delete(u);

		if(passwordNew.equals(""))
		{
			u = new Usuario(username, nombre, apellidos, fecha, ciudad, u.getPassword(), gender, correo);
		}
		else
		{
			if(password.equals(u.getPassword()) && passwordNew.equals(confirmPassword))
				u = new Usuario(username, nombre, apellidos, fecha, ciudad, passwordNew, gender, correo);
		}

		userRepository.save(u);

		model.addAttribute("username", username);

		return inicioUsuario(model, username);
	}

	@RequestMapping("/nuevaOferta")
	public String nuevaOferta(Model model, @RequestParam String name)
	{
		model.addAttribute("username", name);
		return "nuevaOferta";
	}
	
	@RequestMapping("/valorar")
	public String valorar(Model model, @RequestParam String title, @RequestParam int valoracion, @RequestParam String username)
	{
		Anuncio a = anuncioRepository.getByTitle(title);
		int val = a.getValoracion();
		int numVal = a.getNumValoraciones();
		String descrip = a.getDescription();
		Comercio c = a.getLocal();
		anuncioRepository.delete(a);
		
		a = new Anuncio(title,descrip, c);		
		a.setValoracion(valoracion + val);
		a.setNumValoraciones(1 + numVal);
		
		anuncioRepository.save(a);
		
		return mostrarAnuncio(model, title, username);
	}

	/*@RequestMapping("/crearOferta")
	public String crearOferta(Model model, @RequestParam String title, @RequestParam Image imagen)
	{

	}*/
}
