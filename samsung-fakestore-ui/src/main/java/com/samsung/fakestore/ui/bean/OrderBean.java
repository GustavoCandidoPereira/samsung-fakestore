package com.samsung.fakestore.ui.bean;

import com.samsung.fakestore.ui.dto.ClienteDTO;
import com.samsung.fakestore.ui.dto.PedidoDTO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

@Named
@SessionScoped
public class OrderBean implements Serializable {

    private static final long serialVersionUID = -2727510244405038413L;

    private List<ClienteDTO> clientes;
    private List<PedidoDTO> pedidos;

    private Long clienteSelecionado;
    private Integer numeroPedido;
    private Date dataInicio;
    private Date dataFim;

    private boolean pesquisaRealizada = false;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public void init() {
        carregarClientes();
    }

    public void carregarClientes() {
        ClienteDTO[] response = restTemplate.getForObject("http://localhost:8081/api/clientes", ClienteDTO[].class);
        clientes = Arrays.asList(Objects.requireNonNull(response));
    }

    public void buscarPedidos() {
        if (clienteSelecionado == null && numeroPedido == null && (dataInicio == null || dataFim == null)) {
            pedidos = Collections.emptyList();
            pesquisaRealizada = true;
            return;
        }

        StringBuilder url = new StringBuilder("http://localhost:8081/api/pedidos?");
        boolean hasParam = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (clienteSelecionado != null) {
            url.append("clienteId=").append(clienteSelecionado);
            hasParam = true;
        }
        if (numeroPedido != null) {
            if (hasParam) url.append("&");
            url.append("orderNumber=").append(numeroPedido);
            hasParam = true;
        }
        if (dataInicio != null && dataFim != null) {
            if (hasParam) url.append("&");
            url.append("dateFrom=").append(sdf.format(dataInicio));
            url.append("&dateTo=").append(sdf.format(dataFim));
        }

        PedidoDTO[] response = restTemplate.getForObject(url.toString(), PedidoDTO[].class);
        pedidos = Arrays.asList(Objects.requireNonNull(response));
        pesquisaRealizada = true;
    }

    public void resetarFiltros() {
        clienteSelecionado = null;
        numeroPedido = null;
        dataInicio = null;
        dataFim = null;
        pedidos = null;
        pesquisaRealizada = false;
    }

    //Métodos de formatação
    public String formatarNomeCliente(String nome) {
        if (nome == null || nome.isEmpty()) return "";
        String[] palavras = nome.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String palavra : palavras) {
            if (palavra.length() > 1) {
                sb.append(Character.toUpperCase(palavra.charAt(0)))
                  .append(palavra.substring(1)).append(" ");
            } else {
                sb.append(palavra.toUpperCase()).append(" ");
            }
        }
        return sb.toString().trim();
    }
    
    public String formatarNomeClienteSelect(String nome) {
        if (nome == null || nome.isEmpty()) return "";
        String[] partes = nome.toLowerCase().split(" ");
        StringBuilder sb = new StringBuilder();
        for (String p : partes) {
            if (p.length() > 0) {
                sb.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1)).append(" ");
            }
        }
        return sb.toString().trim();
    }

    public String formatarPedidoId(Long pedidoId) {
        if (pedidoId == null) return "#000000";
        return String.format("#%06d", pedidoId);
    }


    public List<ClienteDTO> getClientes() { return clientes; }
    public List<PedidoDTO> getPedidos() { return pedidos; }
    public Long getClienteSelecionado() { return clienteSelecionado; }
    public void setClienteSelecionado(Long clienteSelecionado) { this.clienteSelecionado = clienteSelecionado; }
    public Integer getNumeroPedido() { return numeroPedido; }
    public void setNumeroPedido(Integer numeroPedido) { this.numeroPedido = numeroPedido; }
    public Date getDataInicio() { return dataInicio; }
    public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }
    public Date getDataFim() { return dataFim; }
    public void setDataFim(Date dataFim) { this.dataFim = dataFim; }
    public boolean isPesquisaRealizada() { return pesquisaRealizada; }
}
