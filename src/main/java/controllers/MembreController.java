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
import models.Membre;
import models.MembreDAO;

public class MembreController {
    @FXML
    private TableView<Membre> tableView;

    @FXML
    private TableColumn<Membre, Integer> idColumn;

    @FXML
    private TableColumn<Membre, String> nomColumn;

    @FXML
    private TableColumn<Membre, String> emailColumn;

    @FXML
    private TableColumn<Membre, String> telephoneColumn;

    @FXML
    private TableColumn<Membre, String> dateColumn;

    @FXML
    private TextField nomField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField telephoneField;

    @FXML
    private TextField dateInscriptionField;

    private final MembreDAO membreDAO = new MembreDAO();
    private final ObservableList<Membre> membres = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateInscription"));

        tableView.setItems(membres);
        tableView.setPlaceholder(new Label("Aucun membre trouvé"));
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> showSelectedMembre(newSelection));

        refreshList();
    }

    private void refreshList() {
        membres.setAll(membreDAO.findAll());
    }

    private void showSelectedMembre(Membre membre) {
        if (membre == null) {
            clearForm();
            return;
        }
        nomField.setText(membre.getNom());
        emailField.setText(membre.getEmail());
        telephoneField.setText(membre.getTelephone());
        dateInscriptionField.setText(membre.getDateInscription());
    }

    private void clearForm() {
        nomField.clear();
        emailField.clear();
        telephoneField.clear();
        dateInscriptionField.clear();
    }

    @FXML
    private void addMembre() {
        String nom = nomField.getText().trim();
        String email = emailField.getText().trim();
        String telephone = telephoneField.getText().trim();
        String dateInscription = dateInscriptionField.getText().trim();

        if (nom.isEmpty() || email.isEmpty() || dateInscription.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Veuillez remplir le nom, l'email et la date d'inscription.");
            return;
        }

        Membre membre = new Membre(nom, email, telephone, dateInscription);
        if (membreDAO.create(membre)) {
            refreshList();
            clearForm();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Membre ajouté avec succès.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ajouter le membre.");
        }
    }

    @FXML
    private void updateMembre() {
        Membre selectedMembre = tableView.getSelectionModel().getSelectedItem();
        if (selectedMembre == null) {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner un membre pour le modifier.");
            return;
        }

        String nom = nomField.getText().trim();
        String email = emailField.getText().trim();
        String telephone = telephoneField.getText().trim();
        String dateInscription = dateInscriptionField.getText().trim();

        if (nom.isEmpty() || email.isEmpty() || dateInscription.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Veuillez remplir le nom, l'email et la date d'inscription.");
            return;
        }

        selectedMembre.setNom(nom);
        selectedMembre.setEmail(email);
        selectedMembre.setTelephone(telephone);
        selectedMembre.setDateInscription(dateInscription);

        if (membreDAO.update(selectedMembre)) {
            refreshList();
            clearForm();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Membre modifié avec succès.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de modifier le membre.");
        }
    }

    @FXML
    private void deleteMembre() {
        Membre selectedMembre = tableView.getSelectionModel().getSelectedItem();
        if (selectedMembre == null) {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner un membre pour le supprimer.");
            return;
        }

        if (membreDAO.delete(selectedMembre.getId())) {
            refreshList();
            clearForm();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Membre supprimé avec succès.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer le membre.");
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
