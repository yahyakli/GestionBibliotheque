# GestionBibliotheque

Application JavaFX de gestion de bibliothèque avec une base SQLite locale.

## Exécution

1. Assurez-vous d'utiliser Java 21.
2. Ouvrez le projet dans votre IDE Java ou utilisez Maven.
3. Lancez l'application avec la commande `mvn javafx:run` depuis la racine du projet.
4. La base de données SQLite `library.db` est créée automatiquement lors du premier lancement.

## Structure du projet

- `src/main/java/models` : classes métier et DAO
- `src/main/java/controllers` : contrôleurs JavaFX pour les opérations CRUD
- `src/main/java/views` : vues JavaFX et navigation par onglets
- `schema.sql` : script SQL de création de la base de données

## Fonctionnalités

- Gestion des livres (CRUD)
- Gestion des membres (CRUD)
- Suivi des emprunts avec retour et gestion de quantités

