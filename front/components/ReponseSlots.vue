<script setup lang="ts">
import { ref } from 'vue';

// Recevoir les props
defineProps({
  letter: {
    type: String,
    required: true,
  },
  question: {
    type: String,
    required: true,
  },
});

// État pour suivre si le bouton est cliqué ou non
const isClicked = ref(false);

// Fonction pour gérer le clic
function handleClick() {
  isClicked.value = !isClicked.value;
  console.log('Button clicked! State:', isClicked.value);
}
</script>

<template>
  <button 
    type="button"
    :class="[isClicked ? 'bg-violet-ple'  : 'bg-violet-clair', 'inline-flex items-center rounded-3xl px-4 py-2 cursor-pointer  font-orbitron font-extrabold']"
    @click="handleClick"
  >
    <!-- Cercle avec la lettre -->
    <span
      :class="[isClicked ? 'bg-violet-clair text-faux-blanc' : 'bg-violet-ple text-violet-fonc', 'shadow-xl rounded-full flex items-center justify-center text-xl font-bold']"
      class="w-10 h-fit aspect-square"
    >
      {{ letter }}
    </span>

    <!-- Texte de la question -->
    <span 
      :class="[isClicked ? 'text-violet-fonc':' text-faux-blanc', ' text-left font-medium ml-4']">
      {{ question }}
    </span>
  </button>
</template>
