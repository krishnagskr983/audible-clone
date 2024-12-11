package com.audible.api;

import com.audible.dto.OrderDTO;
import com.audible.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@Validated
@RequestMapping(value="/order-api")
public class OrderAPI {

    @Autowired
    private OrderService orderService;

    @Autowired
    private Environment environment;

    @GetMapping(value = "/order/view-orders/user/{userId}")
    public ResponseEntity<List<OrderDTO>> viewOrders(@PathVariable String userId) throws  ArithmeticException{
        return new ResponseEntity<List<OrderDTO>>(orderService.viewOrders(userId), HttpStatus.OK);
    }


    @PostMapping(value = "/order/place-order")
    public ResponseEntity<String> placeOrder(@RequestBody OrderDTO orderDTO) throws ArithmeticException,
            NoSuchAlgorithmException{
        String msg = orderService.placeOrder(orderDTO);
        return new ResponseEntity<>(environment.getProperty("OrderAPI.ORDER_PLACED_SUCCESS")+msg, HttpStatus.OK);
    }
}
