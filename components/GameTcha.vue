<!-- components/GameTcha.vue -->
<script setup>
import { ref, onUnmounted } from 'vue';

const GRID_SIZE = 18;
const CELL_SIZE = 40;
const INITIAL_SPEED = 200;

// Images pour le serpent et la nourriture
const snakeImages = {
  head: 'images/gameTcha/sketch_filet-0.png',  // Image unique qui sera tournée
  body: 'images/gameTcha/sketch_filet-1.png',
  tail: 'images/gameTcha/sketch_filet-2.png'
};

// Images pour la nourriture
const foodImages = [
  'images/gameTcha/sketch_dechet_01.png',
  'images/gameTcha/sketch_dechet_02.png',
  'images/gameTcha/sketch_dechet_03.png',
  'images/gameTcha/sketch_dechet_04.png',
  'images/gameTcha/sketch_dechet_05.png'
];

const gridSize = ref(GRID_SIZE);
const cellSize = ref(CELL_SIZE);
const snake = ref([
  { x: 4, y: 5, type: 'head', direction: 'RIGHT' },
  { x: 3, y: 5, type: 'body', direction: 'RIGHT' },
  { x: 2, y: 5, type: 'tail', direction: 'RIGHT' }
]);
const food = ref({
  x: 15,
  y: 15,
  image: foodImages[Math.floor(Math.random() * foodImages.length)]
});
const direction = ref('RIGHT');
const nextDirection = ref('RIGHT');
const isRunning = ref(false);
const gameOver = ref(false);
const score = ref(0);
const gameLoop = ref(null);
const gameBoard = ref(null);

const scoreToWin = ref(5);

const emit = defineEmits(['success']);

// Fonction pour obtenir la rotation selon la direction et le type de segment
const getRotation = (segment) => {
  // Pour le corps
  if (segment.type === 'body') {
    const isVertical = segment.isVertical;
    return isVertical ? 'rotate(90deg)' : 'rotate(0deg)';
  }

  // Pour la tête (maintenant inversée)
  if (segment.type === 'head') {
    switch (segment.direction) {
      case 'UP': return 'rotate(90deg)';    // Inversé
      case 'DOWN': return 'rotate(-90deg)'; // Inversé
      case 'LEFT': return 'rotate(0deg)';   // Inversé
      case 'RIGHT': return 'rotate(180deg)';// Inversé
    }
  }

  // Pour la queue (reste inchangé)
  switch (segment.direction) {
    case 'UP': return 'rotate(90deg)';
    case 'DOWN': return 'rotate(-90deg)';
    case 'LEFT': return 'rotate(0deg)';
    case 'RIGHT': return 'rotate(180deg)';
  }
};

// Fonction pour obtenir l'image selon le type de segment
const getSegmentImage = (segment) => {
  switch (segment.type) {
    case 'head': return snakeImages.head;
    case 'tail': return snakeImages.tail;
    default: return snakeImages.body;
  }
};

const generateFood = () => {
  return {
    x: Math.floor(Math.random() * GRID_SIZE),
    y: Math.floor(Math.random() * GRID_SIZE),
    image: foodImages[Math.floor(Math.random() * foodImages.length)]
  };
};

const checkCollision = (head) => {
  if (
      head.x < 0 ||
      head.x >= GRID_SIZE ||
      head.y < 0 ||
      head.y >= GRID_SIZE
  ) {
    return true;
  }
  return snake.value.some((segment, index) => {
    if (index === 0) return false;
    return segment.x === head.x && segment.y === head.y;
  });
};

const updateSegmentOrientations = (segments) => {
  for (let i = 1; i < segments.length - 1; i++) {
    const prev = segments[i - 1];
    const curr = segments[i];
    const next = segments[i + 1];

    // Déterminer si le segment est vertical en regardant ses voisins
    curr.isVertical = prev.x === next.x;
  }
};

const moveSnake = () => {
  const head = { ...snake.value[0] };
  direction.value = nextDirection.value;

  switch (direction.value) {
    case 'UP':
      head.y -= 1;
      break;
    case 'DOWN':
      head.y += 1;
      break;
    case 'LEFT':
      head.x -= 1;
      break;
    case 'RIGHT':
      head.x += 1;
      break;
  }

  head.direction = direction.value;
  head.type = 'head';

  if (checkCollision(head)) {
    gameOver.value = true;
    if (gameLoop.value) {
      clearInterval(gameLoop.value);
    }
    return;
  }

  const newSnake = [head, ...snake.value];

  if (head.x === food.value.x && head.y === food.value.y) {
    food.value = generateFood();
    score.value++;
    if (score.value >= scoreToWin.value) {
      clearInterval(gameLoop.value);
      emit('success');
    }
  } else {
    newSnake.pop();
  }

  // Mise à jour des types et directions
  newSnake[0].type = 'head';
  for (let i = 1; i < newSnake.length - 1; i++) {
    newSnake[i].type = 'body';
  }
  if (newSnake.length > 1) {
    const last = newSnake.length - 1;
    newSnake[last].type = 'tail';
    newSnake[last].direction = newSnake[last - 1].direction;
  }

  // Mettre à jour l'orientation des segments
  updateSegmentOrientations(newSnake);

  snake.value = newSnake;
};

