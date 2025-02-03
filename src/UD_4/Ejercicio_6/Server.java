package UD_4.Ejercicio_6;

import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {
        int PORT = 12345; // Puerto del servidor
        byte[] buffer = new byte[1024];
        int delay = 5000;

        try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
            System.out.println("Servidor iniciado. Esperando conexiones...");

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(receivePacket); // Espera un datagrama
                String word = new String(receivePacket.getData(), 0, receivePacket.getLength());

                if (word.equals("*")) {
                    System.out.println("El cliente ha cerrado la transmisión de palabras");
                    break;
                }//Fin if

                System.out.println("La palabra enviada por el cliente: " + word);

                String upperWord = word.toUpperCase();

                //Delay generado intencionalmente para forzar la excepcion de SocketTimeout de la clase Client
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }//Fin try-catch

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                DatagramPacket sendPacket = new DatagramPacket(
                        upperWord.getBytes(),
                        upperWord.length(),
                        clientAddress,
                        clientPort
                );
                //serverSocket.send(sendPacket);
            }//Fin while
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }//Fin try-catch
    }//FIn main
}//Fin clase Server
