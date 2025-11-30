package Domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Board {
    
    // Constantes para evitar "Magic Numbers"
    private static final int MAX_MONSTER_SLOTS = 5;
    
    // Lista interna privada para garantir encapsulamento
    private final List<MonsterCard> monsters;

    public Board() {
        this.monsters = new ArrayList<>(MAX_MONSTER_SLOTS);
    }

    /**
     * Tenta adicionar um monstro ao tabuleiro.
     * @param card A carta a ser jogada.
     * @throws IllegalStateException se o tabuleiro estiver cheio.
     */
    public void placeMonster(MonsterCard card) {
        if (isFull()) {
            throw new IllegalStateException("O tabuleiro está cheio. Não é possível jogar mais monstros.");
        }
        monsters.add(card);
    }

    public void removeMonster(MonsterCard card) {
        monsters.remove(card);
    }

    public boolean isFull() {
        return monsters.size() >= MAX_MONSTER_SLOTS;
    }

    // Retorna uma lista imutável para impedir que classes externas (como a UI)
    // modifica o estado do tabuleiro diretamente sem passar pelos métodos de regra.
    public List<MonsterCard> getMonsters() {
        return Collections.unmodifiableList(monsters);
    }

    public Optional<MonsterCard> getMonsterAt(int index) {
        if (index >= 0 && index < monsters.size()) {
            return Optional.of(monsters.get(index));
        }
        return Optional.empty();
    }
}