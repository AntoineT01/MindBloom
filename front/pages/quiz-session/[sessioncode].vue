<template>
  <div class="container mx-auto px-4 py-8">
    <div v-if="loading" class="text-center p-8">
      <div class="inline-block animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-violet-600 mb-4"></div>
      <p class="text-lg">Chargement de la session…</p>
    </div>

    <div v-else-if="error" class="text-center text-red-600 p-8">
      <p class="text-xl mb-4">{{ error }}</p>
      <button @click="goBack" class="mt-4 px-4 py-2 bg-violet-600 text-white rounded hover:bg-violet-700">
        Retour à l'accueil
      </button>
    </div>

    <div v-else class="bg-white rounded-lg shadow-md p-6">
      <div v-if="!sessionStarted">
        <div class="flex justify-between items-center mb-8">
          <h2 class="text-2xl font-bold text-violet-800">Salle d'attente</h2>
          <div class="bg-purple-100 text-purple-800 rounded-full px-4 py-1 text-sm font-semibold">
            {{ sessionData.status }}
          </div>
        </div>

        <div class="mb-6 p-4 bg-purple-50 rounded-lg">
          <p class="mb-2">Code de session : <span class="font-bold text-lg">{{ sessionCode }}</span></p>
          <p>Quiz : <span class="font-medium">{{ sessionData.quizTitle || 'Quiz #' + sessionData.quizId }}</span></p>
        </div>

        <div class="mb-8">
          <h3 class="text-xl mb-4 border-b pb-2">Participants ({{ participants.length }})</h3>

          <div v-if="participants.length" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            <div
                v-for="participant in participants"
                :key="participant.id"
                class="p-3 bg-gray-50 rounded-lg flex items-center space-x-3"
            >
              <div class="h-10 w-10 rounded-full bg-violet-200 flex items-center justify-center text-violet-700 font-bold">
                {{ getInitials(participant.nickname) }}
              </div>
              <div>
                <p class="font-medium">{{ participant.nickname }}</p>
                <p class="text-xs text-gray-500">
                  {{ formatJoinTime(participant.joinedAt) }}
                </p>
              </div>
            </div>
          </div>

          <div v-else class="p-4 bg-gray-50 rounded text-center">
            <p class="text-gray-500">Aucun participant pour le moment</p>
          </div>
        </div>

        <div v-if="isHost" class="mt-6 flex justify-center">
          <button
              @click="startSession"
              :disabled="participants.length === 0"
              :class="[
              'px-6 py-3 rounded-lg font-semibold transition',
              participants.length > 0
                ? 'bg-violet-700 text-white hover:bg-violet-800'
                : 'bg-gray-300 text-gray-500 cursor-not-allowed'
            ]"
          >
            Démarrer le quiz
          </button>
        </div>

        <div v-else class="mt-6 p-4 bg-gray-50 rounded-lg text-center">
          <p class="text-gray-600">En attente du démarrage du quiz par l'hôte…</p>
        </div>
      </div>

      <div v-else class="text-center p-8">
        <div class="inline-block animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-violet-600 mb-4"></div>
        <p class="text-xl font-semibold mb-2">Le quiz a démarré !</p>
        <p>Vous allez être redirigé vers le quiz...</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getQuizSession } from '~/services/quizSessionService'
import { getSessionParticipants } from '~/services/participantService'
import { getUserData } from '~/services/authService'

const route = useRoute()
const router = useRouter()

// Important : Utiliser une variable reactive simple, pas un computed
const sessionCode = ref('')

const loading = ref(true)
const error = ref(null)
const sessionStarted = ref(false)
const isHost = ref(false)
const participants = ref([])
const sessionData = ref({})
const intervalId = ref(null)
const currentUser = ref(getUserData())

// Récupérer les données de session stockées par EventJoin
const storedSessionData = ref(
    process.client ? JSON.parse(localStorage.getItem('current_session') || 'null') : null
)

// Fonction utilitaire pour obtenir les initiales d'un nom
const getInitials = (name) => {
  if (!name) return '?';
  return name
      .split(' ')
      .map(part => part.charAt(0))
      .join('')
      .toUpperCase()
      .slice(0, 2);
}

