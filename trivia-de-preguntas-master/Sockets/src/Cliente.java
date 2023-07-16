import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {

    public static void main(String[] args) {

        final String HOST= "127.0.0.1";
        final int PUERTO = 5000;
        DataInputStream in;
        DataOutputStream out;

        try {
            Socket sc = new Socket(HOST, PUERTO);

            in = new DataInputStream(sc.getInputStream()); //recibir los mensajes del cliente
            out = new DataOutputStream(sc.getOutputStream()); //enviar

            out.writeUTF("Hola desde el cliente");

            String mensaje = in.readUTF();

            sc.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
