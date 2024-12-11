package com.audible.api;

import com.audible.dto.CardDTO;
import com.audible.dto.PaymentDTO;
import com.audible.exception.AudibleException;
import com.audible.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(value = "/payment-api")
public class PaymentAPI {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private Environment environment;

    @GetMapping(value = "/payment/details/{paymentId}" )
    public ResponseEntity<PaymentDTO> getPaymentDetails(@PathVariable String paymentId) throws AudibleException{
        return new ResponseEntity<>(paymentService.getPaymentDetails(paymentId), HttpStatus.OK);
    }

    @GetMapping(value = "/payment/card/{cardId}" )
    public ResponseEntity<CardDTO> getCardDetails(@PathVariable String cardId) throws AudibleException{
        return new ResponseEntity<>(paymentService.getCardDetails(cardId), HttpStatus.OK);
    }

    @GetMapping(value = "/payment/view-cards/{userId}" )
    public ResponseEntity<List<CardDTO>> viewCards(@PathVariable String userId) throws AudibleException{
        return new ResponseEntity<>(paymentService.viewCards(userId), HttpStatus.OK);
    }

    @PostMapping(value = "/payment/amount/{amountToPay}")
    public ResponseEntity<String> makePayment(@RequestBody CardDTO cardDTO, @PathVariable Float amountToPay) throws Exception{
        String paymentId = paymentService.makePayment(cardDTO, amountToPay);
        String successMsg = environment.getProperty("PaymentAPI.PAYMENT_SUCCESS")+paymentId;
        return new ResponseEntity<>(successMsg, HttpStatus.CREATED);
    }

    @PostMapping(value = "/payment/add-card")
    public ResponseEntity<String> addCardForPayment(@RequestBody CardDTO cardDTO) throws AudibleException{
        paymentService.addCard(cardDTO);
        return new ResponseEntity<>(environment.getProperty("PaymentAPI.ADD_CARD_SUCCESS"), HttpStatus.OK);
    }

    @DeleteMapping(value = "/payment/delete-card/{cardId}")
    public ResponseEntity<String> deleteCard(@PathVariable String cardId) throws AudibleException{
        paymentService.deleteCard(cardId);
        String successMsg = environment.getProperty("PaymentAPI.DELETED_CARD_SUCCESS")+cardId;
        return new ResponseEntity<>(successMsg, HttpStatus.OK);
    }

}
