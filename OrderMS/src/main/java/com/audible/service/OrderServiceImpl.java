package com.audible.service;

import com.audible.dto.*;
import com.audible.entity.Order;
import com.audible.entity.OrderedBook;
import com.audible.exception.AudibleException;
import com.audible.repository.OrderRepository;
import com.audible.repository.OrderedBookRepository;
import com.audible.utility.HashingUtility;
import lombok.Builder;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service(value = "orderService")
@Transactional
public class OrderServiceImpl implements OrderService{

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderedBookRepository orderedBookRepository;

    @Override
    public List<OrderDTO> viewOrders(String userId) throws AudibleException {
        List<Order> orderList = orderRepository.findOrderByUserId(userId);
        if(orderList.isEmpty()){
            throw new AudibleException("OrderService.NO_ORDERS_FOUND");
        }
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderList.forEach((order) -> {
            OrderDTO orderDTO = OrderServiceImpl.convertToOrderDTO(order);
            String url1 = "http://localhost:6100/audible/user-api/user/";
            UserDTO userDTO = restTemplate().getForObject(url1+order.getUserId(), UserDTO.class);
            orderDTO.setUserDTO(userDTO);
            String url2 = "http://localhost:6600/audible/payment-api/payment/card";
            CardDTO cardDTO = restTemplate().getForObject(url2+order.getCardId(), CardDTO.class);
            orderDTO.setCardDTO(cardDTO);
            String url3 = "http://localhost:6200/audible/book-api/book";
            BookDTO bookDTO = restTemplate().getForObject(url3+order.getOrderedBook().getBookId(), BookDTO.class);
            orderDTO.getOrderedBookDTO().setBookDTO(bookDTO);
            orderDTOList.add(orderDTO);
        });
        return orderDTOList;
    }

    @Override
    public String placeOrder(OrderDTO orderDTO) throws AudibleException, NoSuchAlgorithmException {
        UserDTO userDTO = restTemplate().getForObject("http://localhost:6100/audible/user-api/user/"+orderDTO.getUserDTO().getUserId(), UserDTO.class);
        CardDTO cardDTO = restTemplate().getForObject("http://localhost:6600/audible/payment-api/payment/card/"+orderDTO.getCardDTO().getCardId(), CardDTO.class);
        if(!cardDTO.getCvv().equals(HashingUtility.getHashValue(orderDTO.getCardDTO().getCvv())) || !cardDTO.getUserId().equals(userDTO.getUserId())){
            throw new AudibleException("OrderService.PAYMENT_FAILED");
        }
        Order order = OrderServiceImpl.convertToOrder(orderDTO);
        order.setOrderValueAfterDiscount(orderDTO.getOrderValueAfterDiscount()*(100-orderDTO.getDiscountPercent())/100);
        orderedBookRepository.save(order.getOrderedBook());
        LibraryDTO libraryDTO = new LibraryDTO();
        libraryDTO.setUserId(userDTO.getUserId());
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(order.getOrderedBook().getBookId());
        restTemplate().postForObject("http://localhost:6300/audible/library-api/library/add-book"+bookDTO.getBookId()+"/user/"+userDTO.getUserId(), null, String.class);
        return orderRepository.save(order).getOrderId();
    }

    public static OrderDTO convertToOrderDTO(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(orderDTO.getOrderId());
        orderDTO.setDiscountPercent(order.getDiscountPercent());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setOrderValueAfterDiscount(order.getOrderValueAfterDiscount());
        orderDTO.setOrderValueBeforeDiscount(orderDTO.getOrderValueBeforeDiscount());
        orderDTO.setOrderedBookDTO(convertToOrderedBookDTO(order.getOrderedBook()));
        return orderDTO;
    }

    public static Order convertToOrder(OrderDTO orderDTO){
        Order order = new Order();
        order.setCardId(orderDTO.getCardDTO().getCardId());
        order.setUserId(orderDTO.getUserDTO().getUserId());
        order.setDiscountPercent(orderDTO.getDiscountPercent());
        order.setOrderDate(LocalDateTime.now());
        order.setOrderId(orderDTO.getOrderId());
        order.setOrderStatus("Completed");
        order.setOrderValueBeforeDiscount(orderDTO.getOrderValueBeforeDiscount());
        order.setOrderValueAfterDiscount(orderDTO.getOrderValueAfterDiscount());
        order.setOrderedBook(convertToOrderedBook(orderDTO.getOrderedBookDTO()));
        return order;
    }

    public static OrderedBook convertToOrderedBook(OrderedBookDTO orderedBookDTO){
        OrderedBook orderedBook = new OrderedBook();
        orderedBook.setOrderedBookId(orderedBookDTO.getOrderedBookId());
        orderedBook.setOrderedPrice(orderedBookDTO.getOrderPrice());
        orderedBook.setBookId(orderedBookDTO.getOrderedBookId());
        return orderedBook;
    }

    public static OrderedBookDTO convertToOrderedBookDTO(OrderedBook orderedBook){
        OrderedBookDTO orderedBookDTO = new OrderedBookDTO();
        orderedBookDTO.setOrderedBookId(orderedBook.getOrderedBookId());
        orderedBookDTO.setOrderPrice(orderedBook.getOrderedPrice());
        return orderedBookDTO;
    }

}
