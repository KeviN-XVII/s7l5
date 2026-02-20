package kevinquarta.s7l5.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kevinquarta.s7l5.entities.Utente;
import kevinquarta.s7l5.exceptions.UnauthroizedException;
import kevinquarta.s7l5.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTCheckedFilter extends OncePerRequestFilter {

    private final JWTTools jwtTools;
    private final UtentiService utentiService;

    @Autowired
    public JWTCheckedFilter(JWTTools jwtTools, UtentiService utentiService) {
        this.jwtTools = jwtTools;
        this.utentiService = utentiService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

// ----------- AUTENTICAZIONE
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null|| !authHeader.startsWith("Bearer "))
            throw new UnauthroizedException("Inserire il token nell'Authorization header");

        String accessToken = authHeader.replace("Bearer ", "");

        jwtTools.verifyToken(accessToken);


// ------------ AUTORIZZAZIONE

// ------------RICERCA UTENTE NEL DB
        long utenteId = jwtTools.extractIdFromToken(accessToken);
        Utente authenticatedUtente = utentiService.findById(utenteId);

// ------------ASSOCIAMO UTENTE
        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedUtente,null,authenticatedUtente.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
