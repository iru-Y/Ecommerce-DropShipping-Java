package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.domain.order.Order;
import com.delivery.trizi.trizi.domain.product.ProductModel;
import com.delivery.trizi.trizi.services.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/orders")
@AllArgsConstructor
@Log4j2
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<Page<Order>> getAllProducts(Pageable pageable) {
        log.info("Entrou no gelAllProducts");
        Page<Order> orders = orderService.getAllPageable(pageable);
        return ResponseEntity.ok().body(orders);
    }
    
    @GetMapping(value = "{id}")
    public ResponseEntity<Optional<Order>> findById (@PathVariable String id) {
        return ResponseEntity.ok().body(orderService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Order> post (@RequestBody Order order,
                                       @RequestParam (value = "login") String login,
                                       @RequestParam(value = "description") String description
                                      ) {
        var resp = orderService.post(order, login, description);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @DeleteMapping(value = {"id"})
    public boolean delete (@PathVariable String id) {
       orderService.delete(id);
       return true;
    }


}
