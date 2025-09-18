package com.samsung.fakestore.controller;

import com.samsung.fakestore.dto.PedidoViewDTO;
import com.samsung.fakestore.dto.UsuarioDTO;
import com.samsung.fakestore.service.FakeStoreServico;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final FakeStoreServico servico;

    public ApiController(FakeStoreServico servico) { this.servico = servico; }

    /**
     * /api/pedidos
     * Filtros (todos opcionais, mas exige pelo menos um conjunto válido):
     *  - clienteId (integer)
     *  - dateFrom (yyyy-MM-dd)
     *  - dateTo   (yyyy-MM-dd)
     *  - orderNumber (integer) -> id do cart
     */
    @GetMapping("/pedidos")
    public ResponseEntity<List<PedidoViewDTO>> pedidos(
            @RequestParam Optional<Integer> clienteId,
            @RequestParam Optional<String> dateFrom,
            @RequestParam Optional<String> dateTo,
            @RequestParam Optional<Integer> orderNumber) {

        List<PedidoViewDTO> lista = servico.gerarPedidos(clienteId, dateFrom, dateTo, orderNumber);
        return ResponseEntity.ok(lista);
    }

    /**
     * /api/clientes
     * Retorna lista para combo: id + nome (firstname + lastname)
     */
    @GetMapping("/clientes")
    public List<Map<String,Object>> clientes() {
        List<UsuarioDTO> usuarios = servico.obterUsuarios();
        return usuarios.stream().map(u -> {
            Map<String,Object> m = new HashMap<>();
            String nome = "Unknown";
            if (u.getName() != null) {
                nome = u.getName().getFirstname() + " " + u.getName().getLastname();
            }
            m.put("id", u.getId());
            m.put("nome", nome);
            return m;
        }).collect(Collectors.toList());
    }
}
