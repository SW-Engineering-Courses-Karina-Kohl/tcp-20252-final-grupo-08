package Domain;

public class CardFactory {

    private static final String BASE_RESOURCE_PATH = "/resources/";

    private static final String HEAL_NAME = "Cura";
    private static final String HEAL_DESC = "Recupera 500 pontos de vida do jogador.";
    private static final String HEAL_IMG = "heal.png";
    private static final int HEAL_COST = 2;

    private static final String FIREBALL_NAME = "Bola de Fogo";
    private static final String FIREBALL_DESC = "Causa 600 pontos de dano direto ao oponente.";
    private static final String FIREBALL_IMG = "fireball.png";
    private static final int FIREBALL_COST = 4;

    private static final String SWORDSMAN_NAME = "Espadachim";
    private static final String SWORDSMAN_DESC = "Unidade Simples de ataque e defesa";
    private static final String SWORDSMAN_IMG = "swordsman.png";
    private static final int SWORDSMAN_COST = 3;
    private static final int SWORDSMAN_ATTACK = 1500;
    private static final int SWORDSMAN_DEFENSE = 1200;

    private CardFactory() {}

    public static Card createHealing() {
        return new SpellCard(
            HEAL_NAME,
            HEAL_DESC,
            buildPath(HEAL_IMG),
            HEAL_COST
        );
    }

    public static Card createFireball() {
        return new SpellCard(
            FIREBALL_NAME,
            FIREBALL_DESC,
            buildPath(FIREBALL_IMG),
            FIREBALL_COST
        );
    }

    public static Card createSwordsman() {
        return new MonsterCard(
            SWORDSMAN_NAME,
            SWORDSMAN_DESC,
            buildPath(SWORDSMAN_IMG),
            SWORDSMAN_COST,
            SWORDSMAN_ATTACK,
            SWORDSMAN_DEFENSE
        );
    }

    private static String buildPath(String imageName) {
        return BASE_RESOURCE_PATH + imageName;
    }
}