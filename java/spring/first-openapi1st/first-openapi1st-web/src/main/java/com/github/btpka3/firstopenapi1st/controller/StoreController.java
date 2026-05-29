package com.github.btpka3.firstopenapi1st.controller;

import com.github.btpka3.firstopenapi1st.api.StoreApi;
import com.github.btpka3.firstopenapi1st.model.Order;
import com.github.btpka3.firstopenapi1st.model.OrderListResponse;
import com.github.btpka3.firstopenapi1st.model.OrderStatus;
import com.github.btpka3.firstopenapi1st.model.PlaceOrderRequest;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreController implements StoreApi {

    @Override
    public Order getOrderById(Long orderId) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public OrderListResponse listOrders(Integer pageNum, Integer pageSize, OrderStatus status) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Order placeOrder(PlaceOrderRequest placeOrderRequest) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
