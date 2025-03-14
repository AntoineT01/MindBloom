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
          <p class="text-xl">
            Classement : <span class="font-bold">{{ ranking }}</span>
          </p>
        </div>
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue';
  import { sendScore, fetchLeaderboard, LeaderboardDto } from '@/services/scoreService';
  
  // États réactifs
  const loading = ref(true);
  const error = ref('');
  const score = ref(0);
  const ranking = ref(0);
  
  // Identifiants simulés pour le participant et la session
  const currentParticipantId = 202;
  const currentQuizSessionId = 101;
  
  // Score calculé (à remplacer par votre logique de calcul)
  const calculatedScore = 85;
  
  /**
   * Met à jour le leaderboard en envoyant le score calculé
   * puis en récupérant l'ensemble des scores pour déterminer le classement.
   */
  const updateLeaderboard = async () => {
    try {
      // Préparation des données à envoyer
      const leaderboardEntry: LeaderboardDto = {
        quizSessionId: currentQuizSessionId,
        participantId: currentParticipantId,
        score: calculatedScore
      };
  
      // Envoi du score
      await sendScore(leaderboardEntry);
  
      // Récupération du leaderboard complet
      const leaderboard = await fetchLeaderboard();
  
      // Tri décroissant par score
      leaderboard.sort((a, b) => b.score - a.score);
  
      // Recherche de l'entrée correspondant au participant et à la session courante
      const index = leaderboard.findIndex(
        (entry) =>
          entry.participantId === currentParticipantId &&
          entry.quizSessionId === currentQuizSessionId
      );
  
      if (index !== -1) {
        ranking.value = index + 1; // Le classement commence à 1
        score.value = leaderboard[index].score;
      } else {
        error.value = 'Aucun score trouvé pour ce participant.';
      }
    } catch (err: any) {
      error.value = "Erreur lors de la récupération du leaderboard.";
      console.error(err);
    } finally {
      loading.value = false;
    }
  };
  
  onMounted(() => {
    updateLeaderboard();
  });
  </script>
  
  <style scoped>
  .score-page {
    min-height: 100vh;
    background-color: #f3f4f6;
  }
  </style>
  