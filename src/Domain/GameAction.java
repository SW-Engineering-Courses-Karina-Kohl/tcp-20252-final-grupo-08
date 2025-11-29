package Domain;

// Define o que um botão de ação faz.
public abstract class GameAction { 

    public GameAction() { } 
    
    public abstract String getLabel(); 
    public abstract void execute();    
}