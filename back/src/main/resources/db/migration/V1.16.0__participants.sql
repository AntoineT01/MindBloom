CREATE TABLE IF NOT EXISTS participants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    session_id INT NOT NULL,
    account_id INT NOT NULL,
    nickname VARCHAR(255) NOT NULL,
    joined_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_participants_session
        FOREIGN KEY (session_id)
        REFERENCES quiz_session(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_participants_account
        FOREIGN KEY (account_id)
        REFERENCES account(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
)