# HackHub

HackHub è una piattaforma **web backend REST** per la gestione di **hackathon** sviluppata in **Java 21** con **Spring Boot**.
Il progetto nasce come soluzione all’assegnamento del corso di **Ingegneria del Software** dell’Università di Camerino e punta a coprire l’intero ciclo di vita di un hackathon: dalla creazione dell’evento fino alla proclamazione del team vincitore.

## Obiettivo del progetto

L’obiettivo è realizzare un sistema in grado di gestire:

- la creazione e la consultazione degli hackathon;
- la registrazione dei team e degli utenti;
- la partecipazione degli hackathon da parte dei team;
- il caricamento delle sottomissioni;
- la valutazione finale da parte del giudice;
- le richieste di supporto ai mentori;
- la segnalazione di violazioni del regolamento;
- la comunicazione con servizi esterni per **organizzare delle call** e **pagamento del premio**.

La piattaforma è pensata principalmente come **API REST**, quindi può essere usata da una qualunque interfaccia grafica o da client come Postman.

## Il compito assegnato

La consegna richiedeva di modellare una piattaforma per la gestione di hackathon con i seguenti vincoli principali:

- sviluppo in **Java** e successivo porting su **Spring Boot**;
- supporto a un ciclo di vita di un hackathon con quattro stati: **in iscrizione**, **in corso**, **in valutazione**, **concluso**;
- presenza di più attori con permessi differenti;
- utilizzo di almeno **due design pattern** diversi dal Singleton;
- possibilità di limitare lo strato di presentazione ad API REST o linea di comando.

HackHub implementa questo scenario con un backend strutturato, persistenza locale e integrazioni verso servizi esterni.

## Funzionalità implementate

### Gestione hackathon

- creazione di nuovi hackathon;
- aggiornamento dei dati dell’hackathon;
- visualizzazione della lista completa degli hackathon;
- visualizzazione dei dettagli di un singolo hackathon;
- selezione dell’hackathon corrente;
- aggiunta e rimozione dei mentori;
- iscrizione e abbandono dei team;
- proclamazione del team vincitore;

### Gestione utenti e team

- registrazione e aggiornamento utenti;
- creazione e aggiornamento team;
- inviti tra utenti per entrare in un team;
- vincolo che ogni utente possa appartenere a **un solo team** alla volta;
- consultazione delle informazioni relative al proprio team.

### Gestione sottomissioni

- invio della sottomissione da parte dei team;
- aggiornamento della sottomissione fino alla scadenza prevista;
- consultazione delle sottomissioni da parte degli attori autorizzati;
- valutazione finale con punteggio numerico e giudizio scritto.

### Supporto, violazioni e notifiche

- apertura di richieste di supporto da parte di un utente;
- risposta dei mentori alle richieste;
- pianificazione di call tramite integrazione con un servizio calendar esterno;
- segnalazione delle violazioni del regolamento;
- consultazione delle violazioni registrate;
- gestione di notifiche interne alla piattaforma.

### Integrazioni esterne

- **Google Calendar** per la prenotazione delle call tra mentore e team;
- **PayPal** per l’erogazione del premio in denaro al team vincitore.

## Ruoli supportati

| Ruolo                  | Responsabilità principali                                                 |
|------------------------|---------------------------------------------------------------------------|
| **Visitatore**         | Consulta le informazioni pubbliche sugli hackathon.                       |
| **Utente**             | Gestisce la propria partecipazione, crea o accetta inviti ai team.        |
| **Membro del Team**    | Partecipa agli hackathon, invia e aggiorna la sottomissione.              |
| **Membro dello Staff** | Consulta gli hackathon e le informazioni riservate agli eventi assegnati. |
| **Organizzatore**      | Crea hackathon, gestisce mentori e proclama il vincitore.                 |
| **Mentore**            | Gestisce richieste di supporto, propone call, segnala violazioni.         |
| **Giudice**            | Valuta le sottomissioni al termine dell’hackathon.                        |

## Tecnologie utilizzate

- **Java 21**
- **Spring Boot 3.2**
- **Spring Web** per le API REST
- **Spring Data JPA** per la persistenza
- **H2 Database** come database locale file-based
- **Swagger / OpenAPI** per la documentazione delle API
- **Lombok** per ridurre il boilerplate
- **Google Calendar API**
- **PayPal Checkout SDK**
- **JUnit 5** e **Spring Boot Test** per i test

## Architettura e design pattern

Il progetto adotta una struttura a livelli con componenti separati per:

- **controller**: esposizione delle API REST;
- **service**: logica applicativa;
- **repository**: accesso ai dati;
- **requester/model/dto**: modello del dominio e oggetti di scambio;
- **validator**: validazione degli input;
- **design pattern**: implementazioni dedicate dei pattern scelti.

### Design pattern utilizzati

- **Builder**: utilizzato per la costruzione degli oggetti `Hackathon` in modo progressivo e controllato;
- **Strategy**: utilizzato per astrarre il comportamento di pagamento, in particolare l’integrazione con PayPal.

## Configurazione e avvio

### Requisiti

- Java 21
- Gradle Wrapper incluso nel progetto

### Avvio dell’applicazione

Da terminale, nella cartella `project/app`:

```powershell
./gradlew bootRun
```

Su Windows PowerShell, se necessario:

```powershell
.\gradlew.bat bootRun
```

### Database

Il progetto usa H2 in modalità file-based:

- database: `./data/hackhubdb`
- console H2: `/h2-console`

### Credenziali esterne

Le integrazioni con servizi esterni usano file di configurazione e variabili d’ambiente:

- Google Calendar: `./config/calendar-credentials.json`
- PayPal: `./config/paypal-credentials.json`
- in alternativa, è possibile configurare le variabili:
  - `GOOGLE_CALENDAR_CREDENTIAL_PATH`
  - `PAYPAL_BASE_URL`
  - `PAYPAL_CLIENT_ID`
  - `PAYPAL_CLIENT_SECRET`
  - `PAYPAL_CREDENTIALS_PATH`

## Documentazione API

La documentazione OpenAPI/Swagger è disponibile avviando il progetto e aprendo:

- `http://localhost:8080/swagger-ui/index.html`

## Struttura del progetto

- `controller/` — endpoint REST
- `service/` — logica di business
- `repository/` — accesso ai dati
- `entity/model/` — entità del dominio
- `entity/dto/` — oggetti di risposta
- `entity/requester/` — oggetti di richiesta
- `validator/` — validazioni
- `designpattern/` — implementazioni di Builder e Strategy
- `config/` — credenziali e file esterni
- `postman/` — collezioni, ambienti e test API

## Stato del progetto

HackHub è stato sviluppato come backend completo per la gestione di hackathon, con persistenza locale, validazione degli input, documentazione API e integrazioni verso servizi esterni per calendario e pagamento.

## Informazioni accademiche

**Università di Camerino** – Scuola di Scienze e Tecnologie  
**Ingegneria del Software** A.S. 2025/2026  
**Gruppo:** Git Pushers


