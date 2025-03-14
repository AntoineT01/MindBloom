<template>
  <div>
    <!-- Affichage du quiz en cours -->
    <QuizzDisplay
        v-if="loaded && !quizEnded"
        :question="question"
        :reponses="reponses"
        :duration="duration"
        :type="type"
        @next="handleNext"
    />

    <!-- Affichage de fin du quiz -->
    <div v-else-if="quizEnded" class="flex flex-col items-center justify-center min-h-screen">
      <h2 class="text-2xl font-bold mb-4">Fin du quiz !</h2>
      <BaseButton property1="link-1" @click="navigateToResults" class="mt-6">
        Voir les résultats
      </BaseButton>
    </div>

    <!-- Affichage pendant le chargement -->
    <div v-else class="flex items-center justify-center min-h-screen">
      <p class="text-xl">Chargement du quiz...</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getQuizzFromSession, getQuizz } from '@/services/quizzService'
import BaseButton from '~/components/BaseButton.vue'

const route = useRoute()
const router = useRouter()

// Récupération du code de session depuis les paramètres de l'URL
const sessionCode = ref(route.query.sessionCode || '')

const question = ref('')
const reponses = ref([])
const duration = ref(0)
const type = ref('')
const loaded = ref(false)
const quizEnded = ref(false)
const currentIndex = ref(0)
const questionsList = ref([])

onMounted(async () => {
  if (!sessionCode.value) {
    console.error('Aucun code de session fourni')
    return
  }

  try {
    console.log('Récupération du quiz pour la session:', sessionCode.value)

    // Récupération de l'ID du quiz associé à cette session
    const quizId = await getQuizzFromSession(sessionCode.value)
    console.log('ID du quiz récupéré:', quizId)

    // Récupération des questions du quiz
    const quizResponse = await getQuizz(quizId)
    console.log('Questions récupérées:', quizResponse)

    questionsList.value = quizResponse.quizz

    if (questionsList.value.length > 0) {
      updateQuestion(0)
      loaded.value = true
    } else {
      console.error('Aucune question trouvée pour ce quiz')
    }
  } catch (error) {
    console.error('Erreur lors de la récupération du quiz :', error)
  }
})

function updateQuestion(index) {
  const currentQuestion = questionsList.value[index]

  if (!currentQuestion) {
    console.error('Question non trouvée à l\'index:', index)
    return
  }

  question.value = currentQuestion.question
  duration.value = currentQuestion.time
  type.value = currentQuestion.type

  // Préparation des réponses pour l'affichage
  const letters = ['A', 'B', 'C', 'D', 'E', 'F']
  reponses.value = currentQuestion.reponse.map((rep, index) => ({
    id: rep.id,
    letter: letters[index] || '',
    text: rep.content,
    isCorrect: rep.isCorrect
  }))
}

function handleNext() {
  if (currentIndex.value < questionsList.value.length - 1) {
    currentIndex.value++
    updateQuestion(currentIndex.value)
  } else {
    console.log('Quiz terminé !')
    quizEnded.value = true
  }
}

function navigateToResults() {
  // Redirection vers la page des résultats
  router.push('/result-page')
}
</script>