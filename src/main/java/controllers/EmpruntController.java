package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Emprunt;
import models.EmpruntDAO;
import models.Livre;
import models.LivreDAO;
import models.Membre;
import models.MembreDAO;

public class EmpruntController {
    @FXML
    private TableView<Emprunt> tableView;

    @FXML
    private TableColumn<Emprunt, Integer> idColumn;

    @FXML
    private TableColumn<Emprunt, String> livreColumn;

    @FXML
    private TableColumn<Emprunt, String> membreColumn;

    @FXML
    private TableColumn<Emprunt, String> dateEmpruntColumn;

    @FXML
    private TableColumn<Emprunt, String> dateRetourColumn;

    @FXML
    private TableColumn<Emprunt, String> statutColumn;

    @FXML
    private ComboBox<Livre> livreComboBox;

    @FXML
    private ComboBox<Membre> membreComboBox;

    @FXML
    private TextField dateEmpruntField;

    private final EmpruntDAO empruntDAO = new EmpruntDAO();
    private final LivreDAO livreDAO = new LivreDAO();
    private final MembreDAO membreDAO = new MembreDAO();

    private final ObservableList<Emprunt> emprunts = FXCollections.observableArrayList();
    private final ObservableList<Livre> livres = FXCollections.observableArrayList();
    private final ObservableList<Membre> membres = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        livreColumn.setCellValueFactory(param -> FXCollections.observableValue(getLivreTitle(param.getValue().getLivreId())));
        membreColumn.setCellValueFactory(param -> FXCollections.observableValue(getMembreName(param.getValue().getMembreId())));
        dateEmpruntColumn.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        dateRetourColumn.setCellValueFactory(new PropertyValueFactory<>("dateRetour"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));

        tableView.setItems(emprunts);
        tableView.setPlaceholder(new Label("Aucun emprunt trouvé"));

        dateEmpruntField.setText(java.time.LocalDate.now().toString());

        refreshLists();
    }

    private void refreshLists() {
        livres.setAll(livreDAO.findAll());
        membres.setAll(membreDAO.findAll());
        emprunts.setAll(empruntDAO.findAll());
        livreComboBox.setItems(livres);
        membreComboBox.setItems(membres);
    }

    private String getLivreTitle(int id) {
        return livres.stream().filter(l -> l.getId() == id)
                .map(Livre::getTitre)
                .findFirst()
                .orElse("Livre supprimé");
    }

    private String getMembreName(int id) {
        return membres.stream().filter(m -> m.getId() == id)
                .map(Membre::getNom)
                .findFirst()
                .orElse("Membre supprimé");
    }

    @FXML
    private void addEmprunt() {
        Livre livre = livreComboBox.getValue();
        Membre membre = membreComboBox.getValue();
        String dateEmprunt = dateEmpruntField.getText().trim();

        if (livre == null || membre == null || dateEmprunt.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Veuillez sélectionner un livre, un membre et une date d'emprunt.");
            return;
        }

        Emprunt emprunt = new Emprunt(livre.getId(), membre.getId(), dateEmprunt, "En cours");
        if (!empruntDAO.create(emprunt)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de créer l'emprunt. Vérifiez la quantité du livre.");
            return;
        }

        refreshLists();
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Emprunt enregistré avec succès.");
    }

    @FXML
    private void returnEmprunt() {
        Emprunt selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner un emprunt pour le retourner.");
            return;
        }

        if (selected.getStatut() != null && selected.getStatut().equalsIgnoreCase("Retourné")) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "Cet emprunt a déjà été retourné.");
            return;
        }

        if (empruntDAO.returnEmprunt(selected.getId())) {
            refreshLists();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Emprunt retourné avec succès.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de retourner cet emprunt.");
        }
    }

    @FXML
    private void deleteEmprunt() {
        Emprunt selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner un emprunt pour le supprimer.");
            return;
        }

        if (empruntDAO.delete(selected.getId())) {
            refreshLists();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Emprunt supprimé avec succès.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer cet emprunt.");
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
