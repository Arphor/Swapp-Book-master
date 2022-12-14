package com.swapp.swapp.security;


import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;




public abstract class WebSecurityConfigurerAdapter implements WebSecurityConfigurer<WebSecurity> {

	protected void configurer(HttpSecurity http) throws Exception{
		http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
		http.formLogin();
		http.httpBasic();
	}

/* 	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				.antMatchers("/index", "/register", "/login").permitAll()
				.anyRequest().permitAll()
			)
			.formLogin((form) -> form
				.loginPage("/newbook")
				.permitAll()
			)
			.logout((logout) -> logout.permitAll())
            .csrf().disable();

		return http.build();
	} */

}