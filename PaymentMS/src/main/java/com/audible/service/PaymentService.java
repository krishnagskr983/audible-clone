package com.audible.service;

import com.audible.dto.CardDTO;
import com.audible.dto.PaymentDTO;
import com.audible.exception.AudibleException;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface PaymentService {

    public String makePayment(CardDTO cardDTO, Float amountToPay) throws AudibleException, NoSuchAlgorithmException;
    public void addCard(CardDTO cardDTO) throws AudibleException;
    public void deleteCard(String cardId) throws AudibleException;
    public List<CardDTO> viewCards(String userId) throws AudibleException;
    public PaymentDTO getPaymentDetails(String paymentId) throws AudibleException;
    public CardDTO getCardDetails(String cardId) throws AudibleException;
}
