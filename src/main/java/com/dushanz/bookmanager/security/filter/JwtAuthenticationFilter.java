package com.dushanz.bookmanager.security.filter;

import com.dushanz.bookmanager.service.security.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final AuthenticationService authenticationService;
    private final UserDetailsService userDetailsService;

    @Value("${jwt.bearerPrefix}")
    private String bearerPrefix;

    @Value("${jwt.authHeader}")
    private String authHeader;

    @Value("${jwt.tokenExtractionFailure}")
    private String jwtTokeExtractionFailureMessage;

    @Value("${jwt.tokenExpired}")
    private String jwtTokenExpiredMessage;

    @Value("${jwt.tokenInvalidForm}")
    private String jwtTokenInvalidFormMessage;

    @Autowired
    public JwtAuthenticationFilter(AuthenticationService authenticationService, UserDetailsService userDetailsService) {
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader(authHeader);

        String username = null;
        String jwtToken = null;

        // Expecting "Bearer token" type JWT.
        if (requestTokenHeader != null && requestTokenHeader.startsWith(bearerPrefix)) {
            // extract actual token after the "Bearer" string
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = authenticationService.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                LOG.error(jwtTokeExtractionFailureMessage);
            } catch (ExpiredJwtException e) {
                LOG.error(jwtTokenExpiredMessage);
            }
        } else {
            // invalid JWT token format, exit early without processing
            LOG.warn(jwtTokenInvalidFormMessage);
        }

        // token validation
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (userDetails != null && authenticationService.validateToken(jwtToken)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //current user is authenticated.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
