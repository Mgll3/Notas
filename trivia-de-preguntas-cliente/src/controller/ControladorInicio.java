package controller;

import Cliente.ClienteHilo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Model.Model;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static controller.ControladorDesarrollo.agregarEspacios;
import static controller.ControladorDesarrollo.ordinalIndexOf;

public class ControladorInicio{


    @FXML
    public TextField J1;

    final int maxLength = 10;

    public TextField getJ1() {
        return J1;
    }

    ClienteHilo hilo = new ClienteHilo();
    @FXML
    private void initialize() {
        J1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (J1.getText().length() > maxLength) {
                    String s = J1.getText().substring(0, maxLength);
                    J1.setText(s);
                }
            }
        });
    }

    Model model = new Model();

    public void btnEmpezarJuego(javafx.event.ActionEvent actionEvent) throws IOException {

        if (J1.getText().equals(null) || J1.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Pongase un nombre, por favor.");
        } else {



            model.setJ1(new Model.Jugador(J1.getText(), 0, 0));
            irPantallaFinal();
            /*
            model.setJ1(new Model.Jugador(J1.getText(), 0, 0));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ElegirModo.fxml"));
            Parent root = loader.load();
            ControladorElegirModo controlador = loader.getController();
            Scene ElegirModo = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(ElegirModo);
            stage.setResizable(false);
            stage.show();

             */

        }
    }

    public void irPantallaFinal() throws IOException {

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

    public void escribirPuntajes() {
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
    public void checkLetters() {
        Pattern pattern = Pattern.compile("[a-zA-Z]*");
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (pattern.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        };
        TextFormatter<String> formatter = new TextFormatter<>(filter);
        J1.setTextFormatter(formatter);
    }


}


