<template>
  <div class="min-h-screen flex flex-col bg-background-light dark:bg-background-dark">
    <Navbar />
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
import Navbar from '~/components/Navbar.vue'
</script>