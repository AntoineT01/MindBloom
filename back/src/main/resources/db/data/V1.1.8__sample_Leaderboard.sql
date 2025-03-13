-- Insertion additionnelle de participants pour compléter les sessions

-- Pour la session 1, le script V1.1.7 a déjà inséré 2 participants (ID 1 et 2).
-- On ajoute un participant supplémentaire pour la session 1 (ID 14) en utilisant account_id 3 (existant).
INSERT INTO participants (id, session_id, account_id, nickname, joined_at)
VALUES (14, 1, 3, 'Nina', '2025-01-01 09:10:00');

-- Pour la session 2, le script V1.1.7 a déjà inséré 2 participants (ID 3 et 4).
-- On ajoute un participant supplémentaire pour la session 2 (ID 15) en utilisant account_id 5.
INSERT INTO participants (id, session_id, account_id, nickname, joined_at)
VALUES (15, 2, 5, 'Oscar', '2025-01-02 14:20:00');

-- Pour la session 3, le script V1.1.7 a inséré 1 participant (ID 5).
-- On ajoute un participant supplémentaire pour la session 3 (ID 16) en utilisant account_id 2.
INSERT INTO participants (id, session_id, account_id, nickname, joined_at)
VALUES (16, 3, 2, 'Pam', '2025-01-03 10:35:00');

-- Pour la session 4, on insère 3 participants (IDs 6, 7, 8) en utilisant des account_id existants.
INSERT INTO participants (id, session_id, account_id, nickname, joined_at)
VALUES
    (6, 4, 1, 'Frank', '2025-01-04 10:00:00'),
    (7, 4, 2, 'Grace', '2025-01-04 10:05:00'),
    (8, 4, 3, 'Heidi', '2025-01-04 10:10:00');

-- Pour la session 5, on insère 2 participants (IDs 9, 10) en utilisant des account_id existants.
INSERT INTO participants (id, session_id, account_id, nickname, joined_at)
VALUES
    (9, 5, 1, 'Ivan', '2025-01-05 11:05:00'),
    (10, 5, 2, 'Judy', '2025-01-05 11:10:00');

-- Pour la session 6, on insère 3 participants (IDs 11, 17, 18) en utilisant uniquement des account_id valides (3, 4 et 5).
INSERT INTO participants (id, session_id, account_id, nickname, joined_at)
VALUES
    (11, 6, 3, 'Karl', '2025-01-06 09:10:00'),
    (17, 6, 4, 'Quinn', '2025-01-06 09:15:00'),
    (18, 6, 5, 'Rachel', '2025-01-06 09:20:00');

-- Pour la session 7, on insère 2 participants (IDs 12, 19) en utilisant des account_id existants.
INSERT INTO participants (id, session_id, account_id, nickname, joined_at)
VALUES
    (12, 7, 1, 'Liam', '2025-01-07 12:05:00'),
    (19, 7, 2, 'Steve', '2025-01-07 12:10:00');

-- Pour la session 8, on insère 2 participants (IDs 13, 20) en utilisant des account_id existants.
INSERT INTO participants (id, session_id, account_id, nickname, joined_at)
VALUES
    (13, 8, 3, 'Mia', '2025-01-08 08:05:00'),
    (20, 8, 4, 'Trudy', '2025-01-08 08:10:00');

-- Insertion des données dans la table leaderboard
-- Maintenant, on utilise uniquement des participant_id qui existent
INSERT INTO leaderboard (quiz_session_id, participant_id, score)
VALUES
    -- Session 1 (session_id = 1) : 3 participants
    (1, 1, 80),
    (1, 2, 75),
    (1, 14, 90),

    -- Session 2 (session_id = 2) : 3 participants
    (2, 3, 85),
    (2, 4, 72),
    (2, 15, 95),

    -- Session 3 (session_id = 3) : 2 participants
    (3, 5, 60),
    (3, 16, 88),

    -- Session 4 (session_id = 4) : 3 participants
    (4, 6, 70),
    (4, 7, 92),
    (4, 8, 75),

    -- Session 5 (session_id = 5) : 2 participants
    (5, 9, 100),
    (5, 10, 66),

    -- Session 6 (session_id = 6) : 3 participants
    (6, 11, 78),
    (6, 17, 82),
    (6, 18, 93),

    -- Session 7 (session_id = 7) : 2 participants
    (7, 12, 65),
    (7, 19, 90),

    -- Session 8 (session_id = 8) : 2 participants
    (8, 13, 88),
    (8, 20, 70);
