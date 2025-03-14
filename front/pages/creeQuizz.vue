<template>
  <div class="container mx-auto px-4">
    <div class="flex flex-col items-center justify-center min-h-[80vh] max-w-3xl mx-auto space-y-8">
      <!-- Formulaire de création de Quiz -->
      <form @submit.prevent="handleCreateQuiz" class="w-full flex flex-col space-y-6">
        <!-- Nom du Quizz -->
        <ChampText
            v-model="quizName"
            :withIcon="false"
            :withButton="false"
            inputType="text"
            placeholder="Nom du Quizz"
            :error="formErrors.quizName"
            class="w-full"
        />

        <!-- Description du Quizz -->
        <label class="block">
          <span class="text-gray-600 mb-2">Description</span>
          <textarea
              v-model="quizDescription"
              class="w-full border border-gray-300 rounded px-3 py-2"
              placeholder="Décrivez votre Quizz..."
          ></textarea>
        </label>

        <!-- Liste des questions -->
        <div
            v-for="(question, index) in questions"
            :key="index"
            class="border rounded p-4 space-y-4 mb-4"
        >
          <!-- Texte de la question -->
          <ChampText
              v-model="question.questionText"
              :withIcon="false"
              :withButton="false"
              inputType="text"
              placeholder="Intitulé de la question"
              :error="formErrors[`questionText_${index}`]"
              class="w-full"
          />

          <!-- Type de la question -->
          <label class="block">
            <span class="text-gray-600 mb-2">Type de question</span>
            <select
                v-model="question.questionType"
                class="w-full border border-gray-300 rounded px-3 py-2"
            >
              <option value="text">Texte (choix multiple)</option>
              <option value="image">Image (choix multiple)</option>
              <option value="video">Vidéo (choix multiple)</option>
              <option value="open">Texte (réponse ouverte)</option>
            </select>
          </label>

          <!-- Import de fichier si questionType = image ou video -->
          <div v-if="question.questionType === 'image' || question.questionType === 'video'">
            <label class="block">
              <span class="text-gray-600 mb-2">Importer un fichier</span>
              <input
                  type="file"
                  :accept="question.questionType === 'image' ? 'image/png, image/jpeg' : 'video/mp4'"
                  @change="(e) => handleFileUpload(e, index)"
              />
            </label>
            <div v-if="question.mediaFile" class="text-sm text-gray-500 mt-2">
              Fichier sélectionné : {{ question.mediaFile.name }}
            </div>
          </div>

          <!-- Si la question est de type 'open', afficher un champ pour la réponse suggérée -->
          <template v-if="question.questionType === 'open'">
            <div>
              <span class="text-gray-600 mb-2 block">Réponse suggérée</span>
              <ChampText
                  v-model="question.suggestedAnswer"
                  :withIcon="false"
                  :withButton="false"
                  inputType="text"
                  placeholder="Votre réponse..."
                  class="w-full"
              />
            </div>
          </template>

          <!-- Sinon, afficher les champs pour les réponses valides et invalides -->
          <template v-else>
            <!-- Réponses valides -->
            <div>
              <span class="text-gray-600 mb-2 block">Réponses valides</span>
              <div
                  v-for="(valid, i) in question.validAnswers"
                  :key="i"
                  class="flex items-center space-x-2 mb-2"
              >
                <ChampText
                    v-model="question.validAnswers[i]"
                    :withIcon="false"
                    :withButton="false"
                    inputType="text"
                    placeholder="Réponse valide..."
                    class="flex-1"
                />
                <Button property1="link-1" type="button" @click="removeValidAnswer(index, i)">
                  Suppr
                </Button>
              </div>
              <Button property1="link-1" type="button" @click="addValidAnswer(index)">
                + Réponse valide
              </Button>
            </div>

            <!-- Réponses invalides -->
            <div>
              <span class="text-gray-600 mb-2 block">Réponses invalides</span>
              <div
                  v-for="(invalid, i) in question.invalidAnswers"
                  :key="i"
                  class="flex items-center space-x-2 mb-2"
              >
                <ChampText
                    v-model="question.invalidAnswers[i]"
                    :withIcon="false"
                    :withButton="false"
                    inputType="text"
                    placeholder="Réponse invalide..."
                    class="flex-1"
                />
                <Button property1="link-1" type="button" @click="removeInvalidAnswer(index, i)">
                  Suppr
                </Button>
              </div>
              <Button property1="link-1" type="button" @click="addInvalidAnswer(index)">
                + Réponse invalide
              </Button>
            </div>
          </template>

          <!-- Points et temps de réponse -->
          <div class="flex space-x-4">
            <label class="flex-1">
              <span class="text-gray-600 mb-2 block">Points</span>
              <ChampText
                  :modelValue="question.points.toString()"
                  @update:modelValue="val => question.points = Number(val)"
                  :withIcon="false"
                  :withButton="false"
                  inputType="number"
                  placeholder="Points"
                  class="w-full"
              />
            </label>
            <label class="flex-1">
              <span class="text-gray-600 mb-2 block">Temps (secondes)</span>
              <ChampText
                  :modelValue="question.time.toString()"
                  @update:modelValue="val => question.time = Number(val)"
                  :withIcon="false"
                  :withButton="false"
                  inputType="number"
                  placeholder="Temps (s)"
                  class="w-full"
              />
            </label>
          </div>

          <!-- Bouton pour supprimer la question courante -->
          <div class="flex justify-end">
            <Button property1="link-1" type="button" @click="removeQuestion(index)">
              Supprimer cette question
            </Button>
          </div>
        </div>

        <!-- Bouton pour ajouter une nouvelle question -->
        <Button property1="link-1" type="button" @click="addQuestion">
          + Ajouter une question
        </Button>

        <!-- Bouton pour enregistrer le quiz -->
        <div class="flex justify-center">
          <Button property1="validation" :disabled="loading" type="submit" @click="handleCreateQuiz">
            {{ loading ? 'Création en cours...' : 'Enregistrer le Quizz' }}
          </Button>
        </div>
      </form>

      <!-- Popup global pour les messages -->
      <GlobalPopup
          :is-open="popupOpen"
          :message="popupMessage"
          :title="popupTitle"
          @close="handleClosePopup"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
