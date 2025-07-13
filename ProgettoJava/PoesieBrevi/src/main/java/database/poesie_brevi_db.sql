DROP DATABASE IF EXISTS poesie_brevi_db;
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

-- POPOLAMENTO DataBase
INSERT INTO users (amministratore, email, password, nome, cognome)
VALUES (FALSE, 'dante@poeta.it', 'password123', 'Dante', 'Alighieri'),
       (FALSE, 'petrarca@poeta.it', 'password123', 'Francesco', 'Petrarca'),
       (FALSE, 'leopardi@poeta.it', 'password123', 'Giacomo', 'Leopardi'),
       (FALSE, 'carducci@poeta.it', 'password123', 'Giosuè', 'Carducci'),
       (FALSE, 'dannunzio@poeta.it', 'password123', 'Gabriele', 'D''annunzio'),
       (FALSE, 'pascoli@poeta.it', 'password123', 'Giovanni', 'Pascoli'),
       (FALSE, 'test@prova.com', 'password123', 'Test', 'Prova'),
       (TRUE, 'claudiadeluca@email.com', 'password123', 'Claudia', 'De Luca'),
       (TRUE, 'mariocipolletta@email.com', 'password123', 'Mario', 'Cipolletta'),
       (FALSE, 'test@email.com', 'test', 'test', 'test');

INSERT INTO user_profiles (user_id, username, bio, foto_profilo_path, data_nascita)
VALUES(1, 'Dante_Alighieri', 'Padre della lingua italiana e autore della Divina Commedia.', 'resources/immagine_profilo/danteAlighieri.jpeg', '1265-05-21'),
    (2, 'F_Petrarca', 'Poeta e umanista, autore del Canzoniere.', 'resources/immagine_profilo/petrarca.jpeg', '1304-07-20'),
    (3, 'G_Leopardi', 'Scrivo poesie malinconiche e riflessive.', 'resources/immagine_profilo/Leopardi.jpeg', '1798-06-29'),
    (4, 'G_Carducci', 'Letteratura e poesia sono la mia vita.', 'resources/immagine_profilo/carducci.jpeg', '1835-07-27'),
    (5, 'G_Dannunzio', 'Artista e poeta in cerca della bellezza.', 'resources/immagine_profilo/dannunzio.jpeg', '1863-03-12'),
    (6, 'G_Pascoli', 'Amo raccontare la semplicità della vita.', 'resources/immagine_profilo/pascoli.jpeg', '1855-12-31'),
    (7, 'accountTest', 'Utente di prova per testare il database.', 'resources/immagine_profilo/test.jpeg', '2000-01-01'),
    (8, 'claudietta', 'Ciao! Mi chiamo Claudia.', 'resources/immagine_profilo/fotoClaudia.jpeg', '2003-06-24'),
    (9, 'mariocipo', 'Ciao! Mi chiamo Mario.', 'resources/immagine_profilo/fotoMario.jpeg', '2002-06-17')
;

