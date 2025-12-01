package Domain;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int health;
    private int money;
    private boolean defeated;

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
        this.defeated = false;
    }

    public void drawCard() {
        if (!deck.isEmpty()) {
            hand.add(deck.remove(0));
        }
    }

    public boolean isDefeated() {
        return defeated;
    }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int amount) {
        this.money += amount;
    }

    public void spendMoney(int amount) {
        this.money -= amount;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public List<Card> getHand() {
        return hand;
    }

    public Board getBoard() {
        return board;
    }

    private List<AppliedEffect> usedEffects = new ArrayList<>();

    public List<AppliedEffect> getUsedEffects() {
        return usedEffects;
    }

    public void registerEffectUse(String effectName) {
        for (AppliedEffect eff : usedEffects) {
            if (eff.getEffectName().equals(effectName)) {
                eff.increment();
                return;
            }
        }
        usedEffects.add(new AppliedEffect(effectName));
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0)
            health = 0;
    }
}