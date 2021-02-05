package com.pravin.oyster.repository;

import com.pravin.oyster.exception.CardNotFoundException;
import com.pravin.oyster.model.Card;

public interface CardRepository {

    Card createCard(Card card);

    Card updateCard(Card card) throws CardNotFoundException;

    Card getCard(String cardId) throws CardNotFoundException;
}
