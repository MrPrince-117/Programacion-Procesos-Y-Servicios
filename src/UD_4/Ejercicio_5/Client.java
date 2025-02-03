package UD_4.Ejercicio_5;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    public static String reciveWord(Socket socket) {
        String word = "";
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            word = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }//Fin try-catch
        return word;
    }//Fin metodo reciveWord

    public static void main(String[] args) {
        String serverIP = "127.0.0.1"; // Dirección IP del servidor
        int serverPORT = 12345; // Puerto del servidor
        String word;
        String response;
        Scanner sc = new Scanner(System.in);

        try (Socket socket = new Socket(serverIP, serverPORT)) {
            System.out.println("Conectado al servidor: " + socket.getRemoteSocketAddress());

            while (true) {
                System.out.println("Introduce palabras. Para terminar, introduce un asterisco (*)");
                word = sc.nextLine();
                try {
                    OutputStream outputStream = socket.getOutputStream();
                    PrintWriter writer = new PrintWriter(outputStream, true);
                    writer.println(word);
                } catch (IOException e) {
                    e.printStackTrace();
                }//Fin try-catch
                if (word.equals("*")) {
                    break;
                }//Fin if
                response = reciveWord(socket);
                System.out.println("El servidor devuelve: " + response);
            }//Fin while
        } catch (IOException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }//Fin try-catch
    }//Fin main
}//Fin clase Client