const startGame = () => {
  if (gameBoard.value) {
    gameBoard.value.focus();
  }
  isRunning.value = true;
  gameLoop.value = setInterval(moveSnake, INITIAL_SPEED);
};

const resetGame = () => {
  snake.value = [
    { x: 4, y: 5, type: 'head', direction: 'RIGHT' },
    { x: 3, y: 5, type: 'body', direction: 'RIGHT', isVertical: false },
    { x: 2, y: 5, type: 'tail', direction: 'RIGHT' }
  ];
  food.value = generateFood();
  direction.value = 'RIGHT';
  nextDirection.value = 'RIGHT';
  isRunning.value = false;
  gameOver.value = false;
  score.value = 0;
  if (gameLoop.value) {
    clearInterval(gameLoop.value);
  }
  startGame();
};

const handleKeypress = (event) => {
  const key = event.key;

  switch (key) {
    case 'ArrowUp':
      if (direction.value !== 'DOWN') nextDirection.value = 'UP';
      break;
    case 'ArrowDown':
      if (direction.value !== 'UP') nextDirection.value = 'DOWN';
      break;
    case 'ArrowLeft':
      if (direction.value !== 'RIGHT') nextDirection.value = 'LEFT';
      break;
    case 'ArrowRight':
      if (direction.value !== 'LEFT') nextDirection.value = 'RIGHT';
      break;
  }
};

onUnmounted(() => {
  if (gameLoop.value) {
    clearInterval(gameLoop.value);
  }
});

defineExpose({
  startGame
});
</script>

<template>
  <div class="snake-game">
    <div class="game-header">
      <h2>Snake CAPTCHA</h2>
      <div v-if="isRunning" class="score">Score: {{ score }}/{{ scoreToWin }}</div>
      <div v-if="isRunning " class="instructions">
        Utilisez les flèches du clavier pour collecter {{ scoreToWin }} déchets afin de réussir le CAPTCHA.
      </div>
    </div>

    <div
        class="game-board"
        :style="{ width: `${gridSize * cellSize}px`, height: `${gridSize * cellSize}px` }"
        @keydown="handleKeypress"
        tabindex="0"
        ref="gameBoard"
    >
      <div
          v-for="(segment, index) in snake"
          :key="index"
          class="snake-segment"
          :style="{
          left: `${segment.x * cellSize}px`,
          top: `${segment.y * cellSize}px`,
          width: `${cellSize}px`,
          height: `${cellSize}px`,
          backgroundImage: `url(${getSegmentImage(segment)})`,
          transform: getRotation(segment),
          transformOrigin: 'center'
        }"
      ></div>

      <div
          class="food"
          :style="{
          left: `${food.x * cellSize}px`,
          top: `${food.y * cellSize}px`,
          width: `${cellSize}px`,
          height: `${cellSize}px`,
          backgroundImage: `url(${food.image})`
        }"
      ></div>
    </div>

    <!-- Ajout d'une div overlay pour le game over -->
    <div v-if="gameOver" class="game-over-overlay">
      <div class="game-over">Vous n'êtes pas un humain !</div>
      <div class="game-over">Vous avez collecté {{ score }} déchets.</div>
      <div class="game-over">Collectez {{ scoreToWin }} déchets pour réussir le CAPTCHA.</div>
      <button @click="resetGame" class="reset-btn">
        Réessayer
      </button>
    </div>
  </div>
</template>

<style scoped>
.snake-game {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
  padding: 1rem;
}

.game-header {
  text-align: center;
}

.game-board {
  position: relative;
  background-color: #0d47a1;
  border: 2px solid #333;
  outline: none;
}

.snake-segment {
  position: absolute;
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
  transition: transform 0.1s;
}

.food {
  position: absolute;
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
}

.controls {
  margin-top: 1rem;
}

button {
  padding: 0.5rem 1rem;
  font-size: 1rem;
  cursor: pointer;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
}

button:hover {
  background-color: #45a049;
}

.reset-btn {
  background-color: #f44336;
}

.reset-btn:hover {
  background-color: #da190b;
}

.game-over-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 1rem;
}

.game-over {
  color: white;
  font-size: 1.5rem;
  font-weight: bold;
  text-align: center;
}

.instructions {
  color: #666;
  margin: 0.5rem 0;
}
</style>