// Fonction pour formater le temps de connexion
const formatJoinTime = (joinedAt) => {
  if (!joinedAt) return '';
  const date = new Date(joinedAt);
  return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
}

const fetchSessionData = async () => {
  console.log('Récupération de la session avec le code:', sessionCode.value);

  try {
    if (!sessionCode.value) {
      error.value = 'Code de session manquant';
      loading.value = false;
      return;
    }

    // Récupérer les données de la session
    const session = await getQuizSession(sessionCode.value);
    console.log('Données de session récupérées:', session);
    sessionData.value = session;

    // Vérifier si la session est active
    if (session.status === 'active') {
      sessionStarted.value = true;
      // Attendre 2 secondes avant de rediriger
      setTimeout(() => {
        router.push({
          path: '/quizz',
          query: {
            sessionCode: sessionCode.value,
            participantId: storedSessionData.value?.participantId
          }
        });
      }, 2000);
    } else {
      sessionStarted.value = false;

      // Déterminer si l'utilisateur actuel est l'hôte
      if (currentUser.value) {
        isHost.value = session.hostId === currentUser.value.id;
      }

      // Récupérer la liste des participants
      try {
        const sessionParticipants = await getSessionParticipants(session.id);
        participants.value = sessionParticipants;
      } catch (participantsError) {
        console.warn('Erreur lors de la récupération des participants:', participantsError);
        // Ne pas bloquer l'affichage de la page si la récupération des participants échoue
      }
    }

    error.value = null;
  } catch (err) {
    console.error('Erreur lors de la récupération de la session:', err);
    error.value = err.message || 'Erreur lors de la récupération de la session';
  } finally {
    loading.value = false;
  }
}

const startSession = async () => {
  try {
    // Mettre à jour le statut de la session côté serveur
    const updatePayload = {
      id: sessionData.value.id,
      status: 'active',
      // Inclure d'autres champs nécessaires pour l'API
      quizId: sessionData.value.quizId,
      sessionMode: sessionData.value.sessionMode,
      startTime: new Date().toISOString(),
      endTime: null,
      sessionCode: sessionCode.value
    };

    // Appeler l'API pour mettre à jour la session
    await fetch(`/api/quiz_session/${sessionData.value.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(updatePayload)
    });

    // Marquer la session comme démarrée localement
    sessionStarted.value = true;

    // Rediriger vers la page du quiz
    setTimeout(() => {
      router.push({
        path: '/quizz',
        query: {
          sessionCode: sessionCode.value,
          participantId: storedSessionData.value?.participantId
        }
      });
    }, 2000);
  } catch (error) {
    console.error('Erreur lors du démarrage de la session:', error);
    alert('Impossible de démarrer la session. Veuillez réessayer.');
  }
}

const goBack = () => {
  router.push('/');
}

onMounted(() => {
  console.log('Montage du composant [sessionCode].vue');

  // IMPORTANT : Récupération du paramètre de route ici
  sessionCode.value = route.params.sessionCode;
  console.log('Code de session récupéré:', sessionCode.value);

  // Si le code n'est pas disponible dans la route mais est dans le localStorage
  if (!sessionCode.value && storedSessionData.value?.sessionCode) {
    sessionCode.value = storedSessionData.value.sessionCode;
    console.log('Code de session récupéré depuis le localStorage:', sessionCode.value);
  }

  // Exécuter immédiatement la récupération des données
  fetchSessionData();

  // Mettre en place un intervalle pour vérifier périodiquement l'état de la session
  intervalId.value = window.setInterval(() => {
    console.log('Intervalle de vérification de session déclenché');
    fetchSessionData();
  }, 5000);
})

onBeforeUnmount(() => {
  console.log('Démontage du composant [sessionCode].vue');

  // Nettoyer l'intervalle pour éviter les fuites de mémoire
  if (intervalId.value !== null) {
    clearInterval(intervalId.value);
    intervalId.value = null;
  }
})
</script>