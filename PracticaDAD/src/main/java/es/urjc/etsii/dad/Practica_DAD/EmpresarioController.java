package es.urjc.etsii.dad.Practica_DAD;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	private static String empresarioActual;
	
	public static String getEmpresarioActual()
	{
		return empresarioActual;
	}
	
	@RequestMapping("/guardarEmpresario")
	public String guardarEmpresario(Model model, @RequestParam String username, @RequestParam String nombre, @RequestParam String apellidos, 
										@RequestParam String direccion, @RequestParam String correo, @RequestParam String ciudad, @RequestParam String telefono, 
										@RequestParam String gender, @RequestParam String fecha)
	{
		/*empresarioRepository.setNameByUsername(nombre, username);
		empresarioRepository.setSurnameByUsername(apellidos, username);
		empresarioRepository.setAddressByUsername(direccion, username);
		empresarioRepository.setEmailByUsername(correo, username);
		empresarioRepository.setCityByUsername(ciudad, username);
		empresarioRepository.setTelephoneByUsername(telefono, username);
		empresarioRepository.setGenderByUsername(gender, username);
		empresarioRepository.setDateByUsername(fecha, username);*/
		
		Empresario e = empresarioRepository.getByUsername(username);
		e.setName(nombre);
		e.setAddress(direccion);
		e.setSurname(apellidos);
		e.setCity(ciudad);
		e.setGender(gender);
		e.setEmail(correo);
		e.setDate(fecha);
		e.setTelephone(telefono);
		
		//Falta guardar las contraseñas, pero nos esperamos hasta que metamos seguridad
		
		empresarioRepository.save(e);		
		
		return mostrarPerfilEmpresario(model,username);
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
	
	@RequestMapping("/registroEmpresario")
	public String registroEmpresario(Model model, @RequestParam String username, @RequestParam String name, @RequestParam String apellidos, @RequestParam String email, @RequestParam String fecha, @RequestParam String telefono, @RequestParam String city, @RequestParam String dir, @RequestParam String password, @RequestParam String genero) {
	
		//Ademas aqui deberiamos insertar todos los elementos obtenidos a la base de datos
		
		Empresario e = new Empresario(username, name, apellidos, password, city, dir, email, telefono, fecha, genero);
		
		empresarioRepository.save(e);
		
		model.addAttribute("username", username);
		
		empresarioActual = username;
	
		return "misOfertas";
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
	
	@RequestMapping("/crearOferta")
	public String crearOferta(Model model, @RequestParam String title, @RequestParam String entName, @RequestParam String description, @RequestParam String username, @RequestParam String date)
	{
		String aux = UsuarioController.convertirFecha(date);
		
		Comercio c = comercioRepository.getByEntName(entName);
		Anuncio a = new Anuncio(title, description, c, aux);
		
		anuncioRepository.save(a);
		
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
		
		return inicioEmpresario(model, EmpresarioController.getEmpresarioActual());
	}
}
