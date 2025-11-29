package Domain;

import java.util.ArrayList;
import java.util.List;

public class SpellCard implements Card {
    
    private final String name;
    private final String description;
    private final String imagePath;
    private final int cost;

    public SpellCard(String name, String description, String imagePath, int cost) {
        this.name = name;
        this.description = description; 
        this.imagePath = imagePath;
        this.cost = cost;
    }

    @Override
    public CardType getType() {
        return CardType.SPELL;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public List<GameAction> getAvailableActions() {
        return new ArrayList<>();
    }
}