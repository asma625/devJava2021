package edu.ASpring.devJava2021.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private UserDetailsServiceCustom userDetailsServiceCustom;
    private JwtUtil jwtUtil;


    @Autowired
    public JwtRequestFilter(JwtUtil jwtUtil , UserDetailsServiceCustom userDetailsServiceCustom) {
        this.jwtUtil = jwtUtil;
        this.userDetailsServiceCustom = userDetailsServiceCustom;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){

           String jwt =  authorizationHeader.substring(7);
           String login =  jwtUtil.getTokenBody(jwt).getSubject();
           if(login != null && SecurityContextHolder.getContext().getAuthentication() == null){
               UserDetails userDetails =  userDetailsServiceCustom.loadUserByUsername(login);

               if(jwtUtil.validToken(jwt, userDetails)){

                   UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                           new UsernamePasswordAuthenticationToken(userDetails , null, userDetails.getAuthorities());
                   usernamePasswordAuthenticationToken
                           .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                   SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
               }
           }

        }filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}