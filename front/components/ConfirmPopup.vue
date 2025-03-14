<!-- File: components/ConfirmPopup.vue -->
<template>
  <Teleport to="body">
    <div v-if="isOpen" class="fixed inset-0 flex items-center justify-center z-50">
      <div class="fixed inset-0 bg-black opacity-50" @click="cancel"></div>
      <div class="bg-white rounded-lg shadow-lg p-6 z-10 max-w-sm mx-auto">
        <h2 class="text-xl font-bold mb-4">{{ title }}</h2>
        <p class="mb-6">{{ message }}</p>
        <div class="flex justify-end space-x-4">
          <button @click="cancel" class="px-4 py-2 rounded bg-gray-300 text-gray-800 hover:bg-gray-400 transition">Annuler</button>
          <button @click="confirm" class="px-4 py-2 rounded bg-blue-600 text-white hover:bg-blue-700 transition">Confirmer</button>
        </div>
      </div>
    </div>
  </Teleport>
</template>
<script setup lang="ts">
import { defineProps, defineEmits } from 'vue'
const props = defineProps({
  isOpen: { type: Boolean, required: true },
  title: { type: String, default: 'Confirmation' },
  message: { type: String, default: 'Êtes-vous sûr ?' }
})
const emit = defineEmits(['confirm', 'cancel'])
const confirm = () => { emit('confirm') }
const cancel = () => { emit('cancel') }
</script>
