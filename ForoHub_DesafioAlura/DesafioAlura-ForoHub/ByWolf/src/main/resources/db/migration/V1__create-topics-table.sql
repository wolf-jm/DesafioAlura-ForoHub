CREATE TABLE topics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    message VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    status VARCHAR(10) NOT NULL,
    author VARCHAR(255) NOT NULL,
    course VARCHAR(15) NOT NULL,
    UNIQUE KEY id_unique_title_message (title, message)
);