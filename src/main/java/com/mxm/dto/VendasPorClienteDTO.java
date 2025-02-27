package com.mxm.dto;

import java.math.BigDecimal;

public record VendasPorClienteDTO(String cliente, long quantidade, BigDecimal totalVendas) { }
