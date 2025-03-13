<template>
    <div class="p-8 flex flex-col items-center space-y-8">
      <!-- Section TOP 3 affichée depuis le tableau global -->
      <div class="flex justify-center space-x-8">
        <div
          v-for="(item, index) in topThree"
          :key="item.type + '-' + index"
          class="flex flex-col items-center"
          :class="{ 'transform -translate-y-16': index === 1 }"
        >
          <Profile
            :name="item.name"
            :avatar="item.avatar"
            :color="item.color"
            :tag="true"
          />
          <p class="mt-2 text-center font-semibold">{{ item.points }} pts</p>
        </div>
      </div>
  
      <!-- Section TOP 4 à TOP 9 affichée depuis le même tableau -->
      <div class="grid grid-cols-3 gap-8">
        <div
          v-for="item in others"
          :key="item.classement"
          class="flex flex-col items-center rounded-xl p-4"
        >
          <div class="transform scale-90">
            <BaseScore :points="item.points" :classement="item.classement" />
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { computed, ref } from 'vue'
  import Profile from '@/components/Profile.vue'
  import BaseScore from '@/components/BaseScore.vue'
  
  // Un seul tableau regroupant toutes les données
  const scores = ref([
    // Top 3
    {
      type: 'top',
      name: 'Jane Doe',
      avatar: 'https://via.placeholder.com/300',
      color: '#C0C0C0',
      points: 800,
    },
    {
      type: 'top',
      name: 'John Doe',
      avatar: 'https://via.placeholder.com/300',
      color: '#FFD700',
      points: 1000,
    },
    {
      type: 'top',
      name: 'Mike',
      avatar: 'https://via.placeholder.com/300',
      color: '#CE8946',
      points: 600,
    },
    // Autres (TOP 4 à TOP 9)
    { type: 'others', classement: 4, points: 10 },
    { type: 'others', classement: 5, points: 10 },
    { type: 'others', classement: 6, points: 10 },
    { type: 'others', classement: 7, points: 10 },
    { type: 'others', classement: 8, points: 10 },
    { type: 'others', classement: 9, points: 10 },
    { type: 'others', classement: 9, points: 10 },
    { type: 'others', classement: 9, points: 10 },
    { type: 'others', classement: 9, points: 10 },
    { type: 'others', classement: 9, points: 10 },
    { type: 'others', classement: 9, points: 10 },
    { type: 'others', classement: 9, points: 10 },
    { type: 'others', classement: 9, points: 10 },
    { type: 'others', classement: 9, points: 10 },
    { type: 'others', classement: 9, points: 10 },
    { type: 'others', classement: 9, points: 10 },
    { type: 'others', classement: 9, points: 10 },
  ]);
  
  // Filtrer les 3 premiers éléments pour la section Top 3
  const topThree = computed(() => scores.value.filter(item => item.type === 'top'));
  // Les autres éléments pour la section de scores restants
  const others = computed(() => scores.value.filter(item => item.type === 'others'));
  </script>
  