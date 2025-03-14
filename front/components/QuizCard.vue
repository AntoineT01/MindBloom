<!-- File: components/QuizCard.vue -->
<template>
  <div class="bg-white shadow-lg rounded-lg p-6 border border-gray-200">
    <div class="flex justify-between items-center">
      <div>
        <h3 class="text-xl font-bold text-gray-800">{{ quiz.title }}</h3>
        <p class="mt-2 text-gray-600 text-sm">{{ quiz.description }}</p>
        <div class="mt-3 text-sm">
          <p><span class="font-semibold">Share Code:</span> {{ quiz.shareCode }}</p>
          <p><span class="font-semibold">Time Limit:</span> {{ quiz.timeLimit }} s</p>
          <!-- Affiche le statut de la session uniquement si le quiz est actif -->
          <p v-if="quiz.status === 'ACTIVE'">
            <span class="font-semibold">Session:</span>
            <span :class="quiz.activeSession ? 'text-green-600' : 'text-red-600'">
      {{ quiz.activeSession ? ' Active' : ' Inactive' }}
    </span>
          </p>
          <div v-if="quiz.activeSession && quiz.status != 'DELETED'" class="mt-2">
            <p><span class="font-semibold">Session Code:</span> {{ quiz.activeSession.sessionCode }}</p>
          </div>
        </div>
      </div>
      <span class="px-3 py-1 rounded-full text-xs font-medium"
            :class="{
              'bg-green-100 text-green-800': quiz.status === 'ACTIVE',
              'bg-red-100 text-red-800': quiz.status === 'INACTIVE',
              'bg-gray-100 text-gray-800': quiz.status === 'DELETED'
            }">
        {{ quiz.status }}
      </span>
    </div>
    <div class="mt-6 flex justify-end space-x-4">
      <template v-if="quiz.status === 'ACTIVE'">
        <button
            v-if="!quiz.activeSession"
            @click="start"
            class="px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded text-sm transition transform hover:scale-105 flex items-center justify-center">
          <i class="fas fa-play w-5 text-center"></i>
          <span>Démarrer</span>
        </button>
        <button
            v-else
            @click="stop"
            class="px-4 py-2 bg-red-600 hover:bg-red-700 text-white rounded text-sm transition transform hover:scale-105 flex items-center justify-center">
          <i class="fas fa-stop w-5 text-center"></i>
          <span>Arrêter</span>
        </button>
        <button
            @click="deleteQuiz"
            class="px-4 py-2 bg-gray-600 hover:bg-gray-700 text-white rounded text-sm transition transform hover:scale-105 flex items-center justify-center">
          <i class="fas fa-trash w-5 text-center"></i>
          <span>Supprimer</span>
        </button>
      </template>
      <template v-else-if="quiz.status === 'INACTIVE'">
        <button
            @click="reactivate"
            class="px-4 py-2 bg-green-600 hover:bg-green-700 text-white rounded text-sm transition transform hover:scale-105 flex items-center justify-center">
          <i class="fas fa-redo w-5 text-center"></i>
          <span>Réactiver</span>
        </button>
        <button
            @click="deleteQuiz"
            class="px-4 py-2 bg-gray-600 hover:bg-gray-700 text-white rounded text-sm transition transform hover:scale-105 flex items-center justify-center">
          <i class="fas fa-trash w-5 text-center"></i>
          <span>Supprimer</span>
        </button>
      </template>
      <template v-else>
        <button disabled
                class="px-4 py-2 bg-gray-400 text-white rounded text-sm flex items-center justify-center cursor-not-allowed">
          <span>Supprimé</span>
        </button>
      </template>
    </div>
  </div>
</template>
<script setup lang="ts">
import {defineProps, defineEmits} from 'vue'

const props = defineProps<{
  quiz: {
    id: number,
    title: string,
    description: string,
    status: string,
    shareCode: string,
    timeLimit: number,
    activeSession?: { id: number, sessionCode: string }
  }
}>()
const emit = defineEmits<{
  (e: 'startSession', quiz: any): void,
  (e: 'stopSession', quiz: any): void,
  (e: 'reactivateQuiz', quiz: any): void,
  (e: 'deleteQuiz', quiz: any): void
}>()
const start = () => {
  emit('startSession', props.quiz)
}
const stop = () => {
  emit('stopSession', props.quiz)
}
const reactivate = () => {
  emit('reactivateQuiz', props.quiz)
}
const deleteQuiz = () => {
  emit('deleteQuiz', props.quiz)
}
</script>
