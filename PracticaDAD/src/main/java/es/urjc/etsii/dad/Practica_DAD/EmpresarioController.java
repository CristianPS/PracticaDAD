package es.urjc.etsii.dad.Practica_DAD;


import java.io.IOException;
import java.util.Base64;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class EmpresarioController {

	@Autowired
	private EmpresarioRepository empresarioRepository;
	@Autowired
	private ComercioRepository comercioRepository;
	@Autowired
	private AnuncioRepository anuncioRepository;
	@Autowired
	private ComentarioRepository comentarioRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private static String empresarioActual;
	
	public static String getEmpresarioActual()
	{
		return empresarioActual;
	}
	
	public static void setEmpresarioActual(String emp)
	{
		empresarioActual = emp;
	}
	
	@RequestMapping("/guardarEmpresario")
	public String guardarEmpresario(Model model, @RequestParam String nombre, @RequestParam String apellidos, 
										@RequestParam String direccion, @RequestParam String correo, @RequestParam String ciudad, @RequestParam String telefono, 
										@RequestParam String gender, @RequestParam String fecha, @RequestParam String password, @RequestParam String passwordNew,
										@RequestParam String confirmPassword, HttpServletRequest request)
	{
		/*empresarioRepository.setNameByUsername(nombre, username);
		empresarioRepository.setSurnameByUsername(apellidos, username);
		empresarioRepository.setAddressByUsername(direccion, username);
		empresarioRepository.setEmailByUsername(correo, username);
		empresarioRepository.setCityByUsername(ciudad, username);
		empresarioRepository.setTelephoneByUsername(telefono, username);
		empresarioRepository.setGenderByUsername(gender, username);
		empresarioRepository.setDateByUsername(fecha, username);*/
		
		String name = request.getUserPrincipal().getName();
		Empresario e = empresarioRepository.getByUsername(name);
		e.setName(nombre);
		e.setAddress(direccion);
		e.setSurname(apellidos);
		e.setCity(ciudad);
		e.setGender(gender);
		e.setEmail(correo);
		e.setDate(fecha);
		e.setTelephone(telefono);
		
		if(!passwordNew.equals("") && !confirmPassword.equals(""))
		{
			if(new BCryptPasswordEncoder().matches(password, e.getPassword()) && passwordNew.equals(confirmPassword))
			{
				e.setPassword(confirmPassword);
			}
		}
		
		empresarioRepository.save(e);		
		
		return mostrarPerfilEmpresario(model, request);
	}
	
	@RequestMapping("/inicioEmpresario")
	public String inicioEmpresario(Model model, HttpServletRequest request) {

		String name = request.getUserPrincipal().getName();
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
	
	@RequestMapping("/mostrarPerfilEmpresario")
	public String mostrarPerfilEmpresario(Model model, HttpServletRequest request)
	{
		String name = request.getUserPrincipal().getName();
		Empresario u = empresarioRepository.getByUsername(name);

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

		return "perfilEmpresario";
	}
	
	@RequestMapping("/registroEmpresario")
	public String registroEmpresario(Model model, @RequestParam String username, @RequestParam String name, @RequestParam String apellidos, @RequestParam String email, @RequestParam String fecha, @RequestParam String telefono, @RequestParam String city, @RequestParam String dir, @RequestParam String password, @RequestParam String genero, @RequestParam String confirmpassword) {
	
		//Comprobamos si existe ya algun empresario con ese nombre de usuario o email
		
		if(empresarioRepository.getByUsername(username) != null || empresarioRepository.getByEmail(email) != null || usuarioRepository.getByUsername(username) != null || usuarioRepository.getByEmail(email) != null)
		{
			return "/registroError";
		}
		else
		{
			if(!password.equals(confirmpassword))
			{
				return "/registroError";
			}
			else
			{
				List<String> roles = new LinkedList<>();
				roles.add("ROLE_EMP");
				
				Empresario e = new Empresario(username, name, apellidos, password, city, dir, email, telefono, fecha, genero, roles);
				
				empresarioRepository.save(e);
				
				//model.addAttribute("username", username);
				
				//empresarioActual = username;
			
				//return "misOfertas";
				return "login";
			}
		}
	}
	
	@RequestMapping("/crearComercio")
	public String crearComercio(Model model, @RequestParam String name, @RequestParam String city, @RequestParam String address, @RequestParam String email, @RequestParam String telephone, HttpServletRequest request)
	{
		String username = request.getUserPrincipal().getName();
		Empresario e = empresarioRepository.getByUsername(username);
		Comercio c = new Comercio(name, city, address, email, telephone, e);
		
		e.getComercios().add(c);
		comercioRepository.save(c);
		
		return inicioEmpresario(model, request);
		
		/*model.addAttribute("username", username);
		
		return "misOfertas";*/
	}
	
	@RequestMapping("/crearOferta")
	public String crearOferta(Model model, @RequestParam String title, @RequestParam String entName, @RequestParam String description, @RequestParam String date, @RequestParam MultipartFile img, HttpServletRequest request) throws IOException
	{
		String aux = UsuarioController.convertirFecha(date);
		
		Comercio c = comercioRepository.getByEntName(entName);
		
		byte[] encImage = Base64.getEncoder().encode(img.getBytes());
		
		Anuncio a = new Anuncio(title, description, c, aux, encImage);
		
		anuncioRepository.save(a);
		
		return inicioEmpresario(model, request);
		
		/*model.addAttribute("username", username);
		
		return "misOfertas";	*/		
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Model model, @RequestParam String title, HttpServletRequest request)
	{
		Anuncio a = anuncioRepository.getByTitle(title);
		
		List <Comentario> c = comentarioRepository.findByAnuncio(a);	
		Iterator<Comentario> it = c.iterator();
		 
		while (it.hasNext()) {
			comentarioRepository.delete(it.next());
		}
		
		anuncioRepository.delete(a);
		
		return inicioEmpresario(model, request);
		
		/*model.addAttribute("username", getEmpresarioActual());
		
		return "misOfertas";*/
	}
}
