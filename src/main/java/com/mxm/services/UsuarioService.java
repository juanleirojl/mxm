package com.mxm.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mxm.models.Perfil;
import com.mxm.models.Usuario;
import com.mxm.repository.PerfilRepository;
import com.mxm.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }


    public void salvar(Usuario usuario) {
        // Verifica se já existe um usuário com o mesmo email
        Optional<Usuario> usuarioExistenteEmail = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioExistenteEmail.isPresent()) {
            throw new RuntimeException("Erro: O e-mail já está em uso. Escolha outro e-mail.");
        }

        // Verifica se já existe um usuário com o mesmo username
        Optional<Usuario> usuarioExistenteUsername = usuarioRepository.findByUsername(usuario.getUsername());
        if (usuarioExistenteUsername.isPresent()) {
            throw new RuntimeException("Erro: O nome de usuário já está em uso. Escolha outro.");
        }

        try {
        	usuario.setUsername(usuario.getUsername().toLowerCase());
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // 🔹 Garante que a senha seja criptografada
            usuarioRepository.save(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro ao salvar usuário. Tente novamente.");
        }
    }


    public void atualizar(Long id, Usuario usuarioAtualizado, List<Long> perfisIds, MultipartFile foto) throws IOException {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setUsername(usuarioAtualizado.getUsername().toLowerCase());

        if (!usuarioAtualizado.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuarioAtualizado.getPassword()));
        }

        List<Perfil> perfis = perfilRepository.findAllById(perfisIds);
        usuario.setPerfis(perfis);

        if (!foto.isEmpty()) {
            String fotoUrl = salvarFoto(foto);
            usuario.setFotoUrl(fotoUrl);
        }
        
        SecurityContextHolder.clearContext();

        usuarioRepository.save(usuario);
    }

    public String salvarFoto(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(System.getProperty("user.home"), "uploads");
        
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath); 
            log.info("Pasta criada no caminho {}", uploadPath);
        }

        Path caminhoArquivo = uploadPath.resolve(file.getOriginalFilename());
        log.info("Caminho do arquivo: {}", caminhoArquivo);
        Files.write(caminhoArquivo, file.getBytes());

        return "/uploads/" + file.getOriginalFilename();
    }

    
    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }
/*
    public void alterarSenha(String senhaAtual, String novaSenha, String confirmarSenha) {
        Usuario usuario = autenticacaoService.getUsuarioAutenticado();

        if (!passwordEncoder.matches(senhaAtual, usuario.getPassword())) {
            throw new RuntimeException("Senha atual incorreta.");
        }

        if (!novaSenha.equals(confirmarSenha)) {
            throw new RuntimeException("As senhas não coincidem.");
        }

//        if (novaSenha.length() < 6) {
//            throw new RuntimeException("A senha deve ter pelo menos 6 caracteres.");
//        }
        
        Usuario usuarioExistente = usuarioRepository.findByUsername(usuario.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioExistente.setPassword(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuarioExistente);
    }
*/
    
    public void alterarSenha(String username, String novaSenha, String confirmarSenha) {

        if (!novaSenha.equals(confirmarSenha)) {
            throw new RuntimeException("As senhas não coincidem.");
        }
        
        Usuario usuarioExistente = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioExistente.setPassword(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuarioExistente);
    }
    
}
