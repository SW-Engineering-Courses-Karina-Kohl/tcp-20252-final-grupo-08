[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/9TN0gSSC)
# TCP-20252-final

Este projeto apresenta um jogo de cartas por turnos, no qual o objetivo é reduzir os pontos de vida do oponente a zero utilizando tropas e magias. Abaixo estão descritas as funcionalidades principais e instruções de uso da aplicação.

---

## 🎮 Menu Principal

No **Menu Principal**, o jogador pode iniciar uma partida clicando em **Start**.  
Ao fazer isso, será levado diretamente para a **mesa de jogo**.

---

## 🂠 Mesa de Jogo e Inspeção de Cartas

Clicar em qualquer carta abrirá a **janela de inspeção**, onde é possível visualizar as estatísticas da carta:

- **Nome**
- **Classe** (Tropa ou Magia)
- **Efeito** (caso exista)
- **Custo**
- **Ataque e Defesa** (apenas para tropas)

### Ações possíveis na janela de inspeção

- Se o jogador possuir **dinheiro suficiente**, poderá **colocar cartas no campo**.  
- Jogar cartas consome recursos.

---

## ⚔️ Sistema de Combate

A partir do **segundo turno**, o jogador pode declarar ataques:

1. Clique em uma **tropa no seu lado do campo**.
2. No menu de inspeção, selecione **Atacar**.
3. O ataque sempre é direcionado ao **slot diretamente em frente** ao atacante.

### Regras de ataque

- **Slot vazio:**  
  O oponente recebe dano igual ao **Ataque** do atacante.
  
- **Slot ocupado por uma tropa inimiga:**  
  - A tropa inimiga defende o ataque.  
  - Sua **defesa é reduzida** pelo valor do ataque.  
  - Se sua defesa chegar a **0**, ela é **removida do campo**.

### 🎯 Objetivo do Jogo

Reduzir os **pontos de vida do oponente a 0**.

---

## 🛠️ Controle de Turnos e Pausa

Na parte superior da tela, existem dois botões:

### ⏸️ Botão **Pause**
Abre o **Menu de Pause**, onde é possível:
- Continuar a partida  
- Retornar ao Menu Principal  
- Acessar o Menu de Opções  

### 🔁 Botão **Passar Turno**
Permite que a **CPU execute sua jogada**.

---

