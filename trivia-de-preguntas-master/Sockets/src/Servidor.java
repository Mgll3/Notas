import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;

public class Servidor {
    public static void main(String[] args) {

        ServerSocket servidor = null;
        Socket sc = null;
        DataInputStream in;
        DataOutputStream out;

        final int PUERTO = 5000;

        try {
            servidor = new ServerSocket(PUERTO);
            System.out.println("Servidor Iniciado");
            while(true){

                sc = servidor.accept(); //socket del cliente

                in = new DataInputStream(sc.getInputStream()); //recibir los mensajes del cliente
                out = new DataOutputStream(sc.getOutputStream()); //enviar

                String mensaje = in.readUTF(); //a la espera de leer un mensaje que envien
                System.out.println(mensaje);

                out.writeUTF("Hola mundo desde el servidor");

                sc.close();
                System.out.println("Cliente desconectado");

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}