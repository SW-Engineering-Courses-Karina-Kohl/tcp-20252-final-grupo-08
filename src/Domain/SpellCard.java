package Domain;

public class SpellCard extends AbstractCard {

    public SpellCard(String name, String description, String imagePath, int cost) {
        super(name, description, imagePath, cost);
    }

    @Override
    public CardType getType() {
        return CardType.SPELL;
    }
}