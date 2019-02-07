package es.urjc.etsii.dad.Practica_DAD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class PerfilController {

	@Autowired
	private UsuarioRepository userRepository;
	
	public String mostrarPerfil(Model model)
	{
		Usuario u = userRepository.getByUsername("ji.diaze");
		
		model.addAttribute("username", u.getUsername());
		model.addAttribute("nombre", u.getName());
		model.addAttribute("apellidos", u.getSurname());
		model.addAttribute("correo", u.getEmail());
		model.addAttribute("ciudad", u.getCity());
		model.addAttribute("password", u.getPassword());
		
		return "perfil_usuario";
	}
}
