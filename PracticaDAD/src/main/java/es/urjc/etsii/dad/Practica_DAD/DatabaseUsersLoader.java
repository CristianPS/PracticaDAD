package es.urjc.etsii.dad.Practica_DAD;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUsersLoader {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	/*@PostConstruct
	private void initDatabase()
	{
		List<String> roles = new LinkedList<>();
		roles.add("ROLE_USER");
		usuarioRepository.save(new Usuario("CarlosGilYPollas","Cristian","Posada Santos","01/08/1997","Madrid","contraseña","Hombre","c.posada@alumnos.urjc.es",roles));
	}*/
}
