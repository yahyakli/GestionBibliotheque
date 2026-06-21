package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {

    private static final String DB_NAME = "gestion_bibliotheque";
    private static final String SERVER_URL = getEnv("LIBRARY_DB_SERVER_URL", "jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
    private static final String URL = getEnv("LIBRARY_DB_URL", "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
    private static final String USER = getEnv("LIBRARY_DB_USER", "root");
    private static final String PASSWORD = getEnv("LIBRARY_DB_PASSWORD", "");

    static {
        initializeDatabase();
    }

    private static String getEnv(String name, String fallback) {
        String value = System.getenv(name);
        return value != null && !value.isBlank() ? value : fallback;
    }

    private static void initializeDatabase() {
        try (Connection serverConnection = DriverManager.getConnection(SERVER_URL, USER, PASSWORD);
             Statement serverStatement = serverConnection.createStatement()) {
            serverStatement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME + " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
        } catch (Exception e) {
            System.err.println("Unable to create or access the MySQL database server.");
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS livres ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "titre VARCHAR(255) NOT NULL, "
                    + "auteur VARCHAR(255) NOT NULL, "
                    + "categorie VARCHAR(100), "
                    + "isbn VARCHAR(50), "
                    + "editeur VARCHAR(150), "
                    + "date_publication DATE, "
                    + "emplacement VARCHAR(100), "
                    + "quantite INT NOT NULL DEFAULT 0"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS membres ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nom VARCHAR(255) NOT NULL, "
                    + "email VARCHAR(255) NOT NULL, "
                    + "telephone VARCHAR(50), "
                    + "adresse VARCHAR(255), "
                    + "date_naissance DATE, "
                    + "date_inscription DATE NOT NULL, "
                    + "type_adherent VARCHAR(100) DEFAULT 'Standard', "
                    + "statut VARCHAR(50) DEFAULT 'Actif'"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS emprunts ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "livre_id INT NOT NULL, "
                    + "membre_id INT NOT NULL, "
                    + "date_emprunt DATE NOT NULL, "
                    + "date_retour DATE, "
                    + "date_retour_prevue DATE, "
                    + "statut VARCHAR(50) NOT NULL, "
                    + "notes TEXT, "
                    + "FOREIGN KEY (livre_id) REFERENCES livres(id) ON DELETE CASCADE, "
                    + "FOREIGN KEY (membre_id) REFERENCES membres(id) ON DELETE CASCADE"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

            seedDatabase(connection);
        } catch (Exception e) {
            System.err.println("Unable to initialize MySQL schema.");
            e.printStackTrace();
        }
    }

    private static void seedDatabase(Connection connection) {
        try {
            if (isTableEmpty(connection, "livres")) {
                connection.createStatement().executeUpdate("INSERT INTO livres (titre, auteur, categorie, isbn, editeur, date_publication, emplacement, quantite) VALUES "
                        + "('Le Petit Prince', 'Antoine de Saint-Exupéry', 'Classique', '978-2070612758', 'Gallimard', '1943-04-06', 'Rayon A1', 5), "
                        + "('Clean Code', 'Robert C. Martin', 'Informatique', '978-0132350884', 'Prentice Hall', '2008-08-11', 'Rayon B3', 3), "
                        + "('Les Misérables', 'Victor Hugo', 'Roman', '978-2253004434', 'Bibliothèque', '1862-01-01', 'Rayon C2', 2)");
            }

            if (isTableEmpty(connection, "membres")) {
                connection.createStatement().executeUpdate("INSERT INTO membres (nom, email, telephone, adresse, date_naissance, date_inscription, type_adherent, statut) VALUES "
                        + "('Emma Dupont', 'emma.dupont@example.com', '+33123456789', '12 rue des Fleurs, Paris', '1990-05-14', '2026-01-15', 'Standard', 'Actif'), "
                        + "('Marc Leroy', 'marc.leroy@example.com', '+33198765432', '23 avenue Victor Hugo, Lyon', '1985-11-02', '2026-02-20', 'Premium', 'Actif')");
            }

            if (isTableEmpty(connection, "emprunts")) {
                connection.createStatement().executeUpdate("INSERT INTO emprunts (livre_id, membre_id, date_emprunt, date_retour_prevue, statut, notes) VALUES "
                        + "(1, 1, '2026-06-10', '2026-06-24', 'En cours', 'Exemplaire utilisé en club'), "
                        + "(2, 2, '2026-05-28', '2026-06-11', 'Retourné', 'Retour à temps')");
            }
        } catch (Exception e) {
            System.err.println("Unable to seed MySQL database.");
            e.printStackTrace();
        }
    }

    private static boolean isTableEmpty(Connection connection, String tableName) {
        try (var query = connection.createStatement();
             var results = query.executeQuery("SELECT COUNT(*) FROM " + tableName)) {
            return results.next() && results.getInt(1) == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}