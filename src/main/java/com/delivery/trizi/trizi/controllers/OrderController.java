package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.domain.order.OrderModel;
import com.delivery.trizi.trizi.domain.order.OrderStatusEnum;
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
    public ResponseEntity<Page<OrderModel>> getAllProducts(Pageable pageable) {
        log.info("Entrou no gelAllProducts");
        Page<OrderModel> orders = orderService.getAllPageable(pageable);
        return ResponseEntity.ok().body(orders);
    }
    
    @GetMapping(value = "{id}")
    public ResponseEntity<Optional<OrderModel>> findById (@PathVariable String id) {
        return ResponseEntity.ok().body(orderService.getById(id));
    }

    @PostMapping
    public ResponseEntity<OrderModel> post (@RequestBody OrderModel order,
                                            @RequestParam (value = "mail") String mail,
                                            @RequestParam (value = "description") String description
                                      ) {
        var resp = orderService.post(order, mail, description);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PatchMapping
    public ResponseEntity<OrderModel> patch (@RequestParam(value = "tracker") String tracker,
                                             @RequestParam(value = "description") String description,
                                             @RequestParam(value = "status") OrderStatusEnum status) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderService.patch(tracker, description, status));
    }

    @DeleteMapping(value = {"id"})
    public boolean delete (@PathVariable String id) {
       orderService.delete(id);
       return true;
    }

    @GetMapping("/tracker")
    public ResponseEntity<OrderModel> getByTracker(@RequestParam(value = "tracker") String tracker) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.findByTracker(tracker));
    }
}
