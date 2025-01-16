<template>
  <div class="container mx-auto px-4 py-8">

    <div class="flex justify-center m-8">
      <RejoindreEvenement @join-event="handleJoinEvent" />
    </div>

  </div>
</template>

<script setup lang="ts">

const handleJoinEvent = (code: string) => {
  console.log('Tentative de rejoindre l\'événement avec le code:', code)
  // Ajoutez ici la logique pour gérer le code d'événement
}
</script>