package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.domain.order.OrderModel;
import com.delivery.trizi.trizi.domain.order.OrderStatusEnum;
import com.delivery.trizi.trizi.domain.product.ProductDTO;
import com.delivery.trizi.trizi.domain.product.ProductModel;
import com.delivery.trizi.trizi.services.OrderService;
import com.delivery.trizi.trizi.services.ProductService;
import com.delivery.trizi.trizi.services.UserService;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/orders")
@AllArgsConstructor
@Log4j2
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;

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
    public ResponseEntity<OrderModel> post(
            @RequestParam(value = "m") String mail
    ) {
        var resp = orderService.post(mail);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }


    @PatchMapping
    public ResponseEntity<OrderModel> patch (@RequestParam(value = "t") String tracker,
                                             @RequestParam(value = "m") String mail,
                                             @RequestParam(value = "p") String product,
                                             @RequestParam(value = "s") String status,
                                             @RequestParam(value = "isAtt", defaultValue = "true") boolean isAtt) {

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderService.patch(tracker, mail, product, String.valueOf(OrderStatusEnum.valueOf(status)),isAtt));
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
