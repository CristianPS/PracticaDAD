package es.urjc.etsii.dad.Practica_DAD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioRepositoryAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UsuarioRepository userRepository;
	
	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		
		Usuario user = userRepository.getByUsername(auth.getName());
		
		if (user == null) {
			throw new BadCredentialsException("User not found");
		}
		
		String password = (String) auth.getCredentials();
		if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
			throw new BadCredentialsException("Wrong password");
		}
		
		return new UsernamePasswordAuthenticationToken(user.getUsername(), password);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}
}
