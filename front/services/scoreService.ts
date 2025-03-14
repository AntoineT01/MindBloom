// ~/services/scoreService.ts

export interface LeaderboardDto {
    id?: number;            // Optionnel lors de la création
    quizSessionId: number;  // ID de la session de quiz associée
    participantId: number;  // ID du participant
    score: number;          // Score obtenu
  }
  
  /**
   * Envoie le score calculé vers l'API.
   * Utilise une requête POST vers l'endpoint /api/leaderboard.
   * 
   * @param leaderboard Les données du score à envoyer.
   * @returns La réponse de l'API sous forme d'objet LeaderboardDto.
   */
  export async function sendScore(leaderboard: LeaderboardDto): Promise<LeaderboardDto> {
    const url = '/api/leaderboard';
    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(leaderboard)
      });
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Erreur lors de l\'envoi du score');
      }
      return await response.json();
    } catch (error) {
      console.error('Erreur dans sendScore:', error);
      throw error;
    }
  }
  
  /**
   * Récupère le leaderboard complet depuis l'API.
   * Utilise une requête GET vers l'endpoint /api/leaderboard.
   * 
   * @returns Un tableau d'objets LeaderboardDto.
   */
  export async function fetchLeaderboard(): Promise<LeaderboardDto[]> {
    const url = '/api/leaderboard';
    try {
      const response = await fetch(url, {
        method: 'GET'
      });
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Erreur lors de la récupération du leaderboard');
      }
      return await response.json();
    } catch (error) {
      console.error('Erreur dans fetchLeaderboard:', error);
      throw error;
    }
  }
  