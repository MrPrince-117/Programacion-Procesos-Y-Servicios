package UD_4.Ejercicio_3;

import java.io.*;
import java.net.*;

public class Server {

    public static void sendMessage(int message, Socket clientSocket){
        int send = message*message;
        try {
            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println(send);
        } catch (IOException e) {
            e.printStackTrace();
        }//Fin try-catch
    }//Fin metodo sendMessage

    public static int reciveMessage(Socket clientSocket) {
        int clientMessage = 0;
        try {
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            clientMessage = Integer.parseInt(reader.readLine());
            System.out.println("Número del cliente: " + clientMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }//Fin try-catch
        return clientMessage;
    }//Fin metodo reciveMessage

    public static void main(String[] args) {
        int port = 12345;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor iniciado. Esperando conexiones...");
            Socket clientSocket = serverSocket.accept();
            int clientMessage = reciveMessage(clientSocket);
            sendMessage(clientMessage, clientSocket);
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }//Fin try-catch
    }//Fin main
}//Fin clase Server
