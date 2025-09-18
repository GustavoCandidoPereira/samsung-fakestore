package com.samsung.fakestore.service;

import com.samsung.fakestore.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FakeStoreServico {

    private final RestTemplate rest = new RestTemplate();
    private final String API_USERS = "https://fakestoreapi.com/users";
    private final String API_CARTS = "https://fakestoreapi.com/carts";
    private final String API_PRODUCTS = "https://fakestoreapi.com/products";

    public List<UsuarioDTO> obterUsuarios() {
        UsuarioDTO[] arr = rest.getForObject(API_USERS, UsuarioDTO[].class);
        return arr == null ? List.of() : Arrays.asList(arr);
    }

    public List<ProductDTO> obterProdutos() {
        ProductDTO[] arr = rest.getForObject(API_PRODUCTS, ProductDTO[].class);
        return arr == null ? List.of() : Arrays.asList(arr);
    }

    public List<CartDTO> obterCarts() {
        CartDTO[] arr = rest.getForObject(API_CARTS, CartDTO[].class);
        return arr == null ? List.of() : Arrays.asList(arr);
    }

    /**
     * Gera a lista de pedidos (PedidoViewDTO) já mapeada para o front.
     * Validação: exige pelo menos um filtro (clienteId OR orderNumber OR dataRange)
     */
    public List<PedidoViewDTO> gerarPedidos(
            Optional<Integer> clienteId,
            Optional<String> dataFrom,
            Optional<String> dataTo,
            Optional<Integer> orderNumber) {

        boolean hasDateRange = dataFrom.isPresent() && dataTo.isPresent();

        if (clienteId.isEmpty() && orderNumber.isEmpty() && !hasDateRange) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Para pesquisar informe pelo menos: clienteId, orderNumber, ou dateFrom+dateTo");
        }

        List<UsuarioDTO> usuarios = obterUsuarios();
        Map<Integer, UsuarioDTO> mapUsuarios = usuarios.stream()
                .collect(Collectors.toMap(UsuarioDTO::getId, u -> u));

        List<ProductDTO> produtos = obterProdutos();
        Map<Integer, ProductDTO> mapProdutos = produtos.stream()
                .collect(Collectors.toMap(ProductDTO::getId, p -> p));

        List<CartDTO> carts = obterCarts();

        @SuppressWarnings("unused")
		DateTimeFormatter iso = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        LocalDate fromDate = null;
        LocalDate toDate = null;
        if (hasDateRange) {
            try {
                fromDate = LocalDate.parse(dataFrom.get());
                toDate = LocalDate.parse(dataTo.get());
            } catch (Exception e) {
                // tenta parse se vier em ISO com horário
                try {
                    fromDate = OffsetDateTime.parse(dataFrom.get()).toLocalDate();
                    toDate = OffsetDateTime.parse(dataTo.get()).toLocalDate();
                } catch (Exception ex) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de data inválido. Use yyyy-MM-dd");
                }
            }
        }

        List<PedidoViewDTO> resultado = new ArrayList<>();

        for (CartDTO cart : carts) {
            int cid = cart.getUserId();
            int cartId = cart.getId();

            // filtro por cliente
            if (clienteId.isPresent() && clienteId.get() != cid) continue;
            // filtro por order number
            if (orderNumber.isPresent() && orderNumber.get() != cartId) continue;
            // filtro por data 
            LocalDate cartLocalDate = null;
            if (cart.getDate() != null && !cart.getDate().isEmpty()) {
                try {
                    // tenta OffsetDateTime
                    cartLocalDate = OffsetDateTime.parse(cart.getDate()).toLocalDate();
                } catch (Exception e) {
                    // tenta LocalDate
                    try {
                        cartLocalDate = LocalDate.parse(cart.getDate());
                    } catch (Exception ex) {
                        // se não conseguir parse, deixa null
                        cartLocalDate = null;
                    }
                }
            }

            if (hasDateRange && cartLocalDate != null) {
                if (cartLocalDate.isBefore(fromDate) || cartLocalDate.isAfter(toDate)) continue;
            } else if (hasDateRange && cartLocalDate == null) {
                continue;
            }

            // mapeamento do pedido
            PedidoViewDTO pedido = new PedidoViewDTO();
            pedido.setPedidoId(cartId);
            pedido.setData(cart.getDate() == null ? "" : cart.getDate());

            UsuarioDTO usuario = mapUsuarios.get(cid);
            if (usuario != null && usuario.getName() != null) {
                pedido.setCliente(usuario.getName().getFirstname() + " " + usuario.getName().getLastname());
            } else {
                pedido.setCliente("Unknown");
            }

            List<ItemDTO> itens = new ArrayList<>();
            if (cart.getProducts() != null) {
                for (CartProductRef ref : cart.getProducts()) {
                    ProductDTO prod = mapProdutos.get(ref.getProductId());
                    if (prod != null) {
                        ItemDTO it = new ItemDTO();
                        it.setTitulo(prod.getTitle());
                        it.setCategoria(prod.getCategory());
                        it.setPreco(prod.getPrice());
                        it.setQuantidade(ref.getQuantity());
                        it.setSubtotal(prod.getPrice() * ref.getQuantity());
                        it.setImagem(prod.getImage());
                        itens.add(it);
                    }
                }
            }

            pedido.setItens(itens);
            double total = itens.stream().mapToDouble(ItemDTO::getSubtotal).sum();
            pedido.setTotal(total);

            resultado.add(pedido);
        }

        // Se pesquisa por data e retornou pedidos de múltiplos clientes, o front mostra por cliente.
        return resultado;
    }
}
