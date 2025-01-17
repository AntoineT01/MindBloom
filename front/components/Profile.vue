<script setup lang="ts">
import { reactive } from 'vue';


// Objet réactif contenant les données de l'utilisateur
const user = reactive({
    name: 'Julie.P',
    avatar: 'https://avatar.iran.liara.run/public',
    color: '#A1A1A1', // Couleur par défaut du bord (peut être changée dynamiquement)
    tag: true
});
</script>

<template>
    <div class="flex space-x-6">
      <!-- Version avec bord violet -->
      <div class="flex flex-col items-center">
        <div class="relative">
          <div
            class="h-[300px] w-[300px] border-[10px] rounded-full bg-white flex items-center justify-center shadow-lg"
            :style="{ borderColor: user.color, borderStyle: 'solid' }"
          >
            <img
              :src="user.avatar"
              class="h-[280px] w-[280px] rounded-full"
              alt="Avatar de l'utilisateur"
            />
          </div>
          <div
            v-if="user.tag"
            class="min-h-[30px] min-w-[150px] max-w-[225x]  absolute bottom-[0px] left-1/2 transform -translate-x-1/2 px-4 py-2 rounded-xl border-[5px] font-orbitron font-medium bg-white text-black flex items-center justify-center"
            :style="{ borderColor: user.color, borderStyle: 'solid' }"
          >
            {{ user.name }}
          </div>
        </div>
      </div>
    </div>
  </template>