<script setup lang="ts">
import { defineProps } from 'vue';

const props = defineProps<{
  name: string;
  avatar: string;
  color: string;
  tag?: boolean;
}>();

// On peut définir une valeur par défaut pour tag (true si non spécifié)
const showTag = props.tag !== undefined ? props.tag : true;
</script>

<template>
    <div class="flex space-x-6">
      <!-- Version avec bord violet -->
      <div class="flex flex-col items-center">
        <div class="relative">
          <div
            class="h-[300px] w-[300px] border-[10px] rounded-full bg-white flex items-center justify-center shadow-lg"
            :style="{ borderColor: props.color, borderStyle: 'solid' }"
          >
            <img
              :src="props.avatar"
              class="h-[280px] w-[280px] rounded-full"
              alt="Avatar de l'utilisateur"
            />
          </div>
          <div
            v-if="showTag"
            class="min-h-[30px] min-w-[150px] max-w-[225px] absolute bottom-[0px] left-1/2 transform -translate-x-1/2 px-4 py-2 rounded-xl border-[5px] font-orbitron font-medium bg-white text-black flex items-center justify-center"
            :style="{ borderColor: props.color, borderStyle: 'solid' }"
          >
            {{ props.name }}
          </div>
        </div>
      </div>
    </div>
</template>
