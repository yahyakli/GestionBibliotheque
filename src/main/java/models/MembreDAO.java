package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MembreDAO {
    public List<Membre> findAll() {
        List<Membre> membres = new ArrayList<>();
        String sql = "SELECT id, nom, email, telephone, adresse, date_naissance, date_inscription, type_adherent, statut FROM membres ORDER BY id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Membre membre = new Membre(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("email"),
                        resultSet.getString("telephone"),
                        resultSet.getString("adresse"),
                        resultSet.getString("date_naissance"),
                        resultSet.getString("date_inscription"),
                        resultSet.getString("type_adherent"),
                        resultSet.getString("statut")
                );
                membres.add(membre);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return membres;
    }

    public boolean create(Membre membre) {
        String sql = "INSERT INTO membres (nom, email, telephone, adresse, date_naissance, date_inscription, type_adherent, statut) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, membre.getNom());
            statement.setString(2, membre.getEmail());
            statement.setString(3, membre.getTelephone());
            statement.setString(4, membre.getAdresse());
            statement.setString(5, membre.getDateNaissance());
            statement.setString(6, membre.getDateInscription());
            statement.setString(7, membre.getTypeAdherent());
            statement.setString(8, membre.getStatut());
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Membre membre) {
        String sql = "UPDATE membres SET nom = ?, email = ?, telephone = ?, adresse = ?, date_naissance = ?, date_inscription = ?, type_adherent = ?, statut = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, membre.getNom());
            statement.setString(2, membre.getEmail());
            statement.setString(3, membre.getTelephone());
            statement.setString(4, membre.getAdresse());
            statement.setString(5, membre.getDateNaissance());
            statement.setString(6, membre.getDateInscription());
            statement.setString(7, membre.getTypeAdherent());
            statement.setString(8, membre.getStatut());
            statement.setInt(9, membre.getId());
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int membreId) {
        String sql = "DELETE FROM membres WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, membreId);
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Membre findById(int id) {
        String sql = "SELECT id, nom, email, telephone, adresse, date_naissance, date_inscription, type_adherent, statut FROM membres WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Membre(
                            resultSet.getInt("id"),
                            resultSet.getString("nom"),
                            resultSet.getString("email"),
                            resultSet.getString("telephone"),
                            resultSet.getString("adresse"),
                            resultSet.getString("date_naissance"),
                            resultSet.getString("date_inscription"),
                            resultSet.getString("type_adherent"),
                            resultSet.getString("statut")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
