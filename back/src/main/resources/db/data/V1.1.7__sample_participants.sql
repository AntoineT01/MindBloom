-- V1.1.7__sample_Participants.sql
-- Exemple d'insertion de données pour la table "participants"
-- Colonnes : (id, session_id, account_id, nickname, joined_at)
--
-- Suppose que vous avez déjà des session_id existants
-- et des account_id existants.

INSERT INTO participants (session_id, account_id, nickname, joined_at)
VALUES
    (1, 1, 'Alice',        '2025-01-01 09:00:00'),
    (1, 2, 'Bob',          '2025-01-01 09:05:00'),
    (2, 3, 'Charlie',      '2025-01-02 14:10:00'),
    (2, 4, 'Diana',        '2025-01-02 14:15:00'),
    (3, 5, 'Edward',       '2025-01-03 10:30:00')