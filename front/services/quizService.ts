// ~/services/quizService.ts

import { getAuthToken } from './authService';

export interface Quiz {
  id?: number;
  title: string;
  description: string;
  accountCreator: any; // ici, vous pouvez mettre un objet détaillé
  isPublic: boolean;
  showAnswers: boolean;
  showFinalScore: boolean;
  timeLimit?: number;
  createdAt?: string;
  updatedAt?: string;
  status: string;
  shareCode: string;
}

/**
 * Crée un quiz dans la base via POST /quiz.
 * @param quizData Les données du quiz
 * @returns La réponse du serveur (le quiz créé)
 * @throws Error en cas de problème
 */
export async function createQuiz(quizData: Quiz): Promise<Quiz> {
  try {
    const authToken = getAuthToken();
    const headers: HeadersInit = {
      'Content-Type': 'application/json'
    };
    if (authToken) {
      headers['Authorization'] = `Bearer ${authToken}`;
    }
    const response = await fetch('/api/quiz', {
      method: 'POST',
      headers,
      body: JSON.stringify(quizData)
    });
    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || 'Erreur lors de la création du quiz');
    }
    return await response.json();
  } catch (error) {
    console.error('Erreur dans createQuiz:', error);
    throw error;
  }
}
