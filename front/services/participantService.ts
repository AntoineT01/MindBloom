interface Participant {
    id?: number;
    sessionId: number;
    accountId?: number;
    nickname: string;
    joinedAt?: string;
}

interface ParticipantResponse {
    id: number;
    sessionId: number;
    accountId: number;
    nickname: string;
    joinedAt: string;
}

/**
 * Crée un nouveau participant pour une session de quiz
 * @param participantData Données du participant à créer
 * @returns Les données du participant créé
 */
export async function createParticipant(participantData: Participant): Promise<ParticipantResponse> {
    try {
        console.log('Création d\'un participant avec les données:', participantData);

        const response = await fetch('/api/participants', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(participantData)
        });

        console.log('Statut de réponse pour la création de participant:', response.status);

        if (!response.ok) {
            const errorData = await response.json().catch(() => null);
            throw new Error(
                errorData?.errors?.[0]?.message ||
                `Erreur lors de la création du participant: ${response.status}`
            );
        }

        const createdParticipant = await response.json();
        console.log('Participant créé avec succès:', createdParticipant);
        return createdParticipant;
    } catch (error) {
        console.error('Erreur dans le service participantService:', error);
        if (error instanceof Error) {
            throw error;
        }
        throw new Error('Erreur lors de la création du participant');
    }
}

/**
 * Récupère les participants d'une session de quiz
 * @param sessionId ID de la session
 * @returns Liste des participants
 */
export async function getSessionParticipants(sessionId: number): Promise<ParticipantResponse[]> {
    try {
        const response = await fetch(`/api/participants/session/${sessionId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            const errorData = await response.json().catch(() => null);
            throw new Error(
                errorData?.errors?.[0]?.message ||
                `Erreur lors de la récupération des participants: ${response.status}`
            );
        }

        return await response.json();
    } catch (error) {
        console.error('Erreur lors de la récupération des participants:', error);
        if (error instanceof Error) {
            throw error;
        }
        throw new Error('Erreur lors de la récupération des participants');
    }
}