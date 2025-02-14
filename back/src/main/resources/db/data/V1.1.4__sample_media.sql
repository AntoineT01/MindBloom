-- V1.1.6__sample_media.sql
-- Exemple d'insertion de données pour la table "media"
-- Colonnes : (id, question_id, type, url)
--
-- Suppose que vous avez des question_id existants de 1 à 10 par exemple.
-- Ajustez ces valeurs et les URLs selon votre jeu de données réel.

INSERT INTO media (question_id, type, url)
VALUES
    -- Pour la question 1, on ajoute une image et une vidéo
    (1, 'image', 'https://example.com/images/question1_diagram.png'),
    (1, 'video', 'https://example.com/videos/question1_explanation.mp4'),

    -- Pour la question 2, on ajoute un fichier audio
    (2, 'audio', 'https://example.com/audios/question2_hints.mp3'),

    -- Pour la question 3, on ajoute une image
    (3, 'image', 'https://example.com/images/question3_chart.jpg'),

    -- Pour la question 4, vidéo et audio
    (4, 'video', 'https://example.com/videos/question4_demo.mp4'),
    (4, 'audio', 'https://example.com/audios/question4_clues.mp3'),

    -- Pour la question 5, image
    (5, 'image', 'https://example.com/images/question5_map.png'),

    -- Pour la question 6, vidéo
    (6, 'video', 'https://example.com/videos/question6_illustration.mp4'),

    -- Pour la question 7, audio
    (7, 'audio', 'https://example.com/audios/question7_music.mp3'),

    -- Pour la question 8, image
    (8, 'image', 'https://example.com/images/question8_artwork.jpg');
