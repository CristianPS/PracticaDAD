package es.urjc.etsii.dad.Practica_DAD;

import java.awt.Image;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	private String usuarioActual;
	private String comercioActual;

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
		a1.setDate("01/07/1997");

		Anuncio a2 = new Anuncio();
		a2.setTitle("CachimbaPremium-11Euros");
		a2.setDescription("XXX");
		a2.setLocal(c2);
		a2.setDate("02/08/1998");

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

	@RequestMapping("/añadirComentario")
	public String añadirComentario(Model model, @RequestParam String title, @RequestParam String addComment)
	{
		Usuario u = userRepository.getByUsername(usuarioActual);
		Anuncio a = anuncioRepository.getByTitle(title);
		Comentario c = new Comentario(u, addComment, a);
		comentarioRepository.save(c);
		
		
		/*model.addAttribute("username", usuarioActual);

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
			model.addAttribute("valoracion", a.getValoracion());*/
		
		return mostrarAnuncio(model, title);
	}

	@RequestMapping("/crearOferta")
	public String crearOferta(Model model, @RequestParam String title, @RequestParam String description, @RequestParam String username, @RequestParam String date)
	{
		String aux = convertirFecha(date);
		
		Comercio c = comercioRepository.getByUsername(username);
		Anuncio a = new Anuncio(title, description, c, aux);
		
		anuncioRepository.save(a);
		
		return inicioComercio(model, username);
			
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Model model, @RequestParam String title)
	{
		Anuncio a = anuncioRepository.getByTitle(title);
		
		//a.deleteComments();
		
		List <Comentario> c = comentarioRepository.findByAnuncio(a);
		
		Iterator<Comentario> it = c.iterator();
		 
		while (it.hasNext()) {
			comentarioRepository.delete(it.next());
		}
		
		anuncioRepository.delete(a);
		
		model.addAttribute("username" ,comercioActual);
		
		

		
		// si el anuncio tiene comentarios no va y a veces no te manda a la pagina de inicio
		return inicioComercio(model,comercioActual);
		//return "misOfertas";
	}
	
	/*@RequestMapping("/eliminar")
	public String eliminar(Model model, @RequestParam String title)
	{
		Anuncio a = anuncioRepository.getByTitle(title);
		anuncioRepository.delete(a);
		
		model.addAttribute("username" ,comercioActual);

		
		// si el anuncio tiene comentarios no va y a veces no te manda a la pagina de inicio
		return inicioComercio(model,comercioActual);
		//return "misOfertas";
	}*/
	
	@RequestMapping("/guardar")
	public String guardar(Model model, @RequestParam String username, @RequestParam String nombre, 
							@RequestParam String apellidos, @RequestParam String correo, @RequestParam String ciudad,
							@RequestParam String fecha, @RequestParam String gender, @RequestParam String password, 
							@RequestParam String passwordNew, @RequestParam String confirmPassword)
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
	
	@RequestMapping("/guardarComercio")
	public String guardarComercio(Model model, @RequestParam String username, @RequestParam String nombre, @RequestParam String address,
			@RequestParam String correo, @RequestParam String ciudad, @RequestParam String telephone, 
			@RequestParam String password, @RequestParam String passwordNew, @RequestParam String confirmPassword)
	{
		comercioRepository.setAddressByUsername(address, username);
		comercioRepository.setCityByUsername(ciudad, username);
		comercioRepository.setEmailByUsername(correo, username);
		comercioRepository.setEntNameByUsername(nombre, username);
		comercioRepository.setTelephoneByUsername(telephone, username);

		Comercio u = comercioRepository.getByUsername(username);
		
		if(password.equals(u.getPassword()) && passwordNew.equals(confirmPassword) && !(passwordNew.equals("")))
		{
			comercioRepository.setPasswordByUsername(passwordNew, username);
			model.addAttribute("password",passwordNew);
		}
		
		model.addAttribute("username", username);
		model.addAttribute("nombre", u.getEntName());
		model.addAttribute("address", u.getAddress());
		model.addAttribute("ciudad", u.getCity());
		model.addAttribute("correo", u.getEmail());
		model.addAttribute("telephone", u.getTelephone());

		//return inicioComercio(model, username);
		return mostrarPerfilComercio(model,username);
	}
	
	@RequestMapping("/inicioUsuario")
	public String inicioUsuario(Model model, @RequestParam String name) {

		model.addAttribute("username", name);

		usuarioActual = name;
		return "inicioConUsuario";
	}

	@RequestMapping("/inicioComercio")
	public String inicioComercio(Model model, @RequestParam String name) {

		Comercio c = comercioRepository.getByUsername(name);

		List<Anuncio> anuncios = c.getAnuncios();

		model.addAttribute("anuncios", anuncios);

		model.addAttribute("username", name);
		
		
		comercioActual = name;
		
		System.out.println(comercioActual);

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
	
	@RequestMapping("/mostrarPerfilComercio")
	public String mostrarPerfilComercio(Model model, @RequestParam String username)
	{
		Comercio c = comercioRepository.getByUsername(username);

		model.addAttribute("username", c.getUsername());
		model.addAttribute("nombre", c.getEntName());
		model.addAttribute("address", c.getAddress());
		model.addAttribute("correo", c.getEmail());
		model.addAttribute("ciudad", c.getCity());
		model.addAttribute("password", c.getPassword());
		model.addAttribute("telephone", c.getTelephone());

		return "perfil_empresa";
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
		
		model.addAttribute("username", usuarioActual);

		model.addAttribute("ent", a.getLocal().getEntName());
		model.addAttribute("description", a.getDescription());
		model.addAttribute("title",title);
		model.addAttribute("city", a.getLocal().getCity());
		model.addAttribute("address", a.getLocal().getAddress());
		model.addAttribute("email", a.getLocal().getEmail());
		model.addAttribute("telephone", a.getLocal().getTelephone());
		model.addAttribute("date", a.getDate());
		
		if(a.getNumValoraciones() != 0)
			model.addAttribute("valoracion", a.getValoracion()/a.getNumValoraciones());
		else
			model.addAttribute("valoracion", a.getValoracion());
		
		List<Comentario> comentarios = a.getComments();
		
		model.addAttribute("comentarios", comentarios);

		return "ofertaParticular"; 
	}
	
	@RequestMapping("/mostrarAnuncioPropio")
	public String mostrarAnuncioPropio(Model model, @RequestParam String title)
	{
		Anuncio a = anuncioRepository.getByTitle(title);
		
		model.addAttribute("username", comercioActual);

		model.addAttribute("ent", a.getLocal().getEntName());
		model.addAttribute("description", a.getDescription());
		model.addAttribute("title",title);
		model.addAttribute("city", a.getLocal().getCity());
		model.addAttribute("address", a.getLocal().getAddress());
		model.addAttribute("email", a.getLocal().getEmail());
		model.addAttribute("telephone", a.getLocal().getTelephone());
		model.addAttribute("date", a.getDate());
		
		if(a.getNumValoraciones() != 0)
			model.addAttribute("valoracion", a.getValoracion()/a.getNumValoraciones());
		else
			model.addAttribute("valoracion", a.getValoracion());

		List<Comentario> comentarios = a.getComments();
		
		model.addAttribute("comentarios", comentarios);
		
		return "OfertaPropia";
	}
	/*@RequestMapping("/actualizarUsuario")
	public String actualizarUsuario(Model model, @RequestParam String username, @RequestParam String nombre,
									@RequestParam String apellidos, @RequestParam String correo, @RequestParam String ciudad,
									@RequestParam String fecha, @RequestParam String gender, @RequestParam String password, 
									@RequestParam String passwordNew, @RequestParam String confirmPassword)
	{
		Usuario u = userRepository.getByUsername(username);
		userRepository.setNameByUsername(nombre, username);
		userRepository.setSurnameByUsername(apellidos, username);
		userRepository.setEmailByUsername(correo, username);
		userRepository.setCityByUsername(ciudad, username);
		userRepository.setGenderByUsername(gender, username);
		userRepository.setPasswordByUsername(password, username);
		return "mostrarPerfil";
	}*/
	

/*	@RequestMapping("/guardarEmpresa")
	public String guardarEmpresa(Model model, @RequestParam String username, @RequestParam String nombre, @RequestParam String direccion,
			@RequestParam String correo, @RequestParam String ciudad, @RequestParam String telephone, 
			@RequestParam String password, @RequestParam String passwordNew, @RequestParam String confirmPassword)
	{
		Comercio u = comercioRepository.getByUsername(username);
		comercioRepository.delete(u);

		if(passwordNew.equals(""))
		{
			u = new Comercio(username, u.getPassword(),nombre, ciudad, direccion, correo, telephone);
		}
		else
		{
			if(password.equals(u.getPassword()) && passwordNew.equals(confirmPassword))
				u = new Comercio(username, passwordNew,nombre, ciudad, direccion, correo, telephone);
		}

		comercioRepository.save(u);

		model.addAttribute("username", username);

		return inicioComercio(model, username);
	}
	*/
	


	@RequestMapping("/nuevaOferta")
	public String nuevaOferta(Model model, @RequestParam String name)
	{
		model.addAttribute("username", name);
		return "nuevaOferta";
	}
	
	@RequestMapping("/registroUsuario")
	public String registroUsuario(Model model, @RequestParam String username, @RequestParam String name, @RequestParam String apellidos, @RequestParam String email, @RequestParam String fecha, @RequestParam String genero, @RequestParam String city, @RequestParam String password) {
	
		//Ademas aqui deberiamos insertar todos los elementos obtenidos a la base de datos
	
		String aux = convertirFecha(fecha);
	
		Usuario u = new Usuario(username, name, apellidos, aux, city, password, genero, email);
	
		userRepository.save(u);
	
		model.addAttribute("username", username);
		
		usuarioActual = username;
	
		return "inicioConUsuario";
	}
	
	@RequestMapping("/registroComercio")
	public String registroComercio(Model model, @RequestParam String username, @RequestParam String nameEmpresa, @RequestParam String dir, @RequestParam String email, @RequestParam String fecha, @RequestParam String telefono, @RequestParam String city, @RequestParam String password) {
	
		//Ademas aqui deberiamos insertar todos los elementos obtenidos a la base de datos
		
		
		Comercio c = new Comercio(username, password, nameEmpresa, city, dir, email, telefono);
		
		comercioRepository.save(c);
		
		model.addAttribute("username", username);
		
		comercioActual = username;
	
		return "misOfertas";
	}
	
	@RequestMapping("/valorar")
	public String valorar(Model model, @RequestParam String title, @RequestParam int valoracion)
	{
		Anuncio a = anuncioRepository.getByTitle(title);
		int val = a.getValoracion();
		int numVal = a.getNumValoraciones();
		String date = a.getDate();
		String descrip = a.getDescription();
		Comercio c = a.getLocal();
		anuncioRepository.delete(a);
		
		a = new Anuncio(title,descrip, c, date);		
		a.setValoracion(valoracion + val);
		a.setNumValoraciones(1 + numVal);
		
		anuncioRepository.save(a);
		
		return mostrarAnuncio(model, title);
	}
}