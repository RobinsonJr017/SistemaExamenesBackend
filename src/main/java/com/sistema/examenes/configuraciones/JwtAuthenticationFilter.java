package com.sistema.examenes.configuraciones;

import com.sistema.examenes.servicios.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        System.out.println("🔍 Request: " + method + " " + requestURI);

        // ✅ Permitir rutas públicas sin validación
        if (requestURI.equals("/generate-token") ||
                (requestURI.equals("/usuarios/") && "POST".equals(method))) {
            System.out.println("✅ Ruta pública - permitiendo acceso");
            filterChain.doFilter(request, response);
            return;
        }

        String requestTokenHeader = request.getHeader("Authorization");
        System.out.println("🔍 Authorization Header: " + requestTokenHeader);

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            System.out.println("🔍 JWT Token recibido: " + jwtToken);

            try {
                username = this.jwtUtils.extractUsername(jwtToken);
                System.out.println("🔍 Username extraído del token: " + username);
            } catch (ExpiredJwtException exception) {
                System.out.println("❌ El token ha expirado");
            } catch (Exception exception) {
                System.out.println("❌ Error extrayendo username: " + exception.getMessage());
            }
        } else {
            System.out.println("❌ Token inválido o no empieza con 'Bearer '");
        }

        // ✅ Validar token y setear autenticación en el contexto
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                System.out.println("🔍 UserDetails cargado: " + userDetails.getUsername());

                if (this.jwtUtils.validateToken(jwtToken, userDetails)) {
                    System.out.println("✅ Token válido - autenticando usuario en el contexto");

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    System.out.println("❌ Token inválido según JwtUtils");
                }
            } catch (Exception e) {
                System.out.println("❌ Error cargando UserDetails: " + e.getMessage());
            }
        }

        System.out.println("➡️ Continuando con el filter chain");
        filterChain.doFilter(request, response);
    }
}
