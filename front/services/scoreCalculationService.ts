// ~/services/scoreCalculationService.ts

/**
 * Calcule le score total du participant pour une session donnée.
 * Le score est obtenu en additionnant les points des questions dont la réponse est correcte.
 *
 * @param sessionCode Le code de la session de quiz.
 * @param participantId L'identifiant du participant.
 * @returns Le score total calculé.
 */
export async function calculateScoreForParticipant(sessionCode: string, participantId: number): Promise<number> {
    // Récupérer la session de quiz via son code
    const sessionResponse = await fetch(`/api/quiz_session/${sessionCode}`, { method: 'GET' });
    if (!sessionResponse.ok) {
      const errorData = await sessionResponse.json();
      throw new Error(errorData.message || 'Erreur lors de la récupération de la session de quiz');
    }
    const sessionData = await sessionResponse.json();
    // On récupère le quizId et l'identifiant de la session (pour filtrer les réponses)
    const quizId: number = sessionData.quizId;
    const quizSessionId: number = sessionData.id;
  
    // Récupérer toutes les réponses de session
    const responsesResponse = await fetch('/api/session_responses', { method: 'GET' });
    if (!responsesResponse.ok) {
      const errorData = await responsesResponse.json();
      throw new Error(errorData.message || 'Erreur lors de la récupération des réponses');
    }
    const allResponses = await responsesResponse.json() as Array<{
      sessionId: number;
      participantId: number;
      questionId: number;
      isCorrect: number;
    }>;
  
    // Filtrer les réponses pour la session et le participant donnés
    const participantResponses = allResponses.filter(response =>
      response.sessionId === quizSessionId && response.participantId === participantId
    );
  
    // Récupérer toutes les questions associées au quiz
    const questionsResponse = await fetch(`/api/question/quiz/${quizId}`, { method: 'GET' });
    if (!questionsResponse.ok) {
      const errorData = await questionsResponse.json();
      throw new Error(errorData.message || 'Erreur lors de la récupération des questions');
    }
    const questions = await questionsResponse.json() as Array<{ id: number; points: number }>;
  
    // Créer une table de correspondance questionId -> points
    const pointsMap: { [questionId: number]: number } = {};
    questions.forEach((question) => {
      pointsMap[question.id] = question.points;
    });
  
    // Calculer le score en additionnant les points pour chaque réponse correcte
    const totalScore = participantResponses.reduce((sum, response) => {
      if (response.isCorrect === 1) {
        const pts = pointsMap[response.questionId] || 0;
        return sum + pts;
      }
      return sum;
    }, 0);
  
    return totalScore;
  }
  