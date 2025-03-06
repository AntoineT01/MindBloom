// ~/services/quizSessionService.ts

import { getAuthToken } from './authService'

interface QuizSessionResponse {
    id?: string;
    code?: string;
    status?: string;
    message?: string;
    // Ajoutez d'autres champs selon la structure de votre API
}

/**
 * Vérifie si une session de quiz existe et tente de la rejoindre
 * @param sessionCode Code de la session à rejoindre
 * @returns Les données de la session si trouvée
 * @throws Error si la session n'existe pas ou autre erreur
 */
export async function joinQuizSession(sessionCode: string): Promise<QuizSessionResponse> {
    try {
        console.log('Vérification de la session avec code:', sessionCode);

        // URL pour vérifier si la session existe
        const checkUrl = `/api/quizzsession/${sessionCode}/check`;

        console.log('URL de vérification:', checkUrl);

        // Récupération du token d'authentification (si l'utilisateur est connecté)
        const authToken = getAuthToken();

        // Headers pour la requête
        const headers: HeadersInit = {
            'Content-Type': 'application/json',
        };

        // Ajouter le token d'authentification si disponible
        if (authToken) {
            headers['Authorization'] = `Bearer ${authToken}`;
        }

        // Vérifier si la session existe
        const checkResponse = await fetch(checkUrl, {
            method: 'GET',
            headers
        });

        console.log('Response status (check):', checkResponse.status);

        // Vérifier si la réponse a du contenu JSON
        const contentType = checkResponse.headers.get('content-type');
        const hasContent = contentType && contentType.includes('application/json');

        if (!checkResponse.ok) {
            if (hasContent) {
                const errorData = await checkResponse.json();
                throw new Error(errorData.message || `La session ${sessionCode} n'existe pas`);
            } else {
                throw new Error(`Erreur ${checkResponse.status}: ${checkResponse.statusText}`);
            }
        }

        // Si pas de contenu JSON, retourner une réponse par défaut
        if (!hasContent) {
            console.log('La réponse ne contient pas de JSON valide');
            return { message: 'Session trouvée' };
        }

        // Parser la réponse JSON
        const sessionData = await checkResponse.json();
        console.log('Session trouvée:', sessionData);

        // Si la session est active, tenter de la rejoindre
        if (sessionData.status === 'active' || sessionData.status === 'waiting') {
            const joinUrl = `/api/quizzsession/${sessionCode}/join`;

            // Requête pour rejoindre la session
            const joinResponse = await fetch(joinUrl, {
                method: 'POST',
                headers
            });

            if (!joinResponse.ok) {
                const joinError = await joinResponse.json();
                throw new Error(joinError.message || "Impossible de rejoindre la session");
            }

            const joinData = await joinResponse.json();
            console.log('Session rejointe avec succès:', joinData);

            return joinData;
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

/**
 * Récupère les détails d'une session de quiz
 * @param sessionCode Code de la session à récupérer
 * @returns Les données détaillées de la session
 */
export async function getQuizSession(sessionCode: string): Promise<QuizSessionResponse> {
    try {
        const url = `/api/quizzsession/${sessionCode}`;
        const authToken = getAuthToken();

        const headers: HeadersInit = {
            'Content-Type': 'application/json',
        };

        if (authToken) {
            headers['Authorization'] = `Bearer ${authToken}`;
        }

        const response = await fetch(url, {
            method: 'GET',
            headers
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || "Impossible de récupérer les données de la session");
        }

        return await response.json();
    } catch (error) {
        console.error('Erreur lors de la récupération de la session:', error);
        if (error instanceof Error) {
            throw error;
        }
        throw new Error('Erreur lors de la récupération des données de la session');
    }
}