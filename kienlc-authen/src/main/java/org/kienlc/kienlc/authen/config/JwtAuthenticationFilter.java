package org.kienlc.kienlc.authen.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kienlc.kienlc.authen.service.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null && !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        //Lay token trong header
        final String token = authHeader.substring(7);
        final String userName = jwtService.extractUserName(token);

        //Tao 1 instance Authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (userName != null && authentication != null) {

            //Lay ra UserDetail
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            //Validate
            if (jwtService.isTokenValid(token, userName, userDetails)) {

                //Tao 1 impl cua Authentication la UsernamePasswordAuthenticationToken
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //Set Authentication vao Context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        //Sau khi set vao xong SecurityContext, lan sau filter se check va OK
        filterChain.doFilter(request, response);
    }
}
