-- Script de création de la base de données pour GestionBibliotheque

CREATE TABLE IF NOT EXISTS livres (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    titre TEXT NOT NULL,
    auteur TEXT NOT NULL,
    categorie TEXT,
    quantite INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS membres (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nom TEXT NOT NULL,
    email TEXT NOT NULL,
    telephone TEXT,
    date_inscription TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS emprunts (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    livre_id INTEGER NOT NULL,
    membre_id INTEGER NOT NULL,
    date_emprunt TEXT NOT NULL,
    date_retour TEXT,
    statut TEXT NOT NULL,
    FOREIGN KEY (livre_id) REFERENCES livres(id),
    FOREIGN KEY (membre_id) REFERENCES membres(id)
);
