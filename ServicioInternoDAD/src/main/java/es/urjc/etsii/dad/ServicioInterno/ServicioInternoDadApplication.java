package es.urjc.etsii.dad.ServicioInterno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class ServicioInternoDadApplication extends SpringBootServletInitializer{
	
	private static Class applicationClass = ServicioInternoDadApplication.class;

	public static void main(String[] args) {
		SpringApplication.run(ServicioInternoDadApplication.class, args);
	}

}
