CREATE TABLE IF NOT EXISTS session_responses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    session_id INT NOT NULL,
    participant_id INT NOT NULL,
    question_id INT NOT NULL,
    answer_id INT NULL,
    response_text TEXT NULL,
    submitted_at TIMESTAMP NULL,
    is_correct INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_session_responses_session
        FOREIGN KEY (session_id)
        REFERENCES quiz_session(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_session_responses_participant
        FOREIGN KEY (participant_id)
        REFERENCES participants(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_session_responses_question
        FOREIGN KEY (question_id)
        REFERENCES question(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_session_responses_answer
        FOREIGN KEY (answer_id)
        REFERENCES answer(id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
)