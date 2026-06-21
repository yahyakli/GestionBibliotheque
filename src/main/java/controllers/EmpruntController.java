package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import models.Emprunt;
import models.EmpruntDAO;
import models.Livre;
import models.LivreDAO;
import models.Membre;
import models.MembreDAO;

public class EmpruntController {
    private final EmpruntDAO empruntDAO = new EmpruntDAO();
    private final LivreDAO livreDAO = new LivreDAO();
    private final MembreDAO membreDAO = new MembreDAO();

    private final ObservableList<Emprunt> emprunts = FXCollections.observableArrayList();
    private final ObservableList<Livre> livres = FXCollections.observableArrayList();
    private final ObservableList<Membre> membres = FXCollections.observableArrayList();

    private final TableView<Emprunt> tableView = new TableView<>();
    private final ComboBox<Livre> livreComboBox = new ComboBox<>();
    private final ComboBox<Membre> membreComboBox = new ComboBox<>();
    private final TextField dateEmpruntField = new TextField();

    public Node getView() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(12));

        TableColumn<Emprunt, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(60);

        TableColumn<Emprunt, String> livreColumn = new TableColumn<>("Livre");
        livreColumn.setCellValueFactory(param -> {
            int livreId = param.getValue().getLivreId();
            return FXCollections.observableValue(getLivreTitle(livreId));
        });
        livreColumn.setPrefWidth(260);

        TableColumn<Emprunt, String> membreColumn = new TableColumn<>("Membre");
        membreColumn.setCellValueFactory(param -> {
            int membreId = param.getValue().getMembreId();
            return FXCollections.observableValue(getMembreName(membreId));
        });
        membreColumn.setPrefWidth(260);

        TableColumn<Emprunt, String> dateEmpruntColumn = new TableColumn<>("Date emprunt");
        dateEmpruntColumn.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        dateEmpruntColumn.setPrefWidth(140);

        TableColumn<Emprunt, String> dateRetourColumn = new TableColumn<>("Date retour");
        dateRetourColumn.setCellValueFactory(new PropertyValueFactory<>("dateRetour"));
        dateRetourColumn.setPrefWidth(140);

        TableColumn<Emprunt, String> statutColumn = new TableColumn<>("Statut");
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        statutColumn.setPrefWidth(120);

        tableView.getColumns().addAll(idColumn, livreColumn, membreColumn, dateEmpruntColumn, dateRetourColumn, statutColumn);
        tableView.setItems(emprunts);
        tableView.setPlaceholder(new Label("Aucun emprunt trouvé"));

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(16, 0, 0, 0));

        dateEmpruntField.setPromptText("YYYY-MM-DD");
        dateEmpruntField.setText(java.time.LocalDate.now().toString());

        form.add(new Label("Livre"), 0, 0);
        form.add(livreComboBox, 1, 0);
        form.add(new Label("Membre"), 0, 1);
        form.add(membreComboBox, 1, 1);
        form.add(new Label("Date emprunt"), 0, 2);
        form.add(dateEmpruntField, 1, 2);

        Button addButton = new Button("Emprunter");
        Button returnButton = new Button("Retourner");
        Button deleteButton = new Button("Supprimer");

        addButton.setOnAction(event -> addEmprunt());
        returnButton.setOnAction(event -> returnEmprunt());
        deleteButton.setOnAction(event -> deleteEmprunt());

        HBox actions = new HBox(10, addButton, returnButton, deleteButton);
        actions.setPadding(new Insets(14, 0, 0, 0));

        root.setCenter(tableView);
        root.setRight(form);
        root.setBottom(actions);
        root.setPrefWidth(1000);

        refreshLists();
        return root;
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
                .orElse("Livres supprimé");
    }

    private String getMembreName(int id) {
        return membres.stream().filter(m -> m.getId() == id)
                .map(Membre::getNom)
                .findFirst()
                .orElse("Membre supprimé");
    }

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
