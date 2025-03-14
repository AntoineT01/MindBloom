import { createParticipant } from './participantService';
import { getUserData } from './authService';

interface QuizSessionResponse {
    id?: string | number;
    quizId?: number;
    sessionMode?: string;
    status?: string;
    startTime?: string;
    endTime?: string;
    sessionCode?: string;
    message?: string;
    participants?: Array<{ id: number, name: string }>;
    hostId?: number;
}

export async function getAllQuizSessions(): Promise<Array<QuizSessionResponse>> {
    const res = await fetch('/api/quiz_session', {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
    });
    return await res.json();
}

export async function startQuizSession(payload: any): Promise<QuizSessionResponse> {
    const res = await fetch('/api/quiz_session', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });
    return await res.json();
}

/**
 * Vérifie et rejoint une session de quiz en créant un participant
 * @param sessionCode Code de la session à rejoindre
 * @returns Données de la session et le participant créé
 */
export async function joinQuizSession(sessionCode: string): Promise<QuizSessionResponse> {
    try {
        console.log('Vérification de la session avec code:', sessionCode);

        if (!sessionCode) {
            throw new Error('Code de session non fourni');
        }

        // Étape 1: Vérifier que la session existe
        const checkUrl = `/api/quiz_session/${sessionCode}`;
        console.log('URL de vérification:', checkUrl);

        const checkResponse = await fetch(checkUrl, {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
        });

        console.log('Response status (check):', checkResponse.status);

        if (!checkResponse.ok) {
            const errorData = await checkResponse.json().catch(() => null);
            throw new Error(errorData?.message || `La session ${sessionCode} n'existe pas`);
        }

        // Étape 2: Récupérer les données de la session
        const sessionData = await checkResponse.json();
        console.log('Session trouvée:', sessionData);

        // Étape 3: Créer un participant pour cette session
        if (sessionData && sessionData.id) {
            try {
                const currentUser = getUserData();
                let nickname = 'Invité';
                let accountId = null;

                if (currentUser) {
                    nickname = `${currentUser.firstname} ${currentUser.lastname}`.trim();
                    accountId = currentUser.id;
                } else {
                    // Générer un nom aléatoire pour les utilisateurs anonymes
                    nickname = `Invité-${Math.floor(Math.random() * 10000)}`;
                }

                // Préparer les données pour créer un participant
                const participantData = {
                    sessionId: parseInt(sessionData.id.toString()),
                    accountId: accountId,
                    nickname: nickname,
                    joinedAt: new Date().toISOString()
                };

                // Créer le participant
                const participantResponse = await createParticipant(participantData);
                console.log('Participant créé:', participantResponse);

                // Retourner les données de session avec le participant créé
                return {
                    ...sessionData,
                    participantCreated: true,
                    participantId: participantResponse.id
                };
            } catch (participantError) {
                // Si la création du participant échoue, continuer car la session existe
                console.warn('Erreur lors de la création du participant, mais la session existe:', participantError);
                return {
                    ...sessionData,
                    participantCreated: false,
                    error: 'Impossible de créer un participant'
                };
            }
        }

        return sessionData;
    } catch (error) {
        console.error('Erreur dans le service quizSessionService:', error);
        if (error instanceof Error) {
            throw error;
        }
        throw new Error('Erreur lors de la recherche de la session');
    }
}

export async function getQuizSession(sessionCode: string): Promise<QuizSessionResponse> {
    try {
        if (!sessionCode) {
            throw new Error('Code de session non fourni');
        }

        console.log('Récupération des détails de la session:', sessionCode);
        const res = await fetch(`/api/quiz_session/${sessionCode}`, {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
        });

        if (!res.ok) {
            const errorData = await res.json().catch(() => null);
            throw new Error(errorData?.message || `La session ${sessionCode} n'existe pas`);
        }

        return await res.json();
    } catch (error) {
        console.error('Erreur lors de la récupération de la session:', error);
        throw error;
    }
}

export async function stopQuizSession(payload: any): Promise<QuizSessionResponse> {
    const fullPayload = {
        id: payload.id,
        quizId: payload.quizId,
        sessionMode: payload.sessionMode,
        status: payload.status,
        startTime: payload.startTime,
        endTime: payload.endTime,
        sessionCode: payload.sessionCode
    };
    const res = await fetch(`/api/quiz_session/${payload.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(fullPayload)
    });
    return await res.json();
}

export async function deleteQuiz(quizId: number): Promise<void> {
    await fetch(`/api/quiz/${quizId}`, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' }
    });
}

export async function getQuizzesByUser(userId: number): Promise<Array<any>> {
    const res = await fetch(`/api/quiz/user/${userId}`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
    });
    return await res.json();
}

export async function updateQuizStatus(quiz) {
    const res = await fetch(`/api/quiz/${quiz.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(quiz)
    });
    return await res.json();
}