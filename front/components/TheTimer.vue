<template>
  <div class="flex items-center w-full">
    <div class="w-full flex items-center bg-violet-fonc rounded-full">
      <div class="h-2 bg-violet-clair rounded-full" :style="{ width: progress + '%' }"></div>
    </div>
    <div class="text-black font-bold text-3xl">{{ formatTime(remainingTime) }}s</div>
  </div>
</template>

<script>
export default {
  name: 'TheTimer',
  props: {
    duration: {
      type: Number,
      required: true
    },
    questionId: {
      type: [Number, String],
      default: 0
    }
  },
  data() {
    return {
      remainingTime: this.duration,
      progress: 100, // Initialisation à 100%
      interval: null,
      startTime: null
    };
  },
  watch: {
    // Dès que questionId change, on réinitialise le timer
    questionId(newVal, oldVal) {
      if(newVal !== oldVal) {
        this.resetTimer();
      }
    }
  },
  mounted() {
    this.startProgress();
  },
  beforeUnmount() {
    clearInterval(this.interval);
  },
  methods: {
    startProgress() {
      this.startTime = performance.now();
      this.interval = setInterval(() => {
        const elapsed = (performance.now() - this.startTime) / 1000;
        this.remainingTime = Math.max(this.duration - elapsed, 0);
        this.progress = Math.max((this.remainingTime / this.duration) * 100, 0).toFixed(2);
        if (this.remainingTime <= 0) {
          clearInterval(this.interval);
          this.$emit('timeUp');
        }
      }, 10);
    },
    resetTimer() {
      // On arrête l'intervalle en cours et on réinitialise le timer
      clearInterval(this.interval);
      this.remainingTime = this.duration;
      this.progress = 100;
      this.startProgress();
    },
    formatTime(time) {
      return `${time.toFixed(1)}`;
    }
  }
};
</script>
