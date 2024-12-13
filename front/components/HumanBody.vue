<script setup lang="ts">
import { ref, watch } from 'vue';
import Organ from './Organ.vue';

const organs = ref([
  {
    name: 'coeur',
    src: '/assets/human_body/sketch_coeur.png',
    alt: 'Image d\'un coeur',
    position: { top: '39%', left: '60%' },
    size: '10%',
    visible: true,
    zIndex: 10,
    next: 'poumon',
  },
  {
    name: 'poumon',
    src: '/assets/human_body/sketch_poumons.png',
    alt: 'Image d\'un poumon',
    position: { top: '37%', left: '57%' },
    size: '30%',
    visible: false,
    zIndex: 1,
    next: 'epiderme/peau',
  },
  {
    name: 'epiderme/peau',
    src: '/assets/human_body/sketch_peau.png',
    alt: 'Image d\'une peau',
    position: { top: '42%', left: '25%' },
    size: '13%',
    visible: false,
    zIndex: 1,
    next: 'jambe',
  },
  {
    name: 'jambe',
    src: '/assets/human_body/sketch_os.webp',
    alt: 'Image d\'un os',
    position: { top: '80%', left: '78%' },
    size: '13%',
    visible: false,
    zIndex: 1,
    next: 'sueur',
  },
  {
    name: 'sueur',
    src: '/assets/human_body/sketch_sueur.webp',
    alt: 'Image d\'une sueur',
    position: { top: '45%', left: '82%' },
    size: '13%',
    visible: false,
    zIndex: 1,
    next: 'systeme_digestif',
  },
  {
    name: 'systeme_digestif',
    src: '/assets/human_body/sketch_systeme_digestif.png',
    alt: 'Image d\'une sueur',
    position: { top: '51%', left: '58%' },
    size: '13%',
    visible: false,
    zIndex: 1,
    next: 'cerveau',
  },
  {
    name: 'cerveau',
    src: '/assets/human_body/sketch_systeme_nerveux_central.png',
    alt: 'Image d\'un systeme nerveux central',
    position: { top: '27%', left: '61%' },
    size: '17%',
    visible: false,
    zIndex: 1,
  },
]);

import jsonData from '~/content/Shema.json';

const gameActivated = ref(true);
const showEndModal = ref(false);
const shouldShowEndModal = ref(false);
const gameAlreadyWon = ref(false);
const puissance4AlreadyWon = ref(false);
const showPuissance4Game = ref(false);

const toggleOrgan = (currentOrgan: string) => {
  const organ = organs.value.find((o) => o.name === currentOrgan);

  if (organ?.name === 'coeur' && gameActivated.value && !gameAlreadyWon.value) {
    showGameModal.value = true;
  }

  if (organ?.name === 'poumon' && gameActivated.value && !puissance4AlreadyWon.value) {
    showPuissance4Game.value = true;
  }

  const organData = jsonData['PARTIES_NORMALES'][currentOrgan]; // Récupère les données du JSON
  if (organData) {
    activeOrgan.value = {
      name: currentOrgan,
      ...organData, // Fusionne les données JSON dans `activeOrgan`
    };
  }
  if (organ && organ.next) {
    const nextOrgan = organs.value.find((o) => o.name === organ.next);
    if (nextOrgan) {
      nextOrgan.visible = true;
    }
  }
};

const closeModal = (currentOrgan: string) => {
  activeOrgan.value = null;

  const organ = organs.value.find((o) => o.name === currentOrgan);


  if (organ?.name === 'cerveau') {
    shouldShowEndModal.value = true; // Marque la modal de fin comme devant être affichée
  }
};

const closeEndModal = () => {
  showEndModal.value = false;
};

const closeGameModal = () => {
  showGameModal.value = false;
  gameAlreadyWon.value = true;
};

const closePuissance4Modal = () => {
  puissance4AlreadyWon.value = true;
  showPuissance4Game.value = false;
}

const activeOrgan = ref(null); // État pour l'organe actif
const heartOrgan = ref(false); // État pour l'organe actif
const showGameModal = ref(false); // Contrôle l'ouverture du GameModal

// Surveiller la fermeture de `Popup` pour afficher la modal de fin
watch(activeOrgan, (newValue) => {
  if (!newValue && shouldShowEndModal.value) {
    showEndModal.value = true;
    shouldShowEndModal.value = false;
  }
});
</script>

<template>
  <div class="human_container">
    <img src="/assets/human_body/sketch_personnage.png" alt="Image d'un humain" class="human_body"/>
    <Organ
        v-for="organ in organs"
        v-if="!activeOrgan"
        :key="organ.name"
        :name="organ.name"
        :src="organ.src"
        :alt="organ.alt"
        :position="organ.position"
        :size="organ.size"
        :visible="organ.visible"
        :zIndex="organ.zIndex"
        :onClick="() => toggleOrgan(organ.name)"
    />

    <GameModal v-if="showGameModal" @close="closeGameModal" @verified="closeGameModal"/>
    <EndModal v-if="showEndModal" @close="closeEndModal" />
    <Puissance4  v-if="showPuissance4Game" @close="closePuissance4Modal"/>

    <Popup
        v-if="activeOrgan && !heartOrgan"
        :title="`${activeOrgan.titre}`"
        :description="`${activeOrgan.description}`"
        :src="`${activeOrgan.src}`"
        :infoComplementaire="JSON.parse(JSON.stringify(activeOrgan.info_complementaire))"
        @close="closeModal(activeOrgan.name)"
    />
  </div>
</template>

<style scoped>
.human_container {
  position: relative;
  width: 800px;
  height: auto;
  margin: auto;
}

.human_body {
  width: 100%;
  height: 100%;
}
</style>
