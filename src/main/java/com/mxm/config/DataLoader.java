package com.mxm.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mxm.models.Perfil;
import com.mxm.models.Usuario;
import com.mxm.repository.PerfilRepository;
import com.mxm.repository.UsuarioRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PerfilRepository perfilRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (perfilRepository.count() == 0) {
                Perfil admin = new Perfil();
                admin.setNome("ADMIN");
                perfilRepository.save(admin);

                Perfil cimento = new Perfil();
                cimento.setNome("CIMENTO");
                perfilRepository.save(cimento);

                Perfil licitacao = new Perfil();
                licitacao.setNome("LICITACAO_FEIRA");
                perfilRepository.save(licitacao);
            }

            if (usuarioRepository.count() == 0) {
                Usuario adminUser = new Usuario();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("admin"));
                adminUser.setNome("Administrador");
                adminUser.setEmail("admin@mxmcimentos.com.br");
                adminUser.setFotoUrl("/uploads/ivo-profile.png"); // Caminho da foto
                adminUser.setPerfis(List.of(perfilRepository.findByNome("ADMIN").get()));
                usuarioRepository.save(adminUser);
            }
        };
    }
}
