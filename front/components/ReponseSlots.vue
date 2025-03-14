<script setup lang="ts">
import { ref, watch } from 'vue';

// Définir les props, y compris la nouvelle prop resetSignal
const props = defineProps({
  letter: {
    type: String,
    required: true,
  },
  question: {
    type: String,
    required: true,
  },
  disabled: {
    type: Boolean,
    default: false,
  },
  resetSignal: {
    type: Number,
    default: 0,
  },
});

// Définir l'emit pour notifier le parent
const emit = defineEmits(['selected']);

// État pour suivre si le bouton est cliqué ou non
const isClicked = ref(false);

// Fonction pour gérer le clic
function handleClick() {
  if (props.disabled) return;
  isClicked.value = !isClicked.value;
  console.log('Button clicked! State:', isClicked.value);
  // Émettre l'événement "selected" avec les informations nécessaires
  emit('selected', {
    id: props.letter, // ou un autre identifiant unique si disponible
    text: props.question,
  });
}

// Watcher sur resetSignal pour réinitialiser l'état du bouton
watch(
  () => props.resetSignal,
  (newVal, oldVal) => {
    if (newVal !== oldVal) {
      isClicked.value = false;
      console.log('Reset signal received: isClicked reset to false');
    }
  }
);
</script>

<template>
  <button 
    type="button"
    :disabled="disabled"
    :class="[ 
      disabled 
        ? 'opacity-50 cursor-not-allowed'
        : (isClicked ? 'bg-violet-ple' : 'bg-violet-clair'),
      'inline-flex items-center w-full rounded-3xl min-h-14 max-h-44 px-4 py-2 cursor-pointer font-orbitron font-extrabold'
    ]"
    @click="handleClick"
  >
    <!-- Cercle avec la lettre -->
    <span
      :class="[ 
        disabled 
          ? 'bg-gray-300 text-gray-600'
          : (isClicked ? 'bg-violet-clair text-faux-blanc' : 'bg-violet-ple text-violet-fonc'),
        'shadow-xl rounded-full flex items-center justify-center text-xl font-bold flex-shrink-0 text-center leading-none'
      ]"
      class="w-14 h-14"
    >
      {{ letter }}
    </span>

    <!-- Texte de la question -->
    <span 
      :class="[ 
        disabled 
          ? 'text-gray-600'
          : (isClicked ? 'text-violet-fonc' : 'text-faux-blanc'),
        'text-left font-medium ml-4'
      ]">
      {{ question }}
    </span>
  </button>
</template>
