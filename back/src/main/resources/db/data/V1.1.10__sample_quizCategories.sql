-- V1.1.10__sample_quizCategories.sql
-- Exemple d'insertion de données pour la table "quiz_categories"
-- Colonnes : (quiz_id, category_id)

-- On suppose que vous avez 10 quiz (id = 1..10) et 10 catégories (id = 1..10).
-- Vous pouvez adapter selon le nombre réel de quiz et de catégories existants.

INSERT INTO quiz_categories (quiz_id, category_id)
VALUES
    -- Quiz 1 associée à catégories 1 et 2
    (1, 1),
    (1, 2),

    -- Quiz 2 associée à catégories 2 et 3
    (2, 2),
    (2, 3),

    -- Quiz 3 associée à catégories 1 et 4
    (3, 1),
    (3, 4),

    -- Quiz 4 associée à catégories 3 et 5
    (4, 3),
    (4, 5),

    -- Quiz 5 associée à catégories 1 et 6
    (5, 1),
    (5, 6),

    -- Quiz 6 associée à catégories 2 et 7
    (6, 2),
    (6, 7),

    -- Quiz 7 associée à catégories 1 et 5
    (7, 1),
    (7, 5),

    -- Quiz 8 associée à catégories 4 et 8
    (8, 4),
    (8, 8),

    -- Quiz 9 associée à catégories 2 et 9
    (9, 2),
    (9, 9),

    -- Quiz 10 associée à catégories 3 et 10
    (10, 3),
    (10, 10);
