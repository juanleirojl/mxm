package com.mxm.helpers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.mxm.entity.Cidade;
import com.mxm.entity.Cimento;
import com.mxm.entity.Cliente;
import com.mxm.entity.Empresa;
import com.mxm.entity.Fabrica;
import com.mxm.entity.PrecoCimento;
import com.mxm.repository.CidadeRepository;
import com.mxm.repository.CimentoRepository;
import com.mxm.repository.ClienteRepository;
import com.mxm.repository.EmpresaRepository;
import com.mxm.repository.FabricaRepository;
import com.mxm.repository.PrecoCimentoRepository;

@Component
public class InserirDadosIniciais implements CommandLineRunner {

  @Autowired
  private CidadeRepository cidadeRepository;

  @Autowired
  private EmpresaRepository empresaRepository;

  @Autowired
  private CimentoRepository cimentoRepository;

  @Autowired
  private FabricaRepository fabricaRepository;

  @Autowired
  private ClienteRepository clienteRepository;

  @Autowired
  private PrecoCimentoRepository precoCimentoRepository;

  @Override
  public void run(String... args) throws Exception {
    if (cidadeRepository.count() == 0) {
      Cidade cidade1 = new Cidade();
      cidade1.setNome("Simões Filho");
      Cidade cidade2 = new Cidade();
      cidade2.setNome("Camaçari");
      Cidade cidade3 = new Cidade();
      cidade3.setNome("Feira de Santana");
      Cidade cidade4 = new Cidade();
      cidade4.setNome("Itabuna");
      Cidade cidade5 = new Cidade();
      cidade5.setNome("Laranjeiras");
      Cidade cidade6 = new Cidade();
      cidade6.setNome("Salvador");
      Cidade cidade7 = new Cidade();
      cidade7.setNome("Candeias");
      cidadeRepository
          .saveAll(List.of(cidade1, cidade6, cidade2, cidade3, cidade4, cidade5, cidade7));


      Empresa empresa1 = new Empresa();
      empresa1.setNome("Ecoclean");
      empresa1.setResponsavel("Ivo Menezes");
      empresa1.setTelefone("(71) 8106-0146");
      empresa1.setCnpj("21.845.732/0001-52");
      empresaRepository.save(empresa1);

      Empresa empresa2 = new Empresa();
      empresa2.setNome("Alpes");
      empresa2.setResponsavel("Ivo Menezes");
      empresa2.setTelefone("(71) 8106-0146");
      empresa2.setCnpj("43.693.107/0001-75");
      empresaRepository.save(empresa2);


      // Inserindo algumas fábricas
      Fabrica fabrica1 = new Fabrica();
      fabrica1.setNome("Votorotim");
      fabrica1.setResponsavel("Katia Galega");
      fabrica1.setTelefone("(21) 9876-5432");
      fabrica1.setEndereco("Rua Fábrica, 100");
      fabrica1.setCidade(cidade1);
      fabricaRepository.save(fabrica1);

      Fabrica fabrica2 = new Fabrica();
      fabrica2.setNome("Votorantim");
      fabrica2.setResponsavel("Responsável Fábrica A");
      fabrica2.setTelefone("(21) 9876-5432");
      fabrica2.setEndereco("Rua Fábrica, 100");
      fabrica2.setCidade(cidade1);
      fabricaRepository.save(fabrica2);

      Fabrica fabrica3 = new Fabrica();
      fabrica3.setNome("Nacional");
      fabrica3.setResponsavel("Responsável Fábrica A");
      fabrica3.setTelefone("(21) 9876-5432");
      fabrica3.setEndereco("Rua Fábrica, 100");
      fabrica3.setCidade(cidade1);
      fabricaRepository.save(fabrica3);

      Fabrica fabrica4 = new Fabrica();
      fabrica4.setNome("CNS");
      fabrica4.setResponsavel("Responsável Fábrica A");
      fabrica4.setTelefone("(21) 9876-5432");
      fabrica4.setEndereco("Rua Fábrica, 100");
      fabrica4.setCidade(cidade1);
      fabricaRepository.save(fabrica4);


      // Inserindo alguns cimentos
      Cimento cimento1 = new Cimento();
      cimento1.setTipo("CPIV");
      cimento1.setMarca("Faciment");
      cimento1.setFabrica(fabrica1);
      cimentoRepository.save(cimento1);

      Cimento cimento2 = new Cimento();
      cimento2.setTipo("CPII");
      cimento2.setMarca("Poty");
      cimento2.setFabrica(fabrica2);
      cimentoRepository.save(cimento2);

      Cimento cimento3 = new Cimento();
      cimento3.setTipo("CPII");
      cimento3.setMarca("Montes Claros");
      cimento3.setFabrica(fabrica4);
      cimentoRepository.save(cimento3);

      Cimento cimento4 = new Cimento();
      cimento4.setTipo("CPII");
      cimento4.setMarca("Elizabeth");
      cimento4.setFabrica(fabrica4);
      cimentoRepository.save(cimento4);

      Cimento cimento5 = new Cimento();
      cimento5.setTipo("CPII");
      cimento5.setMarca("Nacional");
      cimento5.setFabrica(fabrica3);
      cimentoRepository.save(cimento5);

      Cimento cimento6 = new Cimento();
      cimento6.setTipo("CPV");
      cimento6.setMarca("Nacional");
      cimento6.setFabrica(fabrica3);
      cimentoRepository.save(cimento6);


      // Inserindo alguns clientes
      Cliente cliente1 = new Cliente();
      cliente1.setNome("Katia Galega");
      cliente1.setTelefone("(11) 9999-8888");
      cliente1.setResponsavel("Responsável Cliente A");
      cliente1.setEndereco("Rua Cliente, 200");
      cliente1.setCidade(cidade1);
      cliente1.setEmail("clientea@email.com");
      clienteRepository.save(cliente1);
      
      Cliente cliente2 = new Cliente();
      cliente2.setNome("Pedro Chaves");
      cliente2.setTelefone("(11) 9999-8888");
      cliente2.setResponsavel("Pedro");
      cliente2.setEndereco("Rua Cliente, 200");
      cliente2.setCidade(cidade1);
      cliente2.setEmail("clientea@email.com");
      clienteRepository.save(cliente2);
      
      Cliente cliente3 = new Cliente();
      cliente3.setNome("Marileide");
      cliente3.setTelefone("(11) 9999-8888");
      cliente3.setResponsavel("Marileide");
      cliente3.setEndereco("Rua Cliente, 200");
      cliente3.setCidade(cidade1);
      cliente3.setEmail("clientea@email.com");
      clienteRepository.save(cliente3);
      
      Cliente cliente4 = new Cliente();
      cliente4.setNome("Flamengos Prime");
      cliente4.setTelefone("(11) 9999-8888");
      cliente4.setResponsavel("Flamengos Prime");
      cliente4.setEndereco("Rua Cliente, 200");
      cliente4.setCidade(cidade1);
      cliente4.setEmail("clientea@email.com");
      clienteRepository.save(cliente4);
      
      Cliente cliente5 = new Cliente();
      cliente5.setNome("Marcio Cimeaço");
      cliente5.setTelefone("(11) 9999-8888");
      cliente5.setResponsavel("Marcio");
      cliente5.setEndereco("Rua Cliente, 200");
      cliente5.setCidade(cidade1);
      cliente5.setEmail("clientea@email.com");
      clienteRepository.save(cliente5);
    }

    if (precoCimentoRepository.count() == 0) {
      Empresa empresa1 = empresaRepository.findByCnpj("21.845.732/0001-52").orElseThrow();
      Empresa empresa2 = empresaRepository.findByCnpj("43.693.107/0001-75").orElseThrow();

      Cimento poty = cimentoRepository.findByMarcaAndTipo("Poty", "CPII").orElseThrow();
      Cimento nacionalCPII = cimentoRepository.findByMarcaAndTipo("Nacional", "CPII").orElseThrow();
      Cimento nacionalCPV = cimentoRepository.findByMarcaAndTipo("Nacional", "CPV").orElseThrow();
      Cimento faciment = cimentoRepository.findByMarcaAndTipo("Faciment", "CPIV").orElseThrow();

      Cidade simoesFilho = cidadeRepository.findByNomeIgnoreCase("Simões Filho").orElseThrow();
      Cidade camacari = cidadeRepository.findByNomeIgnoreCase("Camaçari").orElseThrow();
      Cidade feira = cidadeRepository.findByNomeIgnoreCase("Feira de Santana").orElseThrow();
      Cidade itabuna = cidadeRepository.findByNomeIgnoreCase("Itabuna").orElseThrow();
      Cidade laranjeiras = cidadeRepository.findByNomeIgnoreCase("Laranjeiras").orElseThrow();
      Cidade candeias = cidadeRepository.findByNomeIgnoreCase("Candeias").orElseThrow();
      
   // Ecoclean Preços
      precoCimentoRepository.saveAll(List.of(
          new PrecoCimento(null, empresa1, poty, simoesFilho, 280, new BigDecimal("28.87"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa1, poty, camacari, 280, new BigDecimal("28.87"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa1, poty, feira, 280, new BigDecimal("29.70"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa1, poty, itabuna, 280, new BigDecimal("30.57"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa1, poty, laranjeiras, 280, new BigDecimal("27.63"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa1, nacionalCPII, simoesFilho, 280, new BigDecimal("29.91"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa1, nacionalCPV, simoesFilho, 280, new BigDecimal("31.21"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa1, faciment, candeias, 280, new BigDecimal("26.00"),
              LocalDateTime.now())));

      // Alpes Preços
      precoCimentoRepository.saveAll(List.of(
          new PrecoCimento(null, empresa2, poty, simoesFilho, 280, new BigDecimal("28.87"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa2, poty, camacari, 280, new BigDecimal("28.87"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa2, poty, feira, 280, new BigDecimal("29.70"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa2, poty, itabuna, 280, new BigDecimal("30.57"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa2, poty, laranjeiras, 280, new BigDecimal("27.63"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa2, nacionalCPII, simoesFilho, 280, new BigDecimal("29.10"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa2, faciment, candeias, 280, new BigDecimal("28.10"),
              LocalDateTime.now())));

      precoCimentoRepository.saveAll(List.of(
          // Poty - CPII
          new PrecoCimento(null, empresa1, poty, simoesFilho, 560, new BigDecimal("28.43"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa1, poty, camacari, 560, new BigDecimal("28.43"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa1, poty, feira, 560, new BigDecimal("29.26"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa1, poty, itabuna, 560, new BigDecimal("30.11"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa1, poty, laranjeiras, 560, new BigDecimal("27.21"),
              LocalDateTime.now()),

          new PrecoCimento(null, empresa1, poty, simoesFilho, 720, new BigDecimal("28.29"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa1, poty, camacari, 720, new BigDecimal("28.29"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa1, poty, feira, 720, new BigDecimal("29.11"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa1, poty, itabuna, 720, new BigDecimal("29.96"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa1, poty, laranjeiras, 720, new BigDecimal("27.08"),
              LocalDateTime.now()),

          // Nacional - CPII
          new PrecoCimento(null, empresa1, nacionalCPII, simoesFilho, 560, new BigDecimal("29.91"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa1, nacionalCPII, simoesFilho, 720, new BigDecimal("29.91"),
              LocalDateTime.now()),

          // Nacional - CPV
          new PrecoCimento(null, empresa1, nacionalCPV, simoesFilho, 560, new BigDecimal("31.21"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa1, nacionalCPV, simoesFilho, 720, new BigDecimal("31.21"),
              LocalDateTime.now()),

          // Faciment - CPIV
          new PrecoCimento(null, empresa1, faciment, candeias, 560, new BigDecimal("26.00"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa1, faciment, candeias, 720, new BigDecimal("26.00"),
              LocalDateTime.now())));


      precoCimentoRepository.saveAll(List.of(
          // Poty - CPII
          new PrecoCimento(null, empresa2, poty, simoesFilho, 560, new BigDecimal("28.87"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa2, poty, camacari, 560, new BigDecimal("28.87"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa2, poty, feira, 560, new BigDecimal("29.70"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa2, poty, itabuna, 560, new BigDecimal("30.57"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa2, poty, laranjeiras, 560, new BigDecimal("27.63"),
              LocalDateTime.now()),

          new PrecoCimento(null, empresa2, poty, simoesFilho, 720, new BigDecimal("28.87"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa2, poty, camacari, 720, new BigDecimal("28.87"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa2, poty, feira, 720, new BigDecimal("29.70"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa2, poty, itabuna, 720, new BigDecimal("30.57"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa2, poty, laranjeiras, 720, new BigDecimal("27.63"),
              LocalDateTime.now()),

          // Nacional - CPII
          new PrecoCimento(null, empresa2, nacionalCPII, simoesFilho, 560, new BigDecimal("29.10"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa2, nacionalCPII, simoesFilho, 720, new BigDecimal("29.10"),
              LocalDateTime.now()),

          // Faciment - CPIV
          new PrecoCimento(null, empresa2, faciment, candeias, 560, new BigDecimal("28.10"),
              LocalDateTime.now()),
          new PrecoCimento(null, empresa2, faciment, candeias, 720, new BigDecimal("28.10"),
              LocalDateTime.now())));

    }
  }
}
