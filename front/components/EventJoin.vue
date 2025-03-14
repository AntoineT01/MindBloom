<template>
  <form @submit.prevent="handleJoinEvent" class="flex items-center justify-center gap-8 bg-violet-clair rounded-[90px] h-[62px] w-[756px] px-[30px] py-[10px] relative overflow-hidden">
    <p class="font-['Orbitron-Regular'] text-center text-xl m-0">
      <span class="text-faux-blanc">Rejoignez un </span>
      <span class="text-blanc">événement</span>
    </p>
    <div class="relative h-[45px] w-[274px] -my-[1.5px]">
      <div class="bg-faux-blanc rounded-[90px] h-[45px] relative w-[272px]">
        <input
            v-model="eventCode"
            type="text"
            placeholder="Code de l'événement"
            class="font-['Orbitron-Regular'] text-base h-[17px] left-[21px] absolute top-[14px] w-[230px] text-center placeholder-[#A089AD] text-violet-fonc bg-transparent border-none outline-none"
        />
      </div>
    </div>
    <Button
        property1="go"
        class="-my-[1.5px]"
        :disabled="loading || !eventCode.trim()"
        type="submit"
        @click="handleJoinEvent"
    >
      GO
    </Button>
  </form>

  <GlobalPopup
      :is-open="popupOpen"
      :message="popupMessage"
      :title="popupTitle"
      @close="handleClosePopup"
  />
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import Button from './BaseButton.vue'
import GlobalPopup from './GlobalPopup.vue'
import { usePopup } from '~/composables/usePopup'
import { joinQuizSession } from '~/services/quizSessionService'

const router = useRouter()
const eventCode = ref('')
const loading = ref(false)
const popup = usePopup()
const popupOpen = computed(() => popup.isOpen.value)
const popupMessage = computed(() => popup.message.value)
const popupTitle = computed(() => popup.title.value)

const emit = defineEmits(['join-event'])

const handleClosePopup = () => {
  popup.closePopup()
}

const handleJoinEvent = async () => {
  const code = eventCode.value.trim().toUpperCase() // Standardiser en majuscules
  if (!code) return

  loading.value = true

  try {
    console.log('Tentative de rejoindre la session avec le code:', code)
    // Informer le parent
    emit('join-event', code)

    // Vérifier l'existence de la session et créer un participant
    const sessionData = await joinQuizSession(code)
    console.log('Données de session reçues:', sessionData)

    if (sessionData && sessionData.id) {
      // Stocker les données de session et le participant dans le localStorage
      // pour les réutiliser dans la page de session
      localStorage.setItem('current_session', JSON.stringify({
        sessionId: sessionData.id,
        sessionCode: code,
        participantId: sessionData.participantId,
        quizId: sessionData.quizId,
        joinedAt: new Date().toISOString()
      }));

      // Si la session existe, rediriger vers la page de salle d'attente
      const targetPath = `/quiz-session/${code}`
      console.log('Redirection vers la salle d\'attente:', targetPath)

      // Utiliser le routeur pour naviguer
      router.push({
        path: targetPath
      })
    } else {
      throw new Error('Session introuvable ou invalide')
    }
  } catch (error) {
    console.error('Erreur lors de la tentative de rejoindre la session:', error)

    // Afficher un message d'erreur
    popup.showPopup(
        error instanceof Error
            ? error.message
            : "Session introuvable ou inaccessible",
        "Erreur"
    )
  } finally {
    loading.value = false
  }
}
</script>