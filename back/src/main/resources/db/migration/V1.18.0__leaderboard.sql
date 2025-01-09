CREATE TABLE IF NOT EXISTS leaderboard (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quiz_session_id INT NOT NULL,
    participant_id INT NOT NULL,
    score INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_leaderboard_quiz_session
        FOREIGN KEY (quiz_session_id)
        REFERENCES quiz_session(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_leaderboard_participant
        FOREIGN KEY (participant_id)
        REFERENCES participants(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
)