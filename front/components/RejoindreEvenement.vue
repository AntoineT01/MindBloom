<template>
  <div class="flex items-center justify-center gap-8 bg-violet-clair rounded-[90px] h-[62px] w-[756px] px-[30px] py-[10px] relative overflow-hidden">
    <p class="font-['Orbitron-Regular'] text-center text-xl m-0">
      <span class="text-faux-blanc  ">Rejoignez un </span>
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
        @mousedown="handleJoinEvent"
    />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import Button from './Button.vue'

const eventCode = ref('')

const emit = defineEmits<{
  (e: 'join-event', code: string): void
}>()

const handleJoinEvent = () => {
  if (eventCode.value.trim()) {
    emit('join-event', eventCode.value)
  }
}
</script>