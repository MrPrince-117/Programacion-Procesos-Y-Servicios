package UD_4.Prueba;

import UD_4.Ejercicio_13.ManejadorCliente;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int puerto = 1234;
        System.out.println("Servidor iniciado. Esperando clientes en el puerto " + puerto + "...");
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            while (true) {
                // Aceptar conexiones de clientes
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado desde " + cliente.getInetAddress());

                // Crear un hilo para manejar al cliente
                Thread hiloCliente = new Thread(new ManejadorCliente(cliente));
                hiloCliente.start();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
