// components/GlobalPopup.vue
<template>
  <Teleport to="body">
    <div v-if="showPopup" class="fixed inset-0 z-50 flex items-center justify-center">
      <!-- Overlay -->
      <div class="fixed inset-0 bg-black bg-opacity-50" @click="handleClose"></div>

      <!-- Popup content -->
      <div class="relative bg-white rounded-lg shadow-xl p-6 max-w-md w-full mx-4 z-10">
        <h2 class="text-xl font-semibold mb-2">{{ title }}</h2>
        <p class="text-gray-600 mb-6">{{ message }}</p>
        <div class="flex justify-end">
          <button
              @click="handleClose"
              class="px-4 py-2 bg-violet-600 text-white rounded hover:bg-violet-700 transition-colors"
          >
            {{ buttonText }}
          </button>
        </div>
      </div>
    </div>
  </Teleport>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  isOpen: boolean
  message: string | null
  title?: string
  buttonText?: string
}

const props = withDefaults(defineProps<Props>(), {
  title: 'Message',
  buttonText: 'OK'
})

const emit = defineEmits<{
  (e: 'close'): void
}>()

const showPopup = computed(() => {
  return props.isOpen && props.message !== null && props.message !== ''
})

const handleClose = () => {
  emit('close')
}
</script>