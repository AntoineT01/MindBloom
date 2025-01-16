<!-- components/Navbar.vue -->
<template>
  <nav class="bg-[#E6E6E6] h-[90px] w-full relative">
    <div class="container mx-auto h-full flex items-center pl-20 ">
      <!-- État déconnecté avec boutons Sinscrire et Se connecter -->
      <template v-if="type === 'connexion'">
        <div class="absolute left-1/2 transform -translate-x-1/2">
          <NuxtLink to="/" class="hover:opacity-80 transition-opacity">
            <h1 class="text-[40px] font-['Orbitron-Regular'] text-violet-clair">MindBloom</h1>
          </NuxtLink>
        </div>
        <div class="ml-auto flex items-center space-x-6">
          <Button property1="link-1" />
          <Button property1="link-2" />
        </div>
      </template>

      <!-- État connecté avec photo de profil et bouton déconnexion -->
      <template v-if="type === 'connect'">
        <div class="flex items-center">
          <img
              :src="userImage"
              alt="Profile"
              class="w-[68px] h-[68px] rounded-full object-cover"
          />
          <span class="ml-4 font-['Orbitron-Regular'] text-[40px] text-black">username</span>
        </div>
        <div class="absolute left-1/2 transform -translate-x-1/2">
          <NuxtLink to="/" class="hover:opacity-80 transition-opacity">
            <h1 class="text-[40px] font-['Orbitron-Regular'] text-violet-fonc">MindBloom</h1>
          </NuxtLink>
        </div>
        <Button class="ml-auto" property1="link2-hover" text="Se déconnecter" />
      </template>

      <!-- État hover sur les boutons -->
      <template v-if="type === 'd-connect'">
        <div class="absolute left-1/2 transform -translate-x-1/2">
          <NuxtLink to="/" class="hover:opacity-80 transition-opacity">
            <h1 class="text-[40px] font-['Orbitron-Regular'] text-violet-clair">MindBloom</h1>
          </NuxtLink>
        </div>
        <div class="ml-auto flex items-center space-x-6">
          <Button property1="link1-hover" />
          <Button property1="link2-hover" />
        </div>
      </template>
    </div>
  </nav>
</template>

<script setup lang="ts">
import { defineProps } from 'vue'

const props = defineProps({
  type: {
    type: String,
    validator: (value: string) => ['d-connect', 'connexion', 'connect'].includes(value),
    required: true
  },
  userImage: {
    type: String,
    default: '/default-avatar.png'
  }
})
</script>