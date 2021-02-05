package com.pravin.oyster.service.impl;

import com.pravin.oyster.exception.InvalidAmountException;
import com.pravin.oyster.model.Card;
import com.pravin.oyster.repository.CardRepository;
import com.pravin.oyster.repository.impl.InMemoryCardRepositoryImpl;
import com.pravin.oyster.service.CardService;

public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Card addCard(String cardId) {
        Card card = new Card(cardId);
        return cardRepository.createCard(card);
    }

    @Override
    public Card retrieveCard(String cardId) {
        return cardRepository.getCard(cardId);
    }

    @Override
    public void topup(String cardId, double amount) {
        validateAmount(amount);
        Card card = retrieveCard(cardId);
        cardRepository.updateCard(card.withBalance(card.getBalance() + amount));
    }

    @Override
    public void charge(String cardId, double amount) {
        validateAmount(amount);
        Card card = retrieveCard(cardId);
        cardRepository.updateCard(card.withBalance(card.getBalance() - amount));
    }

    private void validateAmount(double amount) {
        if (amount < 0) {
            throw new InvalidAmountException("Invalid negative amount");
        }
    }
}
