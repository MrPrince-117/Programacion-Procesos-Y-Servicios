package UD_4.Avivi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCPConcurrente {
    public static void main(String[] args) {
        int port = 1234;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor TCP escuchando en el puerto " + port);

            while (true) {
                Socket socketServicio = serverSocket.accept();
                System.out.println("Cliente conectado desde " + socketServicio.getInetAddress());
                AdivinadorTCP hilo = new AdivinadorTCP(socketServicio);
                hilo.start();
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor en el puerto " + port);
        }
    }
}

