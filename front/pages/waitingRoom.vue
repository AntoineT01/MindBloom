<template>
    <div class="p-8 flex flex-col items-center space-y-8">
      <!-- Titre de la salle d'attente -->
      <h2 class="text-2xl font-bold text-center">Salle d'attente</h2>
      
      <!-- Affichage des utilisateurs en attente -->
      <div class="grid grid-cols-3 gap-8">
        <div
          v-for="user in waitingUsers"
          :key="user.id"
          class="flex flex-col items-center rounded-xl p-4 border"
        >
          <Profile
            :name="user.name"
            :avatar="user.avatar"
            :color="user.color"
            :tag="false"
          />
          <p class="mt-2 text-center font-semibold">{{ user.status }}</p>
          <button class="mt-2 bg-blue-500 text-white py-1 px-4 rounded">
            Rejoindre
          </button>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import Profile from '@/components/Profile.vue'
  
  // Tableau unique regroupant les informations des utilisateurs en attente de rejoindre la room
  const waitingUsers = ref([
    {
      id: 1,
      name: 'Alice',
      avatar: 'https://via.placeholder.com/300',
      color: '#FF5733',
      status: 'En attente'
    },
    {
      id: 2,
      name: 'Bob',
      avatar: 'https://via.placeholder.com/300',
      color: '#33C1FF',
      status: 'En attente'
    },
    {
      id: 3,
      name: 'Charlie',
      avatar: 'https://via.placeholder.com/300',
      color: '#8E44AD',
      status: 'En attente'
    },
    // Ajoutez ici d'autres utilisateurs si besoin
  ])
  </script>
  