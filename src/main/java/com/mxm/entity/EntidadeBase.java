package com.mxm.entity;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.mxm.models.Usuario;
import com.mxm.services.AutenticacaoService;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class EntidadeBase {

    @ManyToOne
    @JoinColumn(name = "criado_por", nullable = false)
    private Usuario criadoPor;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @ManyToOne
    @JoinColumn(name = "alterado_por")
    private Usuario alteradoPor;

    @Column(name = "alterado_em")
    private LocalDateTime alteradoEm;

    @PrePersist
    protected void prePersist() {
        if (criadoEm == null) {
            criadoEm = LocalDateTime.now();
        }

        if (criadoPor == null) {
            Usuario usuario = AutenticacaoServiceHolder.getAutenticacaoService().getUsuarioAutenticadoOrNull();
            if (usuario != null) {
                criadoPor = usuario;
            }
        }
    }

    @PreUpdate
    protected void preUpdate() {
        alteradoEm = LocalDateTime.now();
        Usuario usuario = AutenticacaoServiceHolder.getAutenticacaoService().getUsuarioAutenticadoOrNull();
        if (usuario != null) {
            alteradoPor = usuario;
        }
    }

    @Component
    public static class AutenticacaoServiceHolder {
        private static AutenticacaoService autenticacaoService;

        public AutenticacaoServiceHolder(AutenticacaoService autenticacaoService) {
            AutenticacaoServiceHolder.autenticacaoService = autenticacaoService;
        }

        public static AutenticacaoService getAutenticacaoService() {
            return autenticacaoService;
        }
    }
}
