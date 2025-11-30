package Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeckFactory {

    private DeckFactory() {}

    public static List<Card> createRandomDeck(int size) {
        List<Card> deck = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < size; i++) {//randomizador de deck
            int pick = random.nextInt(6);
            
            switch (pick) {
                case 0 -> deck.add(CardFactory.createInfantry());
                case 1 -> deck.add(CardFactory.createFireball());
                case 2 -> deck.add(CardFactory.createHealing());
                case 3 -> deck.add(CardFactory.createShieldInfantry());
                case 4 -> deck.add(CardFactory.createMusketeer());
                case 5 -> deck.add(CardFactory.createUpgrade()); 
            }
        }
        return deck;
    }
}