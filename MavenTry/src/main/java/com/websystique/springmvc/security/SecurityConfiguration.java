package com.websystique.springmvc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.websystique.springmvc.configuration.CsrfHeaderFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	PersistentTokenRepository tokenRepository;

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}

	/* This method sets-up the list of accessing page for each role. */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// Request Mapping accessible
				.antMatchers("/home")
				// Roles
				.access("hasRole('ADMIN') or hasRole('USER') or hasRole('CARGO')")
				// Request Mapping accessible
				.antMatchers("/deliveryBeeding")
				// Roles
				.access("hasRole('CARGO') or hasRole('ADMIN')")
				// Request Mapping accessible
				.antMatchers("/deliveryRequest")
				// Roles
				.access("hasRole('USER') or hasRole('ADMIN')")
				// Request Mapping accessible
				.antMatchers("/newuser/**", "/customer/**", "/delete-user-*",
						"/ajaxEditEmployee", "/cargo", "/list",
						"/getEmployeeList", "/ajaxAddEmployee",
						"/delete-cargo-user-by-ajax", "/ajaxAddCargoUser",
						"/ajaxEditCargoUser", "/delete-employee-by-ajax",
						"/deliveryType*", "/ajaxAddUser", "/listV1",
						"deliveryRequest", "deliveryBeeding",
						"/ajaxAddDeliveryType", "/ajaxEditDeliveryType","/restUser/**", "/restController")
				// Roles
				.access("hasRole('ADMIN')").and().formLogin()
				.loginPage("/login").loginProcessingUrl("/login")
				.usernameParameter("usernameId").passwordParameter("password").and()
				.rememberMe().rememberMeParameter("remember-me")
				.tokenRepository(tokenRepository).tokenValiditySeconds(86400)
				.and().csrf().and().exceptionHandling()
				.accessDeniedPage("/Access_Denied").and()
			      .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class).csrf().csrfTokenRepository(csrfTokenRepository());
		
		// Disable spring security
		// http.csrf().disable(); 

	}
	
	private CsrfTokenRepository csrfTokenRepository() {
		  HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		  repository.setHeaderName("X-XSRF-TOKEN");
		  return repository;
		}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices() {
		PersistentTokenBasedRememberMeServices tokenBasedservice = new PersistentTokenBasedRememberMeServices(
				"remember-me", userDetailsService, tokenRepository);
		return tokenBasedservice;
	}

	@Bean
	public AuthenticationTrustResolver getAuthenticationTrustResolver() {
		return new AuthenticationTrustResolverImpl();
	}

}
