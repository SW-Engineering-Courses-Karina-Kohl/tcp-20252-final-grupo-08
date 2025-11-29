package Domain;

public class MonsterCard extends AbstractCard {
    
    private final int attack;
    private final int defense;

    public MonsterCard(String name, String description, String imagePath, int cost, int attack, int defense) {
        super(name, description, imagePath, cost);
        this.attack = attack;
        this.defense = defense;
    }

    @Override
    public CardType getType() {
        return CardType.MONSTER;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }
}