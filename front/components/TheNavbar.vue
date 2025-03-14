<!-- File: components/TheNavbar.vue -->
<template>
  <nav class="bg-gray-100 shadow-md h-16 w-full">
    <div class="container mx-auto flex items-center justify-between px-6 h-full">
      <div class="flex items-center">
        <NuxtLink to="/" class="flex items-center">
          <img src="/icon.png" alt="Logo" class="w-8 h-8 mr-2" />
          <h1 class="text-2xl font-['Orbitron-Regular']" :class="{ 'text-violet-clair': !isLoggedIn, 'text-violet-fonc': isLoggedIn }">
            MindBloom
          </h1>
        </NuxtLink>
      </div>
      <div v-if="!isLoggedIn" class="flex items-center space-x-4">
        <BaseButton property1="link-1" @click="navigateToSignup" class="text-sm px-3 py-1 flex items-center">
          <i class="fas fa-user-plus mr-1"></i>S'inscrire
        </BaseButton>
        <BaseButton property1="link-2" @click="navigateToLogin" class="text-sm px-3 py-1 flex items-center">
          <i class="fas fa-sign-in-alt mr-1"></i>Se connecter
        </BaseButton>
      </div>
      <template v-else>
        <div class="flex items-center space-x-4">
          <div class="flex items-center cursor-pointer" @click="navigateToProfile">
            <img :src="userAvatar" alt="Profile" class="w-10 h-10 rounded-full object-cover" />
            <span class="ml-2 font-['Orbitron-Regular'] text-base text-black">{{ userName }}</span>
          </div>
          <BaseButton property1="link-2" @click="navigateToManageQuiz" class="text-sm px-3 py-1 flex items-center">
            <i class="fas fa-list mr-1"></i>Gérer ses quizz
          </BaseButton>
          <BaseButton property1="link-2" @click="handleLogout" class="text-sm px-3 py-1 flex items-center">
            <i class="fas fa-sign-out-alt mr-1"></i>Se déconnecter
          </BaseButton>
        </div>
      </template>
    </div>
  </nav>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeMount, onUnmounted } from 'vue'
import BaseButton from '~/components/BaseButton.vue'
import { useAuth } from '~/composables/useAuth'
const auth = useAuth()
const isLoggedIn = ref(false)
const userAvatar = ref('/default-avatar.png')
const userName = ref('Utilisateur')
const updateUserData = () => {
  isLoggedIn.value = auth.isAuthenticated.value
  if (isLoggedIn.value && auth.user.value) {
    userName.value = auth.user.value.firstname || 'Utilisateur'
    userAvatar.value = auth.user.value.avatar || '/default-avatar.png'
  }
}
onBeforeMount(() => { if (process.client) { auth.checkAuth() } })
onMounted(() => {
  if (process.client) {
    updateUserData()
    const interval = setInterval(() => { auth.checkAuth(); updateUserData() }, 5000)
    onUnmounted(() => { clearInterval(interval) })
  }
})
const navigateToSignup = () => { navigateTo('/inscription') }
const navigateToLogin = () => { navigateTo('/connexion') }
const navigateToProfile = () => { navigateTo('/profil') }
const navigateToManageQuiz = () => { navigateTo('/mes-quizz') }
const handleLogout = async () => { if (process.client) { await auth.logout(); isLoggedIn.value = false } }
</script>
