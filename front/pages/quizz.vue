<template>
  <div>
    <!-- Affichage du quiz si les données sont chargées et qu'il reste des questions -->
    <QuizzDisplay v-if="loaded && !quizEnded" :question="question" :reponses="reponses" :duration="duration"
      :type="type" @next="handleNext" />
    <!-- Affichage du message de fin du quiz -->
    <div v-else-if="quizEnded" class="flex items-center justify-center min-h-screen">
      <h2 class="text-2xl font-bold">Fin du quiz !</h2>
    </div>
    <!-- Indicateur de chargement -->
    <div v-else class="flex items-center justify-center min-h-screen">
      Chargement du quiz...
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getQuizzFromSession, getQuizz } from '@/services/quizzService.ts'  // Ajustez le chemin selon votre projet


// La prop quizzId permet d'identifier le quiz à charger
const props = defineProps({
  sessionCode: {
    type: String,
    required: true
  }
})

// Déclaration des données réactives
const question = ref('')
const reponses = ref([])
const duration = ref(0)
const type = ref('')
const loaded = ref(false)
const quizEnded = ref(false)
const currentIndex = ref(0)
const questionsList = ref([])

onMounted(async () => {
  try {
    // Appel à la fonction getQuizz en passant l'ID du quiz (converti en chaîne)

    const getQuizzId = await getQuizzFromSession("SESSION1ABC");
    const quizResponse = await getQuizz(getQuizzId)

    // Stocker l'ensemble des questions dans questionsList
    questionsList.value = quizResponse.quizz

    if (questionsList.value.length > 0) {
      updateQuestion(0)
      loaded.value = true
    }
  } catch (error) {
    console.error('Erreur lors de la récupération du quiz :', error)
  }
})

// Fonction pour mettre à jour la question affichée selon l'index
function updateQuestion(index) {
  const currentQuestion = questionsList.value[index]
  question.value = currentQuestion.question
  duration.value = currentQuestion.time
  type.value = currentQuestion.type

  // Transformation du tableau de réponses (tableau de string) en objets avec lettre et texte.
  const letters = ['A', 'B', 'C', 'D', 'E', 'F']
  reponses.value = currentQuestion.reponse.map((rep, index) => ({
    letter: letters[index] || '',
    text: rep.content,     // récupère le contenu de la réponse
    isCorrect: rep.isCorrect // récupère la valeur de isCorrect
  }))
}

// Méthode appelée lorsque le composant QuizzDisplay émet l'événement "next"
function handleNext() {
  if (currentIndex.value < questionsList.value.length - 1) {
    currentIndex.value++
    updateQuestion(currentIndex.value)
  } else {
    // Plus de question suivante, le quiz est terminé
    quizEnded.value = true
  }
}
</script>
