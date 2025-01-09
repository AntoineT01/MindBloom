CREATE TABLE IF NOT EXISTS quiz_categories (
    quiz_id INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (quiz_id, category_id),
    CONSTRAINT fk_quiz_categories_quiz
        FOREIGN KEY (quiz_id)
        REFERENCES quiz(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_quiz_categories_categorie
        FOREIGN KEY (category_id)
        REFERENCES categorie(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
