CREATE TABLE IF NOT EXISTS quiz (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    creator_id INT NOT NULL,
    is_public BOOLEAN NOT NULL DEFAULT FALSE,
    show_answers BOOLEAN NOT NULL DEFAULT FALSE,
    show_final_score BOOLEAN NOT NULL DEFAULT TRUE,
    time_limit INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status ENUM('active', 'inactive', 'deleted') NOT NULL DEFAULT 'active',
    share_code VARCHAR(255) NOT NULL,
    CONSTRAINT uq_quiz_share_code UNIQUE (share_code),
    CONSTRAINT fk_quiz_account
        FOREIGN KEY (creator_id)
        REFERENCES account(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)COMMENT = 'Representation of an account request in the database';