INSERT INTO poesie (titolo, contenuto, autore_id, tag)
VALUES('Inferno I', 'Nel mezzo del cammin di nostra vita mi ritrovai per una selva oscura, ché la diritta via era smarrita. Ahi quanto a dir qual era è cosa dura esta selva selvaggia e aspra e forte, che nel pensier rinova la paura!', 1, 'classico, viaggio'),
    ('Paradiso XXXIII', 'Vergine Madre, figlia del tuo Figlio, umile e alta più che creatura, termine fisso d’eterno consiglio, tu se’ colei che l’umana natura nobilitasti sì, che ’l suo Fattore non disdegnò di farsi sua fattura.', 1, 'mistico, fede'),
    ('Canzoniere CCLXVI', 'Chiare, fresche et dolci acque, ove le belle membra pose colei che sola a me par donna. Gentil ramo ove piacque (con sospir mi rimembra) a lei di fare al bel fianco colonna.', 2, 'amore, natura'),
    ('Canzoniere I', 'Voi ch’ascoltate in rime sparse il suono di quei sospiri ond’io nudriva ’l core in sul mio primo giovenile errore quand’era in parte altr’uom da quel ch’i’ sono.', 2, 'amore, desiderio'),
    ('L’Infinito', 'Sempre caro mi fu quest’ermo colle, e questa siepe, che da tanta parte dell’ultimo orizzonte il guardo esclude. Ma sedendo e mirando, interminati spazi di là da quella, e sovrumani silenzi, e profondissima quiete io nel pensier mi fingo.', 3, 'riflessione, natura'),
    ('A Silvia', 'Silvia, rimembri ancora quel tempo della tua vita mortale, quando beltà splendea negli occhi tuoi ridenti e fuggitivi, e tu, lieta e pensosa, il limitare di gioventù salivi?', 3, 'giovinezza, ricordi'),
    ('San Martino', 'La nebbia a gl’irti colli piovigginando sale, e sotto il maestrale urla e biancheggia il mare; ma per le vie del borgo dal ribollir de’ tini va l’aspro odor de i vini l’anime a rallegrar.', 4, 'autunno, paesaggio'),
    ('Pianto Antico', 'L’albero a cui tendevi la pargoletta mano, il verde melograno da’ bei vermigli fior, nel muto orto solingo rinverdì tutto or ora e giugno lo ristora di luce e di calor.', 4, 'dolore, perdita'),
    ('La pioggia nel pineto', 'Taci. Su le soglie del bosco non odo parole che dici umane; ma odo parole più nuove che parlano gocciole e foglie lontane. Ascolta. Piove dalle nuvole sparse. Piove su le tamerici salmastre ed arse.', 5, 'natura, amore'),
    ('La sera fiesolana', 'Fresche le mie parole ne la sera ti sien come il fruscio che fan le foglie del gelso ne la man di chi le coglie. Silenziosa luna, e tu casta Diana che indichi nel cielo il tuo corso.', 5, 'paesaggio, amore'),
    ('L’aquilone', 'C’è qualcosa di nuovo oggi nel sole, anzi d’antico: io vivo altrove, e sento che sono intorno nate le viole. Sono tornati miei dolci amici: i passeri.', 6, 'infanzia, ricordi'),
    ('X Agosto', 'San Lorenzo, io lo so perché tanto di stelle per l’aria tranquilla arde e cade, perché sì gran pianto nel concavo cielo sfavilla. Ritornava una rondine al tetto.', 6, 'dolore, perdita');

INSERT INTO commenti (poesia_id, autore_id, contenuto)
VALUES(1, 2, 'Un inizio straordinario e carico di simbolismo.'),
    (2, 3, 'Pura bellezza e profondità spirituale.'),
    (3, 1, 'Un tributo alla natura e all’amore.'),
    (4, 5, 'Riflessioni sull’amore universale, meraviglioso.'),
    (5, 4, 'Un capolavoro intriso di malinconia.'),
    (6, 6, 'Silvia vive nei nostri ricordi, toccante.'),
    (7, 3, 'Un dipinto autunnale a parole, incredibile.'),
    (8, 2, 'La perdita e il dolore, ma con dolcezza.'),
    (9, 1, 'Una descrizione sublime della natura.'),
    (10, 4, 'Le emozioni della sera, così delicate.'),
    (11, 5, 'Infanzia e nostalgia, un connubio perfetto.'),
    (12, 3, 'Dolore e speranza intrecciati in modo sublime.');

INSERT INTO cuori (poesia_id, utente_id)
VALUES(1, 1),
    (2, 3),
    (2, 5),
    (3, 4),
    (3, 7),
    (3, 6),
    (4, 2),
    (5, 5),
    (5, 7),
    (6, 6),
    (7, 1),
    (8, 2),
    (9, 3),
    (10, 4),
    (11, 5),
    (12, 6),
    (12, 1);

INSERT INTO raccolte (titolo, descrizione, autore_id)
VALUES('Raccolta Classici', 'Una selezione delle poesie più celebri della letteratura italiana.', 1),
       ('Raccolta Amore e Natura', 'Poesie che celebrano l’amore e la bellezza della natura.', 2),
       ('Raccolta Riflessioni', 'Riflessioni profonde sulla vita e sull’esistenza.', 3),
       ('Raccolta Autunno', 'Poesie che catturano l’essenza dell’autunno.', 4),
       ('Raccolta Infanzia', 'Ricordi d’infanzia e momenti di nostalgia.', 5),
       ('Raccolta Dolore e Speranza', 'Poesie che esplorano il dolore e la speranza.', 6),
       ('Raccolta test', 'Raccolta per testare PoesieController', 10);