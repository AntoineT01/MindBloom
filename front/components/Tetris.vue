<template>
  <div class="game-container">
    <!-- Bouton pour lancer le jeu -->
    <button @click="showModal = true" class="open-button">
      <div class="button-content">
        <span class="button-text">Tetris</span>
        <span class="button-icon">🎮</span>
      </div>
    </button>

    <!-- Modal du jeu -->
    <div v-if="showModal" class="modal">
      <div class="modal-content">
        <div class="modal-header">
          <h2 class="game-title">Tetris</h2>
          <div class="score-info">
            <div>Score: {{ score }}</div>
            <div>Lignes: {{ lines }}</div>
            <div>Niveau: {{ level }}</div>
          </div>
          <button @click="showModal = false" class="close-button">×</button>
        </div>

        <div class="game-area">
          <div class="board">
            <div v-for="(row, y) in board" :key="y" class="row">
              <div
                  v-for="(cell, x) in row"
                  :key="`${x}-${y}`"
                  class="cell"
                  :class="{
                  filled: cell !== 0,
                  [`color-${cell}`]: cell !== 0
                }"
              ></div>
            </div>
          </div>

          <div class="info-panel">
            <div class="next-piece">
              <h3>Suivant</h3>
              <div class="next-piece-display">
                <div v-for="(row, y) in nextPieceDisplay" :key="y" class="row">
                  <div
                      v-for="(cell, x) in row"
                      :key="`${x}-${y}`"
                      class="cell"
                      :class="{
                      filled: cell !== 0,
                      [`color-${cell}`]: cell !== 0
                    }"
                  ></div>
                </div>
              </div>
            </div>

            <div class="controls">
              <h3>Contrôles</h3>
              <p>↑ : Rotation</p>
              <p>← → : Déplacer</p>
              <p>↓ : Descendre</p>
              <p>Espace : Chute</p>
            </div>

            <button @click="startGame" class="start-button" :disabled="gameStarted">
              Démarrer
            </button>
            <button @click="pauseGame" class="pause-button" :disabled="!gameStarted">
              {{ isPaused ? 'Reprendre' : 'Pause' }}
            </button>
          </div>
        </div>

        <div v-if="gameOver" class="game-over">
          <h2>Game Over!</h2>
          <p>Score final: {{ score }}</p>
          <button @click="resetGame" class="reset-button">
            Nouvelle partie
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const PIECES = {
  I: {
    shape: [[1, 1, 1, 1]],
    color: 1
  },
  J: {
    shape: [
      [1, 0, 0],
      [1, 1, 1]
    ],
    color: 2
  },
  L: {
    shape: [
      [0, 0, 1],
      [1, 1, 1]
    ],
    color: 3
  },
  O: {
    shape: [
      [1, 1],
      [1, 1]
    ],
    color: 4
  },
  S: {
    shape: [
      [0, 1, 1],
      [1, 1, 0]
    ],
    color: 5
  },
  T: {
    shape: [
      [0, 1, 0],
      [1, 1, 1]
    ],
    color: 6
  },
  Z: {
    shape: [
      [1, 1, 0],
      [0, 1, 1]
    ],
    color: 7
  }
};

