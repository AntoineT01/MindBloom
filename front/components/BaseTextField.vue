# components/ChampText.vue
<template>
  <div
      :class="[
      'relative w-[412px] h-[45px] font-[Orbitron-Regular] rounded-[45px]',
      'transition-all duration-200 ease-in-out',
      isHovered && !isPressed ? 'shadow-lg' : '',
      isPressed ? 'button-shadow-inset' : 'button-shadow',
    ]"
      @mouseenter="handleMouseEnter"
      @mouseleave="handleMouseLeave"
      @mousedown="handleMouseDown"
      @mouseup="handleMouseUp"
  >
    <div
        class="rounded-[45px] bg-blanc w-full h-full flex items-center px-6 relative overflow-hidden"
        :class="[
        isPressed ? 'button-shadow-inset' : 'button-shadow'
      ]"
    >
      <!-- Icon au début du champ -->
      <div v-if="withIcon" class="mr-4 w-6 h-6">
        <img
            :src="getIconSrc"
            alt="icon"
            class="w-full h-full"
            :style="{ filter: 'invert(72%) sepia(10%) saturate(574%) hue-rotate(223deg) brightness(89%) contrast(85%)' }"
        />
      </div>

      <!-- Input -->
      <input
          :type="inputType"
          :placeholder="placeholder"
          :value="modelValue"
          @input="$emit('update:modelValue', $event.target.value)"
          class="bg-transparent outline-none flex-1 font-[Orbitron-Regular] text-violet-fonc placeholder-violet-fonc"
          :class="[
          withIcon ? 'pl-2' : 'pl-0',
        ]"
      />

      <!-- Bouton à droite -->
      <div
          v-if="withButton"
          class="absolute right-4 w-[35px] h-[35px] rounded-full flex items-center justify-center transition-all duration-200"
          :class="[
          isButtonHovered ? 'bg-blanc' : 'bg-faux-blanc',
          isButtonPressed ? 'button-shadow-inset' : 'button-shadow'
        ]"
          @mouseenter="handleButtonMouseEnter"
          @mouseleave="handleButtonMouseLeave"
          @mousedown="handleButtonMouseDown"
          @mouseup="handleButtonMouseUp"
      >
        <img
            src="/icons/X.svg"
            alt="close"
            class="w-5 h-5"
            :style="{ filter: isButtonHovered ? 'invert(22%) sepia(19%) saturate(2407%) hue-rotate(265deg) brightness(98%) contrast(85%)' : 'invert(70%)' }"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  },
  withIcon: {
    type: Boolean,
    default: false
  },
  withButton: {
    type: Boolean,
    default: false
  },
  iconType: {
    type: String,
    validator: value => ['email', 'password', 'time', 'points'].includes(value),
    default: 'email'
  },
  inputType: {
    type: String,
    default: 'text'
  },
  placeholder: {
    type: String,
    default: 'Votre texte...'
  }
})

defineEmits(['update:modelValue'])

// États
const isHovered = ref(false)
const isPressed = ref(false)
const isButtonHovered = ref(false)
const isButtonPressed = ref(false)

// Computed pour le chemin des icônes
const getIconSrc = computed(() => {
  const iconMap = {
    email: '/icons/at-email.svg',
    password: '/icons/lock-on.svg',
    time: '/icons/stopwatch.svg',
    points: '/icons/trophy.svg'
  }
  return iconMap[props.iconType]
})

// Handlers
const handleMouseEnter = () => isHovered.value = true
const handleMouseLeave = () => {
  isHovered.value = false
  isPressed.value = false
}
const handleMouseDown = () => isPressed.value = true
const handleMouseUp = () => isPressed.value = false

const handleButtonMouseEnter = () => isButtonHovered.value = true
const handleButtonMouseLeave = () => {
  isButtonHovered.value = false
  isButtonPressed.value = false
}
const handleButtonMouseDown = () => isButtonPressed.value = true
const handleButtonMouseUp = () => isButtonPressed.value = false
</script>