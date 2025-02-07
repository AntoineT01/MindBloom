<script setup>
const roast = "Ah, Mathieu... Le seul mec qui voit un pigeon lâcher sa \"création\" et se dit : \"Mmmh, un snack gratuit !\" 🍽️💩\n"
            + "Frérot, t’as tellement un palais raffiné que même Duolingo t’a envoyé un message : "
            + "\"Bro, c’est pas ça qu’on entend par 'goûter la culture'.\"\n\n"
            + "Ton plat préféré, c’est le \"Nugget Surprise\", sauf que la surprise, c’est que c’est littéralement une fiente de moineau.\n"
            + "Tu veux tellement impressionner Duolingo que t’as appris à dire \"Délicieux\" en 15 langues, juste pour complimenter ton assiette biodégradable.\n\n"
            + "Bref, Mathieu, t’es le seul gars qui voit un banc de pigeons et se dit : \"All you can eat.\" 😭";
</script>

<template>
  <NuxtLayout>
    <TheAnswerDisplay 
      :profileEnabled="true" 
      playerName="julie.p" 
      playerImage="" 
      :texte="roast" 
    />
  </NuxtLayout>
</template>
