import java.io.*;
import java.net.*;

public class Cliente implements Runnable {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(); // Crea un socket de datagrama para enviar el mensaje de broadcast
            socket.setBroadcast(true);

            byte[] buffer = "¿Hay un servidor disponible?".getBytes();
            InetAddress direccionBroadcast = InetAddress.getByName("255.255.255.255");
            DatagramPacket mensaje = new DatagramPacket(buffer, buffer.length, direccionBroadcast, 1234);
            socket.send(mensaje); // Envía el mensaje de broadcast

            byte[] respuestaBuffer = new byte[1024];
            DatagramPacket respuesta = new DatagramPacket(respuestaBuffer, respuestaBuffer.length);
            socket.receive(respuesta); // Espera la respuesta del servidor

            String direccionServidor = respuesta.getAddress().getHostAddress(); // Obtiene la dirección IP del servidor
            System.out.println("Servidor encontrado en la dirección " + direccionServidor);

            socket.close();

            Socket socketServidor = new Socket("127.0.0.1", 1234); // Conecta al servidor en la dirección IP obtenida
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socketServidor.getInputStream()));
            PrintWriter salida = new PrintWriter(socketServidor.getOutputStream(), true);

            salida.println("Hola, servidor!"); // Envía un mensaje al servidor

            String respuestaServidor = entrada.readLine(); // Lee la respuesta del servidor
            System.out.println("Respuesta recibida: " + respuestaServidor);

            socketServidor.close(); // Cierra la conexión con el servidor
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }
}


