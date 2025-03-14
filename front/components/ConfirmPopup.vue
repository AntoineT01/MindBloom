<template>
  <transition name="fade">
    <div v-if="isOpen" class="fixed inset-0 flex items-center justify-center z-50">
      <div class="absolute inset-0 bg-gray-900 opacity-50"></div>
      <div class="bg-white p-6 rounded shadow-md z-10 w-96">
        <h2 class="text-xl font-bold mb-4">{{ title }}</h2>
        <p class="mb-6">{{ message }}</p>
        <div class="flex justify-end space-x-4">
          <!-- Bouton Annuler -->
          <button
              @click="$emit('cancel')"
              class="px-4 py-2 bg-gray-200 hover:bg-gray-300 rounded text-gray-700"
          >
            Annuler
          </button>

          <!-- Bouton Confirmer (avec loader) -->
          <button
              @click="$emit('confirm')"
              :disabled="loading"
              class="relative inline-flex items-center justify-center px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded"
          >
            <span v-if="!loading">Confirmer</span>
            <span v-else class="flex items-center gap-2">
              <!-- Petit spinner -->
              <svg class="animate-spin h-5 w-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                <circle
                    class="opacity-25"
                    cx="12"
                    cy="12"
                    r="10"
                    stroke="currentColor"
                    stroke-width="4"
                />
                <path
                    class="opacity-75"
                    fill="currentColor"
                    d="M4 12a8 8 0 018-8v8H4z"
                />
              </svg>
              <span>Chargement...</span>
            </span>
          </button>
        </div>
      </div>
    </div>
  </transition>
</template>

<script setup lang="ts">
import { defineProps } from 'vue'

const props = defineProps<{
  isOpen: boolean
  title: string
  message: string
  loading?: boolean
}>()
</script>
