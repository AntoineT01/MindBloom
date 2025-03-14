<template>
  <div>
    <!-- Affichage du quiz en cours -->
    <QuizzDisplay
        v-if="loaded && !quizEnded"
        :question="question"
        :reponses="reponses"
        :duration="duration"
        :type="type"
        :session-id="sessionId"
        :participant-id="participantId"
        :question-id="getQuestionId()"
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
    <div v-else class="flex items-center justify-center min-h-screen p-8">
      <div class="inline-block animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-violet-600 mb-4"></div>
      <p class="text-xl ml-4">Chargement du quiz...</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getQuizzFromSession, getQuizz } from '@/services/quizzService'
import BaseButton from '~/components/BaseButton.vue'

const route = useRoute()
const router = useRouter()

// Récupération du code de session depuis les paramètres de l'URL
const sessionCode = ref(route.query.sessionCode || '')
const participantId = ref(parseInt(route.query.participantId) || null)

// Récupération des données de session stockées dans le localStorage
const storedSession = ref(
    process.client ? JSON.parse(localStorage.getItem('current_session') || 'null') : null
)

// Calcul des IDs de session et participant
const sessionId = computed(() => {
  const id = storedSession.value?.sessionId || 1
  return parseInt(id)
})

// Si le participantId n'est pas dans les params, essayez de le récupérer du localStorage
if (!participantId.value && storedSession.value?.participantId) {
  participantId.value = parseInt(storedSession.value.participantId)
}

const question = ref('')
const reponses = ref([])
const duration = ref(0)
const type = ref('')
const loaded = ref(false)
const quizEnded = ref(false)
const currentIndex = ref(0)
const questionsList = ref([])

// Fonction pour obtenir l'ID de la question actuelle
function getQuestionId() {
  if (!questionsList.value || questionsList.value.length === 0 || currentIndex.value >= questionsList.value.length) {
    console.warn('Aucune question disponible pour obtenir l\'ID')
    return 1 // Valeur par défaut
  }

  const currentQuestion = questionsList.value[currentIndex.value]
  console.log('Question actuelle pour ID:', currentQuestion)

  if (!currentQuestion) {
    console.warn('Question actuelle non définie')
    return 1
  }

  if (!currentQuestion.id) {
    console.warn('ID de question non défini, utilisation d\'une valeur par défaut')
    return 1
  }

  const id = parseInt(currentQuestion.id)
  console.log('Retour de l\'ID de question:', id)
  return id
}

onMounted(async () => {
  if (!sessionCode.value) {
    console.error('Aucun code de session fourni')
    return
  }

  try {
    console.log('Récupération du quiz pour la session:', sessionCode.value)
    console.log('Données participant - ID:', participantId.value)

    // Récupération de l'ID du quiz associé à cette session
    const quizId = await getQuizzFromSession(sessionCode.value)
    console.log('ID du quiz récupéré:', quizId)

    if (!quizId) {
      console.error('ID du quiz non trouvé pour la session:', sessionCode.value)
      return
    }

    // Récupération des questions du quiz
    const quizResponse = await getQuizz(quizId)
    console.log('Questions récupérées:', quizResponse)

    if (!quizResponse || !quizResponse.quizz) {
      console.error('Réponse de quiz invalide:', quizResponse)
      return
    }

    questionsList.value = quizResponse.quizz

    // Vérifier que les questions ont un ID
    if (questionsList.value.length > 0) {
      const firstQuestion = questionsList.value[0]
      console.log('Première question du quiz:', firstQuestion)

      if (!firstQuestion.id) {
        console.warn('La première question n\'a pas d\'ID défini')
        // Ajout manuel d'IDs si nécessaire
        questionsList.value = questionsList.value.map((q, index) => ({
          ...q,
          id: q.id || (index + 1)
        }))
      }

      // Vérifier à nouveau
      console.log('Liste de questions après vérification des IDs:',
          questionsList.value.map(q => ({ id: q.id, question: q.question }))
      )

      // Mise à jour de la première question
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
  reponses.value = currentQuestion.reponse.map((rep, idx) => ({
    id: rep.id,
    letter: letters[idx] || '',
    text: rep.content,
    isCorrect: rep.isCorrect,
    questionId: currentQuestion.id // Ajouter l'ID de question aux réponses
  }))

  console.log('Question mise à jour:', {
    question: question.value,
    type: type.value,
    duration: duration.value,
    questionId: currentQuestion.id
  })
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
  router.push({
    path: '/score',
    query: {
      sessionCode: sessionCode.value,
      participantId: participantId.value
    }
  })
}
</script>