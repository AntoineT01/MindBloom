# pages/connexion.vue
<template>
  <div class="container mx-auto px-4">
    <div class="flex flex-col items-center justify-center min-h-[80vh] max-w-md mx-auto space-y-8">
      <form @submit.prevent="handleLogin" class="w-full space-y-6">
        <!-- Champ email -->
        <ChampText
            v-model="loginForm.email"
            :withIcon="true"
            :withButton="false"
            iconType="email"
            inputType="email"
            placeholder="Votre email"
            :error="formErrors.email"
        />

        <!-- Champ mot de passe -->
        <ChampText
            v-model="loginForm.password"
            :withIcon="true"
            :withButton="false"
            iconType="password"
            inputType="password"
            placeholder="Votre mot de passe"
            :error="formErrors.password"
        />

        <!-- Bouton de connexion -->
        <div class="flex justify-center w-full">
          <Button
              property1="link-1"
              :disabled="loading || hasErrors"
              @click="handleLogin"
              type="button"
          >
            {{ loading ? 'Connexion en cours...' : 'Connexion' }}
          </Button>
        </div>
      </form>

      <!-- Lien d'inscription -->
      <div class="text-center pt-4">
        <p class="text-violet-fonc mb-4">Vous n'avez pas de compte ?</p>
        <Button
            property1="validation"
            @click="navigateToSignup"
            type="button"
        >
          S'inscrire
        </Button>
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
import { ref, reactive, computed, onMounted } from 'vue'
import ChampText from '~/components/BaseTextField.vue'
import Button from '~/components/BaseButton.vue'
import GlobalPopup from '~/components/GlobalPopup.vue'
import { usePopup } from '~/composables/usePopup'
import { loginUser } from '~/services/authService'

interface LoginData {
  email: string;
  password: string;
}

const loading = ref(false)
const popup = usePopup()

// Formulaire de connexion
const loginForm = reactive({
  email: '',
  password: ''
})

// Erreurs de validation
const formErrors = reactive({
  email: '',
  password: ''
})

// Validation du formulaire
const validateForm = () => {
  let isValid = true

  // Validation email
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!loginForm.email) {
    formErrors.email = 'L\'email est requis'
    isValid = false
  } else if (!emailRegex.test(loginForm.email)) {
    formErrors.email = 'Email invalide'
    isValid = false
  } else {
    formErrors.email = ''
  }

  // Validation mot de passe
  if (!loginForm.password) {
    formErrors.password = 'Le mot de passe est requis'
    isValid = false
  } else if (loginForm.password.length < 6) {
    formErrors.password = 'Le mot de passe doit contenir au moins 6 caractères'
    isValid = false
  } else {
    formErrors.password = ''
  }

  return isValid
}

// Computed properties
const hasErrors = computed(() => {
  return !!formErrors.email || !!formErrors.password
})

const popupOpen = computed(() => popup.isOpen.value)
const popupMessage = computed(() => popup.message.value)
const popupTitle = computed(() => popup.title.value)

// Handlers
const handleClosePopup = () => {
  popup.closePopup()
}

// Fonction de débogage
onMounted(() => {
  console.log('Page de connexion montée');
})

const handleLogin = async () => {
  console.log('Handle login clicked');

  // if (!validateForm()) {
  //   console.log('Form validation failed');
  //   return;
  // }

  loading.value = true;
  console.log('Loading started');

  try {
    console.log('Sending login request');

    await loginUser({
      email: loginForm.email,
      password: loginForm.password
    });

    console.log('Login successful');
    popup.showPopup("Connexion réussie !", "Succès");

    console.log('Navigating to homeConnected');
    await navigateTo('/accueilConnecte');
  } catch (error) {
    console.error('Login error:', error);

    popup.showPopup(
        error instanceof Error ? error.message : "Erreur de connexion au serveur",
        "Erreur"
    );
  } finally {
    loading.value = false;
    console.log('Loading ended');
  }
}

const navigateToSignup = () => {
  navigateTo('/inscription')
}
</script>

<style scoped>
.font-orbitron {
  font-family: "Orbitron-Regular", sans-serif;
}
</style>