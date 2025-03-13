<template>
    <div class="flex items-center justify-between border-8 border-violet-clair max-w-3xl mx-auto p-4 rounded-xl">
      <!-- Conteneur gauche : texte centré -->
      <div class="flex-1 text-center">
        <span class="text-3xl font-orbitron font-bold ">TOP {{ classement }}</span>
      </div>
  
      <!-- Conteneur central : profil -->
      <div class="flex-1 text-center">
        <Profile name="Julie.P" avatar="https://avatar.iran.liara.run/public" color="#AD49E1" :tag="false" class="w-[100%] h-[100%]"/>
      </div>
  
      <!-- Conteneur droit : texte centré -->
      <div class="flex-1 text-center">
        <span class="text-3xl font-orbitron font-bold ">{{ points }} pts</span>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      points: {
        type: Number,
        required: true
      },
      classement: {
        type: Number,
        required: true
      },
      nom : {
        type:String,
        required : true
      },
      avatar : {
        type : String,
        required : true
      }

    }

  };
  </script>
  