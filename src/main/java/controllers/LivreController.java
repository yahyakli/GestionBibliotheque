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
import models.Livre;
import models.LivreDAO;

public class LivreController {
    private final LivreDAO livreDAO = new LivreDAO();
    private final ObservableList<Livre> livres = FXCollections.observableArrayList();
    private final TableView<Livre> tableView = new TableView<>();

    private final TextField titreField = new TextField();
    private final TextField auteurField = new TextField();
    private final TextField categorieField = new TextField();
    private final TextField quantiteField = new TextField();

    public Node getView() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(12));

        TableColumn<Livre, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(60);

        TableColumn<Livre, String> titreColumn = new TableColumn<>("Titre");
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        titreColumn.setPrefWidth(280);

        TableColumn<Livre, String> auteurColumn = new TableColumn<>("Auteur");
        auteurColumn.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        auteurColumn.setPrefWidth(200);

        TableColumn<Livre, String> categorieColumn = new TableColumn<>("Catégorie");
        categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        categorieColumn.setPrefWidth(180);

        TableColumn<Livre, Integer> quantiteColumn = new TableColumn<>("Quantité");
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        quantiteColumn.setPrefWidth(100);

        tableView.getColumns().addAll(idColumn, titreColumn, auteurColumn, categorieColumn, quantiteColumn);
        tableView.setItems(livres);
        tableView.setPlaceholder(new Label("Aucun livre trouvé"));
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> showSelectedLivre(newSelection));

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(16, 0, 0, 0));

        form.add(new Label("Titre"), 0, 0);
        form.add(titreField, 1, 0);
        form.add(new Label("Auteur"), 0, 1);
        form.add(auteurField, 1, 1);
        form.add(new Label("Catégorie"), 0, 2);
        form.add(categorieField, 1, 2);
        form.add(new Label("Quantité"), 0, 3);
        form.add(quantiteField, 1, 3);

        Button addButton = new Button("Ajouter");
        Button updateButton = new Button("Modifier");
        Button deleteButton = new Button("Supprimer");

        addButton.setOnAction(event -> addLivre());
        updateButton.setOnAction(event -> updateLivre());
        deleteButton.setOnAction(event -> deleteLivre());

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
