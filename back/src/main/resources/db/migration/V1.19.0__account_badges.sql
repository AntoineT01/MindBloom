CREATE TABLE IF NOT EXISTS account_badges (
    account_id INT NOT NULL,
    badge_id INT NOT NULL,
    awarded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (account_id, badge_id),
    CONSTRAINT fk_account_badges_account
        FOREIGN KEY (account_id)
        REFERENCES account(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_account_badges_badge
        FOREIGN KEY (badge_id)
        REFERENCES badge(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
)