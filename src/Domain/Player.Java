package Domain;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int health;
    
    private List<Card> deck;
    private List<Card> hand;
    private Board board; 

    public Player(String name, int health, List<Card> deck) {
        this.name = name;
        this.health = health;
        this.deck = deck;
        this.hand = new ArrayList<>();
        this.board = new Board(); // Garante que o Board é criado
    }

    public void drawCard() {
        if (!deck.isEmpty()) {
            hand.add(deck.remove(0));
        }
    }

    public List<Card> getHand() {
        return hand;
    }
    
    public List<Card> getDeck() {
        return deck;
    }
    
    public Board getBoard() {
        return board;
    }
}