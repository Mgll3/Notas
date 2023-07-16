package controller;

import Model.Model;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.processing.Filer;
import javax.sound.midi.Soundbank;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class ControladorDesarrollo extends ControladorElegirModo {

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
    public Button F5;
    @FXML
    public Label puntajeJ1;
    @FXML
    public Label erradas;
    @FXML
    public Label nombreJ1;
    @FXML
    public Label pregunta;
    @FXML
    public Label topicoLabel;
    @FXML
    public VBox BoxButtons;
    @FXML
    public Label timerLabel;

    public Model.Timer timer;
    public int tiempoCount;


    private Integer puntajeCount = 0;
    private Integer incorrectas = 0;
    private Integer preguntaActual = -1;
    private Integer rondas;

    private Stack<Model.Pregunta> preguntaStack = new Stack<>();

    private boolean ayudaUsada = false;
    private boolean isDone = false;


    @FXML
    public void initialize() throws IOException {


        Timer tiempoEmpleado = new Timer();
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                tiempoCount++;
            }
        };
        tiempoEmpleado.schedule(tarea, 0, 1000);

        model.getJ1().setPuntaje(0);
        model.getJ1().setTiempoAcumulado(0);
        pregunta.setTextAlignment(TextAlignment.CENTER);
        pregunta.setMaxWidth(500);
        if (model.isAleatorio()) {
            rondas = 20;
            generarBancoAleatorio();
        } else {
            rondas = 10;
        }
        timerLabel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (timerLabel.getText().equals("0")) {
                    resetContenedores();
                    try {
                        pasarPregunta();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        String nameAux = model.getJ1().getNombre().toLowerCase();
        nombreJ1.setText(nameAux.substring(0, 1).toUpperCase() + nameAux.substring(1));
        pasarPregunta();


    }

    public boolean isDone() {
        return isDone;
    }

    private void shuffleButtons() {
        ObservableList<Node> workingCollection = FXCollections.observableArrayList(BoxButtons.getChildren());
        Collections.shuffle(workingCollection);
        BoxButtons.getChildren().setAll(workingCollection);
    }

    public void pasarPregunta() throws IOException {

        shuffleButtons();
        preguntaActual++;
        if (preguntaActual < rondas) {
            if (model.isAleatorio()) {
                pregunta.setText(preguntaStack.peek().getPregunta());
                F1.setText(preguntaStack.peek().getRespuesta1());
                F2.setText(preguntaStack.peek().getRespuesta2());
                F3.setText(preguntaStack.peek().getRespuesta3());
                F4.setText(preguntaStack.peek().getRespuesta4());
                topicoLabel.setText(preguntaStack.peek().getTopico());
                model.setTopicoActualNum(preguntaStack.pop().getTopiconum());
            } else {
                pregunta.setText(model.getTopicoActual().getConjuntoDePreguntas().get(preguntaActual).getPregunta());
                F1.setText(model.getTopicoActual().getConjuntoDePreguntas().get(preguntaActual).getRespuesta1());
                F2.setText(model.getTopicoActual().getConjuntoDePreguntas().get(preguntaActual).getRespuesta2());
                F3.setText(model.getTopicoActual().getConjuntoDePreguntas().get(preguntaActual).getRespuesta3());
                F4.setText(model.getTopicoActual().getConjuntoDePreguntas().get(preguntaActual).getRespuesta4());
                topicoLabel.setText(model.getTopicoActual().getNombreTopico());
            }
        } else {
            isDone = true;
            model.getJ1().setTiempoAcumulado(tiempoCount - ((rondas + 1) * 4));
            model.getJ1().setPuntaje(puntajeCount);
            irPantallaFinal();
        }
        colorPregunta();
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
        BoxButtons.setDisable(true);
        F5.setDisable(true);
    }


    public void btnF1(ActionEvent actionEvent) throws IOException {
        puntajeCount++;
        paintButtons();
        puntajeJ1.setText("Correctas: " + puntajeCount.toString());
        model.winSound();
        temporizador();

    }

    public void btnF2(ActionEvent actionEvent) {

        paintButtons();
        incorrectas++;
        erradas.setText("Erradas: " + incorrectas.toString());
        model.loseSound();
        temporizador();

    }

    public void btnF3(ActionEvent actionEvent) {
        paintButtons();
        incorrectas++;
        erradas.setText("Erradas: " + incorrectas.toString());
        model.loseSound();
        temporizador();
    }

    public void btnF4(ActionEvent actionEvent) {
        paintButtons();
        incorrectas++;
        erradas.setText("Erradas: " + incorrectas.toString());
        model.loseSound();
        temporizador();
    }

    public void btnF5(ActionEvent actionEvent) {
        F2.setVisible(false);
        F3.setVisible(false);
        F5.setDisable(true);
        ayudaUsada = true;

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
        BoxButtons.setDisable(false);
        if (!ayudaUsada) {
            F5.setDisable(false);
        }
    }

    private void colorPregunta() {

        switch (model.getTopicoActualNum()) {
            case 1:
                topicoLabel.setStyle("-fx-text-fill: #39b11f");
                break;
            case 2:
                topicoLabel.setStyle("-fx-text-fill:  #ff0000");
                break;
            case 3:
                topicoLabel.setStyle("-fx-text-fill: #000fff");
                break;
            case 4:
                topicoLabel.setStyle("-fx-text-fill: #b79f00");
                break;
            case 5:
                topicoLabel.setStyle("-fx-text-fill: #bb00ff");
                break;
            case 6:
                topicoLabel.setStyle("-fx-text-fill: #00ff7b");
                break;
            default:
                break;
        }
    }

    private void irPantallaFinal() throws IOException {
        escribirPuntajes();
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

    private String agregarEspacios(String nombre) {
        int l = 10 - nombre.length();
        System.out.println(nombre.length());
        System.out.println(l);
        String aux = nombre;
        int i = 0;
        while (i < l) {
            aux = aux + " ";
            i++;
        }
        return aux;
    }


    private void escribirPuntajes() {
        try {
            String data = (agregarEspacios(model.getJ1().getNombre())) + "|" + model.getJ1().getPuntaje() + "|" + model.getJ1().getTiempoAcumulado() + " segundos|";
            File f1 = new File("Resources/puntajes.txt");
            String aux = Model.Load.loadFileAsString("Resources/puntajes.txt");
            String lineToRemove = aux.substring(ordinalIndexOf(aux, "|", 3) + 1);
            lineToRemove = lineToRemove + data;
            FileWriter fileWritter = new FileWriter(f1);
            BufferedWriter bw = new BufferedWriter(fileWritter);
            bw.write(lineToRemove);
            bw.flush();
            bw.close();
            model.leerPuntajes();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }

}
