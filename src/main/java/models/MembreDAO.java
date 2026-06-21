package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MembreDAO {
    public List<Membre> findAll() {
        List<Membre> membres = new ArrayList<>();
        String sql = "SELECT id, nom, email, telephone, date_inscription FROM membres ORDER BY id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Membre membre = new Membre(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("email"),
                        resultSet.getString("telephone"),
                        resultSet.getString("date_inscription")
                );
                membres.add(membre);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return membres;
    }

    public boolean create(Membre membre) {
        String sql = "INSERT INTO membres (nom, email, telephone, date_inscription) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, membre.getNom());
            statement.setString(2, membre.getEmail());
            statement.setString(3, membre.getTelephone());
            statement.setString(4, membre.getDateInscription());
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Membre membre) {
        String sql = "UPDATE membres SET nom = ?, email = ?, telephone = ?, date_inscription = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, membre.getNom());
            statement.setString(2, membre.getEmail());
            statement.setString(3, membre.getTelephone());
            statement.setString(4, membre.getDateInscription());
            statement.setInt(5, membre.getId());
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
        String sql = "SELECT id, nom, email, telephone, date_inscription FROM membres WHERE id = ?";
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
                            resultSet.getString("date_inscription")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
