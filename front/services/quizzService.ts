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


interface QuizzAnswer{
  sessionId: Number;
  participantId: Number;
  questionId: Number;
  answerId:Number;
  responseText: String;
  submittedAt: Date;
  isCorrect: Boolean
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

  // Récupérer la liste des questions
  const questionsResponse = await fetch(questionsUrl, { method: 'GET' });
  const questions = await questionsResponse.json();

  // Pour chaque question, récupérer les réponses et transformer les données
  const quizz = await Promise.all(
    questions.map(async (question: any) => {
      const answersUrl = '/api/answer/question/' + question.id;
      console.log('URL des réponses:', answersUrl);

      const answersResponse = await fetch(answersUrl, { method: 'GET' });
      const answers = await answersResponse.json();
      console.log(answers);

      // Extraction du contenu des réponses
      const reponse: { id: number; content: string }[] = answers.map((answer: any) => ({
        id: answer.id,
        content: answer.content,
      }));


      console.log({
        question: question.content,
        reponse,
        type: question.type,
        time: question.displayTime
      })

      return {
        question: question.content,
        reponse,
        type: question.type,
        time: question.displayTime
      };
    })
  );

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


export async function sendAnswer(answer : QuizzAnswer ): Promise<void> {

  const sessionAnswerUrl = '/api/session_responses';
  const isCorrectAnswerUrl = '/api/answer/';

    console.log(answer);

    const responseAnswerValid = await fetch(isCorrectAnswerUrl + answer.questionId, {
      method: 'GET',
    });

    if (!responseAnswerValid.ok) {
      throw new Error('Erreur lors de la récupération de la réponse');
    }
    
    const data = await responseAnswerValid.json();
    const isCorrect = data.isCorrect;

    const response = await fetch(sessionAnswerUrl, {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json',
      },
        credentials: 'include',  // Important pour recevoir et envoyer les cookies
        body: JSON.stringify({
          "sessionId": answer.sessionId,
          "participantId": answer.participantId,
          "questionId": answer.questionId,
          "answerId": answer.answerId,
          "responseText": answer.responseText,
          "submittedAt": answer.submittedAt,
          "isCorrect": isCorrect
        }),
    });
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
