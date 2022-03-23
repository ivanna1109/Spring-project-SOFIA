package com.ris.izdavacka.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import model.Korisnik;

@SuppressWarnings("serial")
public class MyUserDetails implements UserDetails {
	
	private String username;
	private String password;
	private String ime;
	private String prezime;
	private List<GrantedAuthority> authorities;

	public MyUserDetails(Korisnik k) {
		this.username = k.getKorisnickoIme();
		this.password = k.getSifra();
		this.ime = k.getIme();
		this.prezime = k.getPrezime();

		String role = k.getUloga() == null? "gost" : k.getUloga().getNaziv();
		
		this.authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_" + role));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public String getIme() {
		return ime;
	}
	
	public String getPrezime() {
		return prezime;
	}

}
