package com.github.btpka3.first.springdoc.stub;

import com.github.btpka3.first.springdoc.api.OrderApi;
import com.github.btpka3.first.springdoc.model.Order;
import com.github.btpka3.first.springdoc.model.OrderStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderApiStub implements OrderApi {

    @Override
    public List<Order> list(OrderStatus status) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Order getById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Order create(Order order) {
        throw new UnsupportedOperationException();
    }
}
