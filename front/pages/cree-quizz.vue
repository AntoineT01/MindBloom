<!-- File: pages/cree-quizz.vue -->
<template>
  <div class="container mx-auto px-4 py-8">
    <div class="max-w-3xl mx-auto bg-white shadow-lg rounded-lg p-6">
      <h1 class="text-3xl font-bold text-gray-800 text-center mb-6">Créer un Quiz</h1>
      <form @submit.prevent="handleCreateQuiz" class="space-y-6">
        <ChampText v-model="quizName" :withIcon="false" :withButton="false" inputType="text" placeholder="Nom du Quiz" :error="formErrors.quizName" class="w-full"/>
        <div>
          <label class="block text-gray-600 mb-2">Description</label>
          <textarea v-model="quizDescription" class="w-full border border-gray-300 rounded px-3 py-2" placeholder="Décrivez votre Quiz..."></textarea>
        </div>
        <div v-for="(question, index) in questions" :key="index" class="border rounded p-4 space-y-4">
          <ChampText v-model="question.questionText" :withIcon="false" :withButton="false" inputType="text" placeholder="Intitulé de la question" :error="formErrors[`questionText_${index}`]" class="w-full"/>
          <div>
            <label class="block text-gray-600 mb-2">Type de question</label>
            <select v-model="question.questionType" class="w-full border border-gray-300 rounded px-3 py-2">
              <option value="text">Texte (choix multiple)</option>
              <option value="image">Image (choix multiple)</option>
              <option value="video">Vidéo (choix multiple)</option>
              <option value="open">Texte (réponse ouverte)</option>
            </select>
          </div>
          <div v-if="question.questionType === 'image' || question.questionType === 'video'">
            <label class="block text-gray-600 mb-2">Importer un fichier</label>
            <input type="file" :accept="question.questionType === 'image' ? 'image/png, image/jpeg' : 'video/mp4'" @change="(e) => handleFileUpload(e, index)" class="w-full"/>
            <div v-if="question.mediaFile" class="text-sm text-gray-500 mt-2">
              Fichier sélectionné : {{ question.mediaFile.name }}
            </div>
          </div>
          <template v-if="question.questionType !== 'open'">
            <div>
              <span class="block text-gray-600 mb-2">Réponses valides</span>
              <div v-for="(valid, i) in question.validAnswers" :key="i" class="flex items-center space-x-2 mb-2">
                <ChampText v-model="question.validAnswers[i]" :withIcon="false" :withButton="false" inputType="text" placeholder="Réponse valide..." class="flex-1"/>
                <Button property1="link-1" type="button" @click="removeValidAnswer(index, i)" class="text-sm px-3 py-1">Suppr</Button>
              </div>
              <Button property1="link-1" type="button" @click="addValidAnswer(index)" class="text-sm px-3 py-1">+ Réponse valide</Button>
            </div>
            <div>
              <span class="block text-gray-600 mb-2">Réponses invalides</span>
              <div v-for="(invalid, i) in question.invalidAnswers" :key="i" class="flex items-center space-x-2 mb-2">
                <ChampText v-model="question.invalidAnswers[i]" :withIcon="false" :withButton="false" inputType="text" placeholder="Réponse invalide..." class="flex-1"/>
                <Button property1="link-1" type="button" @click="removeInvalidAnswer(index, i)" class="text-sm px-3 py-1">Suppr</Button>
              </div>
              <Button property1="link-1" type="button" @click="addInvalidAnswer(index)" class="text-sm px-3 py-1">+ Réponse invalide</Button>
            </div>
          </template>
          <div class="flex space-x-4">
            <label class="flex-1">
              <span class="block text-gray-600 mb-2">Points</span>
              <ChampText :modelValue="question.points.toString()" @update:modelValue="val => question.points = Number(val)" :withIcon="false" :withButton="false" inputType="number" placeholder="Points" class="w-full"/>
            </label>
            <label class="flex-1">
              <span class="block text-gray-600 mb-2">Temps (secondes)</span>
              <ChampText :modelValue="question.time.toString()" @update:modelValue="val => question.time = Number(val)" :withIcon="false" :withButton="false" inputType="number" placeholder="Temps (s)" class="w-full"/>
            </label>
          </div>
          <div class="flex justify-end">
            <Button property1="link-1" type="button" @click="removeQuestion(index)" class="text-sm px-3 py-1">Supprimer cette question</Button>
          </div>
        </div>
        <Button property1="link-1" type="button" @click="addQuestion" class="text-sm px-3 py-1">+ Ajouter une question</Button>
        <div class="flex justify-center mt-6">
          <Button property1="validation" :disabled="loading" type="submit" @click="handleCreateQuiz" class="text-sm px-4 py-2">
            {{ loading ? 'Création en cours...' : 'Enregistrer le Quiz' }}
          </Button>
        </div>
      </form>
      <GlobalPopup :is-open="popupOpen" :message="popupMessage" :title="popupTitle" @close="handleClosePopup"/>
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
interface LocalQuestion {
  questionText: string
  questionType: 'text' | 'image' | 'video' | 'open'
  mediaFile: File | null
  validAnswers: string[]
  invalidAnswers: string[]
  points: number
  time: number
}
const quizName = ref('')
const quizDescription = ref('')
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
const popup = usePopup()
const popupOpen = computed(() => popup.isOpen.value)
const popupMessage = computed(() => popup.message.value)
const popupTitle = computed(() => popup.title.value)
const handleClosePopup = () => { popup.closePopup() }
function handleFileUpload(event: Event, questionIndex: number) {
  const input = event.target as HTMLInputElement
  const file = input.files?.[0]
  if (file) { questions.value[questionIndex].mediaFile = file }
}
const addQuestion = () => {
  questions.value.push({
    questionText: '',
    questionType: 'text',
    mediaFile: null,
    validAnswers: [''],
    invalidAnswers: [''],
    points: 10,
    time: 60
  })
}
const removeQuestion = (index: number) => { questions.value.splice(index, 1) }
const addValidAnswer = (questionIndex: number) => { questions.value[questionIndex].validAnswers.push('') }
const removeValidAnswer = (questionIndex: number, answerIndex: number) => { questions.value[questionIndex].validAnswers.splice(answerIndex, 1) }
const addInvalidAnswer = (questionIndex: number) => { questions.value[questionIndex].invalidAnswers.push('') }
const removeInvalidAnswer = (questionIndex: number, answerIndex: number) => { questions.value[questionIndex].invalidAnswers.splice(answerIndex, 1) }
const validateForm = () => {
  let isValid = true
  if (!quizName.value) { formErrors.quizName = 'Le nom du Quiz est requis'; isValid = false }
  else { formErrors.quizName = '' }
  return isValid
}
const handleCreateQuiz = async () => {
  if (!validateForm()) { return }
  try {
    loading.value = true;
    const currentUser = getUserData();
    const creator = currentUser ? { id: currentUser.id } : { id: 0 };
    const quizObj = {
      title: quizName.value,
      description: quizDescription.value,
      isPublic: true,
      showAnswers: true,
      showFinalScore: true,
      timeLimit: 30,
      status: 'ACTIVE',
      shareCode: '',
      creator: creator
    };
    const createdQuiz = await createQuiz(quizObj);
    for (let i = 0; i < questions.value.length; i++) {
      const q = questions.value[i];
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
      };
      const createdQuestion = await createQuestion(questionObj);
      if (q.questionType !== 'open') {
        for (let j = 0; j < q.validAnswers.length; j++) {
          const answerContent = q.validAnswers[j].trim();
          if (answerContent !== '') {
            const answerObj = {
              questionId: createdQuestion.id,
              content: answerContent,
              type: createdQuestion.type,
              isCorrect: true,
              answerOrder: j + 1,
              createdAt: new Date().toISOString(),
              updatedAt: new Date().toISOString()
            };
            await createAnswer(answerObj);
          }
        }
        for (let j = 0; j < q.invalidAnswers.length; j++) {
          const answerContent = q.invalidAnswers[j].trim();
          if (answerContent !== '') {
            const answerObj = {
              questionId: createdQuestion.id,
              content: answerContent,
              type: createdQuestion.type,
              isCorrect: false,
              answerOrder: j + 1,
              createdAt: new Date().toISOString(),
              updatedAt: new Date().toISOString()
            };
            await createAnswer(answerObj);
          }
        }
      }
      if (q.mediaFile) {
        const mediaObj = {
          questionId: createdQuestion.id,
          type: q.questionType.toUpperCase(),
          url: q.mediaFile.name
        };
        await createMedia(mediaObj);
      }
    }
    popup.showPopup('Quiz créé avec succès!', 'Succès');
    quizName.value = '';
    quizDescription.value = '';
    questions.value = [{
      questionText: '',
      questionType: 'text',
      mediaFile: null,
      validAnswers: [''],
      invalidAnswers: [''],
      points: 10,
      time: 60
    }];
  } catch (error) {
    popup.showPopup(error instanceof Error ? error.message : "Une erreur est survenue", "Erreur");
  } finally {
    loading.value = false;
  }
};
</script>
