<!-- File: pages/mes-quiz.vue -->
<template>
  <div class="container mx-auto px-4 py-8">
    <div class="flex flex-col items-center mb-8">
      <h1 class="text-3xl font-bold text-gray-800 mb-4">Mes Quiz</h1>
      <div class="flex space-x-4">
        <BaseButton property1="validation" @click="navigateToCreate" class="text-sm px-4 py-2 flex items-center">
          <i class="fas fa-plus mr-2"></i>Créer un quiz
        </BaseButton>
      </div>
    </div>
    <div v-if="loading" class="text-center text-lg font-medium">Chargement...</div>
    <div v-else class="grid grid-cols-1 gap-6 max-w-4xl mx-auto">
      <QuizCard
          v-for="quiz in mergedQuizzes"
          :key="quiz.id"
          :quiz="quiz"
          @startSession="onStartSession"
          @stopSession="onStopSession"
          @reactivateQuiz="onReactivateQuiz"
          @deleteQuiz="onDeleteQuiz"
      />
    </div>
    <ConfirmPopup
        v-if="showConfirmPopup"
        :isOpen="showConfirmPopup"
        :title="confirmTitle"
        :message="confirmMessage"
        :loading="confirmLoading"
    @confirm="handleConfirm"
    @cancel="handleCancel"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import QuizCard from '~/components/QuizCard.vue'
import BaseButton from '~/components/BaseButton.vue'
import ConfirmPopup from '~/components/ConfirmPopup.vue'
import { getUserData } from '~/services/authService'
import { getQuizzesByUser, getAllQuizSessions, startQuizSession, stopQuizSession, deleteQuiz, updateQuizStatus } from '~/services/quizSessionService'

const router = useRouter()
const loading = ref(true)
const confirmLoading = ref(false) // Variable pour le chargement sur le bouton de confirmation
const quizzes = ref<Array<any>>([])
const sessions = ref<Array<any>>([])

const mergedQuizzes = computed(() => {
  return quizzes.value.map(quiz => {
    const activeSession = sessions.value.find(s => s.quizId === quiz.id && s.status === 'active')
    return { ...quiz, activeSession }
  })
})

const showConfirmPopup = ref(false)
const confirmAction = ref<'start' | 'stop' | 'delete' | 'reactivate' | null>(null)
const selectedQuiz = ref<any>(null)
const confirmTitle = ref('')
const confirmMessage = ref('')

const fetchData = async () => {
  const user = getUserData()
  if (user && user.id) {
    quizzes.value = await getQuizzesByUser(user.id)
  }
  sessions.value = await getAllQuizSessions()
  loading.value = false
}
onMounted(() => { fetchData() })

const onStartSession = (quiz: any) => {
  if (quiz.status !== 'ACTIVE') return
  confirmAction.value = 'start'
  selectedQuiz.value = quiz
  confirmTitle.value = 'Démarrer la session'
  confirmMessage.value = 'Voulez-vous démarrer la session pour ce quiz ?'
  showConfirmPopup.value = true
}

const onStopSession = (quiz: any) => {
  if (!quiz.activeSession) return
  confirmAction.value = 'stop'
  selectedQuiz.value = quiz
  confirmTitle.value = 'Arrêter la session'
  confirmMessage.value = 'Voulez-vous arrêter la session pour ce quiz ?'
  showConfirmPopup.value = true
}

const onReactivateQuiz = (quiz: any) => {
  if (quiz.status !== 'INACTIVE') return
  confirmAction.value = 'reactivate'
  selectedQuiz.value = quiz
  confirmTitle.value = 'Réactiver le quiz'
  confirmMessage.value = 'Voulez-vous réactiver ce quiz ?'
  showConfirmPopup.value = true
}

const onDeleteQuiz = (quiz: any) => {
  confirmAction.value = 'delete'
  selectedQuiz.value = quiz
  confirmTitle.value = 'Supprimer le quiz'
  confirmMessage.value = 'Voulez-vous supprimer définitivement ce quiz ?'
  showConfirmPopup.value = true
}

const generateSessionCode = () => {
  return Math.random().toString(36).substring(2, 11).toUpperCase()
}

const handleConfirm = async () => {
  showConfirmPopup.value = false
  confirmLoading.value = true
  try {
    if (confirmAction.value === 'start' && selectedQuiz.value) {
      const payload = {
        quizId: selectedQuiz.value.id,
        sessionMode: "training",
        status: "active",
        startTime: new Date().toISOString(),
        endTime: new Date().toISOString(),
        sessionCode: generateSessionCode()
      }
      await startQuizSession(payload)
    }
    if (confirmAction.value === 'stop' && selectedQuiz.value && selectedQuiz.value.activeSession) {
      const payload = {
        id: selectedQuiz.value.activeSession.id,
        quizId: selectedQuiz.value.id,
        sessionMode: "training",
        status: "stopped",
        startTime: new Date().toISOString(),
        endTime: new Date().toISOString(),
        sessionCode: selectedQuiz.value.activeSession.sessionCode
      }
      await stopQuizSession(payload)
    }
    if (confirmAction.value === 'reactivate' && selectedQuiz.value) {
      const userData = getUserData();
      const payload = {
        id: selectedQuiz.value.id,
        title: selectedQuiz.value.title,
        description: selectedQuiz.value.description,
        creator: userData,
        isPublic: selectedQuiz.value.isPublic,
        showAnswers: selectedQuiz.value.showAnswers,
        showFinalScore: selectedQuiz.value.showFinalScore,
        timeLimit: selectedQuiz.value.timeLimit,
        createdAt: selectedQuiz.value.createdAt,
        updatedAt: new Date().toISOString(),
        status: "ACTIVE",
        shareCode: selectedQuiz.value.shareCode
      };
      await updateQuizStatus(payload);
    }
    if (confirmAction.value === 'delete' && selectedQuiz.value) {
      await deleteQuiz(selectedQuiz.value.id)
    }
  } catch (error) {
    console.error("Une erreur s'est produite :", error)
  } finally {
    confirmLoading.value = false
    confirmAction.value = null
    selectedQuiz.value = null
    await fetchData()
  }
}

const handleCancel = () => {
  showConfirmPopup.value = false
  confirmAction.value = null
  selectedQuiz.value = null
}

const navigateToCreate = () => {
  router.push({ path: '/cree-quizz' })
}
</script>
