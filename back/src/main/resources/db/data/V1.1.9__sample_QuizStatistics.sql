-- V1.1.10__sample_QuizStatistics.sql
-- Exemple d'insertion de données pour la table "quiz_statistics"
-- Colonnes : (id, quiz_id, total_participants, average_score, average_time_per_question)
-- On suppose que "id" est auto-incrément, donc on ne l'insère pas directement.
-- Assurez-vous que quiz_id 1..10 existent dans la table "quiz".

INSERT INTO quiz_statistics (quiz_id, total_participants, average_score, average_time_per_question)
VALUES
    (1,  50, 80.20, 12.50),  -- Quiz 1
    (2,  35, 75.40, 15.10),  -- Quiz 2
    (3,  60, 90.00, 20.75),  -- Quiz 3
    (4,  25, 68.50, 10.00),  -- Quiz 4
    (5,  45, 72.30, 18.00),  -- Quiz 5
    (6,  40, 85.00, 13.50),  -- Quiz 6
    (7,  55, 78.65, 16.20),  -- Quiz 7
    (8,  30, 92.10, 14.00),  -- Quiz 8
    (9,  20, 70.00, 11.50),  -- Quiz 9
    (10, 10, 95.00, 25.00);  -- Quiz 10
