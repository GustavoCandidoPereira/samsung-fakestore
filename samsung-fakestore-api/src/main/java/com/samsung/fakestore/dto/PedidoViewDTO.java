package com.samsung.fakestore.dto;

import java.util.List;

public class PedidoViewDTO {
    private int pedidoId;
    private String cliente;      // firstname + " " + lastname
    private String data;         // ISO date string
    private List<ItemDTO> itens;
    private double total;

    public int getPedidoId() { return pedidoId; }
    public void setPedidoId(int pedidoId) { this.pedidoId = pedidoId; }
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public List<ItemDTO> getItens() { return itens; }
    public void setItens(List<ItemDTO> itens) { this.itens = itens; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}
