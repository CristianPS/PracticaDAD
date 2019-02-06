package es.urjc.etsii.dad.Practica_DAD;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistroController {

	@RequestMapping("/registroUsuario")
	public String registroUsuario(Model model, @RequestParam String name) {

		model.addAttribute("name", name);
		
		//Ademas aqui deberiamos insertar todos los elementos obtenidos a la base de datos

		return "inicioConUsuario";
	}
}
