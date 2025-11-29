package Domain;

import java.util.List;

public interface Card {
    String getName();
    String getDescription();
    String getImagePath();
    int getCost();
    CardType getType();
    
    List<GameAction> getAvailableActions(); 
}