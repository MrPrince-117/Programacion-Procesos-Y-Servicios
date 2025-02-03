package UD_4.Ejercicio_3;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    
    public static void sendMessage(int message, Socket socket) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }//Fin try-catch
    }//Fin metodo sendMessage

    public static void reciveMessage(Socket socket){
        // Recibir mensaje del servidor
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String serverMessage = reader.readLine();
            System.out.println("Mensaje del servidor: " + serverMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }//Fin try-catch
    }//Fin metodo reciveMessage

    public static void main(String[] args) {
        String serverIP = "127.0.0.1"; // Dirección IP del servidor
        int serverPORT = 12345; // Puerto del servidor

        try (Socket socket = new Socket(serverIP, serverPORT)) {
            System.out.println("Conectado al servidor: " + socket.getRemoteSocketAddress());

            while (true) {
                try{
                    //Introduce un número y se lo envia al servidor
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Indique un numero entero");
                    int num = sc.nextInt();
                    sendMessage(num, socket);
                    break;
                }catch (InputMismatchException e){
                    System.err.println("Escriba un numero entero, evite los decimales y los caracteres de texto");;
                }//Fin try-catch
            }//Fin while

            //Recibe el mensaje del servidor
            reciveMessage(socket);

        } catch (IOException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }//Fin try-catch
    }//Fin main
}//Fin clase Client
