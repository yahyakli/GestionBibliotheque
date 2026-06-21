package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import models.Membre;
import models.MembreDAO;

public class MembreController {
    private final MembreDAO membreDAO = new MembreDAO();
    private final ObservableList<Membre> membres = FXCollections.observableArrayList();
    private final TableView<Membre> tableView = new TableView<>();

    private final TextField nomField = new TextField();
    private final TextField emailField = new TextField();
    private final TextField telephoneField = new TextField();
    private final TextField dateInscriptionField = new TextField();

    public Node getView() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(12));

        TableColumn<Membre, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(60);

        TableColumn<Membre, String> nomColumn = new TableColumn<>("Nom");
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        nomColumn.setPrefWidth(240);

        TableColumn<Membre, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setPrefWidth(260);

        TableColumn<Membre, String> telephoneColumn = new TableColumn<>("Téléphone");
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        telephoneColumn.setPrefWidth(170);

        TableColumn<Membre, String> dateColumn = new TableColumn<>("Date inscription");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateInscription"));
        dateColumn.setPrefWidth(180);

        tableView.getColumns().addAll(idColumn, nomColumn, emailColumn, telephoneColumn, dateColumn);
        tableView.setItems(membres);
        tableView.setPlaceholder(new Label("Aucun membre trouvé"));
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> showSelectedMembre(newSelection));

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(16, 0, 0, 0));

        form.add(new Label("Nom"), 0, 0);
        form.add(nomField, 1, 0);
        form.add(new Label("Email"), 0, 1);
        form.add(emailField, 1, 1);
        form.add(new Label("Téléphone"), 0, 2);
        form.add(telephoneField, 1, 2);
        form.add(new Label("Date inscription"), 0, 3);
        form.add(dateInscriptionField, 1, 3);

        Button addButton = new Button("Ajouter");
        Button updateButton = new Button("Modifier");
        Button deleteButton = new Button("Supprimer");

        addButton.setOnAction(event -> addMembre());
        updateButton.setOnAction(event -> updateMembre());
        deleteButton.setOnAction(event -> deleteMembre());

        HBox actions = new HBox(10, addButton, updateButton, deleteButton);
        actions.setPadding(new Insets(14, 0, 0, 0));

        root.setCenter(tableView);
        root.setRight(form);
        root.setBottom(actions);
        root.setPrefWidth(1000);

        refreshList();
        return root;
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
