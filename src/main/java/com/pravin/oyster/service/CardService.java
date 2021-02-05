package com.pravin.oyster.service;

import com.pravin.oyster.model.Card;

public interface CardService {
    Card addCard(String cardId);

    Card retrieveCard(String cardId);

    void topup(String cardId, double amount);

    void charge(String cardId, double amount);
}
