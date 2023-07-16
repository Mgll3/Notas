package controller;

import Model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;


public class ControladorPantallaFinal {
    @FXML
    public Label Frase;
    @FXML
    public Button closeButton;
    @FXML
    private TableView<info> tableView;
    @FXML
    public TableColumn<info, String> tableColumn1;
    @FXML
    public TableColumn<info, String> tableColumn2;
    @FXML
    public TableColumn<info, String> tableColumn3;


    private Model model;

    @FXML
    public void initialize() {

        Frase.setTextAlignment(TextAlignment.CENTER);
        Frase.setMaxWidth(500);
        Frase.setText(model.elegirFraseAleatoria());
        tableColumn1.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tableColumn2.setCellValueFactory(new PropertyValueFactory<>("Correctas"));
        tableColumn3.setCellValueFactory(new PropertyValueFactory<>("tiempo"));
        tableView.setItems(puntajesList);

    }

    private ObservableList<info> puntajesList = FXCollections.observableArrayList(
            new info(model.getPuntajes().get(9)[0],model.getPuntajes().get(9)[1],model.getPuntajes().get(9)[2]),
            new info(model.getPuntajes().get(8)[0],model.getPuntajes().get(8)[1],model.getPuntajes().get(8)[2]),
            new info(model.getPuntajes().get(7)[0],model.getPuntajes().get(7)[1],model.getPuntajes().get(7)[2]),
            new info(model.getPuntajes().get(6)[0],model.getPuntajes().get(6)[1],model.getPuntajes().get(6)[2]),
            new info(model.getPuntajes().get(5)[0],model.getPuntajes().get(5)[1],model.getPuntajes().get(5)[2]),
            new info(model.getPuntajes().get(4)[0],model.getPuntajes().get(4)[1],model.getPuntajes().get(4)[2]),
            new info(model.getPuntajes().get(3)[0],model.getPuntajes().get(3)[1],model.getPuntajes().get(3)[2]),
            new info(model.getPuntajes().get(2)[0],model.getPuntajes().get(2)[1],model.getPuntajes().get(2)[2]),
            new info(model.getPuntajes().get(1)[0],model.getPuntajes().get(1)[1],model.getPuntajes().get(1)[2]),
            new info(model.getPuntajes().get(0)[0],model.getPuntajes().get(0)[1],model.getPuntajes().get(0)[2])
    );

    public void setModel(Model model) {
        this.model = model;
    }

    public void btnVolver(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }



    public class info {

        private SimpleStringProperty nombre;
        private SimpleStringProperty correctas;
        private SimpleStringProperty tiempo;

        public info(String nombre, String correctas, String tiempo) {
            this.nombre = new SimpleStringProperty(nombre);
            this.correctas = new SimpleStringProperty(correctas);
            this.tiempo = new SimpleStringProperty(tiempo);
        }

        public String getNombre() {
            return nombre.get();
        }

        public SimpleStringProperty nombreProperty() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre.set(nombre);
        }

        public String getCorrectas() {
            return correctas.get();
        }

        public SimpleStringProperty correctasProperty() {
            return correctas;
        }

        public void setCorrectas(String correctas) {
            this.correctas.set(correctas);
        }

        public String gettiempo() {
            return tiempo.get();
        }

        public SimpleStringProperty tiempoProperty() {
            return tiempo;
        }

        public void settiempo(String tiempo) {
            this.tiempo.set(tiempo);
        }
    }


}
