# pages/connexion.vue
<template>
  <div class="container mx-auto px-4">
    <div class="flex flex-col items-center justify-center min-h-[80vh] max-w-md mx-auto space-y-8">

      <form @submit.prevent="handleLogin" class="w-full space-y-6">
        <!-- Champ email -->
        <ChampText
            v-model="email"
            :withIcon="true"
            :withButton="false"
            iconType="email"
            inputType="email"
            placeholder="Votre email"
        />

        <!-- Champ mot de passe -->
        <ChampText
            v-model="password"
            :withIcon="true"
            :withButton="false"
            iconType="password"
            inputType="password"
            placeholder="Votre mot de passe"
        />

        <!-- Bouton de connexion -->
        <div class="flex justify-center w-full">
          <Button property1="link-1" @click="handleLogin">
            Connexion
          </Button>
        </div>
      </form>

      <!-- Lien d'inscription -->
      <div class="text-center pt-4">
        <p class="text-violet-fonc mb-4">Vous n'avez pas de compte ?</p>
        <Button property1="validation" @click="navigateToSignup">
          S'inscrire
        </Button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import ChampText from '~/components/BaseTextField.vue'
import Button from '~/components/BaseButton.vue'

const email = ref('')
const password = ref('')

const handleLogin = async () => {
  try {
    console.log('Tentative de connexion avec:', {
      email: email.value,
      password: password.value
    })
  } catch (error) {
    console.error('Erreur de connexion:', error)
  }
}

const navigateToSignup = () => {
  console.log('Navigation vers inscription')
}
</script>

<style scoped>
.font-orbitron {
  font-family: "Orbitron-Regular", sans-serif;
}
</style>