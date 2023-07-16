package Cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente implements Runnable {

    @Override
    public void run() {
        try {
            Scanner sn = new Scanner(System.in);
            sn.useDelimiter("\n");

            Socket sc = new Socket("127.0.0.1", 5000);
            DataInputStream in = new DataInputStream(sc.getInputStream());
            DataOutputStream out = new DataOutputStream(sc.getOutputStream());
            // Escribe el nombre y se lo manda al servidor
            String nombre = "Cliente";
            out.writeUTF(nombre);

            while (true) {
                // Leer mensaje del servidor
                try{
                    String mensaje = in.readUTF();
                    System.out.println(mensaje);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            /*
            // ejecutamos el hilo
            ClienteHilo hilo = new ClienteHilo(in, out);
            hilo.start();
            hilo.join();

             */


        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
