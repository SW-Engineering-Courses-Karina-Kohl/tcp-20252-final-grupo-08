package Domain;

public class CardFactory {

    private CardFactory() {}

    public static Card createHealing() {
        return new SpellCard(
            "Cura",
            "Recupera 500 pontos de vida do jogador.",
            "/resources/heal.png",
            2 
        );
    }

    public static Card createFireball() {
        return new SpellCard(
            "Bola de Fogo",
            "Causa 600 pontos de dano direto ao oponente.",
            "/resources/fireball.png",
            4 
        );
    }

    public static Card createSwordsman() {
        return new MonsterCard(
            "Espadachim",
            "Unidade Simples de ataque e defesa", 
            "/resources/swordsman.png",
            3,    
            1500, 
            1200  
        );
    }
}