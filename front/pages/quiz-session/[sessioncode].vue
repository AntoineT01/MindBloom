<!-- File: pages/quiz-session/[sessionCode].vue -->
<template>
  <div class="container mx-auto px-4 py-8">
    <div v-if="loading" class="text-center">Chargement de la session…</div>
    <div v-else>
      <div v-if="!sessionStarted">
        <h2 class="text-2xl font-bold mb-4">Salle d'attente</h2>
        <p class="mb-2">Code de session : <strong>{{ sessionCode }}</strong></p>
        <div v-if="participants.length" class="mb-4">
          <h3 class="text-xl mb-2">Participants :</h3>
          <ul class="list-disc list-inside">
            <li v-for="participant in participants" :key="participant.id">
              {{ participant.name }}
            </li>
          </ul>
        </div>
        <div v-if="isHost" class="mt-6">
          <button @click="startSession" class="px-6 py-2 bg-violet-700 text-white rounded hover:bg-violet-800 transition">Démarrer le quiz</button>
        </div>
        <div v-else class="mt-6">
          <p>En attente du démarrage du quiz par l’hôte…</p>
        </div>
      </div>
      <div v-else class="text-center">
        <p>Le quiz a démarré ! Redirection…</p>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getQuizSession } from '~/services/quizSessionService'
const route = useRoute()
const router = useRouter()
const sessionCode = route.params.sessionCode as string
const loading = ref(true)
const sessionStarted = ref(false)
const isHost = ref(false)
const participants = ref<Array<{ id: number, name: string }>>([])
const fetchSession = async () => {
  const sessionData = await getQuizSession(sessionCode)
  if (sessionData.status === 'active') {
    sessionStarted.value = true
    setTimeout(() => {
      router.push({ path: '/quizz', query: { sessionCode } })
    }, 2000)
  } else {
    sessionStarted.value = false
    participants.value = sessionData.participants || []
    const userData = localStorage.getItem('user_data')
    if (userData) {
      const currentUser = JSON.parse(userData)
      isHost.value = sessionData.hostId === currentUser.id
    }
  }
  loading.value = false
}
onMounted(() => {
  fetchSession()
  setInterval(fetchSession, 5000)
})
const startSession = async () => {
  sessionStarted.value = true
  router.push({ path: '/quizz', query: { sessionCode } })
}
</script>