defineOptions({ name: "createQuiz" })

import { ref, reactive, computed } from 'vue'
import ChampText from '~/components/BaseTextField.vue'
import Button from '~/components/BaseButton.vue'
import GlobalPopup from '~/components/GlobalPopup.vue'
import { usePopup } from '~/composables/usePopup'
import { createQuestion, createAnswer, createMedia } from '~/services/questionService'
import { getUserData } from '~/services/authService'
import { createQuiz } from '~/services/quizzService'

// Interface pour nos questions locales
interface LocalQuestion {
  questionText: string;
  questionType: 'text' | 'image' | 'video' | 'open';
  mediaFile: File | null;
  validAnswers: string[];
  invalidAnswers: string[];
  suggestedAnswer?: string; // Pour les questions ouvertes
  points: number;
  time: number;
}

// Champs du Quiz
const quizName = ref('')
const quizDescription = ref('')

// Tableau de questions
const questions = ref<LocalQuestion[]>([
  {
    questionText: '',
    questionType: 'text',
    mediaFile: null,
    validAnswers: [''],
    invalidAnswers: [''],
    points: 10,
    time: 60
  }
])

const loading = ref(false)
const formErrors = reactive<Record<string, string>>({ quizName: '' })

// Popup global
const popup = usePopup()
const popupOpen = computed(() => popup.isOpen.value)
const popupMessage = computed(() => popup.message.value)
const popupTitle = computed(() => popup.title.value)

const handleClosePopup = () => {
  popup.closePopup()
}

function handleFileUpload(event: Event, questionIndex: number) {
  const input = event.target as HTMLInputElement
  const file = input.files?.[0]
  if (file) {
    questions.value[questionIndex].mediaFile = file
  }
}

const addQuestion = () => {
  questions.value.push({
    questionText: '',
    questionType: 'text',
    mediaFile: null,
    validAnswers: [''],
    invalidAnswers: [''],
    suggestedAnswer: '',
    points: 10,
    time: 60
  })
}

const removeQuestion = (index: number) => {
  questions.value.splice(index, 1)
}

const addValidAnswer = (questionIndex: number) => {
  questions.value[questionIndex].validAnswers.push('')
}
const removeValidAnswer = (questionIndex: number, answerIndex: number) => {
  questions.value[questionIndex].validAnswers.splice(answerIndex, 1)
}
const addInvalidAnswer = (questionIndex: number) => {
  questions.value[questionIndex].invalidAnswers.push('')
}
const removeInvalidAnswer = (questionIndex: number, answerIndex: number) => {
  questions.value[questionIndex].invalidAnswers.splice(answerIndex, 1)
}

const validateForm = () => {
  let isValid = true
  if (!quizName.value) {
    formErrors.quizName = 'Le nom du Quizz est requis'
    isValid = false
  } else {
    formErrors.quizName = ''
  }
  return isValid
}

