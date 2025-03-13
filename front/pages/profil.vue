<template>
  <div class="container mx-auto px-4 py-8">
    <div class="flex flex-col items-center justify-center space-y-8">
      <h1 class="text-3xl font-bold text-violet-fonc">Mon Profil</h1>

      <div class="w-full max-w-3xl bg-white rounded-lg shadow-xl p-8">
        <div class="flex flex-col lg:flex-row items-center gap-8">
          <!-- Avatar -->
          <div class="flex-shrink-0">
            <div class="relative h-40 w-40 lg:h-64 lg:w-64">
              <div
                  class="h-full w-full border-[10px] rounded-full bg-white flex items-center justify-center shadow-lg"
                  :style="{ borderColor: '#A1A1A1', borderStyle: 'solid' }"
              >
                <img
                    :src="userData.avatar || '/default-avatar.png'"
                    class="h-[90%] w-[90%] rounded-full object-cover"
                    alt="Avatar de l'utilisateur"
                />
              </div>
            </div>
          </div>

          <!-- Informations utilisateur -->
          <div class="flex-grow">
            <div class="space-y-4">
              <div>
                <h2 class="text-xl font-semibold text-violet-fonc">Informations personnelles</h2>
                <div class="mt-2 space-y-3">
                  <div class="flex flex-col">
                    <span class="text-sm text-gray-500">Prénom</span>
                    <span class="font-medium">{{ userData.firstname || 'Non renseigné' }}</span>
                  </div>
                  <div class="flex flex-col">
                    <span class="text-sm text-gray-500">Nom</span>
                    <span class="font-medium">{{ userData.lastname || 'Non renseigné' }}</span>
                  </div>
                  <div class="flex flex-col">
                    <span class="text-sm text-gray-500">Email</span>
                    <span class="font-medium">{{ userData.email || 'Non renseigné' }}</span>
                  </div>
                </div>
              </div>

              <div class="pt-4">
                <BaseButton
                    property1="validation"
                    @click="navigateToEditProfile"
                >
                  Modifier mon profil
                </BaseButton>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Historique des événements -->
      <div v-if="userEvents.length > 0" class="w-full max-w-3xl">
        <h2 class="text-2xl font-bold text-violet-fonc mb-4">Mes événements récents</h2>
        <div class="bg-white rounded-lg shadow-xl p-6">
          <ul class="divide-y divide-gray-200">
            <li v-for="event in userEvents" :key="event.id" class="py-4 flex justify-between items-center">
              <div>
                <h3 class="font-medium">{{ event.title }}</h3>
                <p class="text-sm text-gray-500">{{ event.date }}</p>
              </div>
              <BaseButton
                  property1="link-2"
                  @click="navigateToEvent(event.id)"
              >
                Voir
              </BaseButton>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import BaseButton from '~/components/BaseButton.vue'
import { getUserData, isUserLoggedIn } from '~/services/authService'
import { useAuth } from '~/composables/useAuth'

const auth = useAuth()
const userData = ref<any>({})
const userEvents = ref<any[]>([])

onMounted(() => {
  if (process.client) {
    // Rediriger vers la page de connexion si l'utilisateur n'est pas connecté
    if (!isUserLoggedIn()) {
      navigateTo('/connexion')
      return
    }

    // Récupérer les données de l'utilisateur
    const user = getUserData()
    if (user) {
      userData.value = user
    }
  }

  // Exemple de données d'événements pour démonstration
  userEvents.value = [
    { id: 1, title: 'Quiz Science', date: '15 mars 2025' },
    { id: 2, title: 'Quiz Culture générale', date: '10 mars 2025' },
    { id: 3, title: 'Quiz Histoire', date: '5 mars 2025' }
  ]
})

const navigateToEditProfile = () => {
  // À implémenter: navigation vers la page d'édition de profil
  console.log('Navigation vers la page d\'édition de profil')
}

const navigateToEvent = (eventId: number) => {
  // À implémenter: navigation vers la page d'événement
  console.log('Navigation vers l\'événement', eventId)
}
</script>