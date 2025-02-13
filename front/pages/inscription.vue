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
            class="w-full"
        />

        <ChampText
            v-model="lastname"
            :withIcon="true"
            :withButton="false"
            iconType="user"
            inputType="text"
            placeholder="Votre nom"
            class="w-full"
        />

        <ChampText
            v-model="mail"
            :withIcon="true"
            :withButton="false"
            iconType="email"
            inputType="email"
            placeholder="Votre email"
            class="w-full"
        />

        <ChampText
            v-model="password"
            :withIcon="true"
            :withButton="false"
            iconType="password"
            inputType="password"
            placeholder="Votre mot de passe"
            class="w-full"
        />

        <ChampText
            v-model="confirmPassword"
            :withIcon="true"
            :withButton="false"
            iconType="password"
            inputType="password"
            placeholder="Confirmez votre mot de passe"
            class="w-full"
        />

        <div class="flex justify-center w-full">
          <Button property1="validation" @click="handleSignup">
            S'inscrire
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
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import ChampText from '~/components/BaseTextField.vue'
import Button from '~/components/BaseButton.vue'

const firstname = ref('')
const lastname = ref('')
const mail = ref('')
const password = ref('')
const confirmPassword = ref('')

const handleSignup = async () => {
  try {
    if (password.value !== confirmPassword.value) {
      console.error('Les mots de passe ne correspondent pas')
      return
    }

    const signupData = {
      firstname: firstname.value,
      lastname: lastname.value,
      mail: mail.value,
      password: password.value
    }

    if (!Object.values(signupData).every(value => value)) {
      console.error('Veuillez remplir tous les champs')
      return
    }

    console.log('Tentative d\'inscription avec:', signupData)
  } catch (error) {
    console.error('Erreur lors de l\'inscription:', error)
  }
}

const navigateToLogin = () => {
  navigateTo('/connexion')
}
</script>