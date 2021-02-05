package com.pravin.oyster.repository.impl;

import com.pravin.oyster.exception.CardNotFoundException;
import com.pravin.oyster.model.Card;
import com.pravin.oyster.repository.CardRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCardRepositoryImpl implements CardRepository {

    private Map<String, Card> cardMap = new HashMap<>();

    @Override
    public Card createCard(Card card) {
        cardMap.put(card.getCardId(), card);
        return card;
    }

    @Override
    public Card updateCard(Card card) {
        return Optional.ofNullable(cardMap.get(card.getCardId())).map(c -> cardMap.put(c.getCardId(), card)).orElseThrow(() -> new CardNotFoundException("Card not found for id: " + card.getCardId()));
    }

    @Override
    public Card getCard(String cardId) {
        return Optional.ofNullable(cardMap.get(cardId)).orElseThrow(() -> new CardNotFoundException("Card not found for id: " + cardId));
    }

}
