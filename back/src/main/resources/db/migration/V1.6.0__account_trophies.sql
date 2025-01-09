CREATE TABLE IF NOT EXISTS account_trophies (
    account_id INT NOT NULL,
    trophy_id INT NOT NULL,
    earned_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (account_id, trophy_id),
    CONSTRAINT fk_account_trophies_account
        FOREIGN KEY (account_id)
        REFERENCES account(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_account_trophies_trophy
        FOREIGN KEY (trophy_id)
        REFERENCES trophies(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
)