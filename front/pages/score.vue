<template>
    <div class="score-page flex flex-col items-center p-6">
      <h1 class="text-3xl font-bold mb-4">Votre score</h1>
      <div class="score-details bg-white shadow rounded-lg p-6 min-w-[300px] text-center">
        <div v-if="loading" class="text-xl">Chargement...</div>
        <div v-else-if="error" class="text-xl text-red-600">{{ error }}</div>
        <div v-else>
          <p class="text-xl mb-2">
            Score : <span class="font-bold">{{ score }}</span>
          </p>
        </div>
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue';
  import { useRoute } from 'vue-router';
  import { calculateScoreForParticipant } from '@/services/scoreCalculationService';
  
  // Récupérer les paramètres d'URL (exemple : /score?sessionCode=ABC123&participantId=1)
  const route = useRoute();
  const sessionCode = route.query.sessionCode as string;
  const participantId = route.query.participantId ? Number(route.query.participantId) : 1;
  
  // États réactifs
  const loading = ref(true);
  const error = ref('');
  const score = ref(0);
  
  /**
   * Met à jour le score en appelant la fonction calculateScoreForParticipant
   */
  const updateScore = async () => {
    try {
      score.value = await calculateScoreForParticipant(sessionCode, participantId);
    } catch (err: any) {
      error.value = "Erreur lors du calcul du score.";
      console.error(err);
    } finally {
      loading.value = false;
    }
  };
  
  onMounted(() => {
    updateScore();
  });
  </script>
  
  <style scoped>
  .score-page {
    min-height: 100vh;
    background-color: #f3f4f6;
  }
  </style>
  