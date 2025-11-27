package Domain;

// Define o que um botão de ação faz.
public interface GameAction {
    String getLabel(); // Texto do botão
    void execute();    // A lógica (temporariamente, um System.out.println)
}