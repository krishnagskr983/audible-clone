package com.audible.service;

import com.audible.dto.CardDTO;
import com.audible.dto.PaymentDTO;
import com.audible.entity.Card;
import com.audible.entity.Payment;
import com.audible.exception.AudibleException;
import com.audible.repository.CardRepository;
import com.audible.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("paymentService")
public class PaymentServiceImpl implements  PaymentService{

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public String makePayment(CardDTO cardDTO, Float amountToPay) throws AudibleException, NoSuchAlgorithmException {
        Optional<Card> cardOptional = cardRepository.findById(cardDTO.getCardId());
        if(cardOptional.isEmpty()){
            throw new AudibleException("PaymentService.NO_CARD_FOUND");
        }
        Payment payment = new Payment();
        payment.setAmount(amountToPay);
        payment.setUserId(cardDTO.getUserId());
        payment.setCardId(cardDTO.getCardId());
        payment.setPaymentTime(LocalDateTime.now());
        return paymentRepository.save(payment).getPaymentId();
    }

    @Override
    public void addCard(CardDTO cardDTO) throws AudibleException {
        Optional<Card> cardOptional = cardRepository.findById(cardDTO.getCardId());
        if(cardOptional.isPresent()){
            throw new AudibleException("PaymentService.CARD_ALREADY_EXISTS");
        }
    }

    @Override
    public void deleteCard(String cardId) throws AudibleException {
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if(cardOptional.isEmpty()){
            throw new AudibleException("PaymentService.NO_CARD_FOUND");
        }
        cardRepository.deleteById(cardId);
    }

    @Override
    public List<CardDTO> viewCards(String userId) throws AudibleException {
        List<Card> cards = cardRepository.findAllById(userId);
        if(cards.isEmpty()){
            throw new AudibleException("PaymentService.NO_CARD_FOUND");
        }
        return cards.stream().map(PaymentServiceImpl::convertToCardDTO).toList();
    }

    @Override
    public PaymentDTO getPaymentDetails(String paymentId) throws AudibleException {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        if(paymentOptional.isEmpty()){
            throw new AudibleException("PaymentService.NO_TRANSACTION_FOUND");
        }
        return PaymentServiceImpl.convertToPaymentDTO(paymentOptional.get());
    }

    @Override
    public CardDTO getCardDetails(String cardId) throws AudibleException {
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if(cardOptional.isEmpty()){
            throw new AudibleException("PaymentService.NO_CARD_FOUND");
        }
        return PaymentServiceImpl.convertToCardDTO(cardOptional.get());
    }

    public static CardDTO convertToCardDTO(Card card){
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardId(card.getCardId());
        cardDTO.setCardType(card.getCardType());
        cardDTO.setUserId(card.getUserId());
        cardDTO.setCvv(card.getCvv());
        cardDTO.setExpiryDate(card.getExpiryDate());
        cardDTO.setNameOnCard(card.getNameOnCard());
        return cardDTO;
    }

    public static Card convertToCard(CardDTO cardDTO){
      Card card = new Card();
      card.setCardId(cardDTO.getCardId());
      card.setCardType(cardDTO.getCardType());
      card.setUserId(cardDTO.getUserId());
      card.setCvv(cardDTO.getCvv());
      card.setExpiryDate(cardDTO.getExpiryDate());
      card.setNameOnCard(cardDTO.getNameOnCard());
      return card;
    }

    public static PaymentDTO convertToPaymentDTO(Payment payment){
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setUserId(payment.getUserId());
        paymentDTO.setCardId(payment.getCardId());
        paymentDTO.setPaymentId(payment.getPaymentId());
        paymentDTO.setPaymentTime(payment.getPaymentTime());
        return paymentDTO;
    }

}
