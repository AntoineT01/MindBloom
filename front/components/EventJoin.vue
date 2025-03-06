<template>
  <div
      class="flex justify-center items-center flex-row gap-8 py-2.5 px-[30px] bg-[#AD49E1] rounded-[90px] w-full max-w-4xl">
    <!-- Texte "Rejoignez un événement" -->
    <span class="text-[#F4F4F4] font-['Orbitron'] text-center whitespace-nowrap">
      Rejoignez un événement
    </span>

    <!-- Champ de code -->
    <div class="flex-grow">
      <input
          v-model="eventCode"
          type="text"
          placeholder="Code de l'événement"
          class="w-full h-[46px] bg-[#FBF2FF] rounded-[90px] px-4 text-[#A089AD] font-['Orbitron'] outline-none text-center"
      />
    </div>

    <!-- Bouton GO -->
    <Button
        property1="go"
        @click="handleJoinEvent"
    >
      GO
    </Button>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import Button from './BaseButton.vue'

const props = defineProps({
  onJoin: {
    type: Function,
    default: () => {}
  }
})

const eventCode = ref('')

const handleJoinEvent = () => {
  if (eventCode.value.trim()) {
    props.onJoin(eventCode.value.trim())
  }
}
</script>