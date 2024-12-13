<!-- components/Navbar.vue -->
<template>
  <nav class="bg-primary-light dark:bg-primary-dark shadow-md">
    <div class="container mx-auto px-4">
      <div class="flex justify-between items-center py-4">
        <NuxtLink to="/" class="text-2xl font-bold text-secondary-light dark:text-secondary-dark hover:text-accent-1-light dark:hover:text-accent-1-dark transition-colors">
          Mon Portfolio
        </NuxtLink>
        <div class="flex items-center space-x-6">
          <NuxtLink
              v-for="item in navItems"
              :key="item.path"
              :to="item.path"
              class="text-text-light dark:text-text-dark hover:text-secondary-light dark:hover:text-secondary-dark transition-colors"
              :class="{ 'font-semibold': isActive(item.path) }"
          >
            {{ item.name }}
          </NuxtLink>
          <ThemeToggle />
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import ThemeToggle from './ThemeToggle.vue'

const route = useRoute()

const navItems = [
  { name: 'Accueil', path: '/' },
  { name: 'CV', path: '/cv' },

]

const isActive = (path: string) => {
  return route.path === path
}
</script>