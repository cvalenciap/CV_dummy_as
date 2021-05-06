package pe.com.sedapal.asi.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import pe.com.sedapal.asi.security.filter.FilterJWT;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Autowired
	private UserDetailsService customUserDetailsService;
	
	@Autowired
	private UserAjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;
	
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
		.antMatchers("/auth/**").permitAll()
		.antMatchers("/api/").permitAll()
		.anyRequest().authenticated()
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.logout().logoutUrl("/auth/logout").invalidateHttpSession(true).logoutSuccessHandler(ajaxLogoutSuccessHandler).clearAuthentication(true)
		.deleteCookies("JSESSIONID")
		//.logout().logoutUrl("/auth/logout").invalidateHttpSession(true).clearAuthentication(true)
		.and()
		.addFilterBefore(new FilterJWT(), UsernamePasswordAuthenticationFilter.class);
//		.addFilterBefore(new FilterJWT(redis), UsernamePasswordAuthenticationFilter.class);
	}
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}
	
	

	
}
