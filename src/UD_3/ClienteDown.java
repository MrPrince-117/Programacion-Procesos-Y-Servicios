package UD_3;


import java.io.*;
        import java.net.*;

public class ClienteDown {
    public static void main(String[] args) {
        try (Socket socket = new Socket("192.168.30.20", 1234);
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Conectado al servidor.");

            // Enviar el comando "shutdown" al servidor
            output.println("shutdown");

            // Leer la respuesta del servidor
            String respuesta = serverInput.readLine();
            System.out.println("Respuesta del servidor: " + respuesta);

            System.out.println("Comando de apagado enviado.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
