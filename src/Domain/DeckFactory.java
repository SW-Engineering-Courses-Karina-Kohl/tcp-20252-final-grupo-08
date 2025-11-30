package Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Gera um deck aleatorio de acordo com as que tem na CardFactory
public class DeckFactory {

    private DeckFactory() {}

    public static List<Card> createRandomDeck(int size) {
        List<Card> deck = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            int pick = random.nextInt(3);
            switch (pick) {
                case 0 -> deck.add(CardFactory.createSwordsman());
                case 1 -> deck.add(CardFactory.createFireball());
                case 2 -> deck.add(CardFactory.createHealing());
            }
        }
        return deck;
    }
}