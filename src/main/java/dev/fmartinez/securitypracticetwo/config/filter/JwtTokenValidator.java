package dev.fmartinez.securitypracticetwo.config.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import dev.fmartinez.securitypracticetwo.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;


public class JwtTokenValidator extends OncePerRequestFilter {

    private final JwtUtils utils;

    public JwtTokenValidator(JwtUtils utils) {
        this.utils = utils;
    }

    //Se le da acceso al usuario solo si el token es valido
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {


        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION); //recibe el token

        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {

            jwtToken = jwtToken.substring(7); //para obtener unicamente el token sin el 'Bearer '

            DecodedJWT decodedJWT = utils.validateToken(jwtToken); // se valida si el token es valido

            String username = utils.extractUsername(decodedJWT); // se obtiene el username que viene en el token

            String stringAuthorities = utils.getSpecificClaim(decodedJWT, "authorities").asString();

            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities); // lista de autorizaciones separados por una coma

            //se setea el usuario en el SecurityContextHolder
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        }

        filterChain.doFilter(request, response);
    }
}
