<template>
  <div class="container mx-auto px-4 py-8">
    <div class="flex justify-center m-8">
      <RejoindreEvenement @join-event="handleJoinEvent" />
    </div>

    <!-- Popup pour les messages d'erreur -->
    <GlobalPopup
        :is-open="popupOpen"
        :message="popupMessage"
        :title="popupTitle"
        @close="handleClosePopup"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import RejoindreEvenement from '~/components/EventJoin.vue'
import GlobalPopup from '~/components/GlobalPopup.vue'
import { usePopup } from '~/composables/usePopup'
import { joinQuizSession } from '~/services/quizSessionService'

const popup = usePopup()
const loading = ref(false)

const popupOpen = computed(() => popup.isOpen.value)
const popupMessage = computed(() => popup.message.value)
const popupTitle = computed(() => popup.title.value)

const handleClosePopup = () => {
  popup.closePopup()
}

const handleJoinEvent = async (code: string) => {
  console.log('Événement join-event reçu dans le parent avec le code:', code)

  if (!code || code.trim() === '') {
    console.error('Code de session vide reçu')
    popup.showPopup('Veuillez entrer un code de session valide', 'Erreur')
    return
  }

  loading.value = true

  try {
    console.log('Tentative de rejoindre la session avec le code:', code)

    // Appel au service pour vérifier si la session existe
    const response = await joinQuizSession(code)
    console.log('Réponse du service:', response)

    // Redirection vers la page de session
    console.log('Redirection vers /quizzsession/' + code)
    navigateTo(`/quizzsession/${code}`)
  } catch (error) {
    console.error('Erreur lors de la recherche de session:', error)

    // Affiche un message d'erreur
    popup.showPopup(
        error instanceof Error ? error.message : "Session introuvable",
        "Erreur"
    )
  } finally {
    loading.value = false
  }
}
</script>