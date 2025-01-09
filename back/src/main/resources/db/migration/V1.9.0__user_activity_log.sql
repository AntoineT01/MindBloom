CREATE TABLE IF NOT EXISTS user_activity_log (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    action VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_activity_log_account
        FOREIGN KEY (account_id)
        REFERENCES account(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
