package com.mxm.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.mxm.dto.TermoCarregamentoDTO;
import com.mxm.dto.termo.TermoCarregamentoFilter;
import com.mxm.dto.termo.TermoCarregamentoRequest;
import com.mxm.entity.TermoCarregamento;
import com.mxm.repository.TermoCarregamentoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TermoCarregamentoService {

  private final TermoCarregamentoRepository termoCarregamentoRepository;

  public List<TermoCarregamento> listar() {
    return termoCarregamentoRepository.findAll();
  }

  public List<TermoCarregamentoDTO> listarSemArquivo() {
    return termoCarregamentoRepository.findAllWithoutArquivo();
  }

  public List<TermoCarregamento> filter(final TermoCarregamentoFilter filter) {
    if (StringUtils.isNotEmpty(filter.getMotorista())) {
      return termoCarregamentoRepository.findByMotorista(filter.getMotorista());
    } else if (StringUtils.isNotEmpty(filter.getPlaca())) {
      return termoCarregamentoRepository.findByPlaca(filter.getPlaca());
    } else {
      return termoCarregamentoRepository.findByNumeroPedido(filter.getNumeroPedido());
    }
  }

  public void cadastrar(TermoCarregamentoRequest request, byte[] arquivoGerado) {
	    try {
	      TermoCarregamento termo = TermoCarregamento.builder()
	          .dataCadastro(transformaDateStringLocalDateTime(request.getData()))
	          .motorista(request.getMotorista())
	          .placa(request.getPlaca())
	          .numeroPedido(request.getNumeroPedido())
	          .quantidadeSacos(request.getQuantidadeSacos())
	          .arquivo(arquivoGerado)
	          .build();

	      termoCarregamentoRepository.save(termo);
	    } catch (Exception e) {
	      throw new RuntimeException("Erro ao cadastrar termo de carregamento", e);
	    }
	  }

  public TermoCarregamento findById(Long id) {
    return termoCarregamentoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Termo de carregamento não encontrado"));
  }

  private LocalDateTime transformaDateStringLocalDateTime(String data) {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(data, dateFormatter);
    LocalTime localTime = LocalTime.now();
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    return localDateTime;
  }

}
