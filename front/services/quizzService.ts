interface QuizzResponse {
  quizz: {
    question: string;
    reponse: {
      id : Number;
      content: string;
    }[];
    type: string;
    time: number;
  }[];
}


interface QuizzAnswer {
    sessionId: number;
    participantId: number;
    questionId: number;
    answerId?: number;
    responseText: string;
    submittedAt: Date;
    isCorrect: boolean;
}

interface res {
  quizz: {
    question: string;
    reponse: string[];
    type: string;
    time: number;
  }[];
}


export async function getQuizz(id: Number): Promise<QuizzResponse> {
    const questionsUrl = '/api/question/quiz/' + id;
    console.log('URL des questions:', questionsUrl);
    const questionsResponse = await fetch(questionsUrl, { method: 'GET' });
    const questions = await questionsResponse.json();

    console.log('Questions brutes récupérées:', questions);

    // S'assurer que chaque question a bien un ID
    const quizz = await Promise.all(
        questions.map(async (question: any, questionIndex: number) => {
            const answersUrl = '/api/answer/question/' + question.id;
            console.log('URL des réponses pour question ID ' + question.id + ':', answersUrl);
            const answersResponse = await fetch(answersUrl, { method: 'GET' });
            const answers = await answersResponse.json();

            // Créer un array de réponses formatées
            const reponse: { id: number; content: string; isCorrect: boolean }[] = answers.map((answer: any) => ({
                id: answer.id,
                content: answer.content,
                isCorrect: answer.isCorrect
            }));

            // Retourner la question avec toutes ses propriétés importantes
            return {
                id: question.id,  // S'assurer que l'ID est bien présent
                question: question.content,
                reponse,
                type: question.type,
                time: question.displayTime,
                points: question.points
            };
        })
    );

    console.log('Questions formatées:', quizz);
    return { quizz };
}

// ~/services/quizService.ts

export async function createQuiz(quizData: any): Promise<any> {
    try {
        const response = await fetch('api/quiz', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(quizData)
        });
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Erreur lors de la création du quiz');
        }
        return await response.json();
    } catch (error) {
        console.error("Erreur dans createQuiz:", error);
        throw error;
    }
}


// Mise à jour de la fonction sendAnswer dans quizzService.ts

// Correction finale de la fonction sendAnswer dans quizzService.ts

export async function sendAnswer(answer: QuizzAnswer): Promise<void> {
    try {
        console.log('Préparation de l\'envoi de la réponse:', answer);

        // Valider les données avant l'envoi
        if (!answer.sessionId || answer.sessionId <= 0) {
            throw new Error('ID de session invalide');
        }

        if (!answer.participantId || answer.participantId <= 0) {
            throw new Error('ID de participant invalide');
        }

        if (!answer.questionId || answer.questionId <= 0) {
            throw new Error('ID de question invalide');
        }

        // Pour les réponses à choix multiples, vérifier si la réponse est correcte
        let isCorrect = 0; // IMPORTANT: Utiliser 0 ou 1 au lieu de false/true

        if (answer.answerId) {
            try {
                const answerId = answer.answerId;
                // Utiliser l'endpoint GET /answer/{id} pour obtenir les détails de la réponse
                const responseAnswerValid = await fetch(`/api/answer/${answerId}`, {
                    method: 'GET',
                });

                if (responseAnswerValid.ok) {
                    const answerData = await responseAnswerValid.json();
                    // Convertir le booléen en entier (0/1)
                    isCorrect = answerData.isCorrect ? 1 : 0;
                    console.log(`Réponse ${answerId} vérifiée, isCorrect=${isCorrect}`);
                } else {
                    console.warn(`Impossible de vérifier si la réponse ${answerId} est correcte`);
                }
            } catch (error) {
                console.warn('Erreur lors de la vérification de la réponse:', error);
                // Ne pas bloquer l'envoi, utiliser la valeur par défaut
            }
        }

        // Préparer les données à envoyer
        const payload = {
            sessionId: answer.sessionId,
            participantId: answer.participantId,
            questionId: answer.questionId,
            answerId: answer.answerId || null,
            responseText: answer.responseText || '',
            submittedAt: answer.submittedAt.toISOString(),
            isCorrect: isCorrect // 0 ou 1, pas de booléen
        };

        console.log('Envoi des données de réponse:', payload);

        // Envoyer la réponse
        const sessionAnswerUrl = '/api/session_responses';
        const response = await fetch(sessionAnswerUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include',
            body: JSON.stringify(payload),
        });

        if (!response.ok) {
            let errorMessage = `Erreur lors de l'envoi de la réponse: ${response.status}`;

            try {
                const errorData = await response.json();
                errorMessage = errorData.message || errorData.errors?.[0]?.message || errorMessage;
            } catch (e) {
                // Ignorer l'erreur de parsing JSON
            }

            throw new Error(errorMessage);
        }

        console.log('Réponse envoyée avec succès');
    } catch (error) {
        console.error("Erreur lors de l'envoi de la réponse:", error);
        throw error;
    }
}


export async function getQuizzFromSession(sessionCode : String ): Promise<Number> {

  const sessionUrl = '/api/quiz_session/';


    const responseAnswerValid = await fetch(sessionUrl + sessionCode, {
      method: 'GET',
    });

    if (!responseAnswerValid.ok) {
      throw new Error('Erreur lors de la récupération de la réponse');
    }
    
    const data = await responseAnswerValid.json();

    return data.quizId;
}
