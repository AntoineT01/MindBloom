<template>
  <div class="container mx-auto px-4 py-8">
    <div class="flex justify-center m-8">
      <RejoindreEvenement @join-event="handleJoinEvent" />
    </div>

    <GlobalPopup
        :is-open="popupOpen"
        :message="popupMessage"
        :title="popupTitle"
        @close="handleClosePopup"
    />
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import RejoindreEvenement from '~/components/EventJoin.vue'
import GlobalPopup from '~/components/GlobalPopup.vue'
import { usePopup } from '~/composables/usePopup'
import { joinQuizSession } from '~/services/quizSessionService'

const router = useRouter()
const popup = usePopup()
const loading = ref(false)

const popupOpen = computed(() => popup.isOpen.value)
const popupMessage = computed(() => popup.message.value)
const popupTitle = computed(() => popup.title.value)

const handleClosePopup = () => {
  popup.closePopup()
}

const handleJoinEvent = async (code) => {
  console.log('Événement join-event reçu dans le parent avec le code:', code)

  if (!code || code.trim() === '') {
    console.error('Code de session vide reçu')
    popup.showPopup('Veuillez entrer un code de session valide', 'Erreur')
    return
  }

  loading.value = true

  try {
    console.log('Tentative de rejoindre la session avec le code:', code)

    // Vérification de l'existence de la session et création du participant
    const sessionData = await joinQuizSession(code)
    console.log('Réponse du service:', sessionData)

    if (!sessionData || !sessionData.id) {
      throw new Error('Session introuvable ou données invalides')
    }

    // Stocker les données de session dans le localStorage
    localStorage.setItem('current_session', JSON.stringify({
      sessionId: sessionData.id,
      sessionCode: code,
      participantId: sessionData.participantId,
      quizId: sessionData.quizId,
      joinedAt: new Date().toISOString()
    }));

    // Redirection vers la page de salle d'attente
    console.log('Redirection vers /quiz-session/' + code)
    router.push(`/quiz-session/${code}`)
  } catch (error) {
    console.error('Erreur lors de la recherche de session:', error)

    popup.showPopup(
        error instanceof Error ? error.message : "Session introuvable",
        "Erreur"
    )
  } finally {
    loading.value = false
  }
}
</script>