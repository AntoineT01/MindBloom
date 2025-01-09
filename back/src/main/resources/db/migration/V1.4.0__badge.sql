CREATE TABLE IF NOT EXISTS badge (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    text VARCHAR(255) NOT NULL,
    image LONGBLOB NULL
)COMMENT = 'Representation of an account request in the database';