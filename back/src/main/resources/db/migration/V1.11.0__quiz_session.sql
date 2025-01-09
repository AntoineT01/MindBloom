CREATE TABLE IF NOT EXISTS quiz_session (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quiz_id INT NOT NULL,
    session_mode VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    start_time TIMESTAMP NULL,
    end_time TIMESTAMP NULL,
    session_code VARCHAR(255) NOT NULL,
    CONSTRAINT uq_session_code UNIQUE (session_code),
    CONSTRAINT fk_quiz_session_quiz
        FOREIGN KEY (quiz_id)
        REFERENCES quiz(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
)