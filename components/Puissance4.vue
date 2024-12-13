<template>
  <div class="game-container">
    <!-- Popup initiale -->
    <div v-if="showIntroPopup" class="intro-popup">
      <div class="popup-content">
        <h2 style="color: white">
          Nos systèmes doutent toujours que vous soyez humain. Prouvez-nous le contraire en battant notre robot 😈
        </h2>
        <p>
        </p>
        <button @click="startGame" class="start-button">Commencer</button>
      </div>
    </div>

    <!-- Modal du jeu -->
    <div class="modal">
      <div class="modal-content">
        <!-- En-tête -->
        <div class="modal-header">
          <h2 class="game-title">CATCHA - Puissance 4 vs IA</h2>
          <button v-if="winner === 1" @click="closeGame" class="close-button">×</button>
        </div>

        <!-- Plateau de jeu -->
        <div class="board-container">
          <div class="board">
            <div v-for="(row, rowIndex) in board" :key="rowIndex" class="row">
              <div
                  v-for="(cell, colIndex) in row"
                  :key="colIndex"
                  class="cell"
                  @click="makeMove(colIndex)"
                  :class="{
                  'player1': cell === 1,
                  'player2': cell === 2,
                  'empty': cell === null
                }"
              >
                <div class="piece"></div>
              </div>
            </div>
          </div>
        </div>

        <!-- Contrôles -->
        <div class="game-controls">
          <div v-if="winner" class="winner-banner" :class="{ 'ai-wins': winner === 2 }">
            <p class="winner-text">
              {{ winner === 1 ? 'Vous avez gagné ! 🎉 Vous pouvez fermer maintenant.' : "L'IA a gagné ! 🤖" }}
            </p>
          </div>
          <p v-else-if="isDraw" class="draw-text">Match nul ! 🤝</p>

          <div class="difficulty-selector">
            <label>Niveau :</label>
            <div class="difficulty-buttons">
              <button
                  v-for="level in ['easy', 'medium', 'hard']"
                  :key="level"
                  @click="setDifficulty(level)"
                  :class="{ active: difficulty === level }"
                  class="difficulty-button"
              >
                {{ getDifficultyLabel(level) }}
              </button>
            </div>
          </div>

          <button @click="resetGame" class="reset-button">
            <span>Nouvelle partie 🔄</span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Puissance4',
  emits: ['close'],
  data() {
    return {
      showIntroPopup: true,
      board: Array(6).fill().map(() => Array(7).fill(null)),
      currentPlayer: 1,
      winner: null,
      isDraw: false,
      showModal: false,
      thinking: false,
      difficulty: 'medium'
    }
  },
  methods: {
    getDifficultyLabel(level) {
      return {
        easy: 'Facile',
        medium: 'Moyen',
        hard: 'Difficile'
      }[level]
    },
    closeGame() {
      this.$emit('close');
    },
    startGame() {
      this.showIntroPopup = false;
    },
    setDifficulty(level) {
      this.difficulty = level;
      this.resetGame();
    },

    async makeMove(col) {
      if (this.winner || this.isDraw || this.thinking || this.currentPlayer !== 1) return;

      if (this.isValidMove(col)) {
        this.dropPiece(col);

        if (!this.winner && !this.isDraw) {
          this.thinking = true;
          await this.delay(500);
          const aiMove = this.getAIMove();
          this.dropPiece(aiMove);
          this.thinking = false;
        }
      }
    },

    isValidMove(col) {
      return this.board[0][col] === null;
    },

    delay(ms) {
      return new Promise(resolve => setTimeout(resolve, ms));
    },

    dropPiece(col) {
      for (let row = 5; row >= 0; row--) {
        if (!this.board[row][col]) {
          this.board[row][col] = this.currentPlayer;
          if (this.checkWin(row, col)) {
            this.winner = this.currentPlayer;
          } else if (this.checkDraw()) {
            this.isDraw = true;
          } else {
            this.currentPlayer = this.currentPlayer === 1 ? 2 : 1;
          }
          break;
        }
      }
    },
    getAIMove() {
      switch (this.difficulty) {
        case 'easy':
          return this.getRandomMove();
        case 'medium':
          return Math.random() < 0.6 ? this.getSmartMove() : this.getRandomMove();
        case 'hard':
          return Math.random() < 0.9 ? this.getSmartMove() : this.getRandomMove();
      }
    },

    getRandomMove() {
      const validMoves = [];
      for (let col = 0; col < 7; col++) {
        if (this.isValidMove(col)) validMoves.push(col);
      }
      return validMoves[Math.floor(Math.random() * validMoves.length)];
    },

    getSmartMove() {
      // Vérifier victoire immédiate
      for (let col = 0; col < 7; col++) {
        if (this.isValidMove(col)) {
          let row = this.getRowForCol(col);
          this.board[row][col] = 2;
          if (this.checkWin(row, col)) {
            this.board[row][col] = null;
            return col;
          }
          this.board[row][col] = null;
        }
      }

      // Bloquer victoire adversaire
      for (let col = 0; col < 7; col++) {
        if (this.isValidMove(col)) {
          let row = this.getRowForCol(col);
          this.board[row][col] = 1;
          if (this.checkWin(row, col)) {
            this.board[row][col] = null;
            return col;
          }
          this.board[row][col] = null;
        }
      }

      // Évaluer chaque colonne
      let bestScore = -Infinity;
      let bestMoves = [];

      for (let col = 0; col < 7; col++) {
        if (this.isValidMove(col)) {
          let row = this.getRowForCol(col);
          let score = this.evaluateMove(row, col);

          if (score > bestScore) {
            bestScore = score;
            bestMoves = [col];
          } else if (score === bestScore) {
            bestMoves.push(col);
          }
        }
      }

      return bestMoves[Math.floor(Math.random() * bestMoves.length)];
    },

    evaluateMove(row, col) {
      let score = 0;

      // Bonus pour le centre
      if (col === 3) score += 3;

      // Vérifier les alignements potentiels
      score += this.checkPotentialAlignments(row, col, 2); // Pour l'IA
      score -= this.checkPotentialAlignments(row, col, 1); // Contre le joueur

      return score;
    },

    checkPotentialAlignments(row, col, player) {
      let count = 0;
      const directions = [
        [[0, 1], [0, -1]], // Horizontal
        [[1, 0], [-1, 0]], // Vertical
        [[1, 1], [-1, -1]], // Diagonal /
        [[1, -1], [-1, 1]] // Diagonal \
      ];

      for (const [dir1, dir2] of directions) {
        let sequence = this.getSequence(row, col, player, dir1, dir2);
        count += this.evaluateSequence(sequence);
      }

      return count;
    },

    getSequence(row, col, player, dir1, dir2) {
      let sequence = [player];
      this.board[row][col] = player;

      for (const [dx, dy] of [dir1, dir2]) {
        let r = row + dx;
        let c = col + dy;
        let count = 0;

        while (r >= 0 && r < 6 && c >= 0 && c < 7 && count < 3) {
          sequence.push(this.board[r][c]);
          r += dx;
          c += dy;
          count++;
        }
      }

      this.board[row][col] = null;
      return sequence;
    },

    evaluateSequence(sequence) {
      let empty = sequence.filter(cell => cell === null).length;
      let player = sequence.filter(cell => cell !== null).length;
      let score = 0;

      if (player === 4) score += 100;
      else if (player === 3 && empty === 1) score += 5;
      else if (player === 2 && empty === 2) score += 2;

      return score;
    },

    getRowForCol(col) {
      for (let row = 5; row >= 0; row--) {
        if (!this.board[row][col]) {
          return row;
        }
      }
      return -1;
    },

    checkWin(row, col) {
      const directions = [
        [[0, 1], [0, -1]], // Horizontal
        [[1, 0], [-1, 0]], // Vertical
        [[1, 1], [-1, -1]], // Diagonal /
        [[1, -1], [-1, 1]] // Diagonal \
      ];

      const player = this.board[row][col];

      for (const [dir1, dir2] of directions) {
        let count = 1;

        for (const [dx, dy] of [dir1, dir2]) {
          let r = row + dx;
          let c = col + dy;
          while (r >= 0 && r < 6 && c >= 0 && c < 7 && this.board[r][c] === player) {
            count++;
            r += dx;
            c += dy;
          }
        }

        if (count >= 4) return true;
      }

      return false;
    },

    checkDraw() {
      return this.board[0].every(cell => cell !== null);
    },

    resetGame() {
      this.board = Array(6).fill().map(() => Array(7).fill(null));
      this.currentPlayer = 1;
      this.winner = null;
      this.isDraw = false;
      this.thinking = false;
    }
  }
}
</script>

