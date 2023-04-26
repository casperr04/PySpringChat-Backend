package com.casperr04.pyspringchatbackend.security;

import com.casperr04.pyspringchatbackend.repository.AuthTokenRepository;
import com.casperr04.pyspringchatbackend.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
public class BearerAuthenticationFilter extends OncePerRequestFilter {
    private AuthTokenRepository authTokenRepository;
    private UserDetailsService userDetailsService;
    private UserRepository userRepository;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        if (authTokenRepository.findAuthTokenByToken(token).isEmpty())
        {
            filterChain.doFilter(request, response);
            return;
        }

        var authToken = authTokenRepository.findAuthTokenByToken(token).get();
        if (!authToken.isValid() || authToken.isExpired()){
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(
                userRepository.findByTokensToken(token).get().getUsername()
        );

        UsernamePasswordAuthenticationToken userPassToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        userPassToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(userPassToken);
        filterChain.doFilter(request, response);
    }
}
