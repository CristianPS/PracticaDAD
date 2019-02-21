package es.urjc.etsii.dad.Practica_DAD;

import java.awt.Image;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
	@Autowired
	private EmpresarioRepository empresarioRepository;
	
	private String usuarioActual;
	private String comercioActual;
	private String anuncioActual;
	private String empresarioActual;

	/*@PostConstruct
	public void init()
	{
		userRepository.save(new Usuario("CristianPS","Cristian","Posada Santos","01/08/1997","Madrid","contraseña","Hombre","c.posada@alumnos.urjc.es"));
		userRepository.save(new Usuario("SitoDiaz","Jose Ignacio","Diaz Errejon","13/07/97","Sevilla la Nueva","contraseña","Hombre","ji.diaze@alumnos.urjc.es"));

		Empresario e1 = new Empresario("PepePontes","Pepe","Pontes Pontes", "contraseña", "Madrid", "C/Alcala, 5", "pepepontes@hotmail.es", "918130251", "01/05/1985", "Hombre");
		
		empresarioRepository.save(e1);
		
		/*comercioRepository.save(new Comercio("Fabrik","Madrid","C/AlcaldeMostoles,5","fabrik@hotmail.es","918170864", e1));
		comercioRepository.save(new Comercio("Anubis","Arroyomolinos","CC.Xanadu","anubis@hotmail.es","918146753", e1));

		//Comercio c1 = comercioRepository.getByUsername("CarlosGil");
		//Comercio c2 = comercioRepository.getByUsername("JorgePRG");

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
	@RequestMapping("/añadirComentario")
	public String añadirComentario(Model model, @RequestParam String title, @RequestParam String addComment)
	{
		Usuario u = userRepository.getByUsername(usuarioActual);
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
		
		if(usuarioActual.equals(c.getUser().getUsername()))
		{
			a.getComments().remove(c);
			comentarioRepository.delete(c);
		}
		
		return mostrarAnuncio(model, title);
	}
	@RequestMapping("/crearOferta")
	public String crearOferta(Model model, @RequestParam String title, @RequestParam String entName, @RequestParam String description, @RequestParam String username, @RequestParam String date)
	{
		String aux = convertirFecha(date);
		
		Comercio c = comercioRepository.getByEntName(entName);
		Anuncio a = new Anuncio(title, description, c, aux);
		
		anuncioRepository.save(a);
		
		return inicioEmpresario(model, username);
			
	}
	
	@RequestMapping("/crearComercio")
	public String crearComercio(Model model, @RequestParam String username, @RequestParam String name, @RequestParam String city, @RequestParam String address, @RequestParam String email, @RequestParam String telephone)
	{
		Empresario e = empresarioRepository.getByUsername(username);
		Comercio c = new Comercio(name, city, address, email, telephone, e);
		
		e.getComercios().add(c);
		comercioRepository.save(c);
		
		return inicioEmpresario(model, username);
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Model model, @RequestParam String title)
	{
		Anuncio a = anuncioRepository.getByTitle(title);
		
		List <Comentario> c = comentarioRepository.findByAnuncio(a);	
		Iterator<Comentario> it = c.iterator();
		 
		while (it.hasNext()) {
			comentarioRepository.delete(it.next());
		}
		
		anuncioRepository.delete(a);
		
		model.addAttribute("username" ,comercioActual);
		
		return inicioEmpresario(model, empresarioActual);
	}
	
	@RequestMapping("/guardar")
	public String guardar(Model model, @RequestParam String username, @RequestParam String nombre, 
							@RequestParam String apellidos, @RequestParam String correo, @RequestParam String ciudad,
							@RequestParam String fecha, @RequestParam String gender, @RequestParam String password, 
							@RequestParam String passwordNew, @RequestParam String confirmPassword)
	{
		Usuario u = userRepository.getByUsername(username);
		
		userRepository.setNameByUsername(nombre, username);
		userRepository.setCityByUsername(ciudad, username);
		userRepository.setEmailByUsername(correo, username);
		userRepository.setGenderByUsername(gender, username);
		userRepository.setSurnameByUsername(apellidos, username);

		if(passwordNew.equals(""))
		{
			userRepository.setPasswordByUsername(password, username);
		}
		else
		{
			if(password.equals(u.getPassword()) && passwordNew.equals(confirmPassword))
				userRepository.setPasswordByUsername(passwordNew, username);
		}

		model.addAttribute("username", username);

		return inicioUsuario(model, username);
	}
	
	@RequestMapping("/guardarAnuncio")
	public String guardarAnuncio(Model model, @RequestParam String title, @RequestParam String description, @RequestParam String date)
	{
		Anuncio a = anuncioRepository.getByTitle(anuncioActual);
		
		anuncioRepository.setTitleById(title, a.getId());
		anuncioRepository.setDescriptionById(description, a.getId());
		anuncioRepository.setDateById(date, a.getId());
		
		return mostrarAnuncioPropio2(model, title, description, date);
	}
	
	@RequestMapping("/guardarComercio")
	public String guardarComercio(Model model, @RequestParam String username, @RequestParam String entName, @RequestParam String address, @RequestParam String correo, @RequestParam String ciudad, @RequestParam String telephone)
	{		
		comercioRepository.setCityByEntName(ciudad, entName);
		comercioRepository.setAddressByEntName(address, entName);
		comercioRepository.setEmailByEntName(correo, entName);
		comercioRepository.setTelephoneByEntName(telephone, entName);
		
		Comercio c = comercioRepository.getByEntName(entName);
		
		model.addAttribute("username", username);
		model.addAttribute("entName", c.getEntName());
		model.addAttribute("address", c.getAddress());
		model.addAttribute("ciudad", c.getCity());
		model.addAttribute("correo", c.getEmail());
		model.addAttribute("telephone", c.getTelephone());

		return mostrarPerfilComercio(model,entName);
	}
	@RequestMapping("/guardarEmpresario")
	public String guardarEmpresario(Model model, @RequestParam String username, @RequestParam String nombre, @RequestParam String apellidos, 
										@RequestParam String direccion, @RequestParam String correo, @RequestParam String ciudad, @RequestParam String telefono, 
										@RequestParam String gender, @RequestParam String fecha)
	{
		empresarioRepository.setNameByUsername(nombre, username);
		empresarioRepository.setSurnameByUsername(apellidos, username);
		empresarioRepository.setAddressByUsername(direccion, username);
		empresarioRepository.setEmailByUsername(correo, username);
		empresarioRepository.setCityByUsername(ciudad, username);
		empresarioRepository.setTelephoneByUsername(telefono, username);
		empresarioRepository.setGenderByUsername(gender, username);
		empresarioRepository.setDateByUsername(fecha, username);
		
		
		return mostrarPerfilEmpresario(model,username);
	}
	@RequestMapping("/inicioUsuario")
	public String inicioUsuario(Model model, @RequestParam String name) {

		model.addAttribute("username", name);

		usuarioActual = name;
		List<Anuncio> anuncios = anuncioRepository.findAll();
		
		
		Comparator<Anuncio> a = (x, b) -> b.getValoracionMedia() - x.getValoracionMedia();
		anuncios.sort(a);		
		List<Anuncio> mejoresAnuncios = anuncios.subList(0, 4);		
		model.addAttribute("mejoresAnuncios", mejoresAnuncios);
		
		return "inicioConUsuario";
	}
	
	@RequestMapping("/inicioEmpresario")
	public String inicioEmpresario(Model model, @RequestParam String name) {
		
		Empresario e = empresarioRepository.getByUsername(name);
		
		List<Comercio> comercios = e.getComercios();
		
		List<Anuncio> anuncios = new LinkedList<>();
		
		for(Comercio c : comercios)
		{
			if(c.getAnuncios() != null)
				anuncios.addAll(c.getAnuncios());
		}
		
		model.addAttribute("anuncios", anuncios);
		
		model.addAttribute("username", name);
		
		empresarioActual = name;

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
	
	@RequestMapping("/mostrarPerfilEmpresario")
	public String mostrarPerfilEmpresario(Model model, @RequestParam String username)
	{
		Empresario u = empresarioRepository.getByUsername(username);

		model.addAttribute("username", u.getUsername());
		model.addAttribute("nombre", u.getName());
		model.addAttribute("apellidos", u.getSurname());
		model.addAttribute("correo", u.getEmail());
		model.addAttribute("ciudad", u.getCity());
		model.addAttribute("password", u.getPassword());
		model.addAttribute("gender", u.getGender());
		model.addAttribute("direccion",u.getAddress());
		model.addAttribute("telefono",u.getTelephone());
		model.addAttribute("fecha", u.getDate());

		return "perfil_empresario";
	}
	@RequestMapping("/mostrarPerfilComercio")
	public String mostrarPerfilComercio(Model model, @RequestParam String entName)
	{
		Comercio c = comercioRepository.getByEntName(entName);

		model.addAttribute("username", empresarioActual);
		model.addAttribute("entName", c.getEntName());
		model.addAttribute("address", c.getAddress());
		model.addAttribute("email", c.getEmail());
		model.addAttribute("city", c.getCity());
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
	
	@RequestMapping("/mostrarComercios")
	public String mostrarComercios(Model model, @RequestParam String name)
	{
		Empresario e = empresarioRepository.getByUsername(name);
		List<Comercio> comercios = e.getComercios();
		model.addAttribute("username", name);
		model.addAttribute("comercios", comercios);
		return "misComercios";
	}

	@RequestMapping("/mostrarComercio")
	public String mostrarComercio(Model model, @RequestParam String entName)
	{
		Comercio c = comercioRepository.getByEntName(entName);
		
		model.addAttribute("entName", c.getEntName());
		model.addAttribute("owner", c.getOwner());
		model.addAttribute("city", c.getCity());
		model.addAttribute("email", c.getEmail());
		model.addAttribute("address", c.getAddress());
		model.addAttribute("telephone", c.getTelephone());
		model.addAttribute("username", c.getOwner().getUsername());
		
		return "comercioParticular";
		
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
			model.addAttribute("valoracion", 0);
		
		List<Comentario> comentarios = a.getComments();
		
		model.addAttribute("comentarios", comentarios);

		return "ofertaParticular"; 
	}
	
	@RequestMapping("/mostrarAnuncio2")
	public String mostrarAnuncio2(Model model, @RequestParam String title, @RequestParam int valoracionMedia)
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
			model.addAttribute("valoracion", valoracionMedia);
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
		
		model.addAttribute("username", empresarioActual);

		model.addAttribute("ent", a.getLocal().getEntName());
		model.addAttribute("description", a.getDescription());
		model.addAttribute("title", a.getTitle());
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
		
		anuncioActual = title;
		
		return "OfertaPropia";
	}
	
	@RequestMapping("/mostrarAnuncioPropio2")
	public String mostrarAnuncioPropio2(Model model, @RequestParam String title, @RequestParam String description, @RequestParam String date)
	{
		Anuncio a = anuncioRepository.getByTitle(title);
		
		model.addAttribute("username", empresarioActual);

		model.addAttribute("ent", a.getLocal().getEntName());
		model.addAttribute("description", description);
		model.addAttribute("title", title);
		model.addAttribute("city", a.getLocal().getCity());
		model.addAttribute("address", a.getLocal().getAddress());
		model.addAttribute("email", a.getLocal().getEmail());
		model.addAttribute("telephone", a.getLocal().getTelephone());
		model.addAttribute("date", date);
		
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
		model.addAttribute("username", empresarioActual);
		return "nuevaOferta";
	}
	
	@RequestMapping("/nuevoComercio")
	public String nuevoComercio(Model model, @RequestParam String username)
	{
		model.addAttribute("username", username);
		return "nuevoComercio";
	}
	
	@RequestMapping("/registroUsuario")
	public String registroUsuario(Model model, @RequestParam String username, @RequestParam String name, @RequestParam String apellidos, @RequestParam String email, @RequestParam String fecha, @RequestParam String genero, @RequestParam String city, @RequestParam String password) {
	
		//Ademas aqui deberiamos insertar todos los elementos obtenidos a la base de datos
	
		String aux = convertirFecha(fecha);
	
		Usuario u = new Usuario(username, name, apellidos, aux, city, password, genero, email);
	
		userRepository.save(u);
		
		usuarioActual = username;
		
		return inicioUsuario(model, username);
	}
	
	@RequestMapping("/registroEmpresario")
	public String registroEmpresario(Model model, @RequestParam String username, @RequestParam String name, @RequestParam String apellidos, @RequestParam String email, @RequestParam String fecha, @RequestParam String telefono, @RequestParam String city, @RequestParam String dir, @RequestParam String password, @RequestParam String genero) {
	
		//Ademas aqui deberiamos insertar todos los elementos obtenidos a la base de datos
		
		Empresario e = new Empresario(username, name, apellidos, password, city, dir, email, telefono, fecha, genero);
		
		empresarioRepository.save(e);
		
		model.addAttribute("username", username);
		
		empresarioActual = username;
	
		return "misOfertas";
	}
	
	@RequestMapping("/valorar")
	public String valorar(Model model, @RequestParam String title, @RequestParam int valoracion)
	{
		Anuncio a = anuncioRepository.getByTitle(title);
		int val = a.getValoracion();
		int numVal = a.getNumValoraciones();
		/*String date = a.getDate();
		String descrip = a.getDescription();
		Comercio c = a.getLocal();*/
		/*anuncioRepository.delete(a);
		
		a = new Anuncio(title,descrip, c, date);		
		a.setValoracion(valoracion + val);
		a.setNumValoraciones(1 + numVal);
		a.updateValoracionMedia();
		
		anuncioRepository.save(a);*/
		
		anuncioRepository.setValoracionById((valoracion+val), a.getId());
		anuncioRepository.setNumValoracionById((1+numVal), a.getId());
		
		int valoracionMedia = (valoracion+val)/(numVal+1);
		
		anuncioRepository.setValoracionMediaById(valoracionMedia, a.getId());
		
		return mostrarAnuncio2(model, title, valoracionMedia);
	}
}