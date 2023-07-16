package controller;

import Model.Model;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.Collections;
import java.util.Stack;


public class ControladorDesarrolloMulti extends ControladorElegirModo {

    @FXML
    AnchorPane AnchorMain;
    @FXML
    public Button F1;
    @FXML
    public Button F2;
    @FXML
    public Button F3;
    @FXML
    public Button F4;
    @FXML
    public Button F51;
    @FXML
    public Button F52;
    @FXML
    public Label puntajeJ1;
    @FXML
    public Label puntajeJ2;
    @FXML
    public Label nombreJ1;
    @FXML
    public Label nombreJ2;
    @FXML
    public Label pregunta;
    @FXML
    public Label topicoLabel;
    @FXML
    public VBox BoxButtons;
    @FXML
    public Label timerLabel;
    @FXML
    public Pane PaneJ1;
    @FXML
    public Pane PaneJ2;
    @FXML
    public Button salir;
    @FXML
    public TextFlow preguntaFlow;

    public Model.Timer timer;
    private Integer puntajeCountJ1 = 0;
    private Integer puntajeCountJ2 = 0;
    private Integer preguntaActual = -1;
    final Integer rondas = 30;
    private Stack<Model.Pregunta> preguntaStack = new Stack<>();
    private boolean ayudaUsadaJ1 = false;
    private boolean ayudaUsadaJ2 = false;
    private int turnoActual = (int) Math.floor(Math.random() * 1);

