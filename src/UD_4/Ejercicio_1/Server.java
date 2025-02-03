package UD_4.Ejercicio_1;

/*
Ejercicio 3.1.-
Realiza un programa servidor TCP que acepte dos clientes. Muestra por cada
cliente conectado sus puertos local y remoto.
Crea también el programa cliente que se conecte a ese servidor. Muestra los
puertos locales y remotos a los que está conectado su socket, y la dirección IP de la
máquina remota a la que se conecta.
 */

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 12345;//Puerto del servidor

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor iniciado. Esperando conexiones...");

            //Aceptar dos clientes
            for (int i = 1; i <= 2; i++) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente " + i + " conectado:");
                System.out.println("  Puerto local: " + clientSocket.getLocalPort());
                System.out.println("  Puerto remoto: " + clientSocket.getPort());
            }//Fin for

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }//Fin try-catch
    }//Fin main
}//Fin clase Server