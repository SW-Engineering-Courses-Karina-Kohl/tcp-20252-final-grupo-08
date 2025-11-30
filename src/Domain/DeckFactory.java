package Domain;

import java.util.ArrayList;
import java.util.Collections; 
import java.util.List;
import java.util.Random;

public class DeckFactory {

    private DeckFactory() {}

    public static List<Card> createStarterDeck() {
        List<Card> deck = new ArrayList<>();
        Random random = new Random();

        //Adicionar 2 Feitiços Aleatórios
        for (int i = 0; i < 2; i++) {
            int pick = random.nextInt(3); 
            switch (pick) {
                case 0 -> deck.add(CardFactory.createFireball());
                case 1 -> deck.add(CardFactory.createHealing());
                case 2 -> deck.add(CardFactory.createUpgrade());
            }
        }

        //Adicionar 3 Tropas Aleatórias
        // Tropas disponíveis: Infantry(0), ShieldInfantry(3), Musketeer(4)
        for (int i = 0; i < 3; i++) {
            int pick = random.nextInt(3); 
            switch (pick) {
                case 0 -> deck.add(CardFactory.createInfantry());
                case 1 -> deck.add(CardFactory.createShieldInfantry());
                case 2 -> deck.add(CardFactory.createMusketeer());
            }
        }
        
        Collections.shuffle(deck);

        return deck;
    }

    public static List<Card> createRandomDeck(int size) {
        return createStarterDeck();
    }
}