package UD_3;
import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        try {
            // Conectar al servidor en la IP local y puerto 1234
            Socket socket = new Socket("192.168.30.20", 1234);
            System.out.println("Conectado al servidor.");

            // Streams para enviar y recibir datos
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Hilo para recibir mensajes del servidor
            Thread recibirMensajes = new Thread(() -> {
                try {
                    String mensajeServidor;
                    while ((mensajeServidor = serverInput.readLine()) != null) {
                        System.out.println(mensajeServidor); // Imprime mensaje de otros clientes
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            recibirMensajes.start();

            // Leer y enviar mensajes desde la consola
            String mensaje;
            while ((mensaje = input.readLine()) != null) {
                output.println(mensaje); // Enviar mensaje al servidor
            }

            // Cerrar conexión
            socket.close();
            System.out.println("Conexión cerrada.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
