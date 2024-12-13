<template>
  <div class="container mx-auto px-4 py-8">
    <HumanBody/>
  </div>
</template>

<script setup lang="ts">
// Aucune logique spécifique n'est nécessaire pour l'instant
import HumanBody from "~/components/HumanBody.vue";
</script>

<style>
body {
  background-image: url('/images/background/fond.png');  /* Ajout du point-virgule */
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}
</style>