<template>
  <div class="flex flex-col items-center w-full bg-gray-100 min-h-screen p-6">
    <!-- Zone de Question (3/5 de l'écran) -->
    <div class="w-3/5 flex flex-col items-center bg-white shadow-lg rounded-lg p-6 h-3/5">
      <div class="w-full flex justify-center">
        <div class="w-3/4 flex justify-center">
          <slot></slot>
        </div>
      </div>
      <h2 class="text-center text-xl font-bold mt-4 text-purple-700">{{ question }}</h2>
    </div>

    <!-- Réponses pour le type "multiple_choice" -->
    <div v-if="type === 'multiple_choice'" class="w-1/2 grid grid-cols-2 gap-4 mt-6">
      <ReponseSlots
        v-for="(rep, index) in reponses"
        :key="index"
        :letter="rep.letter"
        :question="rep.text"
        :resetSignal="currentIndex"
        @selected="setUserAnswer(rep)"
      />
    </div>

    <!-- Zone de réponse pour le type "open" -->
    <div v-if="type === 'open'" class="w-full bottom-10 flex justify-center m-10">
      <TheOpenAnswer class="flex justify-center" @sendMessage="setUserAnswer"/>
    </div>

    <!-- Bouton de validation et timer -->
    <div class="w-full fixed bottom-10 flex flex-col items-center">
      <div v-if="type === 'multiple_choice'" class="mt-6">
        <BaseButton property1="validation" @click="validateAnswer">
          Valider
        </BaseButton>
      </div>
      <div class="mt-4 w-3/4">
        <!-- On passe ici la prop "questionId" afin de réinitialiser le timer -->
        <TheTimer :duration="duration" :questionId="currentIndex" @timeUp="validateAnswer" />
      </div>
    </div>
  </div>
</template>

<script>
// Importez la fonction sendAnswer depuis votre service
import { sendAnswer } from '@/services/quizzService.ts';

export default {
  name: 'QuizzDisplay',
  props: {
    question: {
      type: String,
      required: true
    },
    reponses: {
      type: Array,
      required: true
    },
    duration: {
      type: Number,
      required: true
    },
    type: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      currentIndex: 0,
      // Stocke la réponse choisie par l'utilisateur
      userAnswer: null,
      // Exemple de payload, à adapter selon vos besoins
      answerPayload: {
        sessionId: 1,       // à définir dynamiquement
        participantId: 1,   // à définir dynamiquement
        questionId: 0,
        answerId: 0,        // à définir si applicable pour les QCM
        responseText: '',   // utilisé pour le type "open"
        submittedAt: new Date(),
        isCorrect: false    // ce champ sera mis à jour dans sendAnswer
      }
    }
  },
  methods: {
    // Cette méthode est appelée par vos composants enfants quand une réponse est sélectionnée ou saisie
    setUserAnswer(answer) {
      this.userAnswer = answer;
    },
    async validateAnswer() {
      // Préparation du payload en fonction du type de question
      this.answerPayload.questionId = this.currentIndex; // ou utilisez l'id de la question
      if (this.type === 'multiple_choice') {
        // Supposons que la réponse multiple_choice renvoie un objet avec id et text
        this.answerPayload.answerId = this.userAnswer.id;
        this.answerPayload.responseText = this.userAnswer.text;
      } else if (this.type === 'open') {
        // Pour le type open, userAnswer contiendra le texte saisi
        this.answerPayload.responseText = this.userAnswer;
      }
      // Définissez l'instant d'envoi
      this.answerPayload.submittedAt = new Date();

      try {
        // Envoi de la réponse
        await sendAnswer(this.answerPayload);
        console.log('Réponse envoyée avec succès');
      } catch (error) {
        console.error("Erreur lors de l'envoi de la réponse:", error);
      }
      // Réinitialisez la réponse utilisateur pour la prochaine question (optionnel)
      this.userAnswer = null;
      // Passez à la question suivante
      this.currentIndex++;
      this.$emit('next');
    }
  }
}
</script>
