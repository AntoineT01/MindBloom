CREATE TABLE IF NOT EXISTS media (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_id INT NOT NULL,
    type ENUM('image','video','audio') NOT NULL,
    url VARCHAR(255) NOT NULL,
    CONSTRAINT fk_media_question
        FOREIGN KEY (question_id)
        REFERENCES question(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
)