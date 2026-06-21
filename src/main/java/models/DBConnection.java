package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {

    private static final String URL = "jdbc:sqlite:library.db";

    static {
        initializeDatabase();
    }

    private static void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON");
            statement.execute("CREATE TABLE IF NOT EXISTS livres ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "titre TEXT NOT NULL, "
                    + "auteur TEXT NOT NULL, "
                    + "categorie TEXT, "
                    + "quantite INTEGER NOT NULL DEFAULT 0"
                    + ")");

            statement.execute("CREATE TABLE IF NOT EXISTS membres ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "nom TEXT NOT NULL, "
                    + "email TEXT NOT NULL, "
                    + "telephone TEXT, "
                    + "date_inscription TEXT NOT NULL"
                    + ")");

            statement.execute("CREATE TABLE IF NOT EXISTS emprunts ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "livre_id INTEGER NOT NULL, "
                    + "membre_id INTEGER NOT NULL, "
                    + "date_emprunt TEXT NOT NULL, "
                    + "date_retour TEXT, "
                    + "statut TEXT NOT NULL, "
                    + "FOREIGN KEY(livre_id) REFERENCES livres(id), "
                    + "FOREIGN KEY(membre_id) REFERENCES membres(id)"
                    + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}