<style scoped>
.game-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: radial-gradient(circle at center, #1a1a2e 0%, #16213e 100%);
  font-family: Arial, sans-serif;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.8);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: linear-gradient(135deg, #1e293b, #0f172a);
  padding: 30px;
  border-radius: 20px;
  max-width: 700px;
  width: 90%;
  color: white;
}

.open-button {
  padding: 20px 40px;
  font-size: 24px;
  background: linear-gradient(45deg, #4f46e5, #6366f1);
  color: white;
  border: none;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.game-title {
  font-size: 28px;
  margin: 0;
  color: white;
}

.close-button {
  background: none;
  border: none;
  color: white;
  font-size: 28px;
  cursor: pointer;
}

.board-container {
  margin: 20px 0;
  padding: 15px;
  background: #2563eb;
  border-radius: 15px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.board {
  display: grid;
  gap: 10px;
  padding: 10px;
  background: #2563eb;
  border-radius: 10px;
}

.row {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 10px;
}

.cell {
  aspect-ratio: 1;
  background: #1e293b;
  border-radius: 50%;
  cursor: pointer;
  transition: transform 0.2s;
  position: relative;
}

.cell:hover {
  transform: translateY(-2px);
}

.piece {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background: white;
  transition: all 0.3s;
}

.cell.player1 .piece {
  background: linear-gradient(135deg, #ef4444, #dc2626);
  box-shadow: inset 0 -4px 4px rgba(0, 0, 0, 0.2);
}

.cell.player2 .piece {
  background: linear-gradient(135deg, #facc15, #eab308);
  box-shadow: inset 0 -4px 4px rgba(0, 0, 0, 0.2);
}

.game-controls {
  margin-top: 20px;
}

.winner-banner {
  background: linear-gradient(135deg, #22c55e, #16a34a);
  padding: 20px;
  border-radius: 12px;
  text-align: center;
  margin-bottom: 20px;
  animation: slideIn 0.5s ease-out;
}

.ai-wins {
  background: linear-gradient(135deg, #6366f1, #4f46e5);
}

.winner-text {
  font-size: 24px;
  margin: 0;
  font-weight: bold;
}

.draw-text {
  text-align: center;
  font-size: 24px;
  color: #f59e0b;
  font-weight: bold;
}

.difficulty-selector {
  text-align: center;
  margin: 20px 0;
}

.difficulty-buttons {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 10px;
}

.difficulty-button {
  padding: 10px 20px;
  border: none;
  border-radius: 8px;
  background: rgba(255, 255, 255, 0.1);
  color: white;
  cursor: pointer;
  transition: all 0.3s;
}

.difficulty-button.active {
  background: linear-gradient(135deg, #6366f1, #4f46e5);
  transform: scale(1.05);
}

.reset-button {
  display: block;
  margin: 20px auto;
  padding: 12px 24px;
  background: linear-gradient(135deg, #3b82f6, #2563eb);
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  transition: all 0.3s;
}

.reset-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

@keyframes slideIn {
  from {
    transform: translateY(-20px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

@media (max-width: 600px) {
  .modal-content {
    padding: 20px;
  }

  .game-title {
    font-size: 24px;
  }

  .winner-text {
    font-size: 20px;
  }

  .difficulty-buttons {
    flex-direction: column;
  }

  .cell {
    width: 40px;
    height: 40px;
  }
}


.intro-popup {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.8);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2000;
}

.popup-content {
  background: #1e293b;
  padding: 20px;
  border-radius: 12px;
  text-align: center;
  color: white;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
}

.start-button {
  margin-top: 20px;
  padding: 10px 20px;
  font-size: 16px;
  background: linear-gradient(135deg, #3b82f6, #2563eb);
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: transform 0.2s;
}

.start-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}
</style>