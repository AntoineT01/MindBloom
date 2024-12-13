<template>
  <div>
    <div class="modal-overlay">
      <div class="modal-content">
        <!-- Affichage de l'explication si le jeu n'a pas encore commencé -->
        <div v-if="!gameStarted" class="game-intro">
          <h2 class="text-xl font-bold mb-4 text-center">Bienvenue dans le Snake CAPTCHA</h2>
          <p class="text-center mb-6"><strong>Complétez le CAPTCHA afin de découvrir le site.</strong><br>
            Utilisez les flèches de votre clavier pour contrôler le serpent. Collectez 5 déchets pour prouver que vous êtes humain.
            <br />
            Évitez de heurter les murs ou vous-même !
          </p>
          <button
              @click="startGame"
              class="start-btn px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
          >
            Commencer le jeu
          </button>
        </div>

        <!-- Affichage du jeu Snake une fois lancé -->
        <div v-else>
          <GameTcha ref="gameComponent" @success="handleSuccess" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, nextTick } from 'vue';
import GameTcha from './GameTcha.vue';

// Déclarations de refs et des props
const gameComponent = ref(null);
const emit = defineEmits(['close', 'verified']);
const gameStarted = ref(false);

// Fonction pour lancer le jeu
const startGame = () => {
  gameStarted.value = true;
  nextTick(() => {
    gameComponent.value?.startGame();
  });
};

// Fonction appelée lorsque le jeu est terminé avec succès
const handleSuccess = () => {
  emit('verified'); // Émet l'événement de vérification
};

// Fonction pour fermer la modal
const closeModal = () => {
  emit('close');
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 2rem;
  border-radius: 8px;
  position: relative;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  overflow: auto;
}

.game-intro {
  text-align: center;
}
.start-btn {
  padding: 0.75rem 1.5rem;
  font-size: 1.2rem;
  cursor: pointer;
  background: linear-gradient(45deg, #4CAF50, #45a049);
  color: white;
  border: none;
  border-radius: 8px;
  position: relative;
  overflow: hidden;
  transition: transform 0.2s ease-in-out, background 0.3s ease;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}

.start-btn::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 0;
  height: 0;
  border-radius: 50%;
  transform: translate(-50%, -50%);
  transition: width 0.4s ease, height 0.4s ease;
}

.start-btn:hover::before {
  width: 300%;
  height: 300%;
}

.start-btn:hover {
  transform: scale(1.05);
  box-shadow: 0 6px 15px rgba(0, 128, 0, 0.5);
}

.start-btn:active {
  transform: scale(0.95);
  box-shadow: 0 3px 8px rgba(0, 128, 0, 0.3);
}</style>
