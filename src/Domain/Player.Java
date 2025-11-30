package Domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    
    private String name;
    private int healthPoints;
    private int manaPoints;
    
    private final List<Card> hand;
    private final List<Card> deck;
    private final Board board; 

    public Player(String name, int initialHealth, List<Card> deck) {
        this.name = name;
        this.healthPoints = initialHealth;
        this.deck = new ArrayList<>(deck); 
        this.hand = new ArrayList<>();
        this.board = new Board(); 
        
        Collections.shuffle(this.deck);
    }

    public void drawCard() {
        if (!deck.isEmpty()) {
            Card card = deck.removeFirst(); 
            hand.add(card);
        }
    }

    public void playCard(Card card) {
        if (!hand.contains(card)) {
            throw new IllegalArgumentException("O jogador não possui esta carta na mão.");
        }

        if (card.getCost() > manaPoints) {
            throw new IllegalStateException("Mana insuficiente.");
        }

        payMana(card.getCost());
        hand.remove(card);

        if (card instanceof MonsterCard) {
            board.placeMonster((MonsterCard) card);
        }
    }

    private void payMana(int cost) {
        this.manaPoints -= cost;
    }

    public List<Card> getHand() {
        return Collections.unmodifiableList(hand);
    }

    public Board getBoard() {
        return board;
    }
    
    public int getHealth() { return healthPoints; }
    public int getMana() { return manaPoints; }
}