<template>
  <nav class="bg-[#E6E6E6] h-[90px] w-full relative">
    <div class="container mx-auto h-full flex items-center pl-20">

      <!-- Logo au centre pour toutes les vues -->
      <div class="absolute left-1/2 transform -translate-x-1/2">
        <NuxtLink to="/" class="hover:opacity-80 transition-opacity">
          <h1 class="text-[40px] font-['Orbitron-Regular']" :class="{ 'text-violet-clair': !isLoggedIn, 'text-violet-fonc': isLoggedIn }">MindBloom</h1>
        </NuxtLink>
      </div>

      <!-- Affichage pour utilisateur non connecté -->
      <div v-if="!isLoggedIn" class="ml-auto flex items-center space-x-6 pr-6">
        <BaseButton property1="link-1" @click="navigateToSignup">S'inscrire</BaseButton>
        <BaseButton property1="link-2" @click="navigateToLogin">Se connecter</BaseButton>
      </div>

      <!-- Affichage pour utilisateur connecté -->
      <template v-else>
        <div class="flex items-center">
          <div class="flex items-center cursor-pointer" @click="navigateToProfile">
            <img
                :src="userAvatar"
                alt="Profile"
                class="w-[68px] h-[68px] rounded-full object-cover"
            />
            <span class="ml-4 font-['Orbitron-Regular'] text-[40px] text-black">{{ userName }}</span>
          </div>
        </div>

        <div class="ml-auto flex items-center space-x-6 pr-6">
          <BaseButton property1="link-2" @click="handleLogout">Se déconnecter</BaseButton>
        </div>
      </template>
    </div>
  </nav>
</template>

<script setup lang="ts">
import { ref, onMounted, computed, watch } from 'vue'
import BaseButton from '~/components/BaseButton.vue'
import { isUserLoggedIn, getUserData } from '~/services/authService'
import { useAuth } from '~/composables/useAuth'

const auth = useAuth()

// État d'authentification
const isLoggedIn = ref(false)

// Données utilisateur
const userAvatar = ref('/default-avatar.png')
const userName = ref('utilisateur')

// Vérifier la connexion au chargement
onMounted(() => {
  if (process.client) {
    checkLoginStatus()

    // Vérifier périodiquement le statut d'authentification
    setInterval(checkLoginStatus, 1000)
  }
})

// Vérifie si l'utilisateur est connecté
function checkLoginStatus() {
  if (process.client) {
    const loggedIn = isUserLoggedIn()
    isLoggedIn.value = loggedIn

    if (loggedIn) {
      const userData = getUserData()
      if (userData) {
        userName.value = userData.firstname || 'Utilisateur'
        userAvatar.value = userData.avatar || '/default-avatar.png'
      }
    }
  }
}

// Navigation
const navigateToSignup = () => {
  navigateTo('/inscription')
}

const navigateToLogin = () => {
  navigateTo('/connexion')
}

const navigateToProfile = () => {
  navigateTo('/profil')
}

// Déconnexion
const handleLogout = async () => {
  if (process.client) {
    await auth.logout()
    isAuthenticated.value = false
    // Redirection gérée dans le composable useAuth
  }
}
</script>