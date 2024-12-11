package com.audible.service;

import com.audible.dto.OrderDTO;
import com.audible.exception.AudibleException;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface OrderService {

    List<OrderDTO> viewOrders(String userId) throws AudibleException;
    String placeOrder(OrderDTO orderDTO) throws AudibleException, NoSuchAlgorithmException;
}
