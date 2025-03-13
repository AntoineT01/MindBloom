<template>
  <div class="container mx-auto px-4">
    <div class="flex flex-col items-center justify-center min-h-[80vh] max-w-md mx-auto space-y-8">
      <form @submit.prevent="handleSignup" class="w-full flex flex-col items-center space-y-6">
        <ChampText
            v-model="firstname"
            :withIcon="true"
            :withButton="false"
            iconType="user"
            inputType="text"
            placeholder="Votre prénom"
            :error="formErrors.firstname"
            class="w-full"
        />

        <ChampText
            v-model="lastname"
            :withIcon="true"
            :withButton="false"
            iconType="user"
            inputType="text"
            placeholder="Votre nom"
            :error="formErrors.lastname"
            class="w-full"
        />

        <ChampText
            v-model="mail"
            :withIcon="true"
            :withButton="false"
            iconType="email"
            inputType="email"
            placeholder="Votre email"
            :error="formErrors.mail"
            class="w-full"
        />

        <ChampText
            v-model="password"
            :withIcon="true"
            :withButton="false"
            iconType="password"
            inputType="password"
            placeholder="Votre mot de passe"
            :error="formErrors.password"
            class="w-full"
        />

        <ChampText
            v-model="confirmPassword"
            :withIcon="true"
            :withButton="false"
            iconType="password"
            inputType="password"
            placeholder="Confirmez votre mot de passe"
            :error="formErrors.confirmPassword"
            class="w-full"
        />

        <div class="flex justify-center w-full">
          <Button
              property1="validation"
              @click="handleSignup"
              :disabled="loading"
              type="button"
          >
            {{ loading ? 'Inscription en cours...' : 'S\'inscrire' }}
          </Button>
        </div>
      </form>

      <div class="text-center pt-4 w-full">
        <p class="text-violet-fonc mb-4">Vous avez déjà un compte ?</p>
        <div class="flex justify-center w-full">
          <Button property1="link-1" @click="navigateToLogin">
            Se connecter
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
import { ref, reactive, computed } from 'vue'
import ChampText from '~/components/BaseTextField.vue'
import Button from '~/components/BaseButton.vue'
import GlobalPopup from '~/components/GlobalPopup.vue'
import { usePopup } from '~/composables/usePopup'
import { registerUser } from '~/services/authService'

const firstname = ref('')
const lastname = ref('')
const mail = ref('')
const password = ref('')
const confirmPassword = ref('')
const loading = ref(false)
const popup = usePopup()

// Erreurs de validation
const formErrors = reactive({
  firstname: '',
  lastname: '',
  mail: '',
  password: '',
  confirmPassword: ''
})

// Computed properties
const popupOpen = computed(() => popup.isOpen.value)
const popupMessage = computed(() => popup.message.value)
const popupTitle = computed(() => popup.title.value)

// Handlers
const handleClosePopup = () => {
  popup.closePopup()
}

// Validation du formulaire
const validateForm = () => {
  let isValid = true

  // Validation prénom
  if (!firstname.value) {
    formErrors.firstname = 'Le prénom est requis'
    console.log('le prénom est requis')
    isValid = false
  } else {
    formErrors.firstname = ''
  }

  // Validation nom
  if (!lastname.value) {
    formErrors.lastname = 'Le nom est requis'
    console.log('le nom est requis')
    isValid = false
  } else {
    formErrors.lastname = ''
  }

  // Validation email
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!mail.value) {
    formErrors.mail = 'L\'email est requis'
    console.log('l\'email est requis')
    isValid = false
  } else if (!emailRegex.test(mail.value)) {
    formErrors.mail = 'Email invalide'
    console.log('email invalide')
    isValid = false
  } else {
    formErrors.mail = ''
  }

  // Validation mot de passe
  if (!password.value) {
    formErrors.password = 'Le mot de passe est requis'
    console.log('le mot de passe est requis')
    isValid = false
  } else if (password.value.length < 6) {
    formErrors.password = 'Le mot de passe doit contenir au moins 6 caractères'
    console.log('le mot de passe doit contenir au moins 6 caractères')
    isValid = false
  } else {
    formErrors.password = ''
  }

  // Validation confirmation mot de passe
  if (!confirmPassword.value) {
    formErrors.confirmPassword = 'La confirmation du mot de passe est requise'
    console.log('la confirmation du mot de passe est requise')
    isValid = false
  } else if (confirmPassword.value !== password.value) {
    formErrors.confirmPassword = 'Les mots de passe ne correspondent pas'
    console.log('les mots de passe ne correspondent pas')
    isValid = false
  } else {
    formErrors.confirmPassword = ''
  }

  return isValid
}

const handleSignup = async () => {
  try {
    console.log('Tentative d\'inscription')

    if (!validateForm()) {
      console.error('Formulaire invalide')
      return
    }

    loading.value = true

    const signupData = {
      firstname: firstname.value,
      lastname: lastname.value,
      mail: mail.value,
      password: password.value
    }

    console.log('Envoi des données d\'inscription:', signupData)

    // Appel au service d'inscription
    await registerUser(signupData)

    console.log('Inscription réussie')

    // Affichage du message de succès
    popup.showPopup(
        "Inscription réussie ! Veuillez vérifier votre email pour activer votre compte.",
        "Succès"
    )

    // Réinitialisation du formulaire
    firstname.value = ''
    lastname.value = ''
    mail.value = ''
    password.value = ''
    confirmPassword.value = ''

  } catch (error) {
    console.error('Erreur lors de l\'inscription:', error)

    // Affichage du message d'erreur
    popup.showPopup(
        error instanceof Error ? error.message : "Erreur lors de l'inscription",
        "Erreur"
    )
  } finally {
    loading.value = false
  }
}

const navigateToLogin = () => {
  navigateTo('/connexion')
}
</script>

<style scoped>
.font-orbitron {
  font-family: "Orbitron-Regular", sans-serif;
}
</style>