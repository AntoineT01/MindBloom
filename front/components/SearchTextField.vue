<template>
  <div class="relative w-full">
    <!-- Champ de texte avec style arrondi -->
    <div
        :class="[
        'flex items-center bg-faux-blanc rounded-[90px] shadow-[0px_4px_4px_rgba(0,0,0,0.25)] w-full',
        'px-[30px] py-2.5',
        error ? 'border border-red-500' : ''
      ]"
    >
      <!-- Icône (si demandée) -->
      <div v-if="withIcon" class="mr-3">
        <slot name="icon">
          <svg v-if="iconType === 'search'" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#A089AD" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="11" cy="11" r="8"></circle>
            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
          </svg>
          <svg v-else-if="iconType === 'email'" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#A089AD" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"></path>
            <polyline points="22,6 12,13 2,6"></polyline>
          </svg>
          <svg v-else-if="iconType === 'password'" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#A089AD" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
            <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
          </svg>
        </slot>
      </div>

      <!-- Input -->
      <input
          :type="inputType"
          :placeholder="placeholder"
          class="bg-transparent w-full outline-none font-['Orbitron'] text-violet-fonc placeholder-violet-fonc/60"
          :value="modelValue"
          @input="$emit('update:modelValue', $event.target.value)"
      />
    </div>

    <!-- Message d'erreur -->
    <p v-if="error" class="text-red-500 text-sm mt-1 pl-4">{{ error }}</p>
  </div>
</template>

<script setup lang="ts">
defineProps({
  modelValue: {
    type: String,
    default: ''
  },
  withIcon: {
    type: Boolean,
    default: true
  },
  iconType: {
    type: String,
    default: 'search',
    validator: (value: string) => ['search', 'email', 'password'].includes(value)
  },
  inputType: {
    type: String,
    default: 'text'
  },
  placeholder: {
    type: String,
    default: ''
  },
  error: {
    type: String,
    default: ''
  }
})

defineEmits(['update:modelValue'])
</script>