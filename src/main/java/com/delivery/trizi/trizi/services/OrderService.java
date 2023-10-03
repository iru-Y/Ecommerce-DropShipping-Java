package com.delivery.trizi.trizi.services;

import com.delivery.trizi.trizi.domain.order.OrderModel;
import com.delivery.trizi.trizi.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class OrderService  {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;

    public Page<OrderModel> getAllPageable(Pageable pageable) {
       return orderRepository.findAll(pageable);
    }

    public List<OrderModel> getAll() {
        return orderRepository.findAll();
    }

    public Optional<OrderModel> getById(String id) {
        return orderRepository.findById(id);
    }


    public OrderModel post(OrderModel order, String mail, String description) {
        var u = userService.findByMail(mail);
        var d = productService.getByDescription(description);
        order.setUserModel(u);
        order.setProductModelList(List.of(d));
        return orderRepository.save(order);
    }

//    public OrderModel put(String tracker, String mail, String description) {
//
//    }
    public boolean delete(String id) {
        Optional<OrderModel> orderOptional = orderRepository.findById(id);

        if (orderOptional.isPresent()) {
            OrderModel orderModel = new OrderModel();
            orderRepository.delete(orderModel);
            return true;
        }
        return false;
    }
    public OrderModel findByTracker(String tracker) {
        return orderRepository.findByTracker(tracker);
    }
}