const generateShareCode = (length: number = 6): string => {
  const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'
  let shareCode = ''
  for (let i = 0; i < length; i++) {
    shareCode += characters.charAt(Math.floor(Math.random() * characters.length))
  }
  return shareCode
}

const handleCreateQuiz = async () => {
  console.log('handleCreateQuiz appelé')
  if (!validateForm()) {
    console.error('Formulaire invalide')
    return
  }

  try {
    loading.value = true

    // Récupérer les données de l'utilisateur connecté
    const currentUser = getUserData()
    const creator = currentUser ? { id: currentUser.id } : { id: 0 }

    const quizObj = {
      title: quizName.value,
      description: quizDescription.value,
      isPublic: true,
      showAnswers: true,
      showFinalScore: true,
      timeLimit: 30,
      status: 'ACTIVE',
      shareCode: generateShareCode(),
      creator: creator
    }

    // Créer le quiz via l'API
    const createdQuiz = await createQuiz(quizObj)
    console.log('Quiz créé:', createdQuiz)

    // Création des questions, réponses et médias
    for (let i = 0; i < questions.value.length; i++) {
      const q = questions.value[i]
      const questionObj = {
        quizId: createdQuiz.id,
        content: q.questionText,
        type: q.questionType === 'open' ? 'open_answer' : 'multiple_choice',
        points: q.points,
        questionOrder: i + 1,
        isRequired: true,
        isActive: true,
        displayTime: q.time,
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString(),
        imported: false
      }

      const createdQuestion = await createQuestion(questionObj)
      console.log(`Question ${i + 1} créée:`, createdQuestion)

      if (q.questionType === 'open') {
        // Pour une question ouverte, créer une réponse à partir de suggestedAnswer
        const answerContent = q.suggestedAnswer?.trim()
        if (answerContent && answerContent !== '') {
          const answerObj = {
            questionId: createdQuestion.id,
            content: answerContent,
            type: createdQuestion.type, // Doit être 'open_answer'
            isCorrect: true,
            answerOrder: 1,
            createdAt: new Date().toISOString(),
            updatedAt: new Date().toISOString()
          }
          const createdAnswer = await createAnswer(answerObj)
          console.log(`Réponse ouverte pour question ${i + 1}:`, createdAnswer)
        }
      } else {
        // Pour les questions à choix multiples, créer les réponses valides et invalides
        for (let j = 0; j < q.validAnswers.length; j++) {
          const answerContent = q.validAnswers[j].trim()
          if (answerContent !== '') {
            const answerObj = {
              questionId: createdQuestion.id,
              content: answerContent,
              type: createdQuestion.type,
              isCorrect: true,
              answerOrder: j + 1,
              createdAt: new Date().toISOString(),
              updatedAt: new Date().toISOString()
            }
            const createdAnswer = await createAnswer(answerObj)
            console.log(`Answer valide ${j + 1} pour question ${i + 1}:`, createdAnswer)
          }
        }
        for (let j = 0; j < q.invalidAnswers.length; j++) {
          const answerContent = q.invalidAnswers[j].trim()
          if (answerContent !== '') {
            const answerObj = {
              questionId: createdQuestion.id,
              content: answerContent,
              type: createdQuestion.type,
              isCorrect: false,
              answerOrder: j + 1,
              createdAt: new Date().toISOString(),
              updatedAt: new Date().toISOString()
            }
            const createdAnswer = await createAnswer(answerObj)
            console.log(`Answer invalide ${j + 1} pour question ${i + 1}:`, createdAnswer)
          }
        }
      }

      // Création du média s'il y en a un
      if (q.mediaFile) {
        const mediaObj = {
          questionId: createdQuestion.id,
          type: q.questionType.toUpperCase(),
          url: q.mediaFile.name
        }
        const createdMedia = await createMedia(mediaObj)
        console.log(`Media pour question ${i + 1}:`, createdMedia)
      }
    }

    popup.showPopup('Quizz créé avec succès!', 'Succès')
    // Réinitialisation du formulaire
    quizName.value = ''
    quizDescription.value = ''
    questions.value = [
      {
        questionText: '',
        questionType: 'text',
        mediaFile: null,
        validAnswers: [''],
        invalidAnswers: [''],
        points: 10,
        time: 60
      }
    ]
  } catch (error) {
    console.error('Erreur lors de la création du quiz:', error)
    popup.showPopup(
        error instanceof Error ? error.message : "Une erreur est survenue",
        "Erreur"
    )
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
/* Personnalisez vos styles si nécessaire */
</style>
