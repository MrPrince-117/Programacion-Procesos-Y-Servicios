package UD_4.Ejercicio_5;

import java.io.*;
import java.net.*;

public class Server {

    public static String reciveWords(Socket clientSocket) {
        String word = "";
        try {
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            word = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }//Fin try-catch
        return word;
    }//Fin metodo reciveWords

    public static void sendWords(Socket clientSocket, String word) {
        try {
            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println(word);
        } catch (IOException e) {
            e.printStackTrace();
        }//Fin try-catch
    }//Fin metodo sendWords

    public static void main(String[] args) {
        String word;
        int port = 12345;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor iniciado. Esperando conexiones...");
            Socket clientSocket = serverSocket.accept();

            while (true) {
                word = reciveWords(clientSocket);
                if (word.equals("*")) {
                    System.out.println("El cliente ha cerrado la transmision de palabras");
                    break;
                }//Fin if
                System.out.println("La palabra enviada por el cliente: " + word);
                sendWords(clientSocket, word);
            }//Fin while
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }//Fin try-catch
    }//Fin main
}//Fin clase Server
