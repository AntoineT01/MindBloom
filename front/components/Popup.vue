<template>
    <div class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-999"></div>
      <div class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center bg-blue-500">
        <div class="bg-white border-1 border-black rounded-lg shadow-lg text-right flex-column w-1/2">
          <div class="bg-white border-1 border-black rounded-lg p-4 shadow-lg w-full text-right flex flex-row h-[400px]">
            <div class = "w-1/2 h-[350px]">
              <img class = "text-center border-4 border-black rounded-lg w-full h-full" :src="src">
            </div>
            <div class = "w-1/2">
              <br>
              <br>
              <h2 class="text-4xl font-bold mb-4 text-center underline ml-12 text-black"> {{ title }} </h2>
              <br>
              <p class="mb-4 text-black text-left ml-12"> {{ description }} </p>
            </div>
            <div>
              <button @click="close" class=" px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600 text-white"> <p> X </p> </button>
            </div>
          </div>
          <div class="bg-white border-1 border-black rounded-lg p-4 shadow-lg w-full text-left text-black">
            <div v-for="(info, index) in infoComplementaire" :key="index">
              <p class="ml-8 font-bold underline">{{ info.titre }}</p>
              <p class="ml-8 mb-4">{{ info.contenu }}</p>
            </div>
          </div>
        </div>
      </div>
  </template>

  <script setup>

import { defineProps } from 'vue'

// Définition des props
defineProps({
  title: String,
  description: String,
  src: String,
  infoComplementaire: {
    type: Array,
    default: () => [],
  },
});
const emit = defineEmits()
// Fonction pour fermer le pop-up
const close = () => {
  emit('close') // Émet l'événement pour fermer la modal dans le parent
}
  </script>

<style scoped>
</style>
