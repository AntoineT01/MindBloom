-- 1) Table 'badge'
CREATE TABLE IF NOT EXISTS badge (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    text VARCHAR(255) NOT NULL,
    image LONGBLOB NULL
) COMMENT = 'Table contenant les badges disponibles';

-- 2) Table 'trophies'
CREATE TABLE IF NOT EXISTS trophies (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    image VARCHAR(255) NOT NULL,
    points_required INT NOT NULL
) COMMENT = 'Table contenant les trophées et leurs conditions';

-- 4) Table 'categorie'
CREATE TABLE IF NOT EXISTS categorie (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
) COMMENT = 'Table listant les catégories possibles pour les quiz';

-- 5) Table 'quiz'
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
) COMMENT = 'Table principale des quiz';

-- 6) Table 'quiz_statistics'
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
) COMMENT = 'Table stockant des statistiques globales sur un quiz';

-- 7) Table 'quiz_session'
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
) COMMENT = 'Table représentant une session (partie) d’un quiz';

-- 8) Table 'question'
CREATE TABLE IF NOT EXISTS question (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quiz_id INT NOT NULL,
    content TEXT NOT NULL,
    type VARCHAR(50) NOT NULL,
    points INT NOT NULL DEFAULT 0,
    question_order INT NOT NULL DEFAULT 0,
    is_required BOOLEAN NOT NULL DEFAULT TRUE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    display_time INT NOT NULL DEFAULT 0,
    time_min INT NULL,
    time_max INT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    imported BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_question_quiz
        FOREIGN KEY (quiz_id)
        REFERENCES quiz(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) COMMENT = 'Table stockant les questions d’un quiz';

-- 9) Table 'answer'
CREATE TABLE IF NOT EXISTS answer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_id INT NOT NULL,
    content TEXT NOT NULL,
    type VARCHAR(50) NOT NULL,
    is_correct BOOLEAN NOT NULL DEFAULT FALSE,
    answer_order INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_answer_question
        FOREIGN KEY (question_id)
        REFERENCES question(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) COMMENT = 'Table stockant les réponses possibles pour chaque question';

-- 10) Table 'media'
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
) COMMENT = 'Table stockant les médias associés à une question';

-- 11) Table 'user_activity_log'
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
) COMMENT = 'Table journalisant les actions des utilisateurs';

-- 12) Table 'quiz_categories'
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
) COMMENT = 'Table de liaison entre un quiz et ses catégories';

-- 13) Table 'participants'
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
) COMMENT = 'Table listant les participants (liés à la session et à un compte)';

-- 14) Table 'session_responses'
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
) COMMENT = 'Table enregistrant les réponses des participants en session';

-- 15) Table 'leaderboard'
CREATE TABLE IF NOT EXISTS leaderboard (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quiz_session_id INT NOT NULL,
    participant_id INT NOT NULL,
    score INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_leaderboard_quiz_session
        FOREIGN KEY (quiz_session_id)
        REFERENCES quiz_session(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_leaderboard_participant
        FOREIGN KEY (participant_id)
        REFERENCES participants(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) COMMENT = 'Table gérant le classement de chaque session';

-- 16) Table 'account_trophies'
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
) COMMENT = 'Table de liaison liant un compte à ses trophées obtenus';

-- 17) Table 'account_badges'
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
) COMMENT = 'Table de liaison liant un compte aux badges obtenus';
