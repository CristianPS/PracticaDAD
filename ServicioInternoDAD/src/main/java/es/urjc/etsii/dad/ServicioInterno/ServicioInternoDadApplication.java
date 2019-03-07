package es.urjc.etsii.dad.ServicioInterno;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ServicioInternoDadApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(App.class, args);
    	
	}

}
