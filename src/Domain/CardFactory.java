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

    private static final String UPGRADE_NAME = "Upgrade";
    private static final String UPGRADE_DESC = "Aumenta os pontos de defesa e ataque de uma unidade em 500.";
    private static final String UPGRADE_IMG = "upgrade.png";
    private static final int UPGRADE_COST = 3;

    private static final String INFANTRY_NAME = "Infantaria";
    private static final String INFANTRY_DESC = "Unidade Simples de ataque e defesa";
    private static final String INFANTRY_IMG = "infantry.png";
    private static final int INFANTRY_COST = 3;
    private static final int INFANTRY_ATTACK = 1500;
    private static final int INFANTRY_DEFENSE = 1200;

    private static final String SHIELD_NAME = "Infantaria Pesada";
    private static final String SHIELD_DESC = "Unidade com foco total em defesa e proteção, possui pouco dano.";
    private static final String SHIELD_IMG = "shield_infantry.png";
    private static final int SHIELD_COST = 4;
    private static final int SHIELD_ATTACK = 800;
    private static final int SHIELD_DEFENSE = 2500;

    private static final String MUSKETEER_NAME = "Mosqueteiro";
    private static final String MUSKETEER_DESC = "Unidade de ataque com alto poder ofensivo porém baixa defesa.";
    private static final String MUSKETEER_IMG = "musketeer.png";
    private static final int MUSKETEER_COST = 4;
    private static final int MUSKETEER_ATTACK = 2200;
    private static final int MUSKETEER_DEFENSE = 800;

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

    public static Card createUpgrade() {
        return new SpellCard(
            UPGRADE_NAME,
            UPGRADE_DESC,
            buildPath(UPGRADE_IMG),
            UPGRADE_COST
        );
    }

    public static Card createInfantry() {
        return new MonsterCard(
            INFANTRY_NAME,
            INFANTRY_DESC,
            buildPath(INFANTRY_IMG),
            INFANTRY_COST,
            INFANTRY_ATTACK,
            INFANTRY_DEFENSE
        );
    }

    public static Card createShieldInfantry() {
        return new MonsterCard(
            SHIELD_NAME,
            SHIELD_DESC,
            buildPath(SHIELD_IMG),
            SHIELD_COST,
            SHIELD_ATTACK,
            SHIELD_DEFENSE
        );
    }

    public static Card createMusketeer() {
        return new MonsterCard(
            MUSKETEER_NAME,
            MUSKETEER_DESC,
            buildPath(MUSKETEER_IMG),
            MUSKETEER_COST,
            MUSKETEER_ATTACK,
            MUSKETEER_DEFENSE
        );
    }

    private static String buildPath(String imageName) {
        return BASE_RESOURCE_PATH + imageName;
    }
}