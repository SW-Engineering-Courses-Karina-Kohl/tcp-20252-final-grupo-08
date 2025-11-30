package Domain;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int health;
    private int money; 
    
    private List<Card> deck;
    private List<Card> hand;
    private Board board; 

    public Player(String name, int health, List<Card> deck) {
        this.name = name;
        this.health = health;
        this.deck = deck;
        this.hand = new ArrayList<>();
        this.board = new Board();
        this.money = 10; 
    }

    public void drawCard() {
        if (!deck.isEmpty()) {
            hand.add(deck.remove(0));
        }
    }

    public String getName() {  return name; }

    public int getHealth() { return health; }

    public int getMoney() { return money; }

    public List<Card> getDeck() { return deck; }

    public List<Card> getHand() { return hand; }
    
    public Board getBoard() { return board; }

    private List<AppliedEffect> usedEffects = new ArrayList<>();

    public List<AppliedEffect> getUsedEffects() { return usedEffects; }

    public void registerEffectUse(String effectName) {
        for (AppliedEffect eff : usedEffects) {
            if (eff.getEffectName().equals(effectName)) {
                eff.increment();
                return;
            }
        }
        usedEffects.add(new AppliedEffect(effectName));
    }

}