<!-- File: components/SessionStatusBadge.vue -->
<template>
  <span :class="badgeClass">{{ statusText }}</span>
</template>
<script setup lang="ts">
import { computed, defineProps } from 'vue'
const props = defineProps<{ status: string }>()
const statusText = computed(() => {
  if (props.status === 'active') return 'Active'
  if (props.status === 'waiting') return 'En attente'
  return 'Aucune session'
})
const badgeClass = computed(() => {
  if (props.status === 'active') return 'bg-green-500 text-white px-2 py-1 rounded'
  if (props.status === 'waiting') return 'bg-yellow-500 text-white px-2 py-1 rounded'
  return 'bg-gray-500 text-white px-2 py-1 rounded'
})
</script>
