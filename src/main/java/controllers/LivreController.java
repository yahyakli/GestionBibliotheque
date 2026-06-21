package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Livre;
import models.LivreDAO;

public class LivreController {
    @FXML
    private TableView<Livre> tableView;

    @FXML
    private TableColumn<Livre, Integer> idColumn;

    @FXML
    private TableColumn<Livre, String> titreColumn;

    @FXML
    private TableColumn<Livre, String> auteurColumn;

    @FXML
    private TableColumn<Livre, String> categorieColumn;

    @FXML
    private TableColumn<Livre, Integer> quantiteColumn;

    @FXML
    private TextField titreField;

    @FXML
    private TextField auteurField;

    @FXML
    private TextField categorieField;

    @FXML
    private TextField quantiteField;

    private final LivreDAO livreDAO = new LivreDAO();
    private final ObservableList<Livre> livres = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        auteurColumn.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));

        tableView.setItems(livres);
        tableView.setPlaceholder(new Label("Aucun livre trouvé"));
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> showSelectedLivre(newSelection));

        refreshList();
    }

    private void refreshList() {
        livres.setAll(livreDAO.findAll());
    }

    private void showSelectedLivre(Livre livre) {
        if (livre == null) {
            clearForm();
            return;
        }
        titreField.setText(livre.getTitre());
        auteurField.setText(livre.getAuteur());
        categorieField.setText(livre.getCategorie());
        quantiteField.setText(String.valueOf(livre.getQuantite()));
    }

    private void clearForm() {
        titreField.clear();
        auteurField.clear();
        categorieField.clear();
        quantiteField.clear();
    }

    @FXML
    private void addLivre() {
        String titre = titreField.getText().trim();
        String auteur = auteurField.getText().trim();
        String categorie = categorieField.getText().trim();
        String quantiteText = quantiteField.getText().trim();

        if (titre.isEmpty() || auteur.isEmpty() || quantiteText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Veuillez saisir le titre, l'auteur et la quantité.");
            return;
        }

        int quantite;
        try {
            quantite = Integer.parseInt(quantiteText);
            if (quantite < 0) {
                showAlert(Alert.AlertType.WARNING, "Validation", "La quantité doit être un nombre positif.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Validation", "La quantité doit être un nombre entier.");
            return;
        }

        Livre livre = new Livre(titre, auteur, categorie, quantite);
        if (livreDAO.create(livre)) {
            refreshList();
            clearForm();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Livre ajouté avec succès.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ajouter le livre.");
        }
    }

    @FXML
    private void updateLivre() {
        Livre selectedLivre = tableView.getSelectionModel().getSelectedItem();
        if (selectedLivre == null) {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner un livre pour le modifier.");
            return;
        }

        String titre = titreField.getText().trim();
        String auteur = auteurField.getText().trim();
        String categorie = categorieField.getText().trim();
        String quantiteText = quantiteField.getText().trim();

        if (titre.isEmpty() || auteur.isEmpty() || quantiteText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Veuillez saisir le titre, l'auteur et la quantité.");
            return;
        }

        int quantite;
        try {
            quantite = Integer.parseInt(quantiteText);
            if (quantite < 0) {
                showAlert(Alert.AlertType.WARNING, "Validation", "La quantité doit être un nombre positif.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Validation", "La quantité doit être un nombre entier.");
            return;
        }

        selectedLivre.setTitre(titre);
        selectedLivre.setAuteur(auteur);
        selectedLivre.setCategorie(categorie);
        selectedLivre.setQuantite(quantite);

        if (livreDAO.update(selectedLivre)) {
            refreshList();
            clearForm();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Livre modifié avec succès.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de modifier le livre.");
        }
    }

    @FXML
    private void deleteLivre() {
        Livre selectedLivre = tableView.getSelectionModel().getSelectedItem();
        if (selectedLivre == null) {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner un livre pour le supprimer.");
            return;
        }

        if (livreDAO.delete(selectedLivre.getId())) {
            refreshList();
            clearForm();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Livre supprimé avec succès.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer le livre.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
