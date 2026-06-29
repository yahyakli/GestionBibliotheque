# GestionBibliotheque - Project Overview

## Purpose

This is a JavaFX desktop application for managing a library. It supports:
- book inventory management
- member management
- loan (emprunt) tracking
- database persistence with MySQL

The application is built with Maven and Java 21.

## Project Structure

- `pom.xml` - Maven build configuration with JavaFX and MySQL connector dependencies.
- `src/main/java/Main.java` - JavaFX application entry point.
- `src/main/java/controllers/` - FXML controllers for each main screen.
- `src/main/java/models/` - Data models, DAOs, and database initialization logic.
- `src/main/java/views/` - FXML layouts for the UI.

## Entry Point

- `Main.java`
  - Launches JavaFX application.
  - Loads `/dashboard.fxml`.
  - Opens a `Scene` sized 1100 x 650.

## UI Structure

### `dashboard.fxml`
- A `TabPane` with three tabs:
  - `Livres`
  - `Membres`
  - `Emprunts`
- Each tab includes an FXML file:
  - `livres.fxml`
  - `membres.fxml`
  - `emprunts.fxml`

### `livres.fxml`
- Controller: `controllers.LivreController`
- Shows a table of books and a side form for book details.
- Buttons:
  - `Ajouter` (add book)
  - `Modifier` (update book)
  - `Supprimer` (delete book)

### `membres.fxml`
- Controller: `controllers.MembreController`
- Shows a table of library members and a side form for member details.
- Buttons:
  - `Ajouter` (add member)
  - `Modifier` (update member)
  - `Supprimer` (delete member)

### `emprunts.fxml`
- Controller: `controllers.EmpruntController`
- Shows a table of loan records and a side form to create new loans.
- Buttons:
  - `Emprunter` (create loan)
  - `Retourner` (return loan)
  - `Supprimer` (delete loan)
  - `Rafraîchir` (refresh loan list)

## Data Model

### `models/Livre.java`
- Book entity fields:
  - `id`
  - `titre`
  - `auteur`
  - `categorie`
  - `isbn`
  - `editeur`
  - `datePublication`
  - `emplacement`
  - `quantite`

### `models/Membre.java`
- Member entity fields:
  - `id`
  - `nom`
  - `email`
  - `telephone`
  - `adresse`
  - `dateNaissance`
  - `dateInscription`
  - `typeAdherent`
  - `statut`

### `models/Emprunt.java`
- Loan entity fields:
  - `id`
  - `livreId`
  - `membreId`
  - `dateEmprunt`
  - `dateRetour`
  - `dateRetourPrevue`
  - `statut`
  - `notes`

## Data Access

### `models/DBConnection.java`
- Connects to a MySQL database.
- Default database name: `gestion_bibliotheque`.
- Uses environment variables if present:
  - `LIBRARY_DB_SERVER_URL`
  - `LIBRARY_DB_URL`
  - `LIBRARY_DB_USER`
  - `LIBRARY_DB_PASSWORD`
- Creates database and tables automatically if they do not exist.
- Tables:
  - `livres`
  - `membres`
  - `emprunts`

### `models/LivreDAO.java`
- Methods:
  - `findAll()`
  - `create(Livre)`
  - `update(Livre)`
  - `delete(int)`
  - `findById(int)`

### `models/MembreDAO.java`
- Methods:
  - `findAll()`
  - `create(Membre)`
  - `update(Membre)`
  - `delete(int)`
  - `findById(int)`

### `models/EmpruntDAO.java`
- Methods:
  - `findAll()`
  - `create(Emprunt)`
  - `returnEmprunt(int)`
  - `delete(int)`
- Behavior:
  - `create()` checks if the selected book has available copies.
  - When creating a loan, it decrements the book quantity.
  - Returning a loan updates the loan status, sets `date_retour`, and increments book quantity.
  - Deleting a loan restores quantity if loan is still "En cours".

## Controller Behavior

### `LivreController`
- Loads all books with `livreDAO.findAll()`.
- Binds table columns to book properties.
- Supports selection of a book to populate the form.
- Adds validations for required fields and numeric quantity.

### `MembreController`
- Loads all members with `membreDAO.findAll()`.
- Binds table columns to member properties.
- Supports selection of a member to populate the form.
- Requires `nom`, `email`, and `dateInscription` to add or update.

### `EmpruntController`
- Loads all loans, books, and members.
- Binds loan table columns and resolves book/member names by ID.
- Uses combo boxes for selecting book and member.
- Initializes default loan dates:
  - `dateEmprunt` = today
  - `dateRetourPrevue` = today + 2 weeks
- Adds loans and handles returns and deletion.

## Database Schema Summary

- `livres`
  - `id` INT AUTO_INCREMENT PRIMARY KEY
  - `titre`, `auteur`, `categorie`, `isbn`, `editeur`
  - `date_publication` DATE
  - `emplacement` VARCHAR
  - `quantite` INT NOT NULL

- `membres`
  - `id` INT AUTO_INCREMENT PRIMARY KEY
  - `nom`, `email`, `telephone`, `adresse`
  - `date_naissance` DATE
  - `date_inscription` DATE NOT NULL
  - `type_adherent`, `statut`

- `emprunts`
  - `id` INT AUTO_INCREMENT PRIMARY KEY
  - `livre_id` INT NOT NULL
  - `membre_id` INT NOT NULL
  - `date_emprunt` DATE NOT NULL
  - `date_retour` DATE
  - `date_retour_prevue` DATE
  - `statut` VARCHAR
  - `notes` TEXT
  - Foreign keys to `livres(id)` and `membres(id)`

## Important Notes for a New Developer

- The project uses JavaFX 21 with FXML controllers.
- The FXML files are loaded from `src/main/java/views/` through Maven resources.
- Database initialization happens automatically when the application starts.
- `EmpruntDAO` performs transactional updates across both `emprunts` and `livres`.
- `toString()` methods in `Livre` and `Membre` are used to display objects in combo boxes.

## How to Run

- Use Maven with the JavaFX plugin.
- Example command:
  - `mvn clean javafx:run`

- Ensure a MySQL server is available and reachable.
- Optionally set environment variables for the database URL and credentials.

## Extension Ideas

- Add search/filtering on tables.
- Add user authentication or role permissions.
- Add overdue loan detection and alerts.
- Improve loan history and reporting.
- Use a more advanced UI layout with responsive design.
- Add unit tests for DAOs and controller logic.

## File Mapping

- `Main.java` → app launcher
- `dashboard.fxml` → main tabbed container
- `livres.fxml` → books UI
- `membres.fxml` → members UI
- `emprunts.fxml` → loans UI
- `LivreController.java` → book CRUD logic
- `MembreController.java` → member CRUD logic
- `EmpruntController.java` → loan logic
- `DBConnection.java` → MySQL setup and connection
- `Livre.java`, `Membre.java`, `Emprunt.java` → entity models
- `LivreDAO.java`, `MembreDAO.java`, `EmpruntDAO.java` → database access
