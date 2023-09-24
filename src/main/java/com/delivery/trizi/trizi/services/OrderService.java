package com.delivery.trizi.trizi.services;

import com.delivery.trizi.trizi.domain.order.Order;
import com.delivery.trizi.trizi.domain.product.ProductModel;
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
    private final ProductService productService;
    private final UserService userService;
    public Page<Order> getAllPageable(Pageable pageable) {
       return orderRepository.findAll(pageable);
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> getById(String id) {
        return orderRepository.findById(id);
    }

    public Order post(Order order, String login, String description) {
        var user = userService.findByLogin(login);
        List<ProductModel> descriptions = (productService.getByDescription(description));
        order.setUserModel(user);
        order.setProductModelList(descriptions);
        return orderRepository.save(order);
    }


    public Order put(String id, Order order) {
        return null;
    }
    public boolean delete(String id) {
        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isPresent()) {
            Order order = new Order();
            orderRepository.delete(order);
            return true;
        }
        return false;
    }
    public Order findByTracker(String tracker) {
        return orderRepository.findByTracker(tracker);
    }
}
