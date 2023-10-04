package com.delivery.trizi.trizi.services;

import com.delivery.trizi.trizi.domain.order.OrderModel;
import com.delivery.trizi.trizi.domain.order.OrderStatusEnum;
import com.delivery.trizi.trizi.domain.product.ProductModel;
import com.delivery.trizi.trizi.repositories.OrderRepository;
import com.delivery.trizi.trizi.services.exception.DataBaseException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

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


    public OrderModel post(String mail, String description, Long quantity) {
        OrderModel order = new OrderModel();
        List<ProductModel> products = new ArrayList<>();
        var user = userService.findByMail(mail);
        var product = productService.getByDescription(description);
        if (product.getQuantity() >= quantity) {
                for (int i = 0; i < quantity; i++) {
                    products.add(product);
                }

                order.setProductModelList(products);
                order.setStatus(OrderStatusEnum.SHIPPED);
                productService.updateProductQuantity(product, -quantity);
            } else {
                throw new DataBaseException("Não existe produtos suficientes no estoque");
            }
                 order.setUserModel(user);
            return orderRepository.save(order);
    }

    public OrderModel patch(String tracker, String description, OrderStatusEnum status) {
        var o = orderRepository.findByTracker(tracker);
        var p = productService.getByDescription(description);
        o.setProductModelList(List.of(p));
        o.setStatus(status);
        return orderRepository.save(o);
    }
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
