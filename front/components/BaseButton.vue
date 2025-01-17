<template>
  <div
      :class="[
      'rounded-[90px] relative cursor-pointer',
      className,
      buttonStyles[currentState],
      isGoButton ? 'h-[45px] w-[45px]' : 'h-[50px] w-[200px]'
    ]"
      @mouseenter="handleMouseEnter"
      @mouseleave="handleMouseLeave"
      @mousedown="handleMouseDown"
      @mouseup="handleMouseUp"
  >
    <div
        class="font-['Orbitron-Regular'] text-base absolute text-center"
        :class="[
        textStyles[currentState],
        isGoButton ? 'w-[45px] top-[12px]' : 'w-[200px] top-[14px] left-0'
      ]"
    >
      <template v-if="['link-2', 'link2-click', 'link2-hover'].includes(currentState)">
        Se connecter
      </template>
      <template v-if="['link-1', 'link1-click', 'link1-hover'].includes(currentState)">
        S'inscrire
      </template>
      <template v-if="['delete-click', 'delete-hover', 'delete'].includes(currentState)">
        Supprimer
      </template>
      <template v-if="['validation-click', 'validation-hover', 'validation'].includes(currentState)">
        Valider
      </template>
      <template v-if="['go', 'go-hover', 'go-click'].includes(currentState)">
        GO
      </template>
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
})

const isHovered = ref(false)
const isClicked = ref(false)

const isGoButton = computed(() => {
  return ['go', 'go-hover', 'go-click'].includes(props.property1)
})

const currentState = computed(() => {
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
  isHovered.value = true
}

const handleMouseLeave = () => {
  isHovered.value = false
  isClicked.value = false
}

const handleMouseDown = () => {
  isClicked.value = true
}

const handleMouseUp = () => {
  isClicked.value = false
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