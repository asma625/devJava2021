package edu.ASpring.devJava2021.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import javax.sql.DataSource;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceCustom userDetailsService;

    //@Autowired
    //DataSource dataSource;
    @Autowired
    JwtRequestFilter jwtRequestFilter;

   @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);}




        /*auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "SELECT login, password, 1 "+
                                "FROM utilisateur "+
                                "WHERE login = ?")
                .authoritiesByUsernameQuery(
                        "SELECT login, denomination "+
                                "FROM utilisateur "+
                                "JOIN utilisateur_role ON utilisateur.id = utilisateur_role.utilisateur_id "+
                                "JOIN role ON utilisateur_role.role_id = role.id "+
                                "WHERE login = ?");


    //auth.jdbcAuthentication();
       /* auth.jdbcAuthentication().usersByUsernameQuery(
                 "SELECT login, password, 1" +
                         "FROM utilisateur"+
                         "WHERE login = ?"
         ).authoritiesByUsernameQuery(
                 "SELECT login, role" +
                         "FROM utilisateur"+
                         "WHERE login = ?"
         );

         auth.inMemoryAuthentication()
                .withUser("Asma")
                .password("xxx")
                .roles("USER")
                 .and()
                .withUser("Admin")
                .password("yyy")
                .roles("ADMINISTRATEUR");*/

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
    @Bean
    public AuthenticationManager authenticationManagerBean()  throws Exception{
        return  super.authenticationManagerBean();
    }


    @Override
    protected  void configure(HttpSecurity http) throws Exception {
        //Pour que tout le monde peuvent accéder (laisse passer!)
        //csrf : le qrc code
        http.cors().configurationSource(httpServletRequest -> new CorsConfiguration().applyPermitDefaultValues())
                 .and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/authentification/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMINISTRATEUR")
                .antMatchers("/user/**").hasAnyRole("ADMINISTRATEUR","UTILISATEUR")
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                http.addFilterBefore(jwtRequestFilter , UsernamePasswordAuthenticationFilter.class);

    }

}
