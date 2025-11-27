package Domain;

import java.util.List;



public interface Card {
    
    String getName();
    String getDescription();
    String getImagePath();
    
    List<GameAction> getAvailableActions(); 
}