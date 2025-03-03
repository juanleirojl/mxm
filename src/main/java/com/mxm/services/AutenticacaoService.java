package com.mxm.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mxm.models.Usuario;
import com.mxm.repository.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public AutenticacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username.toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    public Usuario getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new RuntimeException("Usuário autenticado não encontrado no banco de dados!");
        }else if(authentication!=null && authentication.getPrincipal()!=null) {
        	return (Usuario) authentication.getPrincipal();
        }

        String username = authentication.getName();
        log.info("Usuario: {}", username);
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário autenticado não encontrado no banco de dados!"));
    }

    public Usuario getUsuarioAutenticadoOrNull() {
        try {
            return getUsuarioAutenticado();
        } catch (Exception e) {
            return null;
        }
    }
}
