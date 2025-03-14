// ~/services/scoreCalculationService.ts

/**
 * Calcule le score du participant en :
 *  1. Récupérant la session de quiz via son code
 *  2. Récupérant les questions associées au quiz (via quizId)
 *  3. Additionnant les points de chaque question (ici, on suppose que toutes les réponses sont correctes)
 *
 * @param sessionCode Le code de la session de quiz
 * @param participantId L'identifiant du participant (non utilisé ici, mais éventuellement utile pour
 *                      récupérer ses réponses dans une implémentation complète)
 * @returns Le score total calculé
 */
export async function calculateScore(sessionCode: string, participantId: number): Promise<number> {
    // Récupérer la session de quiz via son code
    const sessionResponse = await fetch(`/api/quiz_session/${sessionCode}`, {
      method: 'GET'
    });
    if (!sessionResponse.ok) {
      const errorData = await sessionResponse.json();
      throw new Error(errorData.message || 'Erreur lors de la récupération de la session de quiz');
    }
    const sessionData = await sessionResponse.json();
    const quizId: number = sessionData.quizId;
  
    // Récupérer toutes les questions du quiz
    const questionsResponse = await fetch(`/api/question/quiz/${quizId}`, {
      method: 'GET'
    });
    if (!questionsResponse.ok) {
      const errorData = await questionsResponse.json();
      throw new Error(errorData.message || 'Erreur lors de la récupération des questions');
    }
    // On s'attend à recevoir un tableau de questions
    const questions = await questionsResponse.json() as Array<{ points: number }>;
  
    // Calculer le score total en additionnant les points de chaque question
    const totalScore = questions.reduce((sum, question) => sum + question.points, 0);
    return totalScore;
  }
  