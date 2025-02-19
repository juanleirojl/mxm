package com.mxm.config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.mxm.repository.PedidoRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DatabaseInitializationService {

    private final JdbcTemplate jdbcTemplate;
    private final PedidoRepository pedidoRepository;


    @PostConstruct
    public void initDatabase() {
        if (pedidoRepository.count() == 123) {
        	scriptPedidosIniciais("sql/pedidos_iniciais.sql");
        } else {
            System.out.println("Banco de dados já contém registros. Nenhum script foi executado.");
        }
    }

    private void scriptPedidosIniciais(String filePath) {
        try {
            // Lê o arquivo SQL do classpath (src/main/resources)
            InputStream inputStream = new ClassPathResource(filePath).getInputStream();
            String sql = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines().collect(Collectors.joining("\n"));

            // Executa o SQL
            jdbcTemplate.execute(sql);
            System.out.println("Script SQL executado com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao executar o script SQL: " + e.getMessage());
        }
    }
}
