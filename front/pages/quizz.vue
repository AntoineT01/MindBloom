<template>

    <div class="flex flex-col items-center w-full bg-gray-100 min-h-screen p-6">
      <!-- Zone de Question (3/5 de l'écran) -->
      <div class="w-3/4 flex flex-col items-center bg-white shadow-lg rounded-lg p-6 h-3/5">
        <div class="w-full flex justify-center">
          <div class="w-3/4">
            <slot name="video"></slot>
          </div>
        </div>
        <h2 class="text-center text-xl font-bold mt-4 text-purple-700">{{ question }}</h2>
      </div>
  
      <!-- Réponses (juste après la zone de question) -->
      <div class="w-3/4 grid grid-cols-2 gap-4 mt-6">
        <ReponseSlots v-for="(rep, index) in reponses" :key="index" :letter="rep.letter" :question="rep.text"/>
      </div>
      <!-- Validation et Timer en bas de la page -->
      <div class="w-full fixed bottom-10 flex flex-col items-center">
        <BaseButton />

        <div class="mt-4 w-3/4">
        <TheTimer :duration="duration"/>
      </div>
        </div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      question: {
        type: String,
        required: true
      },
      reponses: {
        type: Array,
        required: true
      },
      duration: {
        type: Number,
        required: true
      }
    }
  };
  </script>
  
  <style scoped>
  body {
    background-color: #f4f4f9;
  }
  </style>