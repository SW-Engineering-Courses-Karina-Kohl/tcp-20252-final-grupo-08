package Domain;

import java.util.*;

public class MonsterCard extends AbstractCard {
    
    private final int baseAttack;
    private final int baseDefense;
    private int currentDefense;


    private final List<MagicStatus> statsUses = new ArrayList<>();
    private final Map<String, Integer> appliedEffects = new HashMap<>();


    public MonsterCard(String name, String description, String imagePath, int cost, int attack, int defense) {
        super(name, description, imagePath, cost);
        this.baseAttack = attack;
        this.baseDefense = defense;
        this.currentDefense = defense;

    }

    @Override
    public CardType getType() { return CardType.MONSTER; }

    public int getBaseAttack() { return baseAttack; }

    public int getBaseDefense() { return baseDefense; }

    public int getAttack() { return baseAttack + statsUses.stream().mapToInt(MagicStatus::getAttackModifier).sum(); }

    public int getDefense() { return currentDefense + statsUses.stream().mapToInt(MagicStatus::getDefenseModifier).sum(); }

    public Map<String, Integer> getAppliedEffects() { return Collections.unmodifiableMap(appliedEffects); }

    public List<MagicStatus> getStatsUses() { return statsUses; }

    public void heal(int amount) { currentDefense += amount; }

    public void applyStatus(MagicStatus status) {
        statsUses.add(status);

        appliedEffects.merge(status.getName(), 1, Integer::sum);
    }

    public void receiveDamage(int amount) {
        currentDefense -= amount;
        if (currentDefense < 0) currentDefense = 0;
    }

    public List<String> getActiveEffectNames() {
        List<String> list = new ArrayList<>();
        for (MagicStatus status : statsUses) {
            list.add(status.getName());
        }
        return list;
    }

    public Map<String, Integer> getEffectCounts() {
        return Collections.unmodifiableMap(appliedEffects);
    }


    /** Remove efeitos expirados */
    public void updateStatsUses() {
        for (MagicStatus status : statsUses) {
            if (status.getName().equals("Burn")) {
                receiveDamage(100); // dano por turno
            }
        }

        statsUses.removeIf(MagicStatus::tick);

        appliedEffects.clear();
        for (MagicStatus status : statsUses) {
            appliedEffects.merge(status.getName(), 1, Integer::sum);
        }

        if (currentDefense < 0) currentDefense = 0;
    }
}