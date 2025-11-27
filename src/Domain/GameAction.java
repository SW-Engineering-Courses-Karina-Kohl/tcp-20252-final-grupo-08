package Domain;

public interface GameAction {
    /**
     * O texto que aparecerá no botão (Ex: "Atacar", "Invocar").
     * @return String com o rótulo do botão.
     */
    String getLabel();

    /**
     * A lógica que será executada ao clicar no botão.
     */
    void execute();
}