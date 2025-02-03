package UD_4.Ejercicio_2;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 12345;//Puerto del servidor

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor iniciado. Esperando conexiones...");

            for (int i = 0; i < 1; i++) {
                Socket clientSocket = serverSocket.accept();

                //Enviar mensaje al cliente
                OutputStream outputStream = clientSocket.getOutputStream();
                PrintWriter writer = new PrintWriter(outputStream, true);
                writer.println("PRUEBA");

                //Recibir mensaje del cliente
                InputStream inputStream = clientSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String clientMessage = reader.readLine();
                System.out.println("Mensaje del cliente: " + clientMessage);
            }//FIn for

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }//Fin try-catch
    }//Fin main
}//Fin clase Server
