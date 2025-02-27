package com.mxm.helpers;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import com.mxm.entity.Pedido;
import com.mxm.entity.PrecoCimento;
import com.mxm.enums.StatusPagamento;
import com.mxm.enums.StatusPedido;
import com.mxm.repository.CidadeRepository;
import com.mxm.repository.CimentoRepository;
import com.mxm.repository.ClienteRepository;
import com.mxm.repository.EmpresaRepository;
import com.mxm.repository.FabricaRepository;
import com.mxm.repository.PedidoRepository;
import com.mxm.repository.PrecoCimentoRepository;
import com.mxm.services.PedidoService;

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
  
  @Autowired
  private PedidoRepository pedidoRepository;
  
  @Autowired
  private PedidoService pedidoService;

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
      cimento5.setMarca("Nacional CPII");
      cimento5.setFabrica(fabrica3);
      cimentoRepository.save(cimento5);

      Cimento cimento6 = new Cimento();
      cimento6.setTipo("CPV");
      cimento6.setMarca("Nacional CPV");
      cimento6.setFabrica(fabrica3);
      cimentoRepository.save(cimento6);


      // Inserindo alguns clientes
      adicionarClientes(cidade6);
    }
    
    if(pedidoRepository.count() == 0) {
    	criarPedidos();
    }

    if (precoCimentoRepository.count() == 0) {
      Empresa empresa1 = empresaRepository.findByCnpj("21.845.732/0001-52").orElseThrow();
      Empresa empresa2 = empresaRepository.findByCnpj("43.693.107/0001-75").orElseThrow();

      Cimento poty = cimentoRepository.findByMarcaAndTipo("Poty", "CPII").orElseThrow();
      Cimento nacionalCPII = cimentoRepository.findByMarcaAndTipo("Nacional CPII", "CPII").orElseThrow();
      Cimento nacionalCPV = cimentoRepository.findByMarcaAndTipo("Nacional CPV", "CPV").orElseThrow();
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

	private void criarPedidos() {
	
		Pedido pedido1 = Pedido.builder()
			    .data(LocalDate.of(2024, 12, 12))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Assis").orElse(null))
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Poty").orElse(null))
			    .quantidade(280)
			    .precoCimentoVendido(new BigDecimal("35.00"))
			    .precoCimentoComprado(new BigDecimal("28.50"))
			    .frete(new BigDecimal("950.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			    
			pedidoService.criarPedido(pedido1);
			
		Pedido pedido2 = Pedido.builder()
			    .data(LocalDate.of(2024, 12, 13))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Assis").orElse(null))
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
			    .quantidade(320)
			    .precoCimentoVendido(new BigDecimal("31.99"))
			    .precoCimentoComprado(new BigDecimal("25.50"))
			    .frete(new BigDecimal("1000"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			    
			pedidoService.criarPedido(pedido2);
			
		Pedido pedido3 = Pedido.builder()
			    .data(LocalDate.of(2024, 12, 13))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Galega").orElse(null))
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
			    .quantidade(320)
			    .precoCimentoVendido(new BigDecimal("29.90"))
			    .precoCimentoComprado(new BigDecimal("25.50"))
			    .frete(new BigDecimal("600"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			    
			pedidoService.criarPedido(pedido3);
			
			Pedido pedido4 = Pedido.builder()
				    .data(LocalDate.of(2024, 12, 12))
				    .cliente(clienteRepository.findByNomeIgnoreCase("Daniel").orElse(null))
				    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
				    .quantidade(320)
				    .precoCimentoVendido(new BigDecimal("29.90"))
				    .precoCimentoComprado(new BigDecimal("25.50"))
				    .frete(new BigDecimal("750"))
				    .statusPedido(StatusPedido.REALIZADO)
				    .statusPagamento(StatusPagamento.PAGO)
				    .build();
				    
				pedidoService.criarPedido(pedido4);
				
			Pedido pedido5 = Pedido.builder()
				    .data(LocalDate.of(2024, 12, 13))
				    .cliente(clienteRepository.findByNomeIgnoreCase("Indio").orElse(null))
				    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
				    .quantidade(280)
				    .precoCimentoVendido(new BigDecimal("31.50"))
				    .precoCimentoComprado(new BigDecimal("25.50"))
				    .frete(new BigDecimal("650"))
				    .statusPedido(StatusPedido.REALIZADO)
				    .statusPagamento(StatusPagamento.PAGO)
				    .build();
				    
				pedidoService.criarPedido(pedido5);
				
			Pedido pedido6 = Pedido.builder()
				    .data(LocalDate.of(2024, 12, 13))
				    .cliente(clienteRepository.findByNomeIgnoreCase("Assis").orElse(null)) 
				    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
				    .quantidade(320)
				    .precoCimentoVendido(new BigDecimal("31.99"))
				    .precoCimentoComprado(new BigDecimal("25.50"))
				    .frete(new BigDecimal("1000"))
				    .statusPedido(StatusPedido.REALIZADO)
				    .statusPagamento(StatusPagamento.PAGO)
				    .build();
				    
				pedidoService.criarPedido(pedido6);
			
			Pedido pedido7 = Pedido.builder()
				    .data(LocalDate.of(2024, 12, 13))
				    .cliente(clienteRepository.findByNomeIgnoreCase("Mario").orElse(null))  // Buscar cliente Assis
				    .cimento(cimentoRepository.findByMarcaIgnoreCase("Poty").orElse(null))   // Buscar cimento Poty
				    .quantidade(280)
				    .precoCimentoVendido(new BigDecimal("33.20"))
				    .precoCimentoComprado(new BigDecimal("28.10"))
				    .frete(new BigDecimal("800"))
				    .statusPedido(StatusPedido.REALIZADO)
				    .statusPagamento(StatusPagamento.PAGO)
				    .build();
				    
				pedidoService.criarPedido(pedido7);
				
  				Pedido pedido8 = Pedido.builder()
  			        .data(LocalDate.of(2024, 12, 20))
  			        .cliente(clienteRepository.findByNomeIgnoreCase("Ubaratão").orElse(null))  // Buscar cliente Ubaratão
  			        .cimento(cimentoRepository.findByMarcaIgnoreCase("Poty").orElse(null))  // Buscar cimento Poty
  			        .quantidade(360)
  			        .precoCimentoVendido(new BigDecimal("33.50"))
  			        .precoCimentoComprado(new BigDecimal("28.10"))
  			        .frete(new BigDecimal("950"))
  			        .statusPedido(StatusPedido.REALIZADO)
  			        .statusPagamento(StatusPagamento.PAGO)
  			        .build();
  				
			pedidoService.criarPedido(pedido8);
			
			Pedido pedido9 = Pedido.builder()
			    .data(LocalDate.of(2024, 12, 21))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Ceasinha").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Poty").orElse(null))
			    .quantidade(280)
			    .precoCimentoVendido(new BigDecimal("33.99"))
			    .precoCimentoComprado(new BigDecimal("28.50"))
			    .frete(new BigDecimal("650.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido9);

			Pedido pedido10 = Pedido.builder()
			    .data(LocalDate.of(2024, 12, 23))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Galega").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
			    .quantidade(320)
			    .precoCimentoVendido(new BigDecimal("29.90"))
			    .precoCimentoComprado(new BigDecimal("25.50"))
			    .frete(new BigDecimal("600.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido10);
			
			Pedido pedido11 = Pedido.builder()
			    .data(LocalDate.of(2024, 12, 23))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Gustavo Fraga").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
			    .quantidade(120)
			    .precoCimentoVendido(new BigDecimal("33.50"))
			    .precoCimentoComprado(new BigDecimal("25.50"))
			    .frete(new BigDecimal("500.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido11);

			Pedido pedido12 = Pedido.builder()
			    .data(LocalDate.of(2024, 12, 23))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Fabio").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Poty").orElse(null))
			    .quantidade(280)
			    .precoCimentoVendido(new BigDecimal("33.50"))
			    .precoCimentoComprado(new BigDecimal("28.07"))
			    .frete(new BigDecimal("800.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido12);

			Pedido pedido13 = Pedido.builder()
			    .data(LocalDate.of(2024, 12, 23))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Assis").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Poty").orElse(null))
			    .quantidade(280)
			    .precoCimentoVendido(new BigDecimal("35.00"))
			    .precoCimentoComprado(new BigDecimal("28.07"))
			    .frete(new BigDecimal("1000.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido13);

			Pedido pedido14 = Pedido.builder()
			    .data(LocalDate.of(2024, 12, 27))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Angelo").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
			    .quantidade(320)
			    .precoCimentoVendido(new BigDecimal("29.99"))
			    .precoCimentoComprado(new BigDecimal("25.50"))
			    .frete(new BigDecimal("700.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido14);

			Pedido pedido15 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 3))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Ceasinha").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Poty").orElse(null))
			    .quantidade(280)
			    .precoCimentoVendido(new BigDecimal("33.99"))
			    .precoCimentoComprado(new BigDecimal("28.50"))
			    .frete(new BigDecimal("650.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido15);


			Pedido pedido16 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 4))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Mario").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
			    .quantidade(120)
			    .precoCimentoVendido(new BigDecimal("31.50"))
			    .precoCimentoComprado(new BigDecimal("25.50"))
			    .frete(new BigDecimal("400.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido16);

			Pedido pedido17 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 6))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Galega").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
			    .quantidade(320)
			    .precoCimentoVendido(new BigDecimal("29.90"))
			    .precoCimentoComprado(new BigDecimal("25.50"))
			    .frete(new BigDecimal("600.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido17);

			Pedido pedido18 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 6))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Ubaratão").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
			    .quantidade(200)
			    .precoCimentoVendido(new BigDecimal("31.00"))
			    .precoCimentoComprado(new BigDecimal("25.50"))
			    .frete(new BigDecimal("400.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido18);

			Pedido pedido19 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 7))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Mario").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Poty").orElse(null))
			    .quantidade(280)
			    .precoCimentoVendido(new BigDecimal("33.20"))
			    .precoCimentoComprado(new BigDecimal("28.07"))
			    .frete(new BigDecimal("800.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido19);

			Pedido pedido20 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 8))
			    .cliente(clienteRepository.findByNomeIgnoreCase("ConstruVida - SF").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
			    .quantidade(160)
			    .precoCimentoVendido(new BigDecimal("31.00"))
			    .precoCimentoComprado(new BigDecimal("25.50"))
			    .frete(new BigDecimal("400.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido20);

			Pedido pedido21 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 8))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Bill Jaua").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
			    .quantidade(160)
			    .precoCimentoVendido(new BigDecimal("31.00"))
			    .precoCimentoComprado(new BigDecimal("25.50"))
			    .frete(new BigDecimal("400.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido21);

			Pedido pedido22 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 8))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Bode - Paripe").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
			    .quantidade(320)
			    .precoCimentoVendido(new BigDecimal("29.50"))
			    .precoCimentoComprado(new BigDecimal("25.50"))
			    .frete(BigDecimal.ZERO)
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido22);

			Pedido pedido23 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 9))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Hudson SM").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Poty").orElse(null))
			    .quantidade(160)
			    .precoCimentoVendido(new BigDecimal("33.50"))
			    .precoCimentoComprado(new BigDecimal("28.50"))
			    .frete(new BigDecimal("400.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido23);

			Pedido pedido24 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 7))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Jura PF").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Poty").orElse(null))
			    .quantidade(280)
			    .precoCimentoVendido(new BigDecimal("34.00"))
			    .precoCimentoComprado(new BigDecimal("28.07"))
			    .frete(new BigDecimal("850.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido24);

			Pedido pedido25 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 10))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Ceasinha").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Poty").orElse(null))
			    .quantidade(280)
			    .precoCimentoVendido(new BigDecimal("33.99"))
			    .precoCimentoComprado(new BigDecimal("28.50"))
			    .frete(new BigDecimal("650.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido25);

			Pedido pedido26 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 14))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Loja").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
			    .quantidade(280)
			    .precoCimentoVendido(new BigDecimal("32.99"))
			    .precoCimentoComprado(new BigDecimal("25.00"))
			    .frete(new BigDecimal("650.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido26);
	
			Pedido pedido27 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 14))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Jura Candeias").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
			    .quantidade(280)
			    .precoCimentoVendido(new BigDecimal("31.00"))
			    .precoCimentoComprado(new BigDecimal("25.00"))
			    .frete(new BigDecimal("650.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido27);

			Pedido pedido28 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 14))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Ubaratão").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Poty").orElse(null))
			    .quantidade(360)
			    .precoCimentoVendido(new BigDecimal("33.50"))
			    .precoCimentoComprado(new BigDecimal("28.49"))
			    .frete(new BigDecimal("900.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido28);

			Pedido pedido29 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 17))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Assis").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Poty").orElse(null))
			    .quantidade(280)
			    .precoCimentoVendido(new BigDecimal("35.00"))
			    .precoCimentoComprado(new BigDecimal("27.92"))
			    .frete(new BigDecimal("1000.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido29);

			Pedido pedido30 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 17))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Eleno SM").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Poty").orElse(null))
			    .quantidade(280)
			    .precoCimentoVendido(new BigDecimal("33.50"))
			    .precoCimentoComprado(new BigDecimal("27.92"))
			    .frete(new BigDecimal("750.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido30);

			Pedido pedido31 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 17))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Fabio").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Poty").orElse(null))
			    .quantidade(280)
			    .precoCimentoVendido(new BigDecimal("33.50"))
			    .precoCimentoComprado(new BigDecimal("27.92"))
			    .frete(new BigDecimal("800.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido31);
	
			Pedido pedido32 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 20))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Loja").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Nacional CPV").orElse(null))
			    .quantidade(80)
			    .precoCimentoVendido(new BigDecimal("27.00"))
			    .precoCimentoComprado(new BigDecimal("15.50"))
			    .frete(new BigDecimal("65.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido32);

			Pedido pedido33 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 20))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Ubaratão").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
			    .quantidade(200)
			    .precoCimentoVendido(new BigDecimal("31.00"))
			    .precoCimentoComprado(new BigDecimal("25.00"))
			    .frete(new BigDecimal("325.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido33);

			Pedido pedido34 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 20))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Mario").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
			    .quantidade(120)
			    .precoCimentoVendido(new BigDecimal("31.50"))
			    .precoCimentoComprado(new BigDecimal("25.00"))
			    .frete(new BigDecimal("325.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido34);

			Pedido pedido35 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 24))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Aguiar").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
			    .quantidade(280)
			    .precoCimentoVendido(new BigDecimal("32.00"))
			    .precoCimentoComprado(new BigDecimal("25.00"))
			    .frete(new BigDecimal("750.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido35);

			Pedido pedido36 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 27))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Angelo").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
			    .quantidade(320)
			    .precoCimentoVendido(new BigDecimal("30.00"))
			    .precoCimentoComprado(new BigDecimal("25.00"))
			    .frete(new BigDecimal("800.00"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido36);

			Pedido pedido37 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 27))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Libania").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Poty").orElse(null))
			    .quantidade(80)
			    .precoCimentoVendido(new BigDecimal("35.00"))
			    .precoCimentoComprado(new BigDecimal("29.58"))
			    .frete(new BigDecimal("0"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido37);

			Pedido pedido38 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 27))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Bereu").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Poty").orElse(null))
			    .quantidade(120)
			    .precoCimentoVendido(new BigDecimal("37.00"))
			    .precoCimentoComprado(new BigDecimal("29.58"))
			    .frete(new BigDecimal("0"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido38);

			Pedido pedido39 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 27))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Itala - MG").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
			    .quantidade(160)
			    .precoCimentoVendido(new BigDecimal("32.00"))
			    .precoCimentoComprado(new BigDecimal("25.00"))
			    .frete(new BigDecimal("0"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido39);

			Pedido pedido40 = Pedido.builder()
			    .data(LocalDate.of(2025, 1, 27))
			    .cliente(clienteRepository.findByNomeIgnoreCase("Vila Naval").orElse(null)) 
			    .cimento(cimentoRepository.findByMarcaIgnoreCase("Faciment").orElse(null))
			    .quantidade(280)
			    .precoCimentoVendido(new BigDecimal("30.00"))
			    .precoCimentoComprado(new BigDecimal("25.00"))
			    .frete(new BigDecimal("0"))
			    .statusPedido(StatusPedido.REALIZADO)
			    .statusPagamento(StatusPagamento.PAGO)
			    .build();
			pedidoService.criarPedido(pedido40);
	
	}

	private void adicionarClientes(Cidade cidade6) {
		// Inserindo alguns clientes
		Cliente cliente1 = new Cliente();
		cliente1.setNome("Galega");
		cliente1.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente1);

		Cliente cliente2 = new Cliente();
		cliente2.setNome("Assis");
		cliente2.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente2);

		Cliente cliente3 = new Cliente();
		cliente3.setNome("Daniel");
		cliente3.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente3);

		Cliente cliente4 = new Cliente();
		cliente4.setNome("Indio");
		cliente4.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente4);

		Cliente cliente5 = new Cliente();
		cliente5.setNome("Mario");
		cliente5.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente5);

		Cliente cliente6 = new Cliente();
		cliente6.setNome("Ubaratão");
		cliente6.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente6);

		Cliente cliente7 = new Cliente();
		cliente7.setNome("Ceasinha");
		cliente7.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente7);

		Cliente cliente8 = new Cliente();
		cliente8.setNome("Gustavo Fraga");
		cliente8.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente8);

		Cliente cliente9 = new Cliente();
		cliente9.setNome("Fabio");
		cliente9.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente9);

		Cliente cliente10 = new Cliente();
		cliente10.setNome("Angelo");
		cliente10.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente10);

		Cliente cliente11 = new Cliente();
		cliente11.setNome("Bill Jaua");
		cliente11.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente11);

		Cliente cliente12 = new Cliente();
		cliente12.setNome("Novo Horizonte");
		cliente12.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente12);

		Cliente cliente13 = new Cliente();
		cliente13.setNome("ConstruVida - SF");
		cliente13.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente13);

		Cliente cliente14 = new Cliente();
		cliente14.setNome("Bode - Paripe");
		cliente14.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente14);

		Cliente cliente15 = new Cliente();
		cliente15.setNome("Hudson SM");
		cliente15.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente15);

		Cliente cliente16 = new Cliente();
		cliente16.setNome("Jura PF");
		cliente16.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente16);

		Cliente cliente17 = new Cliente();
		cliente17.setNome("Jura Candeias");
		cliente17.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente17);

		Cliente cliente18 = new Cliente();
		cliente18.setNome("Loja");
		cliente18.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente18);

		Cliente cliente19 = new Cliente();
		cliente19.setNome("Eleno SM");
		cliente19.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente19);

		Cliente cliente20 = new Cliente();
		cliente20.setNome("Aguiar");
		cliente20.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente20);

		Cliente cliente21 = new Cliente();
		cliente21.setNome("Libania");
		cliente21.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente21);

		Cliente cliente22 = new Cliente();
		cliente22.setNome("Bereu");
		cliente22.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente22);

		Cliente cliente23 = new Cliente();
		cliente23.setNome("Itala - MG");
		cliente23.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente23);

		Cliente cliente24 = new Cliente();
		cliente24.setNome("Vila Naval");
		cliente24.setCidade(cidade6); // Salvador
		clienteRepository.save(cliente24);

	}
}
