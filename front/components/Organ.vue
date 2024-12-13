<script setup lang="ts">
defineProps({
  name: String,
  src: String,
  alt: String,
  position: Object,
  size: String,
  visible: Boolean,
  zIndex: Number,
  onClick: Function,
});
</script>

<template>
  <img
      v-if="visible"
      :src="src"
      :alt="alt"
      :class="'human_' + name"
      :style="{
      top: position.top,
      left: position.left,
      width: size,
      zIndex,
      position: 'absolute',
      transform: 'translate(-50%, -50%)',
    }"
      @click="onClick && onClick()"
  />
</template>

<style scoped>
img {
  height: auto;
  cursor: pointer;
  transition: transform 0.3s ease, filter 0.3s ease;
}

img:hover {
  transform: translate(-50%, -50%) scale(1.1) !important;
  filter: brightness(1.3);
}

.human_os {
  transform: translate(-50%, -50%) rotate(-3deg) scale(0.95) !important; /* Rotation initiale */
}

.human_os:hover {
  transform: translate(-50%, -50%) rotate(-3deg) scale(1.07) !important; /* Rotation initiale */
  filter: brightness(1.15);
}
.human_coeur {
  position: absolute;
  animation: heartbeat 1.5s infinite; /* Animation plus lente */
  transform-origin: center; /* Point d'origine pour l'animation */
}

@keyframes heartbeat {
  0% {
    transform: translate(-50%, -50%) scale(1); /* Taille normale */
    filter: drop-shadow(0 0 0 rgba(255, 0, 0, 0.5)); /* Pas d'effet lumineux */
  }
  25% {
    transform: translate(-50%, -50%) scale(1.1); /* Légère expansion */
    filter: drop-shadow(0 0 8px rgba(255, 0, 0, 0.6)); /* Lumière légère */
  }
  50% {
    transform: translate(-50%, -50%) scale(1); /* Retour à la taille normale */
    filter: drop-shadow(0 0 0 rgba(255, 0, 0, 0.5)); /* Pas d'effet lumineux */
  }
  75% {
    transform: translate(-50%, -50%) scale(1.1); /* Expansion de nouveau */
    filter: drop-shadow(0 0 8px rgba(255, 0, 0, 0.6)); /* Lumière légère */
  }
  100% {
    transform: translate(-50%, -50%) scale(1); /* Retour final */
    filter: drop-shadow(0 0 0 rgba(255, 0, 0, 0.5)); /* Pas d'effet lumineux */
  }
}
</style>
