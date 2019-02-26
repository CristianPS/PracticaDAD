package es.urjc.etsii.dad.Practica_DAD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	public UsuarioRepositoryAuthenticationProvider authenticationProvider;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		

		
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/images/**", "/styles/**", "/scripts/**").permitAll();
		http.authorizeRequests().antMatchers("/login").permitAll();
		http.authorizeRequests().antMatchers("/registro").permitAll();
		
		http.authorizeRequests().anyRequest().authenticated();
		
		http.formLogin().loginPage("/login");
		http.formLogin().usernameParameter("name");
		http.formLogin().passwordParameter("password");
		http.formLogin().defaultSuccessUrl("/inicioUsuario");
		
		http.csrf().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("pass").roles("USER");
	}
}
