<!-- File: pages/profil.vue -->
<template>
  <div class="container mx-auto px-4 py-8">
    <div class="flex flex-col items-center justify-center space-y-8">
      <h1 class="text-3xl font-bold text-violet-fonc">Mon Profil</h1>
      <div class="w-full max-w-3xl bg-white rounded-lg shadow-xl p-6">
        <div class="flex flex-col lg:flex-row items-center gap-8">
          <div class="flex-shrink-0">
            <div class="relative h-40 w-40 lg:h-64 lg:w-64">
              <div class="h-full w-full border-4 rounded-full bg-white flex items-center justify-center shadow-md" :style="{ borderColor: '#A1A1A1' }">
                <img :src="userData.avatar || '/default-avatar.png'" class="h-[90%] w-[90%] rounded-full object-cover" alt="Avatar de l'utilisateur" />
              </div>
            </div>
          </div>
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
                  <div class="flex flex-col">
                    <span class="text-sm text-gray-500">Langue</span>
                    <span class="font-medium">{{ getLocaleLabel(userData.locale) }}</span>
                  </div>
                </div>
              </div>
              <div class="pt-4">
                <BaseButton property1="validation" @click="navigateToEditProfile" class="text-sm px-3 py-1">Modifier mon profil</BaseButton>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import BaseButton from '~/components/BaseButton.vue'
import { getUserData, isUserLoggedIn } from '~/services/authService'

const userData = ref<any>({})
const userEvents = ref<any[]>([])
const locales = { fr: 'Français', en: 'Anglais', es: 'Espagnol', de: 'Allemand' }
const getLocaleLabel = (localeCode: string) => locales[localeCode as keyof typeof locales] || localeCode || 'Non renseigné'

onMounted(() => {
  if (process.client) {
    if (!isUserLoggedIn()) { navigateTo('/connexion'); return }
    const user = getUserData()
    if (user) { userData.value = user }
    userEvents.value = [
      { id: 1, title: 'Quiz Science', date: '15 mars 2025' },
      { id: 2, title: 'Quiz Culture générale', date: '10 mars 2025' },
      { id: 3, title: 'Quiz Histoire', date: '5 mars 2025' }
    ]
  }
})

const navigateToEditProfile = () => { navigateTo('/edit-profile') }
</script>
