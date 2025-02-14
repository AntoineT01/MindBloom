-- V1.1.9__sample_quiz_session.sql
-- Exemple d'insertion de données pour la table "quiz_session".
-- Colonnes : (id, quiz_id, session_mode, status, start_time, end_time, session_code)
--
-- On suppose que "id" est auto-incrément, donc on ne le force pas.
-- On référence "quiz_id" = 1..10 (assurez-vous que ces quiz existent).

INSERT INTO quiz_session (quiz_id, session_mode, status, start_time, end_time, session_code)
VALUES
  -- Session pour le quiz_id = 1
  (1, 'training', 'active',   '2025-01-01 08:00:00', NULL,                  'SESSION1ABC'),
  -- Session examen terminée pour le même quiz
  (1, 'exam',     'finished', '2025-01-02 09:00:00', '2025-01-02 10:00:00', 'SESSION1XYZ'),

  -- Session pour le quiz_id = 2
  (2, 'training', 'active',   '2025-01-03 09:30:00', NULL,                  'SESSION2ABC'),

  -- Session pour le quiz_id = 3
  (3, 'exam',     'finished', '2025-01-04 14:00:00', '2025-01-04 15:00:00', 'SESSION3FIN'),

  -- Session pour le quiz_id = 4
  (4, 'training', 'active',   '2025-01-05 10:00:00', NULL,                  'SESSION4ABC'),

  -- Session pour le quiz_id = 5
  (5, 'training', 'active',   '2025-01-05 11:00:00', NULL,                  'SESSION5ABC'),

  -- Session pour le quiz_id = 6
  (6, 'exam',     'finished', '2025-01-06 09:00:00', '2025-01-06 09:45:00', 'SESSION6FIN'),

  -- Session pour le quiz_id = 7
  (7, 'training', 'active',   '2025-01-07 12:00:00', NULL,                  'SESSION7ABC'),

  -- Session pour le quiz_id = 8
  (8, 'exam',     'canceled', '2025-01-08 08:00:00', NULL,                  'SESSION8CXL'),

  -- Session pour le quiz_id = 9
  (9, 'exam',     'finished', '2025-01-09 16:00:00', '2025-01-09 17:00:00', 'SESSION9FIN'),

  -- Session pour le quiz_id = 10
  (10, 'training','active',   '2025-01-10 09:00:00', NULL,                  'SESSION10AC');
