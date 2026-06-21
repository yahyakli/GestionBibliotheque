package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LivreDAO {
    public List<Livre> findAll() {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT id, titre, auteur, categorie, isbn, editeur, date_publication, emplacement, quantite FROM livres ORDER BY id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Livre livre = new Livre(
                        resultSet.getInt("id"),
                        resultSet.getString("titre"),
                        resultSet.getString("auteur"),
                        resultSet.getString("categorie"),
                        resultSet.getString("isbn"),
                        resultSet.getString("editeur"),
                        resultSet.getString("date_publication"),
                        resultSet.getString("emplacement"),
                        resultSet.getInt("quantite")
                );
                livres.add(livre);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return livres;
    }

    public boolean create(Livre livre) {
        String sql = "INSERT INTO livres (titre, auteur, categorie, isbn, editeur, date_publication, emplacement, quantite) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, livre.getTitre());
            statement.setString(2, livre.getAuteur());
            statement.setString(3, livre.getCategorie());
            statement.setString(4, livre.getIsbn());
            statement.setString(5, livre.getEditeur());
            statement.setString(6, livre.getDatePublication());
            statement.setString(7, livre.getEmplacement());
            statement.setInt(8, livre.getQuantite());
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Livre livre) {
        String sql = "UPDATE livres SET titre = ?, auteur = ?, categorie = ?, isbn = ?, editeur = ?, date_publication = ?, emplacement = ?, quantite = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, livre.getTitre());
            statement.setString(2, livre.getAuteur());
            statement.setString(3, livre.getCategorie());
            statement.setString(4, livre.getIsbn());
            statement.setString(5, livre.getEditeur());
            statement.setString(6, livre.getDatePublication());
            statement.setString(7, livre.getEmplacement());
            statement.setInt(8, livre.getQuantite());
            statement.setInt(9, livre.getId());
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int livreId) {
        String sql = "DELETE FROM livres WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, livreId);
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Livre findById(int id) {
        String sql = "SELECT id, titre, auteur, categorie, isbn, editeur, date_publication, emplacement, quantite FROM livres WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Livre(
                            resultSet.getInt("id"),
                            resultSet.getString("titre"),
                            resultSet.getString("auteur"),
                            resultSet.getString("categorie"),
                            resultSet.getString("isbn"),
                            resultSet.getString("editeur"),
                            resultSet.getString("date_publication"),
                            resultSet.getString("emplacement"),
                            resultSet.getInt("quantite")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
