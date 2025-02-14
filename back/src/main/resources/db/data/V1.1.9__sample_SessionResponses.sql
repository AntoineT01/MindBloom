-- V1.1.11__sample_SessionResponses.sql
-- Exemple d'insertion de données dans la table "session_responses"
--
-- Colonnes : id (auto-incr), session_id, participant_id, question_id, answer_id, response_text, submitted_at, is_correct
-- On suppose que "id" est auto-incrément, donc on ne l'insère pas explicitement.
--
-- Ajustez les valeurs pour :
--  - session_id existant dans "quiz_session"
--  - participant_id existant dans "participants"
--  - question_id existant dans "question"
--  - answer_id existant (ou NULL si question ouverte / pas de réponse choisie)
--  - is_correct = 1 (correct) ou 0 (incorrect)

INSERT INTO session_responses
    (session_id, participant_id, question_id, answer_id, response_text, submitted_at, is_correct)
VALUES
    -- Session 1, Participant 1, Question 1, a multiple-choice answer (id=2?), correct
    (1, 1, 1, 2, NULL, '2025-01-01 09:02:00', 1),

    -- Session 1, Participant 1, Question 2, a multiple-choice answer (id=1?), incorrect
    (1, 1, 2, 1, NULL, '2025-01-01 09:05:00', 0),

    -- Session 1, Participant 2, Question 2, same question but different participant/answer
    (1, 2, 2, 2, NULL, '2025-01-01 09:08:00', 1),

    -- Session 2, Participant 3, Question 9, a correct answer
    (2, 3, 9, 2, NULL, '2025-01-02 14:12:00', 1),

    -- Session 2, Participant 4, Question 9, an incorrect attempt
    (2, 4, 9, 1, NULL, '2025-01-02 14:13:00', 0),

    -- Session 3, Participant 5, Question 15, correct
    (3, 5, 15, 2, NULL, '2025-01-03 10:32:00', 1),

    -- Session 3, Participant 5, Question 16, text-based attempt (ex. open question), answer_id = NULL
    (3, 5, 16, NULL, 'Mars is the red planet!', '2025-01-03 10:35:00', 1),

    -- Session 3, Participant 6, Question 16, same question but incorrect text
    (3, 6, 16, NULL, 'I think it is Jupiter', '2025-01-03 10:38:00', 0),

    -- Session 4, Participant 7, Question 24, correct multiple-choice
    (4, 7, 24, 2, NULL, '2025-01-04 16:48:00', 1),

    -- Session 4, Participant 7, Question 25, incorrect
    (4, 7, 25, 1, NULL, '2025-01-04 16:52:00', 0),

    -- Session 5, Participant 9, Question 30, correct
    (5, 9, 30, 2, NULL, '2025-01-05 18:07:00', 1),

    -- Session 5, Participant 10, Question 30, incorrect
    (5, 10, 30, 1, NULL, '2025-01-05 18:12:00', 0);
