<!-- layouts/default.vue -->
<template>
  <div class="min-h-screen flex flex-col bg-background-light dark:bg-background-dark">
    <Navbar
        :type="currentState"
        :userImage="userImage"
    />
    <main class="flex-grow">
      <slot />
    </main>
    <footer class="bg-primary-light dark:bg-primary-dark text-text-light dark:text-text-dark py-4">
      <div class="container mx-auto px-4 text-center">
        <p>&copy; {{ new Date().getFullYear() }} MindBloom. Tous droits réservés.</p>
      </div>
    </footer>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import Navbar from '~/components/Navbar.vue'

const currentState = ref('connexion')
const userImage = ref('https://avatar.iran.liara.run/public')

// Si vous avez besoin de gérer l'état de connexion
const updateState = (isConnected: boolean) => {
  currentState.value = isConnected ? 'connect' : 'connexion'
}
</script>