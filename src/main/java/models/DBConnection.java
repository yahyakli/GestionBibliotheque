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
        boolean dbJustCreated = false;
        try (Connection serverConnection = DriverManager.getConnection(SERVER_URL, USER, PASSWORD);
             Statement serverStatement = serverConnection.createStatement()) {
            try (var rs = serverStatement.executeQuery("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + DB_NAME + "'")) {
                if (!rs.next()) {
                    serverStatement.executeUpdate("CREATE DATABASE " + DB_NAME + " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
                    dbJustCreated = true;
                }
            }
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

        } catch (Exception e) {
            System.err.println("Unable to initialize MySQL schema.");
            e.printStackTrace();
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