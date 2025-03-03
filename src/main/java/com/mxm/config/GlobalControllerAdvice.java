//package com.mxm.config;
//
//import java.util.Optional;
//
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ModelAttribute;
//
//import com.mxm.models.Usuario;
//import com.mxm.repository.UsuarioRepository;
//
//@ControllerAdvice
//public class GlobalControllerAdvice {
//
//    private final UsuarioRepository usuarioRepository;
//
//    public GlobalControllerAdvice(UsuarioRepository usuarioRepository) {
//        this.usuarioRepository = usuarioRepository;
//    }
//
//    @ModelAttribute
//    public void adicionarUsuarioLogadoAoModelo(Model model) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (principal instanceof UserDetails) {
//            String username = ((UserDetails) principal).getUsername();
//            Optional<UserDetails> usuario = usuarioRepository.findByUsername(username);
//            model.addAttribute("usuario", usuario);
//        }
//    }
//}
