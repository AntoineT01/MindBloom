-- V1.1.5__sample_Leaderboard.sql
-- Exemple d'insertion de données pour la table "leaderboard"
-- Colonnes : (id, quiz_session_id, participant_id, score)

-- Suppose que vous avez déjà des sessions avec ID 1..5
-- et des participants avec ID 1..10
-- Vous pouvez ajuster ces valeurs selon votre base réelle.

INSERT INTO leaderboard (quiz_session_id, participant_id, score)
VALUES
    -- Session 1 : 3 participants
    (1, 1, 80),
    (1, 2, 75),
    (1, 3, 90),

    -- Session 2 : 3 participants
    (2, 2, 85),
    (2, 4, 72),
    (2, 5, 95),

    -- Session 3 : 2 participants
    (3, 1, 60),
    (3, 6, 88),

    -- Session 4 : 3 participants
    (4, 7, 70),
    (4, 8, 92),
    (4, 9, 75),

    -- Session 5 : 2 participants
    (5, 2, 100),
    (5, 10, 66),

    -- Session 6 : 3 participants
    (6, 4, 78),
    (6, 5, 82),
    (6, 6, 93),

    -- Session 7 : 2 participants
    (7, 1, 65),
    (7, 3, 90),

    -- Session 8 : 2 participants
    (8, 8, 88),
    (8, 9, 70);
