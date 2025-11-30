package Domain;

public class MagicStatus {

    private final String name;
    private final int attackModifier;
    private final int defenseModifier;
    private int remainingTurns;

    public MagicStatus(String name, int attackModifier, int defenseModifier, int duration) {
        this.name = name;
        this.attackModifier = attackModifier;
        this.defenseModifier = defenseModifier;
        this.remainingTurns = duration;
    }

    public String getName() {
        return name;
    }

    public int getAttackModifier() {
        return attackModifier;
    }

    public int getDefenseModifier() {
        return defenseModifier;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public boolean tick() {
        remainingTurns--;
        return remainingTurns <= 0;
    }
}
