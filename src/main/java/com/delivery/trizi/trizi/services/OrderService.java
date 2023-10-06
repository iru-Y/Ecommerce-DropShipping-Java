package com.delivery.trizi.trizi.services;

import com.delivery.trizi.trizi.domain.order.OrderModel;
import com.delivery.trizi.trizi.domain.order.OrderStatusEnum;
import com.delivery.trizi.trizi.domain.product.ProductModel;
import com.delivery.trizi.trizi.repositories.OrderRepository;
import com.delivery.trizi.trizi.services.exception.DataBaseException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
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


    public OrderModel post(String mail) {
       OrderModel order = new OrderModel();
       var u = userService.findByMail(mail);
       order.setUserModel(u);
       return orderRepository.save(order);
    }

    public OrderModel patch(String tracker, String mail, String product, String status, boolean isAtt) {
        var u = userService.findByMail(mail);
        var o = orderRepository.findByTracker(tracker);
        var jsonProduct = new Gson().fromJson(product, ProductModel.class);
        var p = productService.getByDescription(jsonProduct.getDescription());
        var quantity = jsonProduct.getQuantity();

        if (!isAtt) {
            o.getProductModelList().clear();
        }

        for (int i = 0; i < quantity; i++) {

            o.getProductModelList().add(p);
        }

        var stats = OrderStatusEnum.valueOf(status);
        o.setTracker(tracker);
        o.setUserModel(u);
        o.setStatus(OrderStatusEnum.valueOf(String.valueOf(stats)));
        productService.updateProductQuantity(p, -quantity);
        orderRepository.save(o);

        return o;
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
