<template>
  <div
      :class="[
      'rounded-[90px] relative cursor-pointer',
      className,
      buttonStyles[currentState],
      isGoButton ? 'h-[45px] w-[45px]' : 'h-[50px] w-[200px]',
      disabled ? 'opacity-50 cursor-not-allowed' : ''
    ]"
      @mouseenter="handleMouseEnter"
      @mouseleave="handleMouseLeave"
      @mousedown="handleMouseDown"
      @mouseup="handleMouseUp"
      @click="handleClick"
  >
    <div
        class="font-['Orbitron-Regular'] text-base absolute text-center"
        :class="[
        textStyles[currentState],
        isGoButton ? 'w-[45px] top-[12px]' : 'w-[200px] top-[14px] left-0'
      ]"
    >
      <slot></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'

const props = defineProps({
  property1: {
    type: String,
    required: true,
    validator: (value: string) => [
      "delete-click",
      "link1-hover",
      "link2-hover",
      "link1-click",
      "link-1",
      "delete",
      "validation",
      "delete-hover",
      "validation-hover",
      "link2-click",
      "link-2",
      "validation-click",
      "go",
      "go-hover",
      "go-click"
    ].includes(value),
  },
  className: {
    type: String,
    default: "",
  },
  disabled: {
    type: Boolean,
    default: false
  }
})

// Définir les émetteurs d'événements
const emit = defineEmits(['click'])

const isHovered = ref(false)
const isClicked = ref(false)

const isGoButton = computed(() => {
  return ['go', 'go-hover', 'go-click'].includes(props.property1)
})

const currentState = computed(() => {
  if (props.disabled) {
    // Retourner l'état de base lorsque le bouton est désactivé
    return props.property1
  }

  if (isClicked.value) {
    switch (props.property1) {
      case 'link-1': return 'link1-click'
      case 'link-2': return 'link2-click'
      case 'delete': return 'delete-click'
      case 'validation': return 'validation-click'
      case 'go': return 'go-click'
      default: return props.property1
    }
  }
  if (isHovered.value) {
    switch (props.property1) {
      case 'link-1': return 'link1-hover'
      case 'link-2': return 'link2-hover'
      case 'delete': return 'delete-hover'
      case 'validation': return 'validation-hover'
      case 'go': return 'go-hover'
      default: return props.property1
    }
  }
  return props.property1
})

const handleMouseEnter = () => {
  if (!props.disabled) {
    isHovered.value = true
  }
}

const handleMouseLeave = () => {
  if (!props.disabled) {
    isHovered.value = false
    isClicked.value = false
  }
}

const handleMouseDown = () => {
  if (!props.disabled) {
    isClicked.value = true
  }
}

const handleMouseUp = () => {
  if (!props.disabled) {
    isClicked.value = false
  }
}

// Nouvelle fonction pour gérer les clics - sans preventDefault
const handleClick = (event) => {
  if (!props.disabled) {
    emit('click')
  }
}

const buttonStyles = {
  // Styles existants
  'link2-click': 'bg-faux-blanc shadow-[inset_0px_4px_4px_rgba(0,0,0,0.25)]',
  'validation': 'bg-vert shadow-[0px_4px_4px_rgba(0,0,0,0.25)]',
  'validation-hover': 'bg-faux-blanc shadow-[0px_4px_4px_rgba(0,0,0,0.25)]',
  'validation-click': 'bg-faux-blanc shadow-[inset_0px_4px_4px_rgba(0,0,0,0.25)]',
  'link-2': 'bg-violet-ple shadow-[0px_4px_4px_rgba(0,0,0,0.25)]',
  'delete-hover': 'bg-faux-blanc shadow-[0px_4px_4px_rgba(0,0,0,0.25)]',
  'link-1': 'bg-violet-clair shadow-[0px_4px_4px_rgba(0,0,0,0.25)]',
  'delete-click': 'bg-faux-blanc shadow-[inset_0px_4px_4px_rgba(0,0,0,0.25)]',
  'delete': 'bg-violet-fonc shadow-[0px_4px_4px_rgba(0,0,0,0.25)]',
  'link2-hover': 'bg-faux-blanc shadow-[0px_4px_4px_rgba(0,0,0,0.25)]',
  'link1-click': 'bg-faux-blanc shadow-[inset_0px_4px_4px_rgba(0,0,0,0.25)]',
  'link1-hover': 'bg-faux-blanc shadow-[0px_4px_4px_rgba(0,0,0,0.25)]',
  'go': 'bg-violet-ple shadow-[0px_4px_4px_rgba(0,0,0,0.25)]',
  'go-hover': 'bg-faux-blanc shadow-[0px_4px_4px_rgba(0,0,0,0.25)]',
  'go-click': 'bg-faux-blanc shadow-[inset_0px_4px_4px_rgba(0,0,0,0.25)]',
}

const textStyles = {
  // Styles existants
  'link2-click': 'text-violet-fonc',
  'validation': 'text-faux-blanc',
  'validation-hover': 'text-vert',
  'validation-click': 'text-vert',
  'link-2': 'text-violet-fonc',
  'delete-hover': 'text-violet-fonc',
  'link-1': 'text-faux-blanc',
  'delete-click': 'text-violet-fonc',
  'delete': 'text-faux-blanc',
  'link2-hover': 'text-violet-fonc',
  'link1-click': 'text-violet-clair',
  'link1-hover': 'text-violet-clair',
  'go': 'text-violet-fonc',
  'go-hover': 'text-violet-clair',
  'go-click': 'text-violet-clair',
}
</script>