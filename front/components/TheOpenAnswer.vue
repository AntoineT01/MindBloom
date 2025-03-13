<template>
  <div class="flex items-center bg-white shadow-lg rounded-full p-2 w-full max-w-md">
    <input 
      type="text" 
      placeholder="Entrez votre texte ici..." 
      class="flex-grow bg-transparent outline-none text-gray-600 px-4" 
      v-model="inputText"
    />
    <button 
      :class="buttonClass"
      @mousedown="isClicked = true"
      @mouseup="isClicked = false"
      @mouseleave="isClicked = false"
      @click="sendMessage"
    >
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" class="w-5 h-5">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10.5 21l8.5-8.5m0 0L10.5 4m8.5 8.5H3" />
      </svg>
    </button>
  </div>
</template>

<script>
import { ref, computed } from 'vue';

export default {
  name: 'TheOpenAnswer',
  emits: ['sendMessage'],
  setup(props, { emit }) {
    const inputText = ref('');
    const isClicked = ref(false);

    const buttonClass = computed(() => {
      return isClicked.value
        ? 'bg-purple-700 text-white rounded-full p-3 transition'
        : 'bg-purple-500 hover:bg-purple-600 text-white rounded-full p-3 transition';
    });

    const sendMessage = () => {
      if (inputText.value.trim() !== '') {
        console.log('Message envoyé:', inputText.value);
        // Émission de l'événement pour passer à la question suivante
        emit('sendMessage');
        inputText.value = '';
      }
    };

    return {
      inputText,
      sendMessage,
      isClicked,
      buttonClass
    };
  }
};
</script>

<style scoped>
button {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
