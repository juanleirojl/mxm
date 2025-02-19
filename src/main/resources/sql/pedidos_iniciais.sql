INSERT INTO pedido (
    numero, data, cliente_id, cimento_id, fabrica_id, quantidade, preco_compra, valor_compra, 
    preco_venda, valor_venda, valor_imposto, frete_id, placa_carro, valor_frete, status, orcamento_cimento_id
) VALUES 
('PED-0001', '2025-02-05', 1, 1, 1, 100, 20.00, 2000.00, 30.00, 3000.00, 500.00, 1, 'ABC1234', 300.00, 'PENDENTE', 1),

('PED-0002', '2025-02-06', 2, 2, 2, 200, 22.00, 4400.00, 32.00, 6400.00, 1000.00, 2, 'DEF5678', 600.00, 'FINALIZADO', 2),

('PED-0003', '2025-02-07', 3, 1, 1, 150, 21.00, 3150.00, 31.00, 4650.00, 750.00, 3, 'GHI9012', 450.00, 'CANCELADO', 3);
