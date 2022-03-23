package com.ris.izdavacka.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
 	UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(userDetailsService);
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/user/**").hasRole("korisnik")
				.antMatchers("/admin/**").hasRole("admin")
				.antMatchers("/employee/**").hasRole("radnik")
				.antMatchers("/guest/**").hasAnyRole("korisnik", "admin", "radnik", "gost")
				.antMatchers("/").permitAll()
			.and().
			    formLogin()
			    	.loginPage("/login.jsp")
			    	.loginProcessingUrl("/login")
			    	.defaultSuccessUrl("/auth/guest") //u kontroleru da je redirekcija na metod koji je mapiran sa guest
			.and()
			    .exceptionHandling().accessDeniedPage("/accessDenied.jsp")
			.and()
		    	.rememberMe()
		    .and()
		    	.csrf().disable();
	}
}
