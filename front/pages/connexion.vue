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

      <!-- Lien vers l'inscription -->
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

    <!-- Popup de notification -->
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
import { useAuth } from '~/composables/useAuth'

// État du formulaire
const loginForm = reactive({
  email: '',
  password: ''
})

// État des erreurs du formulaire
const formErrors = reactive({
  email: '',
  password: ''
})

// Composables
const popup = usePopup()
const auth = useAuth()
const loading = ref(false)

// Rediriger si déjà connecté
onMounted(() => {
  if (process.client) {
    auth.checkAuth();
    if (auth.isAuthenticated.value) {
      navigateTo('/accueil-connecte');
    }
  }
})

// Validation du formulaire
const validateForm = () => {
  let isValid = true;

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!loginForm.email) {
    formErrors.email = 'L\'email est requis';
    isValid = false;
  } else if (!emailRegex.test(loginForm.email)) {
    formErrors.email = 'Email invalide';
    isValid = false;
  } else {
    formErrors.email = '';
  }

  if (!loginForm.password) {
    formErrors.password = 'Le mot de passe est requis';
    isValid = false;
  } else {
    formErrors.password = '';
  }

  return isValid;
};

// Vérification des erreurs
const hasErrors = computed(() => {
  return !!formErrors.email || !!formErrors.password;
});

// État du popup
const popupOpen = computed(() => popup.isOpen.value);
const popupMessage = computed(() => popup.message.value);
const popupTitle = computed(() => popup.title.value);

// Fermeture du popup
const handleClosePopup = () => {
  popup.closePopup();
};

// Connexion
const handleLogin = async () => {
  if (!validateForm()) {
    return;
  }

  loading.value = true;

  try {
    const success = await auth.login(loginForm.email, loginForm.password);

    if (success) {
      popup.showPopup("Connexion réussie !", "Succès");
      await navigateTo('/accueil-connecte');
    } else {
      popup.showPopup(auth.error.value || "Échec de la connexion", "Erreur");
    }
  } catch (error) {
    console.error('Login error:', error);
    popup.showPopup(
        error instanceof Error ? error.message : "Erreur de connexion au serveur",
        "Erreur"
    );
  } finally {
    loading.value = false;
  }
};

// Navigation vers la page d'inscription
const navigateToSignup = () => {
  navigateTo('/inscription');
};
</script>

<style scoped>
.font-orbitron {
  font-family: "Orbitron-Regular", sans-serif;
}
</style>