interface QuizzResponse {
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

      // Extraction du contenu des réponses
      const reponse: string[] = answers.map((answer: any) => answer.content);


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
        const response = await fetch('http://localhost:8081/quiz', {
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

