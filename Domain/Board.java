package Domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Board {

    private static final int MAX_MONSTER_SLOTS = 5;
    private final List<MonsterCard> monsters;

    public Board() {
        this.monsters = new ArrayList<>(MAX_MONSTER_SLOTS);
        // Inicializa com null para representar slots vazios
        for (int i = 0; i < MAX_MONSTER_SLOTS; i++) {
            monsters.add(null);
        }
    }

    public boolean placeMonsterAt(int index, MonsterCard card) {
        if (index >= 0 && index < MAX_MONSTER_SLOTS) {
            if (monsters.get(index) == null) {
                monsters.set(index, card);
                return true;
            }
        }
        return false;
    }

    public void clear() {
        for (int i = 0; i < MAX_MONSTER_SLOTS; i++) {
            monsters.set(i, null);
        }
    }

    public List<MonsterCard> getMonsters() {
        return Collections.unmodifiableList(monsters);
    }

    public Optional<MonsterCard> getMonsterAt(int index) {
        if (index >= 0 && index < monsters.size()) {
            return Optional.ofNullable(monsters.get(index));
        }
        return Optional.empty();
    }

    public void nextTurn() {
        for (MonsterCard monster : monsters) {
            if (monster != null) {
                monster.updateStatsUses();
            }
        }
    }

    public void removeMonsterFrom(int index) {
        if (monsters.get(index) != null) {
            monsters.set(index, null);
        }
    }

    public int countMonsters() {
        int count = 0;
        for (int i = 0; i < 5; i++) {
            if (monsters.get(i) != null) {
                count++;
            }
        }

        return count;
    }

    public Optional<MonsterCard> getOpposingMonster(MonsterCard card, Player enemy) {
        int index = card.getBoardPosition();
        return enemy.getBoard().getMonsterAt(index);
    }
}