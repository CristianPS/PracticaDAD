package es.urjc.etsii.dad.Practica_DAD;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private AnuncioRepository anuncioRepository;
	@Autowired
	private EmpresarioRepository empresarioRepository;
	@Autowired
	private ComercioRepository comercioRepository;
	
	private static String usuarioActual;
	private static String empresarioActual;

	public static String getUsuarioActual()
	{
		return usuarioActual;
	}
	
	/*@PostConstruct
	public void init()
	{
		List<String> roles = new LinkedList<>();
		roles.add("ROLE_USER");
		usuarioRepository.save(new Usuario("CristianPS","Cristian","Posada Santos","01/08/1997","Madrid","contraseña","Hombre","c.posada@alumnos.urjc.es",roles));
		usuarioRepository.save(new Usuario("SitoDiaz","Jose Ignacio","Diaz Errejon","13/07/97","Sevilla la Nueva","contraseña","Hombre","ji.diaze@alumnos.urjc.es",roles));
		
		//usuarioRepository.save(new Usuario("CristianPS","Cristian","Posada Santos","01/08/1997","Madrid","contraseña","Hombre","c.posada@alumnos.urjc.es"));
		//usuarioRepository.save(new Usuario("SitoDiaz","Jose Ignacio","Diaz Errejon","13/07/97","Sevilla la Nueva","contraseña","Hombre","ji.diaze@alumnos.urjc.es"));
		
		roles.clear();
		roles.add("ROLE_EMP");
		
		Empresario e1 = new Empresario("PepePontes","Pepe","Pontes Pontes", "contraseña", "Madrid", "C/Alcala, 5", "pepepontes@hotmail.es", "918130251", "01/05/1985", "Hombre", roles);
		
		empresarioRepository.save(e1);
		
		comercioRepository.save(new Comercio("Fabrik","Madrid","C/AlcaldeMostoles,5","fabrik@hotmail.es","918170864", e1));
		comercioRepository.save(new Comercio("Anubis","Arroyomolinos","CC.Xanadu","anubis@hotmail.es","918146753", e1));

		Comercio c1 = comercioRepository.getByEntName("Fabrik");
		Comercio c2 = comercioRepository.getByEntName("Anubis");

		Anuncio a1 = new Anuncio();
		a1.setTitle("Entrada-10Euros");
		a1.setDescription("XXX");
		a1.setLocal(c1);
		a1.setDate("01/07/1997");

		Anuncio a2 = new Anuncio();
		a2.setTitle("CachimbaPremium-11Euros");
		a2.setDescription("XXX");
		a2.setLocal(c2);
		a2.setDate("02/08/1998");
		
		Anuncio a3 = new Anuncio();
		a3.setTitle("CopaGratis-Resto3€");
		a3.setDescription("XXX");
		a3.setLocal(c1);
		a3.setDate("05/11/2019");
		
		Anuncio a4 = new Anuncio();
		a4.setTitle("Copas5€");
		a4.setDescription("XXX");
		a4.setLocal(c2);
		a4.setDate("03/03/2019");

		anuncioRepository.save(a1);
		anuncioRepository.save(a2);
		anuncioRepository.save(a3);
		anuncioRepository.save(a4);

		c1.getAnuncios().add(a1);
		c1.getAnuncios().add(a3);
		c2.getAnuncios().add(a2);
		c2.getAnuncios().add(a4);

	}*/

	public static String convertirFecha(String fecha)
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
	
	@RequestMapping("/guardar")
	public String guardar(Model model, @RequestParam String username, @RequestParam String nombre, 
							@RequestParam String apellidos, @RequestParam String correo, @RequestParam String ciudad,
							@RequestParam String fecha, @RequestParam String gender, @RequestParam String password, 
							@RequestParam String passwordNew, @RequestParam String confirmPassword)
	{
		Usuario u = usuarioRepository.getByUsername(username);
		
		/*userRepository.setNameByUsername(nombre, username);
		userRepository.setCityByUsername(ciudad, username);
		userRepository.setEmailByUsername(correo, username);
		userRepository.setGenderByUsername(gender, username);
		userRepository.setSurnameByUsername(apellidos, username);*/
		
		u.setName(nombre);
		u.setCity(ciudad);
		u.setEmail(correo);
		u.setGender(gender);
		u.setSurname(apellidos);
		u.setBornDate(fecha);

		if(!passwordNew.equals("") && !confirmPassword.equals(""))
		{
			if(new BCryptPasswordEncoder().matches(password, u.getPassword()) && passwordNew.equals(confirmPassword))
			{
				u.setPassword(confirmPassword);
			}
		}

		usuarioRepository.save(u);
		
		//model.addAttribute("username", username);

		//return inicioUsuario(model, username);
		return mostrarPerfil(model, username);
	}
	
	@RequestMapping("/inicioUsuario")
	public String inicioUsuario(Model model/*, @RequestParam String name*/, HttpServletRequest request) {

		String name = request.getUserPrincipal().getName();
		
		if(usuarioRepository.getByUsername(name) != null)
		{
			model.addAttribute("username", name);
	
			usuarioActual = name;
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
			
			return "inicioConUsuario";
		}
		else if(empresarioRepository.getByUsername(name) != null)
		{
			model.addAttribute("username", name);
			
			empresarioActual = name;
			Empresario e = empresarioRepository.getByUsername(name);
			List<Comercio> comercios = e.getComercios();
			List<Anuncio> anuncios = new LinkedList<>();
			
			for(Comercio c : comercios)
			{
				anuncios.addAll(c.getAnuncios());
			}
			
			model.addAttribute("anuncios", anuncios);
			
			return "misOfertas";
		}
		else
		{
			throw new RuntimeException("No hay ningun usuario registrado con ese nombre");
		}
	}
	
	@RequestMapping("/login")
	public String login()
	{
		return "login";
	}
	
	@RequestMapping("/loginerror")
	public String loginerror()
	{
		return "loginerror";
	}
	
	@RequestMapping("/mostrarPerfil")
	public String mostrarPerfil(Model model, @RequestParam String username)
	{
		Usuario u = usuarioRepository.getByUsername(username);

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
	
	@RequestMapping("/registro")
	public String registro(Model model)
	{
		return "registro";
	}
	
	@RequestMapping("/registroerror")
	public String registroerror()
	{
		return "registroerror";
	}
	
	@RequestMapping("/registroUsuario")
	public String registroUsuario(Model model, @RequestParam String username, @RequestParam String name, @RequestParam String apellidos, @RequestParam String email, @RequestParam String fecha, @RequestParam String genero, @RequestParam String city, @RequestParam String password, @RequestParam String confirmpassword) {
	
		//Si ya existe un usuario con este nombre
		
		if(usuarioRepository.getByUsername(username) != null || usuarioRepository.getByEmail(email) != null || empresarioRepository.getByUsername(username) != null || empresarioRepository.getByEmail(email) != null)
		{
			return "/registroerror";
		}
		else
		{
			if(!password.equals(confirmpassword)) {
				return "/registroerror";
			}
			else
			{
				String aux = convertirFecha(fecha);
				List<String> roles = new LinkedList<>();
				roles.add("ROLE_USER");
				
				Usuario u = new Usuario(username, name, apellidos, aux, city, password, genero, email, roles);
			
				usuarioRepository.save(u);
				
				usuarioActual = username;
				
				//return inicioUsuario(model);
				return login();
			}
		}
	}
}