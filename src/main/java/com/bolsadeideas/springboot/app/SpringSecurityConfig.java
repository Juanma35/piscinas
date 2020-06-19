package com.bolsadeideas.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bolsadeideas.springboot.app.auth.handler.LoginSuccessHandler;
import com.bolsadeideas.springboot.app.models.service.JpaUserDetailsService;

@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar","albaran").permitAll()
		.antMatchers("/ver/**").hasAnyRole("USER")
		.antMatchers("/uploads/**").hasAnyRole("USER")
		.antMatchers("/form/**").hasAnyRole("ADMIN")
		
		.antMatchers("/albaranes/**").hasAnyRole("ADMIN")	
		.antMatchers("/balance/**").hasAnyRole("ADMIN")	
		.antMatchers("/facturas/**").hasAnyRole("ADMIN")	
		.antMatchers("/layout/**").hasAnyRole("ADMIN")	
		.antMatchers("/proveedor/**").hasAnyRole("ADMIN")
		.antMatchers("/productos/**").hasAnyRole("ADMIN")	
		.antMatchers("/materiales/**").hasAnyRole("ADMIN")
		.and()
	    .formLogin()
	        .successHandler(successHandler)
	        .loginPage("/login")
	    .permitAll()
	.and()
	.logout().permitAll()
	.and()
	.exceptionHandling().accessDeniedPage("/error_403");
	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception
	{
		/*@SuppressWarnings("deprecation")
		UserBuilder users = User.withDefaultPasswordEncoder();
		
		build.inMemoryAuthentication()
		.withUser(users.username("juanma").password("12345").roles("ADMIN","USER"));*/
		
		build.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);

	}
}
