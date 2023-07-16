package controller;

import Model.Model;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;


public class ControladorElegirModo extends ControladorInicio{

    @FXML
    public Button F1;
    @FXML
    public Button btnJM2;

    @FXML
    public RadioButton RB1;
    @FXML
    public RadioButton RB2;
    @FXML
    public RadioButton RB3;
    @FXML
    public RadioButton RB4;
    @FXML
    public RadioButton RB5;
    @FXML
    public RadioButton RB6;
    @FXML
    public TextField J2;

    private int selectedTopic;




    ToggleGroup toggleGroup = new ToggleGroup();
    final int maxLength = 10;




    @FXML
    public void initialize() throws IOException {
        model.leerPuntajes();
        J2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (J2.getText().length() > maxLength) {
                    String s = J2.getText().substring(0, maxLength);
                    J2.setText(s);
                }
            }
        });
        RB1.setToggleGroup(toggleGroup);
        RB2.setToggleGroup(toggleGroup);
        RB3.setToggleGroup(toggleGroup);
        RB4.setToggleGroup(toggleGroup);
        RB5.setToggleGroup(toggleGroup);
        RB6.setToggleGroup(toggleGroup);
// listen to changes in selected toggle
        toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> System.out.println());
    }



    private void resetButtons(){
        RB1.setStyle(null);
        RB2.setStyle(null);
        RB3.setStyle(null);
        RB4.setStyle(null);
        RB5.setStyle(null);
        RB6.setStyle(null);
        RadioButton aux;
        switch (selectedTopic){
            case 1:
                RB1.setStyle("-fx-text-fill: #2fff00");
                break;
            case 2:
                RB2.setStyle("-fx-text-fill: #ff0000");
                break;
            case 3:
                RB3.setStyle("-fx-text-fill: #000fff");
                break;
            case 4:
                RB4.setStyle("-fx-text-fill: #ffdd00");
                break;
            case 5:
                RB5.setStyle("-fx-text-fill: #bb00ff");
                break;
            case 6:
                RB6.setStyle("-fx-text-fill: #00ff7b");
                break;
            default:
                break;
        }




    }

    public void btnPuntajes(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Final.fxml"));
        Parent root = loader.load();
        ControladorPantallaFinal controlador = new ControladorPantallaFinal();
        loader.setController(controlador);
        Scene PantallaFinal = new Scene(root);
        Stage stage3 = new Stage();
        stage3.initModality(Modality.APPLICATION_MODAL);
        stage3.setScene(PantallaFinal);
        stage3.setResizable(false);
        controlador.setModel(model);
        stage3.show();
    }

    public void btn1(javafx.event.ActionEvent actionEvent){
        F1.setDisable(false);
        selectedTopic = 1;
        model.setTopicoActualNum(selectedTopic);
        resetButtons();
    }

    public void btn2(javafx.event.ActionEvent actionEvent){
        F1.setDisable(false);
        selectedTopic = 2;
        model.setTopicoActualNum(selectedTopic);
        resetButtons();
    }
    public void btn3(javafx.event.ActionEvent actionEvent){
        F1.setDisable(false);
        selectedTopic = 3;
        model.setTopicoActualNum(selectedTopic);
        resetButtons();
    }
    public void btn4(javafx.event.ActionEvent actionEvent){
        F1.setDisable(false);
        selectedTopic = 4;
        model.setTopicoActualNum(selectedTopic);
        resetButtons();
    }
    public void btn5(javafx.event.ActionEvent actionEvent){
        F1.setDisable(false);
        selectedTopic = 5;
        model.setTopicoActualNum(selectedTopic);
        resetButtons();
    }
    public void btn6(javafx.event.ActionEvent actionEvent){
        F1.setDisable(false);
        selectedTopic = 6;
        model.setTopicoActualNum(selectedTopic);
        resetButtons();
    }

    public void btnJM(javafx.event.ActionEvent actionEvent){
        J2.setDisable(false);
        btnJM2.setDisable(false);

    }
    public void btnJM2Action(javafx.event.ActionEvent actionEvent) throws IOException {

        model.setJ2(new Model.Jugador(J2.getText(),0,0));
        model.setAleatorio(true);
        Model.Topico b = model.elegirTopicoAleatorio();
        model.setTopicoActual(b);
        irDesarrolloMulti();
    }

    public void btnAleatorio(javafx.event.ActionEvent actionEvent) throws IOException {
        model.setAleatorio(true);
        Model.Topico a= model.elegirTopicoAleatorio();
        model.setTopicoActual(a);

        irDesarrollo();

    }

    public void btnF1(javafx.event.ActionEvent actionEvent) throws IOException {
        model.setTopicoActual(model.getTopicos().get(selectedTopic-1));
        model.setAleatorio(false);
        irDesarrollo();
    }

    private void irDesarrollo()throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Desarrollo.fxml"));
        Parent root = loader.load();
        ControladorDesarrollo controlador = loader.getController();
        Scene Desarrollo = new Scene(root);
        Stage stage1 = new Stage();
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setScene(Desarrollo);
        stage1.setResizable(false);
        model.setStageSolo(stage1);

        stage1.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean onHidden, Boolean onShown)
            {
                if(controlador.isDone()){
                    stage1.close();
                }
            }
        });
        stage1.show();
    }

    private void irDesarrolloMulti() throws IOException {
        if (J2.getText().equals(null) || J2.getText().equals("")) {
            JOptionPane.showMessageDialog(null,"Pongale nombre al 2do Jugador, por favor.");
        }else if (J2.getText().equals(model.getJ1().getNombre())){
            JOptionPane.showMessageDialog(null,"Su nombre no puede ser igual al Jugador 1.");
        }else
         {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/DesarrolloMulti.fxml"));
            Parent root = loader.load();
            ControladorDesarrolloMulti controlador = loader.getController();
            Scene DesarrolloMulti = new Scene(root);
            Stage stage2 = new Stage();
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.setScene(DesarrolloMulti);
            stage2.setResizable(false);
            model.setStageMulti(stage2);
            stage2.show();

        }
    }

    public void checkLetters(){
        Pattern pattern = Pattern.compile("[a-zA-Z]*");
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (pattern.matcher(c.getControlNewText()).matches()) {
                return c ;
            } else {
                return null ;
            }
        };
        TextFormatter<String> formatter = new TextFormatter<>(filter);
        J2.setTextFormatter(formatter);
    }
}
