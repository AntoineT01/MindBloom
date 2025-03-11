<template>

  <div class="flex flex-col items-center w-full bg-gray-100 min-h-screen p-6">
    <!-- Zone de Question (3/5 de l'écran) -->
    <div class="w-3/5 flex flex-col items-center bg-white shadow-lg rounded-lg p-6 h-3/5">
      <div class="w-full flex justify-center">
        <div class="w-3/4 flex justify-center">
          <slot></slot>
        </div>
      </div>
      <h2 class="text-center text-xl font-bold mt-4 text-purple-700">{{ question }}</h2>
    </div>



    <!-- Réponses (juste après la zone de question) -->
    <div v-if="type == 'slots'" class="w-1/2 grid grid-cols-2 gap-4 mt-6 ">
      <ReponseSlots v-for="(rep, index) in reponses" :key="index" :letter="rep.letter" :question="rep.text" />
    </div>


    <div v-if="type == 'open'" class="w-full bottom-10 flex justify-center m-10">
      <TheOpenAnswer class=" flex justify-center" />
    </div>


    <div class="w-full fixed bottom-10 flex flex-col items-center">
      <div v-if="type == 'slots'" class="mt-6">
        <BaseButton property1="validation">
          Valider
        </BaseButton>
      </div>
      <div class="mt-4 w-3/4">

        <TheTimer :duration="duration" />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    question: {
      type: String,
      required: true
    },
    reponses: {
      type: Array,
      required: true
    },
    duration: {
      type: Number,
      required: true
    },
    type: {
      type: String,
      required: true
    }
  }
};
</script>

<style scoped>
body {
  background-color: #f4f4f9;
}
</style>