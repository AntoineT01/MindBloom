<template>
  <div class="container mx-auto px-4 py-8">
    <div class="flex flex-col items-center justify-center space-y-8">
      <h1 class="text-3xl font-bold text-violet-fonc">Modifier mon profil</h1>

      <div class="w-full max-w-2xl bg-white rounded-lg shadow-xl p-8">
        <!-- Avatar avec prévisualisation -->
        <div class="flex justify-center mb-8">
          <div class="relative">
            <div
                class="h-40 w-40 border-[8px] rounded-full bg-white flex items-center justify-center shadow-lg"
                :style="{ borderColor: '#A1A1A1', borderStyle: 'solid' }"
            >
              <img
                  :src="avatarPreview"
                  class="h-[90%] w-[90%] rounded-full object-cover"
                  alt="Avatar de l'utilisateur"
              />
            </div>
          </div>
        </div>

        <!-- Formulaire de modification -->
        <div class="space-y-6">
          <!-- Prénom -->
          <div class="space-y-2">
            <ChampText
                v-model="formData.firstname"
                :withIcon="true"
                :withButton="false"
                iconType="user"
                inputType="text"
                placeholder="Votre prénom"
                :error="formErrors.firstname"
            />
          </div>

          <!-- Nom -->
          <div class="space-y-2">
            <ChampText
                v-model="formData.lastname"
                :withIcon="true"
                :withButton="false"
                iconType="user"
                inputType="text"
                placeholder="Votre nom"
                :error="formErrors.lastname"
            />
          </div>

          <!-- Email -->
          <div class="space-y-2">
            <ChampText
                v-model="formData.mail"
                :withIcon="true"
                :withButton="false"
                iconType="email"
                inputType="email"
                placeholder="Votre email"
                :error="formErrors.mail"
            />
          </div>

          <!-- Locale (langue) -->
          <div class="space-y-2">
            <label class="block text-sm font-medium text-gray-700 mb-1">Langue préférée</label>
            <select
                v-model="formData.locale"
                class="bg-faux-blanc border border-gray-300 text-violet-fonc font-['Orbitron'] rounded-[90px] block w-full p-2.5"
            >
              <option value="fr">Français</option>
              <option value="en">English</option>
              <option value="es">Español</option>
              <option value="de">Deutsch</option>
            </select>
          </div>

          <!-- Boutons d'action -->
          <div class="flex justify-between pt-4">
            <Button
                property1="delete"
                @click="cancelEdit"
                type="button"
            >
              Annuler
            </Button>

            <Button
                property1="validation"
                @click="updateProfile"
                :disabled="loading"
                type="button"
            >
              {{ loading ? 'Mise à jour...' : 'Enregistrer' }}
            </Button>
          </div>
        </div>
      </div>
    </div>

    <!-- Popup pour les notifications -->
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
import { getUserData, updateUserProfile, isUserLoggedIn } from '~/services/authService'

// État du composant
const formData = reactive({
  id: 0,
  firstname: '',
  lastname: '',
  mail: '',
  profile: { id: 0, label: '' },
  locale: 'fr'
})

const formErrors = reactive({
  firstname: '',
  lastname: '',
  mail: ''
})

const loading = ref(false)
const popup = usePopup()

// Gestion du popup
const popupOpen = computed(() => popup.isOpen.value)
const popupMessage = computed(() => popup.message.value)
const popupTitle = computed(() => popup.title.value)

const handleClosePopup = () => {
  popup.closePopup()
}

// Génération de l'avatar basé sur le nom
const avatarPreview = computed(() => {
  return `https://api.dicebear.com/7.x/initials/svg?seed=${formData.firstname}+${formData.lastname}`
})

// Initialisation du formulaire
onMounted(() => {
  if (process.client) {
    // Rediriger vers la page de connexion si l'utilisateur n'est pas connecté
    if (!isUserLoggedIn()) {
      navigateTo('/connexion')
      return
    }

    // Récupérer les données de l'utilisateur
    const userData = getUserData()
    if (userData) {
      formData.id = userData.id
      formData.firstname = userData.firstname || ''
      formData.lastname = userData.lastname || ''
      formData.mail = userData.email || ''
      formData.profile = userData.profile || { id: 1, label: 'User' }
      formData.locale = userData.locale || 'fr'
    }
  }
})

// Validation du formulaire
const validateForm = () => {
  let isValid = true

  if (!formData.firstname) {
    formErrors.firstname = 'Le prénom est requis'
    isValid = false
  } else {
    formErrors.firstname = ''
  }

  if (!formData.lastname) {
    formErrors.lastname = 'Le nom est requis'
    isValid = false
  } else {
    formErrors.lastname = ''
  }

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!formData.mail) {
    formErrors.mail = 'L\'email est requis'
    isValid = false
  } else if (!emailRegex.test(formData.mail)) {
    formErrors.mail = 'Email invalide'
    isValid = false
  } else {
    formErrors.mail = ''
  }

  return isValid
}

// Mise à jour du profil
const updateProfile = async () => {
  console.log('Fonction updateProfile appelée');

  if (!validateForm()) {
    console.log('Validation du formulaire échouée');
    return;
  }

  loading.value = true;
  console.log('Début de la mise à jour du profil avec les données:', formData);

  try {
    await updateUserProfile({
      id: formData.id,
      firstname: formData.firstname,
      lastname: formData.lastname,
      mail: formData.mail,
      profile: formData.profile,
      locale: formData.locale
    });

    console.log('Mise à jour du profil réussie');
    popup.showPopup('Votre profil a été mis à jour avec succès !', 'Succès');

    // Attendre un peu avant de rediriger
    setTimeout(() => {
      navigateTo('/profil');
    }, 1500);
  } catch (error) {
    console.error('Erreur lors de la mise à jour du profil:', error);

    popup.showPopup(
        error instanceof Error ? error.message : 'Erreur lors de la mise à jour du profil',
        'Erreur'
    );
  } finally {
    loading.value = false;
  }
};

// Annuler la modification
const cancelEdit = () => {
  navigateTo('/profil')
}
</script>