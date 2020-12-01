package com.example.demo.Security;

import javax.sql.DataSource; 

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.modal.User;

//import com.example.demo.Principal.UserPrincipalDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private DataSource dataSource;
	@Autowired
	private UserDetailsService userDetailsService;
	

	@Bean
	public UserDetailsService userDetailsService() {
		return super.userDetailsService();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.authenticationProvider(authProvider());
//		 auth
//         .inMemoryAuthentication()
//             .withUser("admin").password(encoder().encode("123")).roles("ADMIN").and()
//             .withUser("user").password(encoder().encode("123")).roles("USER");
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select username,password, enabled from userstable where username=?")
				.authoritiesByUsernameQuery("select username,roles from user_roles where username=?");

	
	}

	//authorizeRequests() We're allowing anonymous access on /login so that users can authenticate
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
		.authorizeRequests().antMatchers("/").permitAll()
				.antMatchers("/register").permitAll()
				.antMatchers("/show-users").hasAnyAuthority("ADMIN")
				.antMatchers("/edit-user").hasAnyAuthority("ADMIN")
				.antMatchers("/show-request").hasAnyAuthority("ADMIN")
				.antMatchers("/producer").hasAnyAuthority("ADMIN")
				.antMatchers("/save-user").permitAll()
				.antMatchers("/send").permitAll()
				.antMatchers("/show-request").hasAnyAuthority("ADMIN")
				.antMatchers("/delete-request").hasAnyAuthority("ADMIN")
				.antMatchers("/accepte").hasAnyAuthority("ADMIN")
				.antMatchers("/delete-user").hasAnyAuthority("ADMIN")
				.anyRequest().authenticated()
				.and()
				.formLogin()
		        // .loginProcessingUrl("/login-user") // the process of the login where i send my data	//here if i put it the css is gone
		          .defaultSuccessUrl("/username", true) // where the user go after he login
		          .failureUrl("/login-user") //when fail login :3
				.permitAll().and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) .logoutSuccessUrl("/login").permitAll();
				http.exceptionHandling().accessDeniedPage("/403");
	}

	public static PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}

	

	/*
	 * @Bean public DaoAuthenticationProvider authenticationProvider() {
	 * 
	 * DaoAuthenticationProvider adoAuthenticationProvider = new
	 * DaoAuthenticationProvider();
	 * adoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
	 * adoAuthenticationProvider.setUserDetailsService(this.
	 * userPrincipalDetailsService); return adoAuthenticationProvider; }
	 */
	public static String PasswordEncoder(String password) {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password);
	}

	@Bean
	static PasswordEncoder passwordEncodernew() {
		return new BCryptPasswordEncoder();
	}
}
