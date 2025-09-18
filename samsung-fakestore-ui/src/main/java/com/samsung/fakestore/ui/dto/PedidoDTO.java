package com.samsung.fakestore.ui.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PedidoDTO {

	private Long pedidoId; 
	private String cliente; 
	private Date data; 
	private Double total;
	private List<ProdutoDTO> itens;
	
	public Long getPedidoId() {
		return pedidoId;
	}
	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public List<ProdutoDTO> getItens() {
		return itens;
	}
	public void setItens(List<ProdutoDTO> itens) {
		this.itens = itens;
	}
}
