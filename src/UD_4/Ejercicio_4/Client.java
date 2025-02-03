package UD_4.Ejercicio_4;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String[] args) {
         String serverIP = "127.0.0.1";//Dirección IP del servidor
         int serverPORT = 12345;//Puerto del servidor
        int numClientes;

        while (true) {
            try{
                System.out.println("Indique el numero de clientes");
                Scanner sc = new Scanner(System.in);
                numClientes = sc.nextInt();
                break;
            }catch (InputMismatchException e){
                System.err.println("Escriba un numero entero, evite los decimales y los caracteres de texto");;
            }//Fin try-catch
        }//Fin while


        try {
            for (int i = 0; i < numClientes; i++) {
                Thread cliente = new Thread(() -> connectToServer(serverIP, serverPORT));
                cliente.start();
                cliente.join();
            }
        } catch (InterruptedException e) {
            System.err.println("Error al esperar la finalización de los hilos: " + e.getMessage());
        }//Fin try-catch
    }//Fin main

    private static void connectToServer(String serverIp, int serverPort) {
        try (Socket socket = new Socket(serverIp, serverPort)) {
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String serverMessage = reader.readLine();
            System.out.println(serverMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }//Fin try-catch
    }//Fin metodo connectToServer
}//Fin clase Client
