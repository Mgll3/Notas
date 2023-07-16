package Cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ClienteHilo extends Thread {

    private DataInputStream in;
    private DataOutputStream out;

    public ClienteHilo(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
    }

    public ClienteHilo() {

    }

    @Override
    public void run() {
        try {
            String mensaje = in.readUTF();
            System.out.println(mensaje);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
