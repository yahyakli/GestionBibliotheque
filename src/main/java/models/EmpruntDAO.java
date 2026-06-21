package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDAO {
    public List<Emprunt> findAll() {
        List<Emprunt> emprunts = new ArrayList<>();
        String sql = "SELECT id, livre_id, membre_id, date_emprunt, date_retour, date_retour_prevue, statut, notes FROM emprunts ORDER BY id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Emprunt emprunt = new Emprunt(
                        resultSet.getInt("id"),
                        resultSet.getInt("livre_id"),
                        resultSet.getInt("membre_id"),
                        resultSet.getString("date_emprunt"),
                        resultSet.getString("date_retour"),
                        resultSet.getString("date_retour_prevue"),
                        resultSet.getString("statut"),
                        resultSet.getString("notes")
                );
                emprunts.add(emprunt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return emprunts;
    }

    public boolean create(Emprunt emprunt) {
        if (!hasAvailableCopies(emprunt.getLivreId())) {
            return false;
        }

        String insertSql = "INSERT INTO emprunts (livre_id, membre_id, date_emprunt, date_retour_prevue, statut, notes) VALUES (?, ?, ?, ?, ?, ?)";
        String updateLivreSql = "UPDATE livres SET quantite = quantite - 1 WHERE id = ?";

        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement updateLivre = connection.prepareStatement(updateLivreSql)) {
                updateLivre.setInt(1, emprunt.getLivreId());
                updateLivre.executeUpdate();
            }

            try (PreparedStatement insertEmprunt = connection.prepareStatement(insertSql)) {
                insertEmprunt.setInt(1, emprunt.getLivreId());
                insertEmprunt.setInt(2, emprunt.getMembreId());
                insertEmprunt.setString(3, emprunt.getDateEmprunt());
                insertEmprunt.setString(4, emprunt.getDateRetourPrevue());
                insertEmprunt.setString(5, emprunt.getStatut());
                insertEmprunt.setString(6, emprunt.getNotes());
                insertEmprunt.executeUpdate();
            }

            connection.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean returnEmprunt(int empruntId) {
        String querySql = "SELECT livre_id, statut FROM emprunts WHERE id = ?";
        String updateEmpruntSql = "UPDATE emprunts SET date_retour = ?, statut = ? WHERE id = ?";
        String updateLivreSql = "UPDATE livres SET quantite = quantite + 1 WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement queryStatement = connection.prepareStatement(querySql)) {
            queryStatement.setInt(1, empruntId);
            try (ResultSet resultSet = queryStatement.executeQuery()) {
                if (!resultSet.next()) {
                    return false;
                }

                int livreId = resultSet.getInt("livre_id");
                String currentStatus = resultSet.getString("statut");
                if (currentStatus != null && currentStatus.equalsIgnoreCase("Retourné")) {
                    return false;
                }

                connection.setAutoCommit(false);
                try (PreparedStatement updateEmprunt = connection.prepareStatement(updateEmpruntSql);
                     PreparedStatement updateLivre = connection.prepareStatement(updateLivreSql)) {
                    updateEmprunt.setString(1, java.time.LocalDate.now().toString());
                    updateEmprunt.setString(2, "Retourné");
                    updateEmprunt.setInt(3, empruntId);
                    updateEmprunt.executeUpdate();

                    updateLivre.setInt(1, livreId);
                    updateLivre.executeUpdate();
                }
                connection.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int empruntId) {
        String querySql = "SELECT livre_id, statut FROM emprunts WHERE id = ?";
        String deleteSql = "DELETE FROM emprunts WHERE id = ?";
        String updateLivreSql = "UPDATE livres SET quantite = quantite + 1 WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement queryStatement = connection.prepareStatement(querySql)) {
            queryStatement.setInt(1, empruntId);
            try (ResultSet resultSet = queryStatement.executeQuery()) {
                if (!resultSet.next()) {
                    return false;
                }
                int livreId = resultSet.getInt("livre_id");
                String currentStatus = resultSet.getString("statut");
                connection.setAutoCommit(false);
                if (currentStatus != null && currentStatus.equalsIgnoreCase("En cours")) {
                    try (PreparedStatement updateLivre = connection.prepareStatement(updateLivreSql)) {
                        updateLivre.setInt(1, livreId);
                        updateLivre.executeUpdate();
                    }
                }

                try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                    deleteStatement.setInt(1, empruntId);
                    deleteStatement.executeUpdate();
                }
                connection.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean hasAvailableCopies(int livreId) {
        String sql = "SELECT quantite FROM livres WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, livreId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("quantite") > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
