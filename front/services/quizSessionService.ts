// File: ~/services/quizSessionService.ts
interface QuizSessionResponse {
    id?: string;
    quizId?: number;
    sessionMode?: string;
    status?: string;
    startTime?: string;
    endTime?: string;
    sessionCode?: string;
    message?: string;
}

export async function getAllQuizSessions(): Promise<Array<QuizSessionResponse>> {
    const res = await fetch('/api/quiz_session', { method: 'GET', headers: { 'Content-Type': 'application/json' } });
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

        // Headers pour la requête
        const headers: HeadersInit = {
            'Content-Type': 'application/json',
        };

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
            const joinUrl = `/api/quizz_session/${sessionCode}/join`;

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
    await fetch(`/api/quiz/${quizId}`, { method: 'DELETE', headers: { 'Content-Type': 'application/json' } });
}

export async function getQuizzesByUser(userId: number): Promise<Array<any>> {
    const res = await fetch(`/api/quiz/user/${userId}`, { method: 'GET', headers: { 'Content-Type': 'application/json' } });
    return await res.json();
}

export async function updateQuizStatus(quizId: number, status: string): Promise<any> {
    const res = await fetch(`/api/quiz/${quizId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ status })
    });
    return await res.json();
}