export default {
  name: 'Tetris',
  data() {
    return {
      showModal: false,
      board: Array(20).fill().map(() => Array(10).fill(0)),
      currentPiece: null,
      currentPiecePosition: {x: 0, y: 0},
      nextPiece: null,
      nextPieceDisplay: Array(4).fill().map(() => Array(4).fill(0)),
      gameStarted: false,
      gameOver: false,
      isPaused: false,
      score: 0,
      lines: 0,
      level: 1,
      dropInterval: null,
      dropSpeed: 1000
    }
  },

  methods: {
    // Assurez-vous que la méthode startGame initialise correctement
    startGame() {
      if (this.gameStarted) return;

      this.resetGame();
      this.gameStarted = true;
      this.spawnPiece();
      this.setupControls();
      this.startDropping(); // Assurez-vous que cette ligne est présente
    },

    resetGame() {
      this.board = Array(20).fill().map(() => Array(10).fill(0));
      this.score = 0;
      this.lines = 0;
      this.level = 1;
      this.gameOver = false;
      this.currentPiece = null;
      this.nextPiece = null;
      this.dropSpeed = 1000;
      this.isPaused = false;
      if (this.dropInterval) {
        clearInterval(this.dropInterval);
      }
    },

    pauseGame() {
      if (!this.gameStarted) return;

      this.isPaused = !this.isPaused;
      if (this.isPaused) {
        clearInterval(this.dropInterval);
      } else {
        this.startDropping();
      }
    },

    getRandomPiece() {
      const pieces = Object.keys(PIECES);
      const piece = PIECES[pieces[Math.floor(Math.random() * pieces.length)]];
      return {
        shape: JSON.parse(JSON.stringify(piece.shape)),
        color: piece.color
      };
    },

    // Modifiez la méthode spawnPiece
    spawnPiece() {
      if (!this.nextPiece) {
        this.currentPiece = this.getRandomPiece();
        this.nextPiece = this.getRandomPiece();
      } else {
        this.currentPiece = this.nextPiece;
        this.nextPiece = this.getRandomPiece();
      }

      // Positionner la pièce en haut au centre
      this.currentPiecePosition = {
        x: Math.floor(this.board[0].length / 2) - Math.floor(this.currentPiece.shape[0].length / 2),
        y: -2 // Commencer au-dessus du plateau pour permettre une apparition progressive
      };

      this.updateNextPieceDisplay();
      this.drawBoard();

      // Vérifier la collision uniquement pour les cellules visibles
      if (this.checkCollisionOnSpawn()) {
        this.gameOver = true;
        this.gameStarted = false;
        clearInterval(this.dropInterval);
      }
    },

    // Ajouter cette nouvelle méthode pour vérifier la collision au spawn
    checkCollisionOnSpawn() {
      const piece = this.currentPiece.shape;
      const pos = this.currentPiecePosition;

      for (let y = 0; y < piece.length; y++) {
        for (let x = 0; x < piece[y].length; x++) {
          if (piece[y][x]) {
            const boardY = pos.y + y;
            const boardX = pos.x + x;

            // Vérifier uniquement les cellules qui sont dans le plateau
            if (boardY >= 0 && boardY < this.board.length) {
              if (
                  boardX < 0 ||
                  boardX >= this.board[0].length ||
                  this.board[boardY][boardX]
              ) {
                return true;
              }
            }
          }
        }
      }
      return false;
    },

    updateNextPieceDisplay() {
      this.nextPieceDisplay = Array(4).fill().map(() => Array(4).fill(0));
      const piece = this.nextPiece.shape;
      const offsetY = Math.floor((4 - piece.length) / 2);
      const offsetX = Math.floor((4 - piece[0].length) / 2);

      for (let y = 0; y < piece.length; y++) {
        for (let x = 0; x < piece[y].length; x++) {
          if (piece[y][x]) {
            this.nextPieceDisplay[y + offsetY][x + offsetX] = this.nextPiece.color;
          }
        }
      }
    },

    setupControls() {
      document.addEventListener('keydown', this.handleKeyPress);
    },

    // Modifiez la méthode startDropping
    startDropping() {
      if (this.dropInterval) {
        clearInterval(this.dropInterval);
      }
      this.dropInterval = setInterval(() => {
        if (!this.isPaused && this.gameStarted) {
          this.dropPiece();
        }
      }, this.dropSpeed);
    },

    handleKeyPress(event) {
      if (!this.gameStarted || this.gameOver || this.isPaused) return;

      switch (event.key) {
        case 'ArrowLeft':
          this.movePiece(-1);
          break;
        case 'ArrowRight':
          this.movePiece(1);
          break;
        case 'ArrowDown':
          this.dropPiece();
          break;
        case 'ArrowUp':
          this.rotatePiece();
          break;
        case ' ':
          this.hardDrop();
          break;
      }
    },
    movePiece(direction) {
      const newX = this.currentPiecePosition.x + direction;
      const oldX = this.currentPiecePosition.x;

      this.currentPiecePosition.x = newX;
      if (this.checkCollision()) {
        this.currentPiecePosition.x = oldX;
      }
      this.drawBoard();
    },

    rotatePiece() {
      const oldShape = this.currentPiece.shape;
      const rotated = oldShape[0].map((_, i) =>
          oldShape.map(row => row[i]).reverse()
      );

      const oldPiece = {...this.currentPiece};
      this.currentPiece = {...this.currentPiece, shape: rotated};

      if (this.checkCollision()) {
        this.currentPiece = oldPiece;
      }
      this.drawBoard();
    },

    dropPiece() {
      const newY = this.currentPiecePosition.y + 1;
      const oldY = this.currentPiecePosition.y;

      this.currentPiecePosition.y = newY;
      if (this.checkCollision()) {
        this.currentPiecePosition.y = oldY;
        this.mergePiece();
        this.clearLines();
        this.spawnPiece();
      }
    },


    hardDrop() {
      while (!this.checkCollision()) {
        this.currentPiecePosition.y++;
      }
      this.currentPiecePosition.y--;
      this.mergePiece();
      this.clearLines();
      this.spawnPiece();
      this.drawBoard();
    },

    // Modifier la méthode checkCollision pour la rendre plus permissive avec les positions négatives en Y
    checkCollision() {
      const piece = this.currentPiece.shape;
      const pos = this.currentPiecePosition;

      for (let y = 0; y < piece.length; y++) {
        for (let x = 0; x < piece[y].length; x++) {
          if (piece[y][x]) {
            const boardY = pos.y + y;
            const boardX = pos.x + x;

            if (
                boardX < 0 ||
                boardX >= this.board[0].length ||
                boardY >= this.board.length ||
                (boardY >= 0 && this.board[boardY][boardX])
            ) {
              return true;
            }
          }
        }
      }
      return false;
    },

    // Modifiez la méthode mergePiece
    mergePiece() {
      const piece = this.currentPiece.shape;
      const pos = this.currentPiecePosition;

      for (let y = 0; y < piece.length; y++) {
        for (let x = 0; x < piece[y].length; x++) {
          if (piece[y][x]) {
            const boardY = pos.y + y;
            if (boardY >= 0) {
              // Créez une copie du tableau board avant de le modifier
              const newBoard = this.board.map(row => [...row]);
              newBoard[boardY][pos.x + x] = this.currentPiece.color;
              this.board = newBoard;
            }
          }
        }
      }
    },

    clearLines() {
      let linesCleared = 0;

      for (let y = this.board.length - 1; y >= 0; y--) {
        if (this.board[y].every(cell => cell !== 0)) {
          this.board.splice(y, 1);
          this.board.unshift(Array(10).fill(0));
          linesCleared++;
          y++;
        }
      }

      if (linesCleared > 0) {
        this.updateScore(linesCleared);
      }
    },

    updateScore(linesCleared) {
      const points = [40, 100, 300, 1200];
      this.score += points[linesCleared - 1] * this.level;
      this.lines += linesCleared;
      this.level = Math.floor(this.lines / 10) + 1;

      this.dropSpeed = Math.max(100, 1000 - (this.level - 1) * 100);
      clearInterval(this.dropInterval);
      this.startDropping();
    },

    // Modifier la méthode drawBoard pour gérer correctement les positions négatives
    drawBoard() {
      const tempBoard = this.board.map(row => [...row]);

      if (this.currentPiece) {
        const piece = this.currentPiece.shape;
        const pos = this.currentPiecePosition;

        for (let y = 0; y < piece.length; y++) {
          for (let x = 0; x < piece[y].length; x++) {
            if (piece[y][x]) {
              const boardY = pos.y + y;
              const boardX = pos.x + x;
              if (boardY >= 0 && boardY < 20 && boardX >= 0 && boardX < 10) {
                tempBoard[boardY][boardX] = this.currentPiece.color;
              }
            }
          }
        }
      }

      this.board = tempBoard;
    }
  },

  beforeDestroy() {
    document.removeEventListener('keydown', this.handleKeyPress);
    clearInterval(this.dropInterval);
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
  color: white;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.5);
}

  .modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

  .game-title {
    font-size: 28px;  /* Correction ici : suppression des espaces autour du tiret */
    margin: 0;
  }

  .score-info {
  display: flex;
  gap: 15px;
}

  .score-info div {
  background: rgba(255, 255, 255, 0.1);
  padding: 8px 15px;
  border-radius: 6px;
}

  .game-area {
  display: flex;
  gap: 20px;
  margin: 20px 0;
}

  /* Plateau de jeu */
  .board {
  display: grid;
  grid-template-rows: repeat(20, 30px);
  gap: 1px;
  background: #2a2a2a;
  padding: 10px;
  border-radius: 8px;
  border: 2px solid #3a3a3a;
}

  .row {
  display: grid;
  grid-template-columns: repeat(10, 30px);
  gap: 1px;
}

  .cell {
  width: 30px;
  height: 30px;
  background: #121212;
  border-radius: 4px;
  transition: background-color 0.2s;
}

  /* Couleurs des pièces */
  .color-1 {
  background: #00f0f0;
  box-shadow: inset 0 0 10px rgba(0, 240, 240, 0.5);
}
  .color-2 {
  background: #0000f0;
  box-shadow: inset 0 0 10px rgba(0, 0, 240, 0.5);
}
  .color-3 {
  background: #f0a000;
  box-shadow: inset 0 0 10px rgba(240, 160, 0, 0.5);
}
  .color-4 {
  background: #f0f000;
  box-shadow: inset 0 0 10px rgba(240, 240, 0, 0.5);
}
  .color-5 {
  background: #00f000;
  box-shadow: inset 0 0 10px rgba(0, 240, 0, 0.5);
}
  .color-6 {
  background: #a000f0;
  box-shadow: inset 0 0 10px rgba(160, 0, 240, 0.5);
}
  .color-7 {
  background: #f00000;
  box-shadow: inset 0 0 10px rgba(240, 0, 0, 0.5);
}

  .info-panel {
    min-width: 200px;  /* Correction : suppression des espaces autour du tiret */
    display: flex;
    flex-direction: column;
    gap: 20px;
  }

  .next-piece {
  background: #2a2a2a;
  padding: 15px;
  border-radius: 8px;
}

  .next-piece h3 {
  margin: 0 0 10px 0;
  text-align: center;
}

  .next-piece-display {
  display: grid;
  grid-template-rows: repeat(4, 25px);
  gap: 1px;
}

  .controls {
  background: #2a2a2a;
  padding: 15px;
  border-radius: 8px;
}

  .controls h3 {
  margin: 0 0 10px 0;
  text-align: center;
}

  .controls p {
  margin: 8px 0;
  color: #ccc;
  text-align: center;
}

  button {
  padding: 12px 24px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  transition: all 0.3s;
  background: linear-gradient(45deg, #4f46e5, #6366f1);
  color: white;
  width: 100%;
}

  button:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(79, 70, 229, 0.3);
}

  button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

  .close-button {
  background: none;
  width: auto;
  font-size: 24px;
}

  .game-over {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: rgba(0, 0, 0, 0.9);
  padding: 30px;
  border-radius: 15px;
  text-align: center;
  animation: fadeIn 0.5s;
}

  @keyframes fadeIn {
  from {opacity: 0;}
  to {opacity: 1;}
}

  /* Media Queries */
  @media (max-width: 768px) {
  .game-area {
  flex-direction: column;
}

  .cell {
  width: 25px;
  height: 25px;
}

  .board {
  grid-template-rows: repeat(20, 25px);
}

  .row {
  grid-template-columns: repeat(10, 25px);
}

  .modal-content {
  padding: 15px;
  margin: 10px;
}

  .score-info {
  flex-direction: column;
  gap: 5px;
}
}
</style>