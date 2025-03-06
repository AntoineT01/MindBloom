<template>
  <form @submit.prevent="handleJoinEvent" class="flex items-center justify-center gap-8 bg-violet-clair rounded-[90px] h-[62px] w-[756px] px-[30px] py-[10px] relative overflow-hidden">
    <p class="font-['Orbitron-Regular'] text-center text-xl m-0">
      <span class="text-faux-blanc">Rejoignez un </span>
      <span class="text-blanc">événement</span>
    </p>

    <div class="relative h-[45px] w-[274px] -my-[1.5px]">
      <div class="bg-faux-blanc rounded-[90px] h-[45px] relative w-[272px]">
        <input
            v-model="eventCode"
            type="text"
            placeholder="Code de l'événement"
            class="font-['Orbitron-Regular'] text-base h-[17px] left-[21px] absolute top-[14px] w-[230px] text-center placeholder-[#A089AD] text-violet-fonc bg-transparent border-none outline-none"
        />
      </div>
    </div>

    <Button
        property1="go"
        class="-my-[1.5px]"
        :disabled="loading || !eventCode.trim()"
        type="submit"
        @click="handleJoinEvent"
    >
      GO
    </Button>
  </form>

  <!-- Popup pour les messages d'erreur -->
  <GlobalPopup
      :is-open="popupOpen"
      :message="popupMessage"
      :title="popupTitle"
      @close="handleClosePopup"
  />
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import Button from './BaseButton.vue'
import GlobalPopup from './GlobalPopup.vue'
import { usePopup } from '~/composables/usePopup'

const eventCode = ref('')
const loading = ref(false)
const popup = usePopup()

const popupOpen = computed(() => popup.isOpen.value)
const popupMessage = computed(() => popup.message.value)
const popupTitle = computed(() => popup.title.value)

const emit = defineEmits<{
  (e: 'join-event', code: string): void
}>()

const handleClosePopup = () => {
  popup.closePopup()
}

const handleJoinEvent = () => {
  const code = eventCode.value.trim()
  if (!code) return

  // Simplement émettre l'événement pour que le parent gère la logique
  loading.value = true
  console.log('Envoi du code de session au parent:', code)
  emit('join-event', code)

  // Réinitialiser le champ après soumission
  setTimeout(() => {
    loading.value = false
  }, 500)

  // Pour le débogage
  console.log('Événement join-event émis avec le code:', code)
}
</script>