<script setup lang="ts">
import { reactive } from 'vue';
import { ref } from 'vue';

// État pour suivre si le bouton est cliqué ou non
const isClicked = ref(false);

function handleClick() {
  isClicked.value = !isClicked.value;
  console.log('Button clicked! State:', isClicked.value);
}


// Objet réactif contenant les données de l'utilisateur
const slot = reactive({
    question: 'sqdfsdfsf sqdfqsdfsdfqsjdfkjqskdfjqsjkfkqsjfjqsdfqksjdfkqsjkdf',
    letter: 'A'
});


</script>

  

<template>
    <button 
      type="button"
      :class="[isClicked ? 'bg-purple-700' : 'bg-purple-500', 'inline-flex items-center rounded-3xl px-4 py-2 cursor-pointer transition-all duration-300 hover:bg-purple-600 focus:outline-none']"
      @click="handleClick"
    >
      <!-- Cercle avec la lettre -->
      <span 
        :class="[isClicked ? 'bg-white text-black' : 'bg-purple-700 text-white','rounded-full  w-10 h-10 flex items-center justify-center text-xl font-bold transition-all duration-300 hover:scale-105 active:scale-95']"
      >
        A
      </span>
      <!-- Texte de la question -->
      <span class="text-white font-medium ml-4">
        Lorem ipsum is simply dummy text of the printing and typesetting industry. Lorem ipsum has been the
      </span>
    </button>
  </template>
  