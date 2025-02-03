package UD_4.Ejercicio_6;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        String serverHost = "localhost";
        int PORT = 12345;
        byte[] buffer = new byte[1024];
        int timeout = 4000;

        try (DatagramSocket clientSocket = new DatagramSocket()) {
            Scanner sc = new Scanner(System.in);
            clientSocket.setSoTimeout(timeout);
            System.out.println("Conectado al servidor: " + clientSocket.getRemoteSocketAddress());

            while (true) {
                System.out.println("Introduce palabras. Para terminar, introduce un asterisco (*)");
                String word = sc.nextLine();

                // Enviar palabra al servidor
                DatagramPacket sendPacket = new DatagramPacket(
                        word.getBytes(),
                        word.length(),
                        InetAddress.getByName(serverHost),
                        PORT
                );
                clientSocket.send(sendPacket);

                // Terminar si se envía "*"
                if (word.equals("*")) {
                    break;
                }//FIn if

                // Recibir respuesta del servidor
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                try {
                    clientSocket.receive(receivePacket);
                    String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("El servidor devuelve " + response);
                } catch (SocketTimeoutException e) {
                    System.err.println("Tiempo de espera agotado, el paquete se ha perdido");
                }//Fin try-catch
            }//Fin while
        } catch (IOException e) {
            System.err.println("Error de conexion: " + e.getMessage());
        }//Fin try-catch
    }//Fin main
}//Fin clase Client
