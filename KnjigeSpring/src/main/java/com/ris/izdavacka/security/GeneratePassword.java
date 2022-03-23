package com.ris.izdavacka.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneratePassword {
//	public static void main(String[] args) {
//        String password = "radnikProba";
//        System.out.println(encryptPassword(password));
//	}
	
	public static String encryptPassword(String password) {
		return encryptPass(password);
	}
	
	private static String encryptPass(String password) {
		BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
		return passEncoder.encode(password);
	}
}
