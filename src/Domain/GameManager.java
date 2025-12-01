package Domain;

import Graphical_Interfaces.MatchWindow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class GameManager {

    private final Player player;
    private final Player enemy;
    private final MatchWindow window;

    private boolean isPlayerTurn;
    private int turnCount;

    public GameManager(Player player, Player enemy, MatchWindow window) {
        this.player = player;
        this.enemy = enemy;
        this.window = window;
        this.turnCount = 0;
        this.isPlayerTurn = true;
    }

    public void startGame() {
        window.refreshUI();
        startPlayerTurn();
    }

    // NÃO FUNCIONA

    // public void startGameLoop() {

    // Random random = new Random();
    // Player startingPlayer = random.nextBoolean() ? player : enemy;
    // Player otherPlayer = startingPlayer == player ? enemy : player;
    // Player currentPlayer = startingPlayer;

    // while (!player.isDefeated() && !enemy.isDefeated()) {

    // window.refreshUI();

    // if (currentPlayer == player) {
    // startPlayerTurn();
    // } else
    // startEnemyTurn();

    // Player temp = currentPlayer;
    // currentPlayer = otherPlayer;
    // otherPlayer = temp;

    // }
    // }

    private void startPlayerTurn() {
        isPlayerTurn = true;
        turnCount++;

        player.addMoney(2);
        player.drawCard();

        window.refreshUI();
    }

    public void endPlayerTurn() {
        if (!isPlayerTurn)
            return;

        List<MonsterCard> monsters = player.getBoard().getMonsters();
        for (int i = 0; i < 5; i++) {
            if (monsters.get(i) != null) {
                monsters.get(i).setAttacked(false);
            }
        }
        isPlayerTurn = false;
        startEnemyTurn();
    }

    private void startEnemyTurn() {
        SwingUtilities.invokeLater(() -> {
            try {
                // Simulação simples de pensamento
                // Nota: Em uma thread de UI real, usar Thread.sleep pode congelar levemente,
                // mas para este exemplo simples funciona.

                enemy.addMoney(2);
                enemy.drawCard();

                executeEnemyAI();

                window.refreshUI();

                startPlayerTurn();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void executeEnemyAI() {
        List<Card> hand = enemy.getHand();

        // 1. Embaralha a mão para não jogar sempre a primeira carta que vê
        Collections.shuffle(hand);

        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);

            // Verifica se tem dinheiro e se é um Monstro
            if (enemy.getMoney() >= card.getCost() && card instanceof MonsterCard monster) {

                int targetSlot = -1;

                // ESTRATÉGIA A: Counter (Defesa)
                // Procura um slot onde o Player tem monstro mas o Inimigo está vazio
                for (int slot = 0; slot < 5; slot++) {
                    boolean playerHasMonster = player.getBoard().getMonsterAt(slot).isPresent();
                    boolean enemySlotEmpty = enemy.getBoard().getMonsterAt(slot).isEmpty();

                    if (playerHasMonster && enemySlotEmpty) {
                        targetSlot = slot;
                        break; // Achou um lugar prioritário para bloquear!
                    }
                }

                // ESTRATÉGIA B: Aleatório (Ataque/Preenchimento)
                // Se não precisou bloquear ninguém, escolhe um slot vazio aleatório
                if (targetSlot == -1) {
                    List<Integer> emptySlots = new ArrayList<>();
                    for (int slot = 0; slot < 5; slot++) {
                        if (enemy.getBoard().getMonsterAt(slot).isEmpty()) {
                            emptySlots.add(slot);
                        }
                    }

                    if (!emptySlots.isEmpty()) {
                        Random r = new Random();
                        targetSlot = emptySlots.get(r.nextInt(emptySlots.size()));
                    }
                }

                // Se encontrou um lugar válido para jogar
                if (targetSlot != -1) {
                    boolean success = enemy.getBoard().placeMonsterAt(targetSlot, monster);
                    if (success) {
                        enemy.spendMoney(card.getCost());
                        enemy.getHand().remove(card);
                        i--; // Ajusta o índice pois a lista diminuiu
                        // O loop continua para ver se dá para jogar mais cartas
                    }
                }
            }
        }
    }

    public void checkWinCondition() {
        if (player.getHealth() <= 0) {
            JOptionPane.showMessageDialog(window, "GAME OVER! O Oponente venceu.");
            System.exit(0);
        } else if (enemy.getHealth() <= 0) {
            JOptionPane.showMessageDialog(window, "VITÓRIA! Você venceu.");
            System.exit(0);
        }
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public int getTurnCount() {
        return turnCount;
    }
}