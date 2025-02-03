package UD_4.Ejercicio_2;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String serverIP = "127.0.0.1"; // Dirección IP del servidor
        int serverPORT = 12345; // Puerto del servidor

        try (Socket socket = new Socket(serverIP, serverPORT)) {
            System.out.println("Conectado al servidor: " + socket.getRemoteSocketAddress());

            // Recibir mensaje del servidor
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String serverMessage = reader.readLine();
            System.out.println("Mensaje del servidor: " + serverMessage);

            //Enviar mensaje al servidor
            String clientMessage = serverMessage.toLowerCase();//Convertimos los caracteres de la cadena a minuscula
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println(clientMessage);

        } catch (IOException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }//Fin try-catch
    }//Fn main
}//Fin clase Client
