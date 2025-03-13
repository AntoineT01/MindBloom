// ~/services/questionService.ts

export interface Question {
  id?: number;
  quizId: number;
  content: string;
  type: string; // 'multiple_choice' ou 'open_answer'
  points: number;
  questionOrder: number;
  isRequired: boolean;
  isActive: boolean;
  displayTime: number;
  timeMin?: number;
  timeMax?: number;
  createdAt?: string;
  updatedAt?: string;
  imported: boolean;
}

/**
 * Crée une question via POST /question.
 */
export async function createQuestion(questionData: Question): Promise<Question> {
  try {
    const headers: HeadersInit = {
      'Content-Type': 'application/json'
    };
    const response = await fetch('/api/question', {
      method: 'POST',
      headers,
      body: JSON.stringify(questionData)
    });
    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || 'Erreur lors de la création de la question');
    }
    return await response.json();
  } catch (error) {
    console.error('Erreur dans createQuestion:', error);
    throw error;
  }
}

export interface Answer {
  id?: number;
  questionId: number;
  content: string;
  type: string; // "multiple_choice" ou "open_answer"
  isCorrect: boolean;
  answerOrder: number;
  createdAt?: string;
  updatedAt?: string;
}

/**
 * Crée une réponse via POST /answer.
 */
export async function createAnswer(answerData: Answer): Promise<Answer> {
  try {
    const headers: HeadersInit = {
      'Content-Type': 'application/json'
    };
    const response = await fetch('/api/answer', {
      method: 'POST',
      headers,
      body: JSON.stringify(answerData)
    });
    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || 'Erreur lors de la création de la réponse');
    }
    return await response.json();
  } catch (error) {
    console.error('Erreur dans createAnswer:', error);
    throw error;
  }
}

export interface Media {
  id?: number;
  questionId: number;
  type: string; // "IMAGE" ou "VIDEO"
  url: string;
}

/**
 * Crée un média via POST /media.
 */
export async function createMedia(mediaData: Media): Promise<Media> {
  try {
    const headers: HeadersInit = {
      'Content-Type': 'application/json'
    };
    const response = await fetch('/api/media', {
      method: 'POST',
      headers,
      body: JSON.stringify(mediaData)
    });
    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || 'Erreur lors de la création du média');
    }
    return await response.json();
  } catch (error) {
    console.error('Erreur dans createMedia:', error);
    throw error;
  }
}
