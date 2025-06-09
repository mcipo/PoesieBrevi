-- DROP DATABASE IF EXISTS poesie_brevi_db;
CREATE DATABASE IF NOT EXISTS poesie_brevi_db;
USE poesie_brevi_db;

CREATE TABLE IF NOT EXISTS users
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    email          VARCHAR(100) NOT NULL UNIQUE,
    password       VARCHAR(64)  NOT NULL,
    nome           VARCHAR(50)  NOT NULL,
    cognome        VARCHAR(50)  NOT NULL,
    amministratore BOOLEAN DEFAULT FALSE,

    INDEX idx_email (email)
);

CREATE TABLE IF NOT EXISTS user_profiles
(
    user_id           INT         NOT NULL PRIMARY KEY,
    username          VARCHAR(50) NOT NULL UNIQUE,
    bio               VARCHAR(200),
    foto_profilo_path VARCHAR(255),
    data_nascita      DATE,

    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,

    INDEX idx_username (username)
);

CREATE TABLE raccolte
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    titolo      VARCHAR(100) NOT NULL,
    descrizione VARCHAR(200),
    autore_id   INT          NOT NULL,

    FOREIGN KEY (autore_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS poesie
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    titolo         VARCHAR(100) NOT NULL,
    contenuto      TEXT         NOT NULL,
    autore_id      INT          NOT NULL,
    tag            VARCHAR(50),
    data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    visibile       BOOLEAN   DEFAULT TRUE,
    raccolta_id    INT,

    FOREIGN KEY (autore_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (raccolta_id) REFERENCES raccolte (id) ON DELETE SET NULL,

    INDEX idx_autore (autore_id),
    INDEX idx_tag (tag),
    INDEX idx_data_creazione (data_creazione)
);

CREATE TABLE IF NOT EXISTS commenti
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    poesia_id      INT  NOT NULL,
    autore_id      INT  NOT NULL,
    contenuto      TEXT NOT NULL,
    data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (poesia_id) REFERENCES poesie (id) ON DELETE CASCADE,
    FOREIGN KEY (autore_id) REFERENCES users (id) ON DELETE CASCADE,

    INDEX idx_poesia (poesia_id),
    INDEX idx_autore (autore_id),
    INDEX idx_data_creazione (data_creazione)
);

CREATE TABLE IF NOT EXISTS cuori
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    poesia_id INT NOT NULL,
    utente_id INT NOT NULL,

    FOREIGN KEY (poesia_id) REFERENCES poesie (id) ON DELETE CASCADE,
    FOREIGN KEY (utente_id) REFERENCES users (id) ON DELETE CASCADE,

    UNIQUE KEY unique_like (poesia_id, utente_id)
);


INSERT INTO users (amministratore, email, password, nome, cognome)
VALUES (TRUE, 'admin@poesiebrevi.com', '1234', 'Admin', 'Sistema'),
       (FALSE, 'poeta1@example.com', '1234', 'Mario', 'Rossi');

INSERT INTO user_profiles (user_id, username, bio, foto_profilo_path, data_nascita)
VALUES (1, 'admin', 'Amministratore della piattaforma', NULL, '1980-01-01'),
       (2, 'poeta_mario', 'Amo scrivere poesie brevi.', NULL, '1990-06-15');

INSERT INTO poesie (titolo, contenuto, autore_id, tag)
VALUES ('Alba', 'Il sole sorge, la mente riposa.', 2, 'natura'),
       ('Pioggia', 'Gocce leggere danzano sull’asfalto.', 2, 'tempo');

INSERT INTO commenti (poesia_id, autore_id, contenuto)
VALUES (1, 1, 'Bellissima poesia, complimenti!'),
       (2, 1, 'Molto evocativa.');

INSERT INTO cuori (poesia_id, utente_id)
VALUES (1, 1),
       (2, 1),
       (2, 2);
