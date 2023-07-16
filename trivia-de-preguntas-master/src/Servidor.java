import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor implements Runnable{

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(5000);
            Socket sc;

            System.out.println("Servidor iniciado");
            while (true) {
                try{
                    // Espero la conexion del cliente
                    sc = server.accept();

                    DataInputStream in = new DataInputStream(sc.getInputStream());
                    DataOutputStream out = new DataOutputStream(sc.getOutputStream());

                    // Pido al cliente el nombre al cliente
                    out.writeUTF("Indica tu nombre");
                    String nombreCliente = in.readUTF();
                    System.out.println("Creada la conexion con el cliente " + nombreCliente);

                    if (nombreCliente.contains("envia")){
                        String mensaje = "hola";
                        out.writeUTF(mensaje);
                        System.out.println("hola");
                    }

                    // Inicio el hilo
                    ServidorHilo hilo = new ServidorHilo(in, out, nombreCliente);
                    hilo.start();



                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
