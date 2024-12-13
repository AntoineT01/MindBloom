<template>
  <div class="bg-primary text-white py-8">
    <div class="container mx-auto px-4">
      <header class="mb-8">
        <h1 class="text-4xl font-bold text-secondary mb-2">{{ cv.name }}</h1>
        <p class="text-xl text-gray-300">{{ cv.profession }}</p>
        
      </header>

      <main>
        <CV :cv="cv" class="mb-12" />

        <section class="bg-accent-2 shadow-md rounded-lg p-6">
          <h2 class="text-2xl font-semibold mb-6 text-secondary">Mes Projets</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            <ProjectCard
                v-for="project in projects"
                :key="project.id"
                :title="project.title"
                :description="project.description"
            />
          </div>
          <br>
          <div>
            <button @click="openPopup" class="px-6 py-3 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition mb-8">
              Ouvrir le Pop-up
            </button>
            <!-- Affichage de la coupe après la fermeture du pop-up -->
            <div v-if="isPopupClosed" class="mt-10 p-6 bg-red-200 rounded-full shadow-lg text-center">
              <p class="text-xl font-semibold text-red-600">Coupe !</p>
            </div>

            <!-- Intégration du composant enfant -->
            <Popup
              v-if="isPopupOpen"
              :title="'Coeur : Le coeur humain'"
              :description="'Le cœur est un organe vital situé dans la poitrine, légèrement à gauche du centre du thorax. Il joue un rôle fondamental dans la circulation sanguine.'"
              src="/assets/images/coeur.png"
              :fluidTitle="'Circulation fluide'"
              :fluidText="['Le sang circule dans le corps via le système cardiovasculaire.', 'Les courants marins agissent comme un système circulatoire.']"
              :extraInfo="'Information supplémentaire sur la circulation du sang et des courants marins.'"
              @close="closeModal"
            />
          </div>
        </section>
    </main>
  </div>
  </div>
</template>

<script setup lang="ts">
import { useProjectsStore } from '~/stores/projects'
import CV from '~/components/CV.vue'
import Popup from '~/components/Popup.vue'
import { ref, computed } from 'vue'

// Variables d'état
const isPopupOpen = ref(false)
const isPopupClosed = ref(false)

useHead({
  title: 'CV'
})

const openPopup = () => {
  isPopupOpen.value = true
}

const closeModal = () => {
  isPopupOpen.value = false
}

// Charger le contenu du CV depuis le fichier JSON
const { data: cv } = await useAsyncData('cv', () => queryContent('cv').findOne())

// Utiliser le store Pinia pour gérer les projets
const projectsStore = useProjectsStore()
await projectsStore.fetchProjects()
const projects = computed(() => projectsStore.projects)
</script>
