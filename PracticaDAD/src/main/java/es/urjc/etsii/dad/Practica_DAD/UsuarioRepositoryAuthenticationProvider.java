package es.urjc.etsii.dad.Practica_DAD;

import java.util.LinkedList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
	@Autowired
	private EmpresarioRepository empresaryRepository;
	
	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		
		Usuario user = userRepository.getByUsername(auth.getName());
		Empresario empresary = empresaryRepository.getByUsername(auth.getName());
		
		if (user == null && empresary == null)
		{
			throw new BadCredentialsException("User not found");
		}
		else if (user != null)
		{
			String password = (String) auth.getCredentials();
			if(!new BCryptPasswordEncoder().matches(password, user.getPassword()))
			{
				throw new BadCredentialsException("Wrong password");
			}
			
			List<GrantedAuthority> roles = new LinkedList<>();
			for(String role : user.getRoles())
			{
				roles.add(new SimpleGrantedAuthority(role));
			}
			
			return new UsernamePasswordAuthenticationToken(user.getUsername(), password, roles);
		}
		else
		{
			String password = (String) auth.getCredentials();
			if(!new BCryptPasswordEncoder().matches(password, empresary.getPassword()))
			{
				throw new BadCredentialsException("Wrong password");
			}
			
			List<GrantedAuthority> roles = new LinkedList<>();
			for(String role : empresary.getRoles())
			{
				roles.add(new SimpleGrantedAuthority(role));
			}
			
			return new UsernamePasswordAuthenticationToken(empresary.getUsername(), password, roles);
		}

		
		/*String password = (String) auth.getCredentials();
		if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
			throw new BadCredentialsException("Wrong password");
		}
		
		List<GrantedAuthority> roles = new LinkedList<>();
		for(String role : user.getRoles())
		{
			roles.add(new SimpleGrantedAuthority(role));
		}
		
		return new UsernamePasswordAuthenticationToken(user.getUsername(), password, roles);*/
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}
}