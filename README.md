<p align="center">
  <img src="logo.png" alt="Poesie Brevi Logo" width="200"/>
</p>

<h1 align="center">Poesie Brevi</h1>

**Poesie Brevi** è una piattaforma che consente agli utenti di scrivere, condividere e organizzare poesie, interagendo con la community tramite cuori e commenti. 
Questo progetto è stato sviluppato nell'ambito del corso di **Ingegneria del Software** (A.A. 2024-25) presso l’**Università degli Studi di Napoli Federico II**.

## 👩‍💻 Autori

- Claudia De Luca 
- Mario Cipolletta 

---

## ✨ Funzionalità Principali

- **Registrazione e Login**
  - Registrazione con e-mail, nome, cognome e password
  - Creazione e modifica del profilo personale con biografia e immagine

- **Gestione Poesie**
  - Pubblicazione di poesie (max 500 caratteri)
  - Assegnazione di titolo, tag tematici e visibilità (pubblica/privata)
  - Associazione a raccolte nuove o esistenti

- **Gestione Raccolte**
  - Creazione e visualizzazione di raccolte personali

- **Interazioni Social**
  - Feed con le ultime 5 poesie pubbliche di altri utenti
  - Sistema di “cuori” (like)
  - Commenti testuali alle poesie
    
- **Statistiche**
  - Dashboard per la visualizzazione:
    - degli autori più attivi (con più poesie pubblicate);
    - del numero totale di poesie pubblicate negli ultimi 7 giorni;
    - delle poesie con più interazioni.
    
---

## 🧱 Architettura

Il sistema è implementato in **Java 22** utilizzando il pattern architetturale **BCED**:

- **Boundary**: interfacce grafiche e interazioni utente (Swing)
- **Controller**: logica dei casi d’uso (Login, Registrazione, Profilo, Poesie)
- **Entity**: classi del dominio (User, Poesia, Raccolta, Commento, Cuore)
- **Database**: classi DAO per persistenza su MySQL
- **DTO**: classi DTO per garantire il disaccoppiamento tra Boundary e Controller

---

## 🧪 Testing

- **JUnit Tests** per unità
- **Test funzionali** su casi d’uso (login e creazione poesia)
- **Complessità ciclomatica** calcolata per metodi chiave

---

## 🔧 Requisiti Tecnici

- **Java JDK 22+**
- **MySQL** attivo con tabelle predisposte
- **mysql-connector-j-9.3.0.jar** incluso nel classpath

---

## 🚀 Avvio del Progetto

1. Clona la repository:
   ```bash
   git clone https://github.com/mcipo/PoesieBrevi
   cd PoesieBrevi
   ```

2. Importa il progetto in un IDE (es. IntelliJ)

3. Configura il file di connessione al database (`DatabaseConnection.java`)

4. Compila ed esegui `Main.java`

---

## 🔗 JavaDoc

- [JavaDoc del Progetto disponibile](https://mcipo.github.io/PoesieBrevi/)
