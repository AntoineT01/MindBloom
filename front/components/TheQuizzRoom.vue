<template>
    <div>
      <template v-if="!quizEnded">
        <QuizzDisplay
          :question="currentQuestion.question"
          :reponses="currentQuestion.reponses"
          :duration="currentQuestion.duration"
          :type="currentQuestion.type"
          @next="handleNext"
        />
      </template>
      <template v-else>
        <div class="flex flex-col items-center justify-center min-h-screen">
          <h2 class="text-2xl font-bold">Fin du Quiz !</h2>
        </div>
      </template>
    </div>
  </template>
  
  <script>
  import QuizzDisplay from './QuizzDisplay.vue'
  
  export default {
    name: 'QuizRoom',
    components: { QuizzDisplay },
    props: {
      questions: {
        type: Array,
        required: true
      }
    },
    data() {
      return {
        currentIndex: 0,
        quizEnded: false
      }
    },
    computed: {
      currentQuestion() {
        return this.questions[this.currentIndex]
      }
    },
    methods: {
      handleNext() {
        if (this.currentIndex < this.questions.length - 1) {
          this.currentIndex++
        } else {
          console.log('Quiz terminé !')
          this.quizEnded = true
        }
      }
    }
  }
  </script>
  