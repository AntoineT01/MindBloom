<script setup lang="ts">
// Import du composant Profile
import Profile from './Profile.vue';

// Définition des props
defineProps<{
  profileEnabled: boolean; // Permet d'afficher ou de masquer le profil
  playerName?: string; // Nom du joueur (facultatif)
  playerImage?: string; // Image du joueur (facultatif)
  texte: string; // Texte à afficher (obligatoire)
}>();

// Valeurs par défaut pour les props
const defaultPlayerName = 'Mathieu';
const defaultPlayerImage = 'https://via.placeholder.com/150';
</script>

<template>
  <div class="relative flex items-center p-4 w-full">
    <!-- Section Profil -->
    <div v-if="profileEnabled" class="flex-shrink-0 relative z-10">
      <Profile
        :name="playerName || defaultPlayerName"
        :image="playerImage || defaultPlayerImage"
      />
    </div>

    <!-- Section Texte -->
    <div
      class="absolute bg-faux-blanc shadow-2xl text-sm rounded-[80px] w-3/4 left-1/2 top-1/2 transform -translate-x-1/2 -translate-y-1/2 overflow-hidden"
      :style="{
        height: '60%', // Hauteur égale à 60% de la hauteur de l'image
      }"
    >
      <!-- Texte avec gestion des débordements -->
      <div
        class="pl-[12%] pr-[6%] font-orbitron text-violet-defaut text-3xl break-words whitespace-normal overflow-hidden h-full"
      >
        {{ texte }}qsdfqsfqsdfqsdfqsdfqsfqsdfqsdfqsdfqsfqsdfqsdfqsdfqsfqsdfqsdfqsdfqsfqsdfqsdfqsdfqsfqsdfqsdfqsdfqsfqsdfqsdfqsdfqsfqsdfqsdfqsdfqsfqsdfqsdfqsdfqsfqsdfqsdfqsdfqsfqsdfqsdfqsdfqsfqsdfqsdfqsdfqsfqsdfqsdfqsdfqsfqsdfqsdfqsdfqsfqsdfqsdfqsdfqsfqsdfqsdf <br />

      </div>
    </div>
  </div>
</template>
