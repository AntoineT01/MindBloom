<!-- File: components/BaseButton.vue -->
<template>
  <div
      :class="[
      'rounded-full relative cursor-pointer transition-transform duration-150 ease-in-out transform hover:scale-105',
      className,
      buttonStyles[currentState],
      isGoButton ? 'h-10 w-10' : 'h-12 w-48',
      disabled ? 'opacity-50 cursor-not-allowed' : ''
    ]"
      @mouseenter="handleMouseEnter"
      @mouseleave="handleMouseLeave"
      @mousedown="handleMouseDown"
      @mouseup="handleMouseUp"
      @click="handleClick"
  >
    <div
        class="font-['Orbitron-Regular'] text-base absolute text-center flex items-center justify-center"
        :class="[
        textStyles[currentState],
        isGoButton ? 'w-10 top-2' : 'w-48 top-3 left-0'
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
    ].includes(value)
  },
  className: { type: String, default: "" },
  disabled: { type: Boolean, default: false }
})
const emit = defineEmits(['click'])
const isHovered = ref(false)
const isClicked = ref(false)
const isGoButton = computed(() => ['go', 'go-hover', 'go-click'].includes(props.property1))
const currentState = computed(() => {
  if (props.disabled) { return props.property1 }
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
const handleMouseEnter = () => { if (!props.disabled) { isHovered.value = true } }
const handleMouseLeave = () => { if (!props.disabled) { isHovered.value = false; isClicked.value = false } }
const handleMouseDown = () => { if (!props.disabled) { isClicked.value = true } }
const handleMouseUp = () => { if (!props.disabled) { isClicked.value = false } }
const handleClick = () => { if (!props.disabled) { emit('click') } }
const buttonStyles = {
  'link2-click': 'bg-gray-100 shadow-inner',
  'validation': 'bg-green-600 shadow-md',
  'validation-hover': 'bg-green-500 shadow-md',
  'validation-click': 'bg-green-700 shadow-inner',
  'link-2': 'bg-indigo-500 shadow-md',
  'delete-hover': 'bg-red-500 shadow-md',
  'link-1': 'bg-indigo-300 shadow-md',
  'delete-click': 'bg-red-700 shadow-inner',
  'delete': 'bg-red-600 shadow-md',
  'link2-hover': 'bg-gray-100 shadow-md',
  'link1-click': 'bg-gray-100 shadow-inner',
  'link1-hover': 'bg-gray-100 shadow-md',
  'go': 'bg-indigo-300 shadow-md',
  'go-hover': 'bg-indigo-400 shadow-md',
  'go-click': 'bg-indigo-500 shadow-inner'
}
const textStyles = {
  'link2-click': 'text-indigo-700',
  'validation': 'text-white',
  'validation-hover': 'text-white',
  'validation-click': 'text-white',
  'link-2': 'text-white',
  'delete-hover': 'text-white',
  'link-1': 'text-white',
  'delete-click': 'text-white',
  'delete': 'text-white',
  'link2-hover': 'text-indigo-700',
  'link1-click': 'text-indigo-800',
  'link1-hover': 'text-indigo-800',
  'go': 'text-indigo-700',
  'go-hover': 'text-indigo-800',
  'go-click': 'text-indigo-800'
}
</script>
