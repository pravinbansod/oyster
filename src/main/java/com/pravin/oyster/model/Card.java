package com.pravin.oyster.model;

public class Card {
    private final String cardId;
    private double balance;

    public Card(String cardId) {
        this(cardId, 0D);
    }

    public Card(String cardId, double balance) {
        this.cardId = cardId;
        this.balance = balance;
    }

    public Card withBalance(double amount) {
        return new Card(this.cardId, amount);
    }

    public String getCardId() {
        return cardId;
    }

    public double getBalance() {
        return balance;
    }
}
