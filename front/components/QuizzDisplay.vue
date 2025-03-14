<template>
  <div class="flex flex-col items-center w-full bg-gray-100 min-h-screen p-6">


    <div class="w-3/5 flex flex-col items-center bg-white shadow-lg rounded-lg p-6 h-3/5">
      <div class="w-full flex justify-center">
        <div class="w-3/4 flex justify-center">
          <slot></slot>
        </div>
      </div>
      <h2 class="text-center text-xl font-bold mt-4 text-purple-700">{{ question }}</h2>
    </div>

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

    <div v-if="type === 'open_answer'" class="w-full bottom-10 flex justify-center m-10">
      <TheOpenAnswer class="flex justify-center" @sendMessage="emitNext"/>
    </div>

    <div class="w-full fixed bottom-10 flex flex-col items-center">
      <div v-if="type === 'multiple_choice'" class="mt-6">
        <BaseButton property1="validation" @click="validateAnswer">
          Valider
        </BaseButton>
      </div>
      <div class="mt-4 w-3/4">
        <TheTimer :duration="duration" :questionId="currentIndex" @timeUp="validateAnswer" />
      </div>
    </div>
  </div>
</template>

<script>
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
    },
    // Nouvelles props pour les données de session
    sessionId: {
      type: [Number, String],
      default: null
    },
    participantId: {
      type: [Number, String],
      default: null
    },
    questionId: {
      type: [Number, String],
      default: null
    }
  },
  data() {
    return {
      debug: true, // Activer le débogage temporairement
      currentIndex: 0,
      userAnswer: null,
      answerPayload: {
        sessionId: null,
        participantId: null,
        questionId: null,
        answerId: null,
        responseText: '',
        submittedAt: new Date(),
        isCorrect: false
      },
      // ID de question à utiliser si aucun n'est fourni
      fallbackQuestionId: 1
    }
  },
  created() {
    // Récupérer les données de session depuis le localStorage si nécessaire
    const storedSession = localStorage.getItem('current_session');
    let sessionData = null;

    if (storedSession) {
      try {
        sessionData = JSON.parse(storedSession);
      } catch (e) {
        console.error('Erreur lors de la lecture des données de session:', e);
      }
    }

    // Initialiser les données de session
    this.answerPayload.sessionId = this.sessionId ? parseInt(this.sessionId) : (sessionData?.sessionId ? parseInt(sessionData.sessionId) : 1);
    this.answerPayload.participantId = this.participantId ? parseInt(this.participantId) : (sessionData?.participantId ? parseInt(sessionData.participantId) : 1);

    // Extraire l'ID depuis les réponses si disponible
    if (this.reponses && this.reponses.length > 0 && this.reponses[0].questionId) {
      this.fallbackQuestionId = this.reponses[0].questionId;
    }

    console.log('Données de session initialisées:', {
      sessionId: this.answerPayload.sessionId,
      participantId: this.answerPayload.participantId
    });
  },
  watch: {
    // Observer les changements de questionId
    questionId: {
      immediate: true,
      handler(newVal) {
        if (newVal) {
          this.answerPayload.questionId = parseInt(newVal);
          console.log('ID de question mis à jour via prop:', this.answerPayload.questionId);
        }
      }
    },
    // Observer si les réponses contiennent un questionId
    reponses: {
      immediate: true,
      handler(newVal) {
        if (newVal && newVal.length > 0 && newVal[0].questionId) {
          this.fallbackQuestionId = newVal[0].questionId;
          console.log('ID de question de secours mis à jour via réponses:', this.fallbackQuestionId);
        }
      }
    }
  },
  methods: {
    // Méthode pour récupérer l'ID de question actuel, utilisant plusieurs sources possibles
    getCurrentQuestionId() {
      // Ordre de priorité: prop questionId > ID extrait des réponses > valeur par défaut 1
      const id = this.questionId
          ? parseInt(this.questionId)
          : (this.fallbackQuestionId || 1);

      return id;
    },
    setUserAnswer(answer) {
      console.log('Réponse sélectionnée:', answer);
      this.userAnswer = answer;

      // Conserver le questionId si présent dans la réponse
      if (answer && answer.questionId) {
        this.fallbackQuestionId = answer.questionId;
      }
    },
    emitNext(text) {
      // Pour les réponses textuelles ouvertes
      if (this.type === 'open_answer') {
        this.userAnswer = text;
        this.validateAnswer();
      }
    },
    async validateAnswer() {
      try {
        // Récupérer l'ID de question le plus fiable
        const questionId = this.getCurrentQuestionId();

        if (!questionId || questionId <= 0) {
          // Définir un ID par défaut si nécessaire
          console.warn('ID de question invalide ou manquant, utilisation de l\'ID 1 par défaut');
          this.answerPayload.questionId = 1;
        } else {
          this.answerPayload.questionId = questionId;
        }

        if (this.type === 'multiple_choice' && this.userAnswer) {
          this.answerPayload.answerId = this.userAnswer.id ? parseInt(this.userAnswer.id) : null;
          this.answerPayload.responseText = this.userAnswer.text || '';
        } else if (this.type === 'open_answer') {
          this.answerPayload.answerId = null;
          this.answerPayload.responseText = this.userAnswer || '';
        }

        this.answerPayload.submittedAt = new Date();

        console.log('Envoi de la réponse avec questionId=' + this.answerPayload.questionId + ':', this.answerPayload);

        await sendAnswer(this.answerPayload);
        console.log('Réponse envoyée avec succès');
      } catch (error) {
        console.error("Erreur lors de l'envoi de la réponse:", error);
      }

      // Réinitialiser la réponse utilisateur
      this.userAnswer = null;

      // Incrémenter l'index et émettre l'événement pour passer à la question suivante
      this.currentIndex++;
      this.$emit('next');
    }
  }
}
</script>