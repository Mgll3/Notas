package Model;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Model {


    private static Topico topicoActual;
    private static LinkedList<Topico> topicos = new LinkedList<Topico>();
    private static LinkedList<String> frases = new LinkedList<String>();
    private static boolean ayuda = true;
    private static Jugador J1;
    private static Jugador J2;
    private static boolean isAleatorio = false;
    private static int topicoActualNum = 0;
    private AudioClip winFx;
    private AudioClip loseFx;
    private static MediaPlayer mediaPlayer;
    private static LinkedList<String[]> puntajes;
    private Stage stageSolo;
    private Stage stageMulti;
    public Model() {

        agregarTopicos();
        agregarFrases();
    }

    public Stage getStageSolo() {
        return stageSolo;
    }

    public void leerPuntajes(){
        this.puntajes = Load.loadPuntajes("Resources/puntajes.txt",1);
    }

    public void setStageSolo(Stage stageSolo) {
        this.stageSolo = stageSolo;
    }

    public Stage getStageMulti() {
        return stageMulti;
    }

    public void setStageMulti(Stage stageMulti) {
        this.stageMulti = stageMulti;
    }

    public static LinkedList<String[]> getPuntajes() {
        return puntajes;
    }

    public static void winSound(){
        String s = "Resources/winFx.wav";
        Media h =  new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }

    public static void loseSound(){
        String s = "Resources/loseFx.wav";
        Media h =  new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }




    public static int getTopicoActualNum() {
        return topicoActualNum;
    }

    public static void setTopicoActualNum(int topicoActualNum) {
        Model.topicoActualNum = topicoActualNum;
    }

    public static Jugador getJ2() {
        return J2;
    }

    public static void setJ2(Jugador j2) {
        J2 = j2;
    }

    public static Jugador getJ1() {
        return J1;
    }

    public static void setJ1(Jugador j1) {
        J1 = j1;
    }

    public static boolean isAleatorio() {
        return isAleatorio;
    }

    public static void setAleatorio(boolean aleatorio) {
        isAleatorio = aleatorio;
    }


    public static Topico getTopicoActual() {
        return topicoActual;
    }

    public static void setTopicoActual(Topico topicoActual) {
        Model.topicoActual = topicoActual;
    }

    public static LinkedList<Topico> getTopicos() {
        return topicos;
    }


    private static LinkedList<Model.Pregunta> shufflePreguntas(LinkedList<Model.Pregunta> preguntaTopico) {
        LinkedList<Model.Pregunta> aux = preguntaTopico;
        Collections.shuffle(aux);
        return aux;
    }

    public static Topico elegirTopicoAleatorio() {  //entrega un topico random entre 1 a 6
        int aux = topicoActualNum;
        while (aux == topicoActualNum) {
            aux = (int) Math.floor(Math.random() * 6);
        }
        Topico aleatorio = topicos.get(aux);
        setTopicoActualNum(aux);
        //System.out.println(aux);
        return aleatorio;
    }
    public static String elegirFraseAleatoria() {  //entrega un topico random entre 1 a 6
        int aux = topicoActualNum;
        while (aux == topicoActualNum) {
            aux = (int) Math.floor(Math.random() * 16);
        }
        String aleatorio = frases.get(aux);
        setTopicoActualNum(aux);

        return aleatorio;
    }

    static private void agregarFrases(){
        frases.add("A veces se gana, a veces se pierde, pero siempre se Aprende, sigue adelante!");
        frases.add("Mas suerte a la próxima, igual sigue esforzándote, a la proxima aciertas!");
        frases.add("El genio se hace con un 1% de talento, y un 99% de trabajo, sigue adelante!");
        frases.add("Para poder seguir a veces hay que empezar de nuevo, sigue adelante!");
        frases.add("El camino al éxito es la actitud, animate!");
        frases.add("Lo único imposible es aquello que no intentas, Vamos!");
        frases.add("No se fracasa hasta que se deja de intentar, Vamos!");
        frases.add("Si crees que puedes, ya estás a medio camino, a la proxima aciertas!");
        frases.add("Cuando existen ganas todo es posible, Vamos! ");
        frases.add("Cree en ti y todo será posible, animate! ");
        frases.add("El fracaso es éxito si aprendemos de él, a la proxima aciertas! ");
        frases.add("Cuanto más hacemos, más podemos hacer, a la proxima aciertas! ");
        frases.add("La buena fortuna favorece a los osados, animate! ");
        frases.add("La victoria no lo es todo: ¡Es lo único! ");
        frases.add("cáete 7 veces y levántate 8, animate! ");
        frases.add("Cuanto más difícil es la victoria, mayor es la felicidad de ganar, animate! ");
    }
    static private void agregarTopicos() {       //agrega toda la informacion a los topicos
        Topico topico1 = new Topico("Geografía",1);
        Topico topico2 = new Topico("Matematicas",2);
        Topico topico3 = new Topico("Ciencia",3);
        Topico topico4 = new Topico("Historia",4);
        Topico topico5 = new Topico("Latín",5);
        Topico topico6 = new Topico("Filosofía",6);
        LinkedList<Model.Pregunta> preguntaGeo = Load.loadQuestions("Resources/preguntasGeo.txt", 13,topico1.getNombreTopico(),topico1.getTopiconum());
        LinkedList<Model.Pregunta> preguntaMate = Load.loadQuestions("Resources/preguntasMate.txt", 10,topico2.getNombreTopico(),topico2.getTopiconum());
        LinkedList<Model.Pregunta> preguntasciencia = Load.loadQuestions("Resources/preguntasCiencia.txt", 10,topico3.getNombreTopico(),topico3.getTopiconum());
        LinkedList<Model.Pregunta> preguntashistoria = Load.loadQuestions("Resources/preguntasHistoria.txt", 10,topico4.getNombreTopico(),topico4.getTopiconum());
        LinkedList<Model.Pregunta> preguntaLatin = Load.loadQuestions("Resources/preguntasLatin.txt", 17,topico5.getNombreTopico(),topico5.getTopiconum());
        LinkedList<Model.Pregunta> preguntaFilo = Load.loadQuestions("Resources/preguntasFilo.txt", 13,topico6.getNombreTopico(),topico6.getTopiconum());
        topico1.setConjuntoDePreguntas(shufflePreguntas(preguntaGeo));
        topico2.setConjuntoDePreguntas(shufflePreguntas(preguntaMate));
        topico3.setConjuntoDePreguntas(shufflePreguntas(preguntasciencia));
        topico4.setConjuntoDePreguntas(shufflePreguntas(preguntashistoria));
        topico5.setConjuntoDePreguntas(shufflePreguntas(preguntaLatin));
        topico6.setConjuntoDePreguntas(shufflePreguntas(preguntaFilo));
        getTopicos().add(topico1);
        getTopicos().add(topico2);
        getTopicos().add(topico3);
        getTopicos().add(topico4);
        getTopicos().add(topico5);
        getTopicos().add(topico6);
    }

    public static class Load {

        /**
         * Carga de archivos
         */


        public static String loadFileAsString(String path) {
            StringBuilder builder = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(path));
                String line;
                while ((line = br.readLine()) != null) {
                    builder.append(line + "\n");
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
            return builder.toString();
        }

        public static LinkedList<Model.Pregunta> loadQuestions(String path, int numPreguntas, String topic, int topicNum) {
            String file = loadFileAsString(path);
            String[] tokens = file.split("\\|");
            LinkedList<Model.Pregunta> aux = new LinkedList<>();
            for (int j = 0; j < numPreguntas * 5; j += 5) {
                aux.add(new Model.Pregunta(tokens[j], tokens[j + 1], tokens[j + 2], tokens[j + 3], tokens[j + 4],topic,topicNum));
            }
            return aux;
        }

        public static LinkedList<String[]> loadPuntajes(String path, int numPuntajes) {
            String file = loadFileAsString(path);
            String[] tokens = file.split("\\|");
            LinkedList<String[]> aux = new LinkedList<String[]>();
            for (int j = 0; j < numPuntajes * 3; j += 3) {
                String[] auxStr = new String[3];
                auxStr[0] = tokens[j];auxStr[1] = tokens[j+1];auxStr[2] = tokens[j+2];
                aux.add(auxStr);
            }
            return aux;
        }
    }


    public static class Timer extends AnchorPane {
        private Label label;

        private Timeline anim;
        private int tmp = 4;
        private String S = "";

        public Timer(Label label) {
            this.label = label;
            anim = new Timeline(new KeyFrame(Duration.seconds(1), e -> timeLabel()));
            anim.setCycleCount(Timeline.INDEFINITE);
            anim.play();
        }

        private void timeLabel() {
            if (tmp > 0) {
                tmp--;
            } else {
                anim.stop();
                return;
            }
            S = tmp + "";
            label.setText(S);
        }
    }

    public static class Pregunta {
        String pregunta = "";
        String respuesta1 = "";
        String respuesta2 = "";
        String respuesta3 = "";
        String respuesta4 = "";
        String topico = "";
        Integer Topiconum;

        public Integer getTopiconum() {
            return Topiconum;
        }

        public void setTopiconum(Integer topiconum) {
            Topiconum = topiconum;
        }

        public Pregunta(String pregunta, String respuesta1, String respuesta2, String respuesta3, String respuesta4, String topico, Integer topicoNum) {
            this.pregunta = pregunta;
            this.respuesta1 = respuesta1;
            this.respuesta2 = respuesta2;
            this.respuesta3 = respuesta3;
            this.respuesta4 = respuesta4;
            this.topico = topico;
            this.Topiconum = topicoNum;
        }

        public String getTopico() {
            return topico;
        }

        public String getPregunta() {
            return pregunta;
        }


        public String getRespuesta1() {
            return respuesta1;
        }


        public String getRespuesta2() {
            return respuesta2;
        }


        public String getRespuesta3() {
            return respuesta3;
        }


        public String getRespuesta4() {
            return respuesta4;
        }

    }

    public static class Jugador {
        String nombre = "";
        int puntaje = 0;
        int tiempoAcumulado = 0;

        public Jugador(String nombre, int puntaje, int tiempoAcumulado) {
            this.nombre = nombre;
            this.puntaje = puntaje;
            this.tiempoAcumulado = tiempoAcumulado;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public int getPuntaje() {
            return puntaje;
        }

        public void setPuntaje(int puntaje) {
            this.puntaje = puntaje;
        }

        public int getTiempoAcumulado() {
            return tiempoAcumulado;
        }

        public void setTiempoAcumulado(int tiempoAcumulado) {
            this.tiempoAcumulado = tiempoAcumulado;
        }
    }

    public static class Topico {

        String nombreTopico = "";
        Integer topiconum;
        LinkedList<Model.Pregunta> conjuntoDePreguntas = new LinkedList<Model.Pregunta>();

        public Topico(String nombreTopico,Integer topiconum) {
            this.nombreTopico = nombreTopico;
            this.topiconum = topiconum;
        }

        public Integer getTopiconum() {
            return topiconum;
        }

        public String getNombreTopico() {
            return nombreTopico;
        }

        public void setNombreTopico(String nombreTopico) {
            this.nombreTopico = nombreTopico;
        }

        public LinkedList<Model.Pregunta> getConjuntoDePreguntas() {
            return conjuntoDePreguntas;
        }

        public void setConjuntoDePreguntas(LinkedList<Model.Pregunta> conjuntoDePreguntas) {
            this.conjuntoDePreguntas = conjuntoDePreguntas;
        }

        public Model.Pregunta entregarPregunta(int d) {  // metodo que entrega la pregunta en la posicion d

            return this.getConjuntoDePreguntas().get(d);
        }


    }
}
