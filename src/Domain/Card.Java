package Domain;

import java.util.List;

public interface Card {
    /**
     * @return Nome da carta ,descrição, informações etc
     */
    String getTitle();

    
      @return 
     
    String getDescription();

    
      @return 
     
    String getImagePath();

    /**
     * RF-6: Retorna a lista de ações que esta carta permite fazer no momento.
     * Isso permite que a interface crie os botões dinamicamente.
     */
    List<GameAction> getAvailableActions();
}