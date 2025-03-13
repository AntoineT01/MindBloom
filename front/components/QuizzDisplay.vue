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

    <!-- Réponses pour le type "slots" -->
    <div v-if="type === 'multiple_choice'" class="w-1/2 grid grid-cols-2 gap-4 mt-6">
      <ReponseSlots
        v-for="(rep, index) in reponses"
        :key="index"
        :letter="rep.letter"
        :question="rep.text"
      />
    </div>

    <!-- Zone de réponse pour le type "open" -->
    <div v-if="type === 'open'" class="w-full bottom-10 flex justify-center m-10">
      <TheOpenAnswer class="flex justify-center" @sendMessage="emitNext"/>
    </div>

    <!-- Bouton de validation et timer -->
    <div class="w-full fixed bottom-10 flex flex-col items-center">
      <div v-if="type === 'multiple_choice'" class="mt-6">
        <BaseButton property1="validation" @click="emitNext">
          Valider
        </BaseButton>
      </div>
      <div class="mt-4 w-3/4">
        <!-- On utilise ici la prop "duration" et on ajoute :key pour forcer la recréation du timer -->
        <TheTimer :duration="duration" :key="$parent.currentIndex" @timeUp="emitNext" />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'QuizzDisplay',
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
  },
  methods: {
    emitNext() {
      this.$emit('next')
    }
  }
}
</script>
