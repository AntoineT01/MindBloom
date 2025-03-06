<template>
  <div class="container mx-auto px-4">
    <div class="flex flex-col items-center justify-center min-h-[80vh] max-w-md mx-auto space-y-8">
      <div class="text-center">
        <h2 class="text-2xl font-orbitron text-violet-fonc mb-4">Confirmation de compte</h2>

        <!-- Affichage du statut -->
        <div v-if="loading" class="mb-6">
          <p class="text-gray-600">Vérification en cours...</p>
        </div>
        <div v-else-if="confirmationStatus === 'success'" class="mb-6">
          <p class="text-vert font-medium mb-4">Votre compte a été activé avec succès !</p>
          <Button
              property1="link-1"
              @click="navigateToLogin"
              type="button"
          >
            Se connecter
          </Button>
        </div>
        <div v-else-if="confirmationStatus === 'error'" class="mb-6">
          <p class="text-red-500 font-medium mb-2">Échec de l'activation du compte</p>
          <p class="text-gray-600 mb-4">{{ errorMessage }}</p>
          <Button
              property1="validation"
              @click="retryConfirmation"
              type="button"
          >
            Réessayer
          </Button>
        </div>
      </div>
    </div>

    <!-- Popup pour les messages -->
    <GlobalPopup
        :is-open="popupOpen"
        :message="popupMessage"
        :title="popupTitle"
        @close="handleClosePopup"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import Button from '~/components/BaseButton.vue'
import GlobalPopup from '~/components/GlobalPopup.vue'
import { usePopup } from '~/composables/usePopup'

// État du composant
const loading = ref(true)
const confirmationStatus = ref<'success' | 'error' | null>(null)
const errorMessage = ref('')
const route = useRoute()
const popup = usePopup()

// Computed properties pour le popup
const popupOpen = computed(() => popup.isOpen.value)
const popupMessage = computed(() => popup.message.value)
const popupTitle = computed(() => popup.title.value)

// Fermeture du popup
const handleClosePopup = () => {
  popup.closePopup()
}

// Fonction pour confirmer le compte
const confirmAccount = async (token: string) => {
  loading.value = true

  try {
    // Utilisation du proxy configuré
    const response = await fetch('/api/account-requests', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ token }),
    })

    console.log('Response status:', response.status)

    if (response.status === 200 || response.status === 204) {
      confirmationStatus.value = 'success'
      popup.showPopup('Votre compte a été activé avec succès !', 'Confirmation réussie')
    } else {
      // Tenter de récupérer un message d'erreur si disponible
      try {
        const errorData = await response.json()
        errorMessage.value = errorData.message || 'Une erreur est survenue lors de la confirmation du compte.'
      } catch (e) {
        errorMessage.value = `Erreur ${response.status}: ${response.statusText}`
      }

      confirmationStatus.value = 'error'
      popup.showPopup(errorMessage.value, 'Erreur')
    }
  } catch (error) {
    console.error('Confirmation error:', error)
    confirmationStatus.value = 'error'

    if (error instanceof Error) {
      errorMessage.value = error.message
    } else {
      errorMessage.value = 'Une erreur est survenue lors de la confirmation du compte.'
    }

    popup.showPopup(errorMessage.value, 'Erreur')
  } finally {
    loading.value = false
  }
}

// Navigation vers la page de connexion
const navigateToLogin = () => {
  navigateTo('/connexion')
}

// Tentative de nouvelle confirmation
const retryConfirmation = () => {
  const token = route.params.token as string
  if (token) {
    confirmAccount(token)
  } else {
    errorMessage.value = 'Aucun token trouvé dans l\'URL.'
    popup.showPopup(errorMessage.value, 'Erreur')
  }
}

// Vérification du token au chargement de la page
onMounted(() => {
  const token = route.params.token as string

  if (token) {
    confirmAccount(token)
  } else {
    loading.value = false
    confirmationStatus.value = 'error'
    errorMessage.value = 'Aucun token trouvé dans l\'URL.'
    popup.showPopup(errorMessage.value, 'Erreur')
  }
})
</script>

<style scoped>
.font-orbitron {
  font-family: "Orbitron-Regular", sans-serif;
}
</style>