    @FXML
    public void initialize() {
        pregunta.setTextAlignment(TextAlignment.CENTER);
        pregunta.setMaxWidth(500);
        generarBancoAleatorio();
        timerLabel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (timerLabel.getText().equals("0")) {
                    resetContenedores();
                    pasarPregunta();
                }
            }
        });
        String nameAux = model.getJ1().getNombre().toLowerCase();
        nombreJ1.setText(nameAux.substring(0, 1).toUpperCase() + nameAux.substring(1));
        nameAux = model.getJ2().getNombre().toLowerCase();
        nombreJ2.setText(nameAux.substring(0, 1).toUpperCase() + nameAux.substring(1));
        switchTurns();
        pasarPregunta();

    }

    private void switchTurns() {
        if (turnoActual == 0) {
            PaneJ2.setDisable(true);
            PaneJ2.setStyle(null);
            PaneJ1.setStyle("-fx-background-color: #00FFF7");
            PaneJ1.setDisable(false);
        } else {
            PaneJ1.setDisable(true);
            PaneJ1.setStyle(null);
            PaneJ2.setStyle("-fx-background-color: #7019d6");
            PaneJ2.setDisable(false);
        }
    }

    public void btnSalir(ActionEvent actionEvent){
        Stage stage = (Stage) nombreJ1.getScene().getWindow();
        stage.close();
    }

    private void shuffleButtons() {
        ObservableList<Node> workingCollection = FXCollections.observableArrayList(BoxButtons.getChildren());
        Collections.shuffle(workingCollection);
        BoxButtons.getChildren().setAll(workingCollection);
    }

    public void pasarPregunta() {
        colorPregunta();
        switchTurns();
        shuffleButtons();
        preguntaActual++;
        if (preguntaActual < rondas) {
                pregunta.setText(preguntaStack.peek().getPregunta());
                F1.setText(preguntaStack.peek().getRespuesta1());
                F2.setText(preguntaStack.peek().getRespuesta2());
                F3.setText(preguntaStack.peek().getRespuesta3());
                F4.setText(preguntaStack.peek().getRespuesta4());
                topicoLabel.setText(preguntaStack.pop().getTopico());
        } else {
            paintButtons();
            salir.setDisable(false);
            salir.setVisible(true);
            if (puntajeCountJ1 > puntajeCountJ2) {
                pregunta.setText("¡Ha ganado " + model.getJ1().getNombre()+"!");
                preguntaFlow.setStyle("-fx-background-color: #00fff7");


            } else if (puntajeCountJ1 < puntajeCountJ2) {
                pregunta.setText("¡Ha ganado " + model.getJ2().getNombre()+"!");
                preguntaFlow.setStyle("-fx-background-color: #7019d6");
            }
            else{
                pregunta.setText("¡Ha sido un empate!");
                preguntaFlow.setStyle("-fx-background-color: #ffffff");
            }
        }


    }

    private void generarBancoAleatorio() {
        int i = 0;
        Stack<Model.Pregunta> aux = new Stack<>();
        aux.push(model.getTopicoActual().getConjuntoDePreguntas().get(0));
        while (i < rondas) {
            int rd = (int) Math.abs(Math.random() * 9);
            Model.Pregunta pregunta = model.elegirTopicoAleatorio().getConjuntoDePreguntas().get(rd);
            if (!aux.empty() && !aux.contains(pregunta)) {
                aux.push(pregunta);
                i++;
            }
        }
        while (!aux.empty()) {
            preguntaStack.push(aux.pop());
        }
    }

    private void temporizador() {
        if (preguntaActual < rondas) {
            timerLabel.setVisible(true);
            timer = new Model.Timer(timerLabel);
            AnchorMain.getChildren().add(timer);
        }
    }

    private void paintButtons() {
        F1.setStyle("-fx-background-color: #83FF00");
        F2.setStyle("-fx-background-color: #FF0000");
        F3.setStyle("-fx-background-color: #FF0000");
        F4.setStyle("-fx-background-color:#FF0000");
        F1.setDisable(true);
        F2.setDisable(true);
        F3.setDisable(true);
        F4.setDisable(true);
        F51.setDisable(true);
        F52.setDisable(true);

    }


    public void btnF1(ActionEvent actionEvent) {
        if (turnoActual == 0) {
            puntajeCountJ1++;
            turnoActual = 1;
            puntajeJ1.setText("Puntaje: " + puntajeCountJ1.toString());
        } else if (turnoActual == 1) {
            puntajeCountJ2++;
            turnoActual = 0;
            puntajeJ2.setText("Puntaje: " + puntajeCountJ2.toString());
        }
        paintButtons();
        model.winSound();
        temporizador();


    }

    public void btnF2(ActionEvent actionEvent) {
        if (turnoActual == 0) {
            turnoActual = 1;
        } else if (turnoActual == 1) {
            turnoActual = 0;
        }
        paintButtons();
        model.loseSound();
        temporizador();

    }

    public void btnF3(ActionEvent actionEvent) {
        if (turnoActual == 0) {
            turnoActual = 1;
        } else if (turnoActual == 1) {
            turnoActual = 0;
        }
        paintButtons();
        model.loseSound();
        temporizador();
    }

    public void btnF4(ActionEvent actionEvent) {
        if (turnoActual == 0) {
            turnoActual = 1;
        } else if (turnoActual == 1) {
            turnoActual = 0;
        }
        paintButtons();
        model.loseSound();
        temporizador();
    }

    public void btnF51(ActionEvent actionEvent) {
        F2.setVisible(false);
        F3.setVisible(false);
        F51.setDisable(true);
        ayudaUsadaJ1 = true;

    }

    public void btnF52(ActionEvent actionEvent) {
        F2.setVisible(false);
        F3.setVisible(false);
        F52.setDisable(true);
        ayudaUsadaJ2 = true;

    }

    public void resetContenedores() {
        timerLabel.setText("");
        timerLabel.setVisible(false);
        F2.setVisible(true);
        F3.setVisible(true);
        F1.setStyle(null);
        F2.setStyle(null);
        F3.setStyle(null);
        F4.setStyle(null);
        F1.setDisable(false);
        F2.setDisable(false);
        F3.setDisable(false);
        F4.setDisable(false);
        if (!ayudaUsadaJ1) {
            F51.setDisable(false);
        }
        if (!ayudaUsadaJ2) {
            F52.setDisable(false);
        }
    }

    private void colorPregunta() {

        switch (model.getTopicoActualNum()) {
            case 1:
                topicoLabel.setStyle("-fx-text-fill:  #2fff00");
                break;
            case 2:
                topicoLabel.setStyle("-fx-text-fill:  #ff0000");
                break;
            case 3:
                topicoLabel.setStyle("-fx-text-fill:  #000fff");
                break;
            case 4:
                topicoLabel.setStyle("-fx-text-fill: #ffdd00");
                break;
            case 5:
                topicoLabel.setStyle("-fx-text-fill: #bb00ff");
                break;
            case 6:
                topicoLabel.setStyle("-fx-text-fill:  #00ff7b");
                break;
            default:
                break;
        }
    }
}
