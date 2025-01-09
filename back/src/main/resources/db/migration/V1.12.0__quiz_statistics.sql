CREATE TABLE IF NOT EXISTS quiz_statistics (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quiz_id INT NOT NULL,
    total_participants INT NOT NULL DEFAULT 0,
    average_score DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    average_time_per_question DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    CONSTRAINT fk_quiz_statistics_quiz
        FOREIGN KEY (quiz_id)
        REFERENCES quiz(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
)