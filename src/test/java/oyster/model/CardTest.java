package oyster.model;

import com.pravin.oyster.model.Card;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CardTest {

    @Test
    public void canCreateInstanceWithBalance() {
        Card card = new Card("ID1");
        Card updatedCard = card.withBalance(10D);
        assertEquals("ID1", updatedCard.getCardId());
        assertEquals(10D, updatedCard.getBalance(), 0D);
    }

}
