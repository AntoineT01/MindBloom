<template>
  <div>

    <section class="bg-white shadow-md rounded-lg p-6 mb-8">
      <h2 class="text-2xl font-semibold mb-4">Compétences</h2>
      <div class="flex flex-wrap">
        <span
            v-for="skill in cv.skills"
            :key="skill"
            class="bg-blue-100 text-blue-800 px-3 py-1 rounded-full text-sm font-semibold mr-2 mb-2"
        >
          {{ skill }}
        </span>
      </div>
    </section>

    <section class="bg-white shadow-md rounded-lg p-6 mb-8">
      <h2 class="text-2xl font-semibold mb-4">Expérience</h2>
      <div v-for="job in cv.experience" :key="job.title" class="mb-4">
        <h3 class="text-xl font-semibold text-blue-600">{{ job.title }}</h3>
        <p class="text-gray-600 italic">{{ job.company }} | {{ job.period }}</p>
        <p class="text-gray-700 mt-2">{{ job.description }}</p>
      </div>
    </section>

    <section class="bg-white shadow-md rounded-lg p-6 mb-8">
      <h2 class="text-2xl font-semibold mb-4">Formation</h2>
      <div v-for="edu in cv.education" :key="edu.degree" class="mb-4">
        <h3 class="text-xl font-semibold text-blue-600">{{ edu.degree }}</h3>
        <p class="text-gray-600 italic">{{ edu.subject }}</p>

        <p class="text-gray-700 mt-2">{{ edu.school }}, {{ edu.period }}</p>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  cv: {
    summary: string;
    skills: string[];
    experience: Array<{
      title: string;
      company: string;
      period: string;
      description: string;
    }>;
    education: Array<{
      degree: string;
      school: string;
      year: string;
    }>;
  }
}>();
</script>