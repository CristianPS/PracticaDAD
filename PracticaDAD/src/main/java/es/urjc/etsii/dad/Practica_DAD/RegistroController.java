package es.urjc.etsii.dad.Practica_DAD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistroController {
	
	@Autowired
	private UsuarioRepository userRepository;

	@RequestMapping("/registroUsuario")
	public String registroUsuario(Model model, @RequestParam String username, @RequestParam String name, @RequestParam String apellidos, @RequestParam String email, @RequestParam String fecha, @RequestParam String genero, @RequestParam String city, @RequestParam String password) {

		//Ademas aqui deberiamos insertar todos los elementos obtenidos a la base de datos
		
		Usuario u = new Usuario(username, name, apellidos, "gola", city, password, genero, email);
		
		userRepository.save(u);
		
		model.addAttribute("username", username);

		return "inicioConUsuario";
	}
}
