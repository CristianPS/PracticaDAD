package es.urjc.etsii.dad.Practica_DAD;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ComercioController {

	@Autowired
	private ComercioRepository comercioRepository;
	@Autowired
	private EmpresarioRepository empresarioRepository;
	
	@RequestMapping("/guardarComercio")
	public String guardarComercio(Model model, @RequestParam String username, @RequestParam String entName, @RequestParam String address, @RequestParam String correo, @RequestParam String ciudad, @RequestParam String telephone, HttpServletRequest request)
	{		
		/*comercioRepository.setCityByEntName(ciudad, entName);
		comercioRepository.setAddressByEntName(address, entName);
		comercioRepository.setEmailByEntName(correo, entName);
		comercioRepository.setTelephoneByEntName(telephone, entName);*/
		
		Comercio c = comercioRepository.getByEntName(entName);
		c.setAddress(address);
		c.setCity(ciudad);
		c.setEmail(correo);
		c.setTelephone(telephone);
		
		comercioRepository.save(c);
		
		/*model.addAttribute("username", username);
		model.addAttribute("entName", c.getEntName());
		model.addAttribute("address", c.getAddress());
		model.addAttribute("ciudad", c.getCity());
		model.addAttribute("correo", c.getEmail());
		model.addAttribute("telephone", c.getTelephone());*/

		return mostrarPerfilComercio(model, entName, request);
	}
	
	@RequestMapping("/mostrarPerfilComercio")
	public String mostrarPerfilComercio(Model model, @RequestParam String entName, HttpServletRequest request)
	{
		Comercio c = comercioRepository.getByEntName(entName);

		model.addAttribute("username", request.getUserPrincipal().getName());
		model.addAttribute("entName", c.getEntName());
		model.addAttribute("address", c.getAddress());
		model.addAttribute("email", c.getEmail());
		model.addAttribute("city", c.getCity());
		model.addAttribute("telephone", c.getTelephone());

		return "perfilEmpresa";
	}
	
	@RequestMapping("/mostrarComercios")
	public String mostrarComercios(Model model, HttpServletRequest request)
	{
		String name = request.getUserPrincipal().getName();
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
	
	@RequestMapping("/nuevoComercio")
	public String nuevoComercio(Model model, HttpServletRequest request)
	{
		model.addAttribute("username", request.getUserPrincipal().getName());
		return "nuevoComercio";
	}
}
