package oyster.service.impl;

import com.pravin.oyster.exception.InvalidAmountException;
import com.pravin.oyster.model.Card;
import com.pravin.oyster.repository.CardRepository;
import com.pravin.oyster.repository.impl.InMemoryCardRepositoryImpl;
import com.pravin.oyster.service.CardService;
import com.pravin.oyster.service.impl.CardServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CardServiceImplTest {

    private CardRepository cardRepository;
    private CardServiceImpl underTest;

    @Before
    public void setup() {
        cardRepository = new InMemoryCardRepositoryImpl();
        underTest = new CardServiceImpl(cardRepository);
    }
    @Test
    public void canAddCard() {
        underTest.addCard("ID1");
        Card card = cardRepository.getCard("ID1");
        assertNotNull(card);
        assertEquals("ID1", card.getCardId());
    }

    @Test
    public void canRetrieveCard() {
        cardRepository.createCard(new Card("ID1"));
        Card card = underTest.retrieveCard("ID1");
        assertNotNull(card);
        assertEquals("ID1", card.getCardId());
    }

    @Test
    public void canTopup() {
        cardRepository.createCard(new Card("ID1", 10D));
        underTest.topup("ID1", 10.50D);
        Card card = cardRepository.getCard("ID1");
        assertNotNull(card);
        assertEquals("ID1", card.getCardId());
        assertEquals(20.50D, card.getBalance(), 0D);
    }

    @Test
    public void canCharge() {
        cardRepository.createCard(new Card("ID1", 10D));
        underTest.charge("ID1", 5.50D);
        Card card = cardRepository.getCard("ID1");
        assertNotNull(card);
        assertEquals("ID1", card.getCardId());
        assertEquals(4.50D, card.getBalance(), 0D);
    }

    @Test(expected = InvalidAmountException.class)
    public void topupThrowsInvalidAmountExceptionWhenNegativeAmount() {
        cardRepository.createCard(new Card("ID1"));
        underTest.topup("ID1", -10.50D);
    }
    @Test(expected = InvalidAmountException.class)
    public void chargeThrowsInvalidAmountExceptionWhenNegativeAmount() {
        cardRepository.createCard(new Card("ID1"));
        underTest.charge("ID1", -10.50D);
